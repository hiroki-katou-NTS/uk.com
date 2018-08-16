package nts.uk.shr.infra.data.jdbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.context.loginuser.DefaultLoginUserContext;

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
	private static final String NULL_VALUE = "NULL";

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
	
	public static String toInsertWithCommonField(String insertQuery){
		List<String> fields = new ArrayList<>();
		List<String> values = new ArrayList<>();
		getDefaultInsertField().forEach(fv -> {
			fields.add(fv.field);
			values.add(toString(fv.value));
		});
		
		String fieldInQ = StringUtils.join(fields.toArray(), ", ");
		String valueInQ = StringUtils.join(values.toArray(), ", ");
		
		List<String> splitted = new ArrayList<>(Arrays.asList(insertQuery.split(Pattern.quote(OPEN_KOMA))));
		
		splitted.add(1, ", ");
		splitted.add(1, fieldInQ);
		splitted.add(1, OPEN_KOMA);
		
		int listCurrentLength = splitted.size();
		splitted.add(listCurrentLength - 1, ",");
		splitted.add(listCurrentLength - 1, valueInQ);
		splitted.add(listCurrentLength - 1, OPEN_KOMA);
		
		return StringUtils.join(splitted.toArray(), "");
	}
	
	public static String toUpdateWithCommonField(String insertQuery){
		Object updated[] = getDefaultUpdateField().map(
				f -> StringUtils.join(f.field, " = ", toString(f.value)))
				.collect(Collectors.toList()).toArray();
		
		String valueInQ = StringUtils.join(updated, ", ");
		
		List<String> splitted = new ArrayList<>(Arrays.asList(insertQuery.split(" " + WHERE + " ")));

		if(splitted.size() > 1){
			splitted.add(1, " " + WHERE + " ");	
		}
		splitted.add(1, valueInQ);
		splitted.add(1, ", ");
		
		return StringUtils.join(splitted.toArray(), "");
	}

	public static String buildInCondition(Collection<?> values){
		return StringUtils.join(OPEN_KOMA, StringUtils.join(values.stream()
				.map(v -> toString(v)).toArray(), ","), CLOSE_KOMA);
	}
	
	public static String toString(Object original){
		if(original == null){
			return NULL_VALUE;
		}
		return StringUtils.join(DEFAULT_STRING_QUOTE, original, DEFAULT_STRING_QUOTE);
	}
	
	public static String removeConditionIfHave(String query){
		return query.replace(WHERE, "").replace(CONDITION, "");
	}
	
	public static Stream<FieldWithValue> getDefaultInsertField(){
		GeneralDateTime now = GeneralDateTime.now();
		LoginUserContext user = new DefaultLoginUserContext("", true);//AppContexts.user();
		String programId = "";//AppContexts.programId();
		
		return Stream.concat(getDefaultInsertField(now, user, programId), 
							getDefaultUpdateField(now, user, programId));
	}
	
	public static Stream<FieldWithValue> getDefaultUpdateField(){
		GeneralDateTime now = GeneralDateTime.now();
		LoginUserContext user = new DefaultLoginUserContext("", true);//AppContexts.user();
		String programId = "";//AppContexts.programId();
		
		return getDefaultUpdateField(now, user, programId);
	}

	private static Stream<FieldWithValue> getDefaultInsertField(GeneralDateTime now, LoginUserContext user,
			String programId) {
		return Stream.of(new FieldWithValue("INS_DATE", " = ", now),
						new FieldWithValue("INS_CCD", " = ", user.companyCode()),
						new FieldWithValue("INS_SCD", " = ", user.employeeCode()),
						new FieldWithValue("INS_PG", " = ", programId));
	} 

	private static Stream<FieldWithValue> getDefaultUpdateField(GeneralDateTime now, LoginUserContext user,
			String programId) {
		return Stream.of(new FieldWithValue("UPD_DATE", " = ", now),
						new FieldWithValue("UPD_CCD", " = ", user.companyCode()),
						new FieldWithValue("UPD_SCD", " = ", user.employeeCode()),
						new FieldWithValue("UPD_PG", " = ", programId));
	} 
	
	private static String build(String... values) {
		return StringUtils.join(values, DEFAULT_SEPERATOR);
	}
}
