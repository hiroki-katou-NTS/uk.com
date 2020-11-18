package nts.uk.cnv.dom.tabledesign;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.tabledefinetype.DataType;
import nts.uk.cnv.dom.tabledefinetype.DatabaseSpec;
import nts.uk.cnv.dom.tabledefinetype.databasetype.DatabaseType;

public class TableDesignTest {

	@Test
	public void test_createSimpleTableSql_SQLServer() {
		TableDesign target = createDummy();

		String result = target.createSimpleTableSql(DatabaseType.valueOf("sqlserver").spec());

		DatabaseSpec postgreSpec = DatabaseType.valueOf("postgres").spec();
		Assert.assertTrue(!result.contains(postgreSpec.tableCommentDdl("[TABLE_NAME]", "[TABLE_COMMENT]")));
		Assert.assertTrue(!result.contains(postgreSpec.columnCommentDdl("[TABLE_NAME]", "[COLUMN_NAME_1]", "[COLUMN_COMMENT]")));
		Assert.assertTrue(!result.contains(postgreSpec.columnCommentDdl("[TABLE_NAME]", "[COLUMN_NAME_2]", "[COLUMN_COMMENT]")));
		Assert.assertTrue(!result.contains(postgreSpec.rlsDdl("[TABLE_NAME]")));
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
	}

	@Test
	public void test_createFullTableSql_SQLServer() {
		TableDesign target = createDummy();

		DatabaseSpec sqlserverSpec = DatabaseType.valueOf("sqlserver").spec();
		DatabaseSpec postgreSpec = DatabaseType.valueOf("postgres").spec();
		String result = target.createFullTableSql(sqlserverSpec);

		Assert.assertTrue(result.contains(sqlserverSpec.tableCommentDdl("[TABLE_NAME]", "[TABLE_COMMENT]")));
		Assert.assertTrue(result.contains(sqlserverSpec.columnCommentDdl("[TABLE_NAME]", "[COLUMN_NAME_1]", "[COLUMN_COMMENT]")));
		Assert.assertTrue(result.contains(sqlserverSpec.columnCommentDdl("[TABLE_NAME]", "[COLUMN_NAME_2]", "[COLUMN_COMMENT]")));
		Assert.assertTrue(!result.contains(postgreSpec.rlsDdl("[TABLE_NAME]")));
	}

	@Test
	public void test_createFullTableSql_Postgres() {
		TableDesign target = createDummy();

		DatabaseSpec postgreSpec = DatabaseType.valueOf("postgres").spec();

		String result = target.createFullTableSql(postgreSpec);

		Assert.assertTrue(result.contains(postgreSpec.tableCommentDdl("[TABLE_NAME]", "[TABLE_COMMENT]")));
		Assert.assertTrue(result.contains(postgreSpec.columnCommentDdl("[TABLE_NAME]", "[COLUMN_NAME_1]", "[COLUMN_COMMENT]")));
		Assert.assertTrue(result.contains(postgreSpec.columnCommentDdl("[TABLE_NAME]", "[COLUMN_NAME_2]", "[COLUMN_COMMENT]")));
		Assert.assertTrue(result.contains(postgreSpec.rlsDdl("[TABLE_NAME]")));
	}

	private TableDesign createDummy() {
		List<ColumnDesign> cols = new ArrayList<>();
		List<Indexes> indexes = new ArrayList<>();
		cols.add(new ColumnDesign(0, "[COLUMN_NAME_1]", DataType.INT, 1, 0, false, true, 1, false, 0,
				"", "[COLUMN_COMMENT]", "[CHECK]"));
		cols.add(new ColumnDesign(0, "[COLUMN_NAME_2]", DataType.INT, 1, 0, false, true, 1, false, 0,
				"", "[COLUMN_COMMENT]", "[CHECK]"));
		indexes.add(new Indexes("[PKEY_NAME]", "PRIMARY KEY", true, Arrays.asList("[COLUMN_NAME_1]"), new ArrayList<>()));
		indexes.add(new Indexes("[INDEX_NAME]", "INDEX", false, Arrays.asList("[COLUMN_NAME_2]"), new ArrayList<>()));

		return new TableDesign(
					"[TABLE_NAME]", "", "[TABLE_COMMENT]",
					GeneralDateTime.now(), GeneralDateTime.now(),
					cols,
					indexes);
	}
}
