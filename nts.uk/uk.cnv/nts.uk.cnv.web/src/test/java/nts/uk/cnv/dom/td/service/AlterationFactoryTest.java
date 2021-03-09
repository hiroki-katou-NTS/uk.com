package nts.uk.cnv.dom.td.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.alteration.content.AddColumn;
import nts.uk.cnv.dom.td.alteration.content.ChangeColumnComment;
import nts.uk.cnv.dom.td.alteration.content.ChangeColumnJpName;
import nts.uk.cnv.dom.td.alteration.content.ChangeColumnType;
import nts.uk.cnv.dom.td.alteration.content.ChangeIndex;
import nts.uk.cnv.dom.td.alteration.content.ChangePK;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableJpName;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableName;
import nts.uk.cnv.dom.td.alteration.content.ChangeUK;
import nts.uk.cnv.dom.td.alteration.content.RemoveColumn;
import nts.uk.cnv.dom.td.alteration.content.RemoveTable;
import nts.uk.cnv.dom.td.tabledefinetype.DataType;
import nts.uk.cnv.dom.td.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.td.tabledesign.DefineColumnType;
import nts.uk.cnv.dom.td.tabledesign.Indexes;
import nts.uk.cnv.dom.td.tabledesign.Snapshot;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableName;

@RunWith(JMockit.class)
public class AlterationFactoryTest {
	@Tested
	private AlterationFactory factory;

	private final String featureId = "root";
	private final String userName = "ai_muto";
	private final String tableName = "KRCDT_FOO_BAR";
	private final AlterationMetaData meta = new AlterationMetaData(
			featureId,
			userName,
			"おるたこめんと"
		);

	Optional<TableDesign> base;
	Alteration alt;

	@Before
	public void initialize() {
		base = createNewstSnapshot();
		alt = Alteration.createEmpty(tableName, meta);
	}

	@Test
	public void test_AddTable() {
		alt.getContents().addAll(AlterationType.TABLE_CREATE.createContent(Optional.empty(), base));

		val result = factory.create(tableName, meta, Optional.empty(), base);

		Assert.assertEquals(result, alt);
	}

	@Test
	public void test_RenameTable() {
		alt.getContents().add(new ChangeTableName("KRCDT_BAZ_QUX"));
		Optional<TableDesign> alterd = createAltered(base, alt);

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertEquals(result, alt);
	}

	@Test
	public void test_RenameTableJpName() {
		alt.getContents().add(new ChangeTableJpName("ふがふが"));
		Optional<TableDesign> alterd = createAltered(base, alt);

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertEquals(result, alt);
	}

	@Test
	public void test_ChangePK() {
		alt.getContents().add(new ChangePK(Arrays.asList("SID", "YMD", "ITEM_CD"), false));
		Optional<TableDesign> alterd = createAltered(base, alt);

		Assert.assertTrue(alterd.isPresent());
		Optional<ColumnDesign> cd = alterd.get().getColumns().stream()
				.filter(c -> c.getName().equals("ITEM_CD"))
				.findFirst();
		Assert.assertTrue(cd.isPresent() && cd.get().isPrimaryKey());

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertEquals(result, alt);
	}

	@Test
	public void test_ChangeUK() {
		alt.getContents().add(new ChangeUK("KRCDU_FOO_BAR", Arrays.asList("SID", "YMD", "ITEM_CD"), false));
		Optional<TableDesign> alterd = createAltered(base, alt);

		Assert.assertTrue(alterd.isPresent());
		Optional<ColumnDesign> cd = alterd.get().getColumns().stream()
				.filter(c -> c.getName().equals("ITEM_CD"))
				.findFirst();
		Assert.assertTrue(cd.isPresent() && cd.get().isUniqueKey());

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertEquals(result, alt);
	}

	@Test
	public void test_ChangeIndex() {
		alt.getContents().add(new ChangeIndex("KRCDI_FOO_BAR", Arrays.asList("SID", "YMD", "ITEM_CD"), false, true));
		Optional<TableDesign> alterd = createAltered(base, alt);

		Assert.assertTrue(alterd.isPresent());
		Optional<Indexes> index = alterd.get().getIndexes().stream()
				.filter(idx -> idx.getName().equals("KRCDI_FOO_BAR"))
				.findFirst();
		Assert.assertTrue(index.isPresent() && index.get().isUnique());

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertEquals(result, alt);
	}

	@Test
	public void test_RemoveTable() {
		alt.getContents().add(new RemoveTable(tableName));
		Optional<TableDesign> alterd = createAltered(base, alt);

		Assert.assertFalse(alterd.isPresent());

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertEquals(result, alt);
	}

	@Test
	public void test_addColumn() {
		DefineColumnType type = new DefineColumnType(DataType.BOOL, 0, 0, false, "", "");
		ColumnDesign newColumn = new ColumnDesign(3, "COMFIRM_ATR", "確認区分", type, false, 0, false, 0, "", 3);
		alt.getContents().add(new AddColumn("COMFIRM_ATR", newColumn));
		Optional<TableDesign> alterd = createAltered(base, alt);

		Assert.assertTrue(alterd.isPresent());
		Optional<ColumnDesign> column = alterd.get().getColumns().stream()
				.filter(col -> col.getName().equals("COMFIRM_ATR"))
				.findFirst();
		Assert.assertTrue(column.isPresent() && column.get().equals(newColumn));

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertEquals(result, alt);
	}

	@Test
	public void test_changeColumnType() {
		DefineColumnType coltype = new DefineColumnType(
				DataType.CHAR, 4, 0, false, "", "");
		alt.getContents().add(new ChangeColumnType("ITEM_CD", coltype));
		Optional<TableDesign> alterd = createAltered(base, alt);

		Assert.assertTrue(alterd.isPresent());
		Optional<ColumnDesign> column = alterd.get().getColumns().stream()
				.filter(col -> col.getName().equals("ITEM_CD"))
				.findFirst();
		Assert.assertTrue(column.isPresent());

		DefineColumnType resultColtype = new DefineColumnType(
				column.get().getType(),
				column.get().getMaxLength(),
				column.get().getScale(),
				column.get().isNullable(),
				column.get().getDefaultValue(),
				column.get().getCheck());
		Assert.assertEquals(resultColtype, coltype);

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertEquals(result, alt);
	}

	@Test
	public void test_changeColumnName() {
		alt.getContents().add(new ChangeColumnJpName("ITEM_CD", "★項目コード★"));
		Optional<TableDesign> alterd = createAltered(base, alt);

		Assert.assertTrue(alterd.isPresent());
		Optional<ColumnDesign> column = alterd.get().getColumns().stream()
				.filter(col -> col.getName().equals("ITEM_CD"))
				.findFirst();
		Assert.assertTrue(column.isPresent() && column.get().getJpName().equals("★項目コード★"));

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertEquals(result, alt);
	}

	@Test
	public void test_changeColumnComment() {
		alt.getContents().add(new ChangeColumnComment("ITEM_CD", "こめんと"));
		Optional<TableDesign> alterd = createAltered(base, alt);

		Assert.assertTrue(alterd.isPresent());
		Optional<ColumnDesign> column = alterd.get().getColumns().stream()
				.filter(col -> col.getName().equals("ITEM_CD"))
				.findFirst();
		Assert.assertTrue(column.isPresent() && column.get().getComment().equals("こめんと"));

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertEquals(result, alt);
	}

	@Test
	public void test_delColumn() {
		alt.getContents().add(new RemoveColumn("ITEM_CD"));
		Optional<TableDesign> alterd = createAltered(base, alt);

		Assert.assertTrue(alterd.isPresent());
		Optional<ColumnDesign> column = alterd.get().getColumns().stream()
				.filter(col -> col.getName().equals("ITEM_CD"))
				.findFirst();
		Assert.assertFalse(column.isPresent());

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertEquals(result, alt);
	}

	private Optional<TableDesign> createNewstSnapshot() {
		return Optional.of(
				new Snapshot(
					"root",
					GeneralDateTime.ymdhms(2021, 2, 26, 15, 30, 0),
					createDummy()
				));
	}

	private Optional<TableDesign> createAltered(Optional<TableDesign> base, Alteration alt) {
		return base.get().applyAlteration(Arrays.asList(alt));
	}

	private TableDesign createDummy() {
		List<ColumnDesign> cols = new ArrayList<>();
		List<Indexes> indexes = new ArrayList<>();
		DefineColumnType sidType = new DefineColumnType(DataType.CHAR, 36, 0, false, "", "");
		DefineColumnType ymdType = new DefineColumnType(DataType.DATE, 0, 0, false, "", "");
		DefineColumnType itemCdType = new DefineColumnType(DataType.CHAR, 0, 0, false, "", "");
		cols.add(new ColumnDesign(0, "SID", "社員ID", sidType, true, 1, false, 0, "", 0));
		cols.add(new ColumnDesign(1, "YMD", "年月日", ymdType, true, 2, false, 0, "", 1));
		cols.add(new ColumnDesign(2, "ITEM_CD", "項目コード", itemCdType, false, 0, false, 0, "", 2));
		indexes.add(Indexes.createPk(new TableName(tableName), Arrays.asList("SID", "YMD"), true));
		indexes.add(Indexes.createIndex("KRCDI_FOO_BAR", Arrays.asList("SID", "YMD"), false, false));

		return new TableDesign(
			tableName, tableName, "ほげほげ",
			cols,
			indexes);
	}
}
