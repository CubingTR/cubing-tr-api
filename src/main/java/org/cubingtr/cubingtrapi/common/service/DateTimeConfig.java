package org.cubingtr.cubingtrapi.common.service;

import org.springframework.scheduling.support.CronExpression;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.List;

public class DateTimeConfig {
	public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	public static final DateTimeFormatter dateTimeFormatterWithoutT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static final DateTimeFormatter dateTimeWithZoneFormatterWithMilliSecond = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSZZZZZ");
	public static final DateTimeFormatter dateTimeWithZoneFormatterWithMilliSecondWithoutT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSZZZZZ");

	public static final DateTimeFormatter dateTimeWithZoneFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
	public static final DateTimeFormatter dateTimeWithZoneFormatterWithoutT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssZZZZZ");

	public static final DateTimeFormatter dateTimeFormatterArcGis = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static final DateTimeFormatter dateTimeFormatterReadable = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private static final DateTimeFormatter dateTimeFormatterPIServer;

	static {
		dateTimeFormatterPIServer = new DateTimeFormatterBuilder()
				.parseCaseInsensitive()
				.append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
				.appendLiteral('Z')
				.toFormatter();
	}

	private static final List<DateTimeFormatter> dateTimeFormatterList = Arrays.asList(
			DateTimeFormatter.ISO_DATE_TIME,
			dateTimeFormatter,
			dateTimeFormatterWithoutT,
			dateTimeFormatterArcGis,
			dateTimeFormatterPIServer
	);

	private static final List<DateTimeFormatter> dateTimeFormatterWithZoneList = Arrays.asList(
			dateTimeWithZoneFormatter,
			dateTimeWithZoneFormatterWithoutT,
			dateTimeWithZoneFormatterWithMilliSecond,
			dateTimeWithZoneFormatterWithMilliSecondWithoutT
	);


	public static String convertDateTimeValueToDateTimeStringAtSystemZoneOffset(Object dateTimeValue, String dateTimeFormat) {
		DateTimeFormatter parsedDateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
		return convertDateTimeValueToDateTimeStringAtSystemZoneOffset(dateTimeValue, parsedDateTimeFormatter);
	}

	public static String convertDateTimeValueToDateTimeStringAtSystemZoneOffset(Object dateTimeValue, DateTimeFormatter prmDateTimeFormatter) {
		if (dateTimeValue == null) {
			return null;
		}

		Instant dateTimeValueAsInstant;
		if (dateTimeValue instanceof Number) {
			long epochMilli = ((Number) dateTimeValue).longValue();
			dateTimeValueAsInstant = Instant.ofEpochMilli(epochMilli);
		} else if (dateTimeValue instanceof String) {
			dateTimeValueAsInstant = convertDateTimeStrToInstant((String) dateTimeValue);
		} else if (dateTimeValue instanceof LocalDateTime) {
			dateTimeValueAsInstant = convertLocalDateTimeToInstant((LocalDateTime) dateTimeValue);
		} else {
			throw new IllegalArgumentException("unsupported formatted date: " + dateTimeValue + " type: " + dateTimeValue.getClass().getName());
		}

		ZonedDateTime zonedDateTimeFromEpochMilli = dateTimeValueAsInstant.atZone(ZoneId.systemDefault());
		return prmDateTimeFormatter.format(zonedDateTimeFromEpochMilli);
	}

	public static String convertDateTimeValueAtUTCToDateTimeStringAtSystemZoneOffset(Object dateTimeValue, String dateTimeFormat) {
		DateTimeFormatter parsedDateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
		return convertDateTimeValueAtUTCToDateTimeStringAtSystemZoneOffset(dateTimeValue, parsedDateTimeFormatter);
	}

	public static String convertDateTimeValueAtUTCToDateTimeStringAtSystemZoneOffset(Object dateTimeValue, DateTimeFormatter prmDateTimeFormatter) {
		if (dateTimeValue == null) {
			return null;
		}

		Instant dateTimeValueAsInstant;
		if (dateTimeValue instanceof Number) {
			long epochMilli = ((Number) dateTimeValue).longValue();
			dateTimeValueAsInstant = Instant.ofEpochMilli(epochMilli);
		} else if (dateTimeValue instanceof String) {
			dateTimeValueAsInstant = convertDateTimeStrToInstant((String) dateTimeValue, true);
		} else if (dateTimeValue instanceof LocalDateTime) {
			dateTimeValueAsInstant = convertLocalDateTimeAtUTCToInstant((LocalDateTime) dateTimeValue);
		} else {
			throw new IllegalArgumentException("unsupported formatted date: " + dateTimeValue + " type: " + dateTimeValue.getClass().getName());
		}

		ZonedDateTime zonedDateTimeFromEpochMilli = dateTimeValueAsInstant.atZone(ZoneId.systemDefault());
		return prmDateTimeFormatter.format(zonedDateTimeFromEpochMilli);
	}

	public static Long convertDateTimeStrToInstantEpochMilli(String dateTimeStr) {
		if (dateTimeStr == null) {
			return null;
		}

		return convertDateTimeStrToInstant(dateTimeStr).toEpochMilli();
	}

	public static Instant convertDateTimeStrToInstant(String dateTimeStr) {
		return convertDateTimeStrToInstant(dateTimeStr, false);
	}

	public static Instant convertDateTimeStrToInstant(String dateTimeStr, boolean isAtUTC) {
		if (dateTimeStr == null) {
			return null;
		}

		Instant result = null;

		for (DateTimeFormatter timeFormatter : dateTimeFormatterWithZoneList) {
			try {

				OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateTimeStr, timeFormatter);
				result = offsetDateTime.toInstant();

			} catch (Exception ex) {
				// this might be ok, will throw if can not parse in the next try
			}
		}

		if (result == null) {
			for (DateTimeFormatter timeFormatter : dateTimeFormatterList) {
				try {

					LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr, timeFormatter);
					if (isAtUTC) {
						result = convertLocalDateTimeAtUTCToInstant(localDateTime);
					} else {
						result = convertLocalDateTimeToInstant(localDateTime);
					}

				} catch (Exception ex) {
					// this might be ok, will throw if can not parse in the next try
				}
			}
		}

		return result;
	}

	public static LocalDate convertDateStrToLocalDate(String dateStr) {
		if (dateStr == null) {
			return null;
		}

		return LocalDate.parse(dateStr, dateFormatter);
	}

	public static LocalDateTime convertDateTimeStrToLocalDateTime(String dateTimeStr) {
		if (dateTimeStr == null) {
			return null;
		}

		for (DateTimeFormatter timeFormatter : dateTimeFormatterList) {
			try {
				return LocalDateTime.parse(dateTimeStr, timeFormatter);
			} catch (Exception ex) {
				// this might be ok, will throw if can not parse in the next try
			}
		}
		throw new IllegalArgumentException("Unsupported formatted date : " + dateTimeStr);
	}

	private static Instant convertLocalDateTimeToInstant(LocalDateTime localDateTime) {
		// convert LocalDateTime which is at system default zone to Instant
		return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
	}

	private static Instant convertLocalDateTimeAtUTCToInstant(LocalDateTime localDateTimeAtUTC) {
		return localDateTimeAtUTC.toInstant(ZoneOffset.UTC);
	}

	public static LocalDateTime nowInUTC() {
		return LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
	}

	public static OffsetDateTime toUTCOffsetDateTime(OffsetDateTime offsetDateTime) {
		return offsetDateTime.withOffsetSameInstant(ZoneOffset.UTC);
	}

	public static String convertLocalDateTimeToSystemLocalString(LocalDateTime localDateTime, DateTimeFormatter prmDateTimeFormatter) {
		if (localDateTime == null) {
			return null;
		}

		return prmDateTimeFormatter.format(OffsetDateTime.ofInstant(convertLocalDateTimeToInstant(localDateTime), ZoneOffset.systemDefault()));
	}


	public static String convertLocalDateTimeAtUTCToSystemLocalString(LocalDateTime localDateTimeAtUTC, DateTimeFormatter prmDateTimeFormatter) {
		if (localDateTimeAtUTC == null) {
			return null;
		}

		return prmDateTimeFormatter.format(OffsetDateTime.ofInstant(convertLocalDateTimeAtUTCToInstant(localDateTimeAtUTC), ZoneOffset.systemDefault()));
	}

	public static String convertDateTimeAtUTCToUTCString(LocalDateTime dateTimeInUTC, DateTimeFormatter prmDateTimeFormatter) {
		return prmDateTimeFormatter.format(OffsetDateTime.ofInstant(dateTimeInUTC.toInstant(ZoneOffset.UTC), ZoneOffset.UTC));
	}

	public static long convertDateTimeAtUTCToEpochMilli(LocalDateTime localDateTimeAtUTC) {
		return convertLocalDateTimeAtUTCToInstant(localDateTimeAtUTC).toEpochMilli();
	}

	public static String convertLocalDateTimeAtSystemZoneToUTC(LocalDateTime localDateTime, DateTimeFormatter prmDateTimeFormatter) {
		Instant instant = convertLocalDateTimeToInstant(localDateTime);
		ZonedDateTime zonedDateTime = instant.atZone(ZoneOffset.UTC);
		return prmDateTimeFormatter.format(zonedDateTime);
	}

	public static int dataDurationToSeconds(String dataDuration) {
		char unit = dataDuration.charAt(dataDuration.length() - 1);
		int value = Integer.parseInt(dataDuration.substring(0, dataDuration.length() - 1));

		int seconds;
		switch (unit) {
			case 'm':
				seconds = value * 60;
				break;
			case 'h':
				seconds = value * 60 * 60;
				break;
			case 'd':
				seconds = value * 24 * 60 * 60;
				break;
			case 'w':
				seconds = value * 7 * 24 * 60 * 60;
				break;
			default:
				seconds = value;
		}
		return seconds;
	}


	/**
	 * This method returns the last execution time by now and cron expression.
	 * @param expression
	 * @param now
	 * @return
	 */
	public static LocalDateTime getPrevTimeByCronExpression(CronExpression expression, LocalDateTime now) {
		LocalDateTime nextTime = expression.next(now);
		Duration between = Duration.between(nextTime, expression.next(nextTime));
		return nextTime.minus(between.multipliedBy(2));
	}


	public static void main(String[] args) {
		System.out.println(convertDateTimeValueAtUTCToDateTimeStringAtSystemZoneOffset(nowInUTC(), dateTimeFormatterReadable));
		System.out.println(convertDateTimeValueAtUTCToDateTimeStringAtSystemZoneOffset("2021-12-29 15:11:03", dateTimeFormatterReadable));

		System.out.println(convertDateTimeValueAtUTCToDateTimeStringAtSystemZoneOffset("2021-12-29T10:11:03-05:00", dateTimeFormatterReadable));
		/*
		System.out.println(convertLocalDateTimeToSystemLocalString(LocalDateTime.now(), DateTimeConfig.dateTimeWithZoneFormatter));
		System.out.println(convertLocalDateTimeAtUTCToSystemLocalString(nowInUTC(), DateTimeConfig.dateTimeWithZoneFormatter));


		System.out.println(convertDateTimeValueToDateTimeStringAtSystemZoneOffset("2021-11-22T13:00:00.1231+06:00", dateTimeWithZoneFormatter));
		System.out.println(convertDateTimeValueToDateTimeStringAtSystemZoneOffset("2021-11-22T13:00:00.1231+03:00", dateTimeWithZoneFormatter));
		System.out.println(convertDateTimeValueToDateTimeStringAtSystemZoneOffset("2021-11-22T13:00:00.1231Z", dateTimeWithZoneFormatter));
		System.out.println(convertDateTimeValueToDateTimeStringAtSystemZoneOffset("2021-11-22T13:00:00.1231-05:00", dateTimeWithZoneFormatter));

		System.out.println(convertDateTimeValueToDateTimeStringAtSystemZoneOffset(convertLocalDateTimeAtUTCToInstant(nowInUTC()).toEpochMilli(), dateTimeWithZoneFormatter));
		*/
		/*
		System.out.println(convertDateTimeStrToInstantAtUTCZone("2021-11-21T14:31:27.1231"));
		System.out.println(convertDateTimeStrToInstantAtUTCZone("2021-11-22T14:31:27.1231+06:00"));
		System.out.println(convertDateTimeStrToInstantAtUTCZone("2021-11-23T14:31:27-05:00"));
		System.out.println(convertDateTimeStrToInstantAtUTCZone("2021-11-24 06:38:50.1231-05:00"));
		System.out.println(convertDateTimeStrToInstantAtUTCZone("2021-11-25T06:38:50-05:00"));
		System.out.println(convertDateTimeStrToInstantAtUTCZone("2021-11-26 06:38:05"));
		System.out.println(convertDateTimeStrToInstantAtUTCZone("2021-11-27T06:38:50"));

		//System.out.println(dateTimeFormatter.format(convertLocalDateTimeToInstantAtUTCZone(LocalDateTime.now())));
		System.out.println(convertDateTimeAtUTCToSystemLocalString(nowInUTC(), dateTimeFormatterReadable));
		System.out.println(convertDateTimeAtUTCToUTCString(nowInUTC(), dateTimeWithZoneFormatter));
		 */
	}


}
