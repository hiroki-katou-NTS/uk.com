package nts.uk.shr.infra.data.jdbc;

import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.Test;

import nts.uk.shr.infra.i18n.entity.LanguageMaster;

public class JDBCQueryBuilderTest {

	@Test
	public void test_select_all() {
		String query = JDBCQueryBuilder.start(LanguageMaster.class)
				.type(QueryType.SELECT).build();
		
		Assert.isNotNull(query, "test");
	}
	
	@Test()
	public void test_select_some() {
		String query = JDBCQueryBuilder.start(LanguageMaster.class)
				.type(QueryType.SELECT).selectFields("LANGUAGE_ID", "LANGUAGE_CODE").build();
		
		Assert.isNotNull(query, "test");
	}
	

	
	@Test()
	public void test_select_where() {
		String query = JDBCQueryBuilder.start(LanguageMaster.class)
				.type(QueryType.SELECT).selectFields("LANGUAGE_ID")
				.conditions(new FieldWithValue("LANGUAGE_ID", " = ", "ja"), ConditionJoin.OR).build();
		
		Assert.isNotNull(query, "test");
	}
	
	@Test()
	public void test_delete() {
		String query = JDBCQueryBuilder.start(LanguageMaster.class)
				.type(QueryType.DELETE)
				.conditions(new FieldWithValue("LANGUAGE_ID", " = ", "ja"), ConditionJoin.OR).build();
		
		Assert.isNotNull(query, "test");
	}
	
	@Test()
	public void test_update() {
		String query = JDBCQueryBuilder.start(LanguageMaster.class)
				.type(QueryType.UPDATE).changeFields(new FieldWithValue("LANGUAGE_NAME", " = ", "test"))
				.conditions(new FieldWithValue("LANGUAGE_ID", " = ", "ja"), ConditionJoin.OR).build();
		
		Assert.isNotNull(query, "test");
	}
	
	@Test()
	public void test_insert() {
		String query = JDBCQueryBuilder.start(LanguageMaster.class)
				.type(QueryType.INSERT)
				.changeFields(new FieldWithValue("LANGUAGE_NAME", "=", "test"), 
						new FieldWithValue("LANGUAGE_ID", "=", "test"), 
						new FieldWithValue("LANGUAGE_CODE", "=", "test"))
				.build();
		
		Assert.isNotNull(query, "test");
	}
	
	@Test()
	public void test_change_insert() {
		String query = "INSERT INTO CISMT_LANGUAGE (LANGUAGE_ID, LANGUAGE_CODE, LANGUAGE_NAME) VALUES ('ja', 'ja', 'ja')";
		query = JDBCUtil.toInsertWithCommonField(query);
		Assert.isNotNull(query, "test");
	}
	
	@Test()
	public void test_change_update() {
		String query = "UPDATE CISMT_LANGUAGE SET LANGUAGE_NAME = 'ja' WHERE LANGUAGE_ID = 'ja'";
		query = JDBCUtil.toUpdateWithCommonField(query);
		Assert.isNotNull(query, "test");
	}
	
	@Test()
	public void test_change_updateNoWhere() {
		String query = "UPDATE CISMT_LANGUAGE SET LANGUAGE_NAME = 'ja'";
		query = JDBCUtil.toUpdateWithCommonField(query);
		Assert.isNotNull(query, "test");
	}
	
	@Test()
	public void test_change_update2() {
		String query = "UPDATE CISMT_LANGUAGE SET LANGUAGE_NAME = 'ja' WHERE LANGUAGE_ID = 'ja';"
				+ "UPDATE CISMT_LANGUAGE SET LANGUAGE_NAME = 'ba' WHERE LANGUAGE_ID = 'ba';"
				+ "UPDATE CISMT_LANGUAGE SET LANGUAGE_NAME = 'aa' WHERE LANGUAGE_ID = 'ca';"
				+ "UPDATE CISMT_LANGUAGE SET LANGUAGE_NAME = 'ca' WHERE LANGUAGE_ID = 'ca';";
		query = JDBCUtil.toUpdateWithCommonField(query, true);
		Assert.isNotNull(query, "test");
	}
	
	@Test()
	public void test_change_insert2() {
		String query = "INSERT INTO CISMT_LANGUAGE (LANGUAGE_ID, LANGUAGE_CODE, LANGUAGE_NAME) VALUES ('ja', 'ja', 'ja');"
				+ "INSERT INTO CISMT_LANGUAGE (LANGUAGE_ID, LANGUAGE_CODE, LANGUAGE_NAME) VALUES ('ba', 'ba', 'ba');"
				+ "INSERT INTO CISMT_LANGUAGE (LANGUAGE_ID, LANGUAGE_CODE, LANGUAGE_NAME) VALUES ('aa', 'aa', 'aa');"
				+ "INSERT INTO CISMT_LANGUAGE (LANGUAGE_ID, LANGUAGE_CODE, LANGUAGE_NAME) VALUES ('ca', 'ca', 'ca');";
		query = JDBCUtil.toInsertWithCommonField(query, true);
		Assert.isNotNull(query, "test");
	}
}
