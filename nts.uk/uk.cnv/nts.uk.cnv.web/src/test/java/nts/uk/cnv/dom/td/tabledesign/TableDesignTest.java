package nts.uk.cnv.dom.td.tabledesign;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import nts.uk.cnv.dom.td.tabledefinetype.DataType;
import nts.uk.cnv.dom.td.tabledefinetype.DatabaseSpec;
import nts.uk.cnv.dom.td.tabledefinetype.databasetype.DatabaseType;

public class TableDesignTest {
	private final String OBJECT_NAME_REGEX = "\\[*[A-Z]+[A-Z|0-9|_]*\\]*";
	private final String TABLE_NAME_REGEX = OBJECT_NAME_REGEX;
	private final String COLUMN_NAME_REGEX = OBJECT_NAME_REGEX;

	private final String COLUMN_DEFINITION_REGEX = ""
			+ COLUMN_NAME_REGEX
			+ "\\s*(DECIMAL|INT|NVARCHAR|VARCHAR|NCHAR|CHAR|DATETIME2|DATE)*\\s*(\\([0-9|,]+\\))*"
			+ "\\s*(NULL|NOT NULL)"
			+ "\\s*(DEFAULT\\s*['|A-Z|0-9]*\\s*)*";
	private final String TABLE_CONSTRAINT_REGEX = ""
			+ "(CONSTRAINT\\s*" + OBJECT_NAME_REGEX + ")*\\s*"
			+ "(PRIMARY KEY|UNIQUE)\\s*\\(\\s*(" + COLUMN_NAME_REGEX + ")+\\s*(\\s*,\\s*" + COLUMN_NAME_REGEX + "\\s*)*\\s*\\);*\\s*";

	private final String CREATE_TABLE_REGEX = "CREATE TABLE\\s*" + TABLE_NAME_REGEX + "\\s*\\(\\s*"
			+ "(" + COLUMN_DEFINITION_REGEX + ")+\\s*"
			+ "(,\\s*" + COLUMN_DEFINITION_REGEX + ")*\\s*"
			+ "(,\\s*" + TABLE_CONSTRAINT_REGEX + ")*\\s*"
			+ "\\)" + "\\s*";

	@Test
	public void test_createSimpleTableSql_SQLServer() {
		TableDesign target = createDummy();

		String result = target.createSimpleTableSql(DatabaseType.valueOf("sqlserver").spec());

		DatabaseSpec postgreSpec = DatabaseType.valueOf("postgres").spec();
		Assert.assertTrue(!result.contains(postgreSpec.tableCommentDdl("[TABLE_NAME]", "[TABLE_COMMENT]")));
		Assert.assertTrue(!result.contains(postgreSpec.columnCommentDdl("[TABLE_NAME]", "[COLUMN_NAME_1]", "[COLUMN_COMMENT]")));
		Assert.assertTrue(!result.contains(postgreSpec.columnCommentDdl("[TABLE_NAME]", "[COLUMN_NAME_2]", "[COLUMN_COMMENT]")));
		Assert.assertTrue(!result.contains(postgreSpec.rlsDdl("[TABLE_NAME]")));
		Assert.assertTrue(result.split(";")[0].matches(CREATE_TABLE_REGEX));
	}

	@Test
	public void test_createSimpleTableSql_Postgres() {
		TableDesign target = createDummy();

		String result = target.createFullTableSql(DatabaseType.valueOf("sqlserver").spec());

		DatabaseSpec postgreSpec = DatabaseType.valueOf("postgres").spec();
		Assert.assertTrue(!result.contains(postgreSpec.tableCommentDdl("[TABLE_NAME]", "[TABLE_COMMENT]")));
		Assert.assertTrue(!result.contains(postgreSpec.columnCommentDdl("[TABLE_NAME]", "[COLUMN_NAME_1]", "[COLUMN_COMMENT]")));
		Assert.assertTrue(!result.contains(postgreSpec.columnCommentDdl("[TABLE_NAME]", "[COLUMN_NAME_2]", "[COLUMN_COMMENT]")));
		Assert.assertTrue(!result.contains(postgreSpec.rlsDdl("[TABLE_NAME]")));
		Assert.assertTrue(result.split(";")[0].matches(CREATE_TABLE_REGEX));
	}

	@Test	public void test_createFullTableSql_SQLServer() {
		TableDesign target = createDummy();

		DatabaseSpec sqlserverSpec = DatabaseType.valueOf("sqlserver").spec();
		DatabaseSpec postgreSpec = DatabaseType.valueOf("postgres").spec();
		String result = target.createFullTableSql(sqlserverSpec);

		Assert.assertTrue(result.contains(sqlserverSpec.tableCommentDdl("[TABLE_NAME]", "[TABLE_COMMENT]")));
		Assert.assertTrue(result.contains(sqlserverSpec.columnCommentDdl("[TABLE_NAME]", "[COLUMN_NAME_1]", "[COLUMN_COMMENT]")));
		Assert.assertTrue(result.contains(sqlserverSpec.columnCommentDdl("[TABLE_NAME]", "[COLUMN_NAME_2]", "[COLUMN_COMMENT]")));
		Assert.assertTrue(!result.contains(postgreSpec.rlsDdl("[TABLE_NAME]")));
		Assert.assertTrue(result.split(";")[0].matches(CREATE_TABLE_REGEX));
	}

	@Test
	public void test_createFullTableSql_Postgres() {
		TableDesign target = createDummy();

		DatabaseSpec postgreSpec = DatabaseType.valueOf("postgres").spec();

		String result = target.createFullTableSql(postgreSpec);

		Assert.assertTrue(result.contains(postgreSpec.tableCommentDdl("[TABLE_NAME]", "[TABLE_COMMENT]")));
		Assert.assertTrue(result.contains(postgreSpec.columnCommentDdl("[TABLE_NAME]", "[COLUMN_NAME_1]", "[COLUMN_COMMENT]")));
		Assert.assertTrue(result.contains(postgreSpec.columnCommentDdl("[TABLE_NAME]", "[COLUMN_NAME_2]", "[COLUMN_COMMENT]")));
		//Assert.assertTrue(result.contains(postgreSpec.rlsDdl("[TABLE_NAME]")));
		Assert.assertTrue(result.split(";")[0].matches(CREATE_TABLE_REGEX));
	}

	private TableDesign createDummy() {
		List<ColumnDesign> cols = new ArrayList<>();
		List<Indexes> indexes = new ArrayList<>();
		DefineColumnType type = new DefineColumnType(DataType.INT, 1, 0, false, "[COLUMN_COMMENT]", "[CHECK]");
		cols.add(new ColumnDesign(0, "[COLUMN_NAME_1]", "", type, true, 1, false, 0, "", 0));
		cols.add(new ColumnDesign(1, "[COLUMN_NAME_2]", "", type, true, 1, false, 0, "", 1));
		indexes.add(Indexes.createPk(new TableName("TABLE_NAME"), Arrays.asList("[COLUMN_NAME_1]"), true));
		indexes.add(Indexes.createIndex("[INDEX_NAME]", Arrays.asList("[COLUMN_NAME_2]"), false, false));

		return new TableDesign(
					"[TABLE_ID]", "[TABLE_NAME]", "[TABLE_JPNAME]",
					cols,
					indexes);
	}
}
