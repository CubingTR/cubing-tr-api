package org.cubingtr.cubingtrapi.common.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class GenericDataMap extends LinkedHashMap<String, Object> implements Serializable {

	private static final long serialVersionUID = 2659763602755760825L;

	public static GenericDataMap from(Map<String, ?> source) {
		if (source == null)
			return null;

		GenericDataMap genericDataMap = new GenericDataMap();
		genericDataMap.putAll(source);
		return genericDataMap;
	}

	public <T> List<T> getAsList(String key) {
		return getAsList(this, key);
	}

	public static <T> List<T> getAsList(Map data, String key) {
		if (data.containsKey(key))
			return (List<T>) data.get(key);

		return null;
	}

	public <K, V> Map<K, V> getAsMap(String key) {
		return getAsMap(this, key);
	}

	public static <K, V> Map<K, V> getAsMap(Map data, String key) {
		return getAsMap(data, key, true);
	}

	public static <K, V> Map<K, V> getAsMap(Map data, String key, boolean isRequired) {
		try {
			if (data.containsKey(key))
				return (Map<K, V>) data.get(key);
		} catch (Exception ex) {
			if (isRequired) {
				throw ex;
			}
		}
		return null;
	}

	public GenericDataMap getAsGenericDataMap(String key) {
		Map<String, Object> map = getAsMap(this, key, false);
		if (map == null) {
			return null;
		}
		return from(map);
	}

	public String getAsString(String key) {
		return getAsString(this, key);
	}

	public static String getAsString(Map data, String key) {
		if (data.containsKey(key)) {
			Object value = data.get(key);
			if (value == null) {
				return null;
			}

			if (value instanceof String) {
				return (String) value;
			}
			return String.valueOf(value);
		}

		return null;
	}

	public Integer getAsInteger(String key) {
		return getAsInteger(this, key);
	}

	public static Integer getAsInteger(Map data, String key) {
		if (data.get(key) != null) {
			return ((Number) data.get(key)).intValue();
		}

		return null;
	}

	public Double getAsDouble(String key) {
		return getAsDouble(this, key);
	}

	public static Double getAsDouble(Map data, String key) {
		if (data.get(key) != null) {
			return ((Number) data.get(key)).doubleValue();
		}
		return null;
	}

	public Long getAsLong(String key) {
		return getAsLong(this, key);
	}

	public static Long getAsLong(Map data, String key) {
		if (data.get(key) != null) {
			return ((Number) data.get(key)).longValue();
		}

		return null;
	}

	public boolean getAsBoolean(Map data, String key) {
		Object value = data.get(key);
		if (value != null) {
			if (value instanceof Boolean) {
				return (Boolean)value;
			}
			return Boolean.valueOf(value.toString());
		}

		return false;

	}

	public boolean getAsBoolean(String key) {
		return getAsBoolean(this, key);
	}

	public static LocalDateTime getAsDateTime(Map data, String key) {
		Object value = data.get(key);
		if (value == null)
			return null;

		if (value instanceof LocalDateTime) {
			return (LocalDateTime) value;
		}

		if (value instanceof String) {
			LocalDateTime.parse((String) value);
		}

		throw new UnsupportedOperationException(String.format("not supported date time object of type: %s, value: %s", value.getClass().getName(), value.toString()));
	}

	public LocalDateTime getAsDateTime(String key) {
		return getAsDateTime(this, key);
	}

	public static LocalDate getAsDate(Map data, String key) {
		Object value = data.get(key);
		if (value == null)
			return null;

		if (value instanceof LocalDate) {
			return (LocalDate) value;
		}

		if (value instanceof String) {
			LocalDate.parse((String) value);
		}

		throw new UnsupportedOperationException(String.format("not supported date time object of type: %s, value: %s", value.getClass().getName(), value.toString()));
	}

	public LocalDate getAsDate(String key) {
		return getAsDate(this, key);
	}

}
