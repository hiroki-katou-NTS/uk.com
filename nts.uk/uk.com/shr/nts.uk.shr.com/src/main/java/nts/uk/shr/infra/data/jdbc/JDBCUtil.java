package nts.uk.shr.infra.data.jdbc;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

public class JDBCUtil {

	public static final String TARGET_FIELD = "{targetField}";
	public static final String TARGET_TABLE = "{targetTable}";
	public static final String FIELD_VALUE = "{fieldValue}";
	public static final String CONDITION = "{condition}";
	private static final String SELECT = "SELECT";
	private static final String WHERE = "WHERE";
	private static final String UPDATE = "UPDATE";
	private static final String INSERT = "INSERT";
	private static final String DELETE = "DELETE";
	private static final String FROM = "FROM";
	private static final String SET = "SET";
	private static final String INTO = "INTO";
	private static final String VALUES = "VALUES";
	private static final String OPEN_KOMA = "(";
	private static final String CLOSE_KOMA = ")";
	private static final String DEFAULT_SEPERATOR = " ";
	private static final String DEFAULT_STRING_QUOTE = "'";

	public static String selectTemplate() {
		return build(SELECT, TARGET_FIELD, FROM, TARGET_TABLE);
	}

	public static String selectWhereTemplate() {
		return build(SELECT, TARGET_FIELD, FROM, TARGET_TABLE, WHERE, CONDITION);
	}

	public static String updateTemplate() {
		return build(UPDATE, TARGET_TABLE, SET, TARGET_FIELD, WHERE, CONDITION);
	}

	public static String deleteTemplate() {
		return build(DELETE, FROM, TARGET_TABLE, WHERE, CONDITION);
	}
	
	public static String insertTemplate() {
		return build(INSERT, INTO, TARGET_TABLE, OPEN_KOMA, TARGET_FIELD, CLOSE_KOMA, VALUES, OPEN_KOMA, FIELD_VALUE, CLOSE_KOMA);
	}

	public static String buildInCondition(Collection<?> values){
		return StringUtils.join(OPEN_KOMA, StringUtils.join(values.stream()
				.map(v -> toString(v.toString())).toArray(), ","), CLOSE_KOMA);
	}
	
	public static String toString(String original){
		return StringUtils.join(DEFAULT_STRING_QUOTE, original, DEFAULT_STRING_QUOTE);
	}
	
	public static String removeConditionIfHave(String query){
		return query.replace(WHERE, "").replace(CONDITION, "");
	}
	
	private static String build(String... values) {
		return StringUtils.join(values, DEFAULT_SEPERATOR);
	}
}
