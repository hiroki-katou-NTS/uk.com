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
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationFactory;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.alteration.content.AddColumn;
import nts.uk.cnv.dom.td.alteration.content.ChangeColumnComment;
import nts.uk.cnv.dom.td.alteration.content.ChangeColumnJpName;
import nts.uk.cnv.dom.td.alteration.content.ChangeColumnName;
import nts.uk.cnv.dom.td.alteration.content.ChangeColumnType;
import nts.uk.cnv.dom.td.alteration.content.ChangeIndex;
import nts.uk.cnv.dom.td.alteration.content.ChangePK;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableJpName;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableName;
import nts.uk.cnv.dom.td.alteration.content.ChangeUK;
import nts.uk.cnv.dom.td.alteration.content.RemoveColumn;
import nts.uk.cnv.dom.td.alteration.content.RemoveTable;
import nts.uk.cnv.dom.td.schema.prospect.TableProspect;
import nts.uk.cnv.dom.td.schema.snapshot.Snapshot;
import nts.uk.cnv.dom.td.schema.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.DefineColumnType;
import nts.uk.cnv.dom.td.schema.tabledesign.Indexes;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.tabledefinetype.DataType;

@RunWith(JMockit.class)
public class AlterationFactoryTest {
	@Tested
	private AlterationFactory factory;

	//private final String alterationId = IdentifierUtil.randomUniqueId();
	private final String featureId = "root";
	private final String userName = "ai_muto";
	private final String tableName = "KRCDT_FOO_BAR";
	private final AlterationMetaData meta = new AlterationMetaData(
			featureId,
			userName,
			"おるたこめんと"
		);

	Snapshot ss;
	Optional<TableDesign> base;
	Alteration alt;

	@Before
	public void initialize() {
		ss = createSnapshot();
		base = Optional.of((TableDesign) ss);
		alt = Alteration.createEmpty(tableName, meta);
	}

	@Test
	public void test_AddTable() {
		Optional<TableProspect> empty = new Snapshot().createTableProspect(new ArrayList<>());
		val alterd = Optional.of((TableDesign) ss);
		alt.getContents().addAll(AlterationType.TABLE_CREATE.createContent(empty, alterd));

		val result = factory.create(tableName, meta, Optional.empty(), alterd);

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_RenameTable() {
		alt.getContents().add(new ChangeTableName("KRCDT_BAZ_QUX"));
		Optional<TableDesign> alterd = createAltered(ss, alt);

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_RenameTableJpName() {
		alt.getContents().add(new ChangeTableJpName("ふがふが"));
		Optional<TableDesign> alterd = createAltered(ss, alt);

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_ChangePK() {
		alt.getContents().add(new ChangePK(Arrays.asList("SID", "YMD", "ITEM_CD"), false));
		Optional<TableDesign> alterd = createAltered(ss, alt);

		Assert.assertTrue(alterd.isPresent());
		Optional<Indexes> pk = alterd.get().getIndexes().stream()
				.filter(idx -> idx.isPK())
				.findFirst();
		Assert.assertTrue(pk.isPresent() && pk.get().getColumns().contains("ITEM_CD"));

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_ChangeUK() {
		alt.getContents().add(new ChangeUK("KRCDU_FOO_BAR", Arrays.asList("SID", "YMD", "ITEM_CD"), false));
		Optional<TableDesign> alterd = createAltered(ss, alt);

		Assert.assertTrue(alterd.isPresent());
		Optional<Indexes> uk = alterd.get().getIndexes().stream()
				.filter(idx -> idx.isUK())
				.findFirst();
		Assert.assertTrue(uk.isPresent() && uk.get().getColumns().contains("ITEM_CD"));

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_ChangeIndex() {
		alt.getContents().add(new ChangeIndex("KRCDI_FOO_BAR", Arrays.asList("SID", "YMD", "ITEM_CD"), true));
		Optional<TableDesign> alterd = createAltered(ss, alt);

		Assert.assertTrue(alterd.isPresent());
		Optional<Indexes> index = alterd.get().getIndexes().stream()
				.filter(idx -> idx.getName().equals("KRCDI_FOO_BAR"))
				.findFirst();
		Assert.assertTrue(index.isPresent() && index.get().isClustered());

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_RemoveTable() {
		alt.getContents().add(new RemoveTable());
		Optional<TableDesign> alterd = createAltered(ss, alt);

		Assert.assertFalse(alterd.isPresent());

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_addColumn() {
		DefineColumnType type = new DefineColumnType(DataType.BOOL, 0, 0, false, "", "");
		ColumnDesign newColumn = new ColumnDesign("3", "COMFIRM_ATR", "確認区分", type, "", 3);
		alt.getContents().add(new AddColumn("3", newColumn));
		Optional<TableDesign> alterd = createAltered(ss, alt);

		Assert.assertTrue(alterd.isPresent());
		Optional<ColumnDesign> column = alterd.get().getColumns().stream()
				.filter(col -> col.getId().equals("3"))
				.findFirst();
		Assert.assertTrue(column.isPresent() && column.get().equals(newColumn));

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_changeColumnType() {
		DefineColumnType coltype = new DefineColumnType(
				DataType.CHAR, 4, 0, false, "", "");
		alt.getContents().add(new ChangeColumnType("2", coltype));
		Optional<TableDesign> alterd = createAltered(ss, alt);

		Assert.assertTrue(alterd.isPresent());
		Optional<ColumnDesign> column = alterd.get().getColumns().stream()
				.filter(col -> col.getId().equals("2"))
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

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_changeColumnName() {
		alt.getContents().add(new ChangeColumnName("2", "ITEM_CODE"));
		Optional<TableDesign> alterd = createAltered(ss, alt);

		Assert.assertTrue(alterd.isPresent());
		Optional<ColumnDesign> column = alterd.get().getColumns().stream()
				.filter(col -> col.getId().equals("2"))
				.findFirst();
		Assert.assertTrue(column.isPresent() && column.get().getName().equals("ITEM_CODE"));

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_changeColumnJpName() {
		alt.getContents().add(new ChangeColumnJpName("2", "★項目コード★"));
		Optional<TableDesign> alterd = createAltered(ss, alt);

		Assert.assertTrue(alterd.isPresent());
		Optional<ColumnDesign> column = alterd.get().getColumns().stream()
				.filter(col -> col.getId().equals("2"))
				.findFirst();
		Assert.assertTrue(column.isPresent() && column.get().getJpName().equals("★項目コード★"));

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_changeColumnComment() {
		alt.getContents().add(new ChangeColumnComment("2", "こめんと"));
		Optional<TableDesign> alterd = createAltered(ss, alt);

		Assert.assertTrue(alterd.isPresent());
		Optional<ColumnDesign> column = alterd.get().getColumns().stream()
				.filter(col -> col.getId().equals("2"))
				.findFirst();
		Assert.assertTrue(column.isPresent() && column.get().getComment().equals("こめんと"));

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_delColumn() {
		alt.getContents().add(new RemoveColumn("2"));
		Optional<TableDesign> alterd = createAltered(ss, alt);

		Assert.assertTrue(alterd.isPresent());
		Optional<ColumnDesign> column = alterd.get().getColumns().stream()
				.filter(col -> col.getId().equals("2"))
				.findFirst();
		Assert.assertFalse(column.isPresent());

		val result = factory.create(tableName, meta, base, alterd);

		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	private Snapshot createSnapshot() {
		return new Snapshot(
					"root",
					"",
					createDummy()
				);
	}

	private Optional<TableDesign> createAltered(Snapshot base, Alteration alt) {
		Optional<TableProspect> altered = base.createTableProspect(Arrays.asList(alt));
		return altered.isPresent()
				? Optional.of((TableDesign)altered.get())
				: Optional.empty();
	}

	private TableDesign createDummy() {
		List<ColumnDesign> cols = new ArrayList<>();
		List<Indexes> indexes = new ArrayList<>();
		DefineColumnType sidType = new DefineColumnType(DataType.CHAR, 36, 0, false, "", "");
		DefineColumnType ymdType = new DefineColumnType(DataType.DATE, 0, 0, false, "", "");
		DefineColumnType itemCdType = new DefineColumnType(DataType.CHAR, 0, 0, false, "", "");
		cols.add(new ColumnDesign("0", "SID", "社員ID", sidType, "", 0));
		cols.add(new ColumnDesign("1", "YMD", "年月日", ymdType, "", 1));
		cols.add(new ColumnDesign("2", "ITEM_CD", "項目コード", itemCdType, "", 2));
		indexes.add(Indexes.createPk(new TableName(tableName), Arrays.asList("SID", "YMD"), true));
		indexes.add(Indexes.createIndex("KRCDI_FOO_BAR", Arrays.asList("SID", "YMD"), false));

		return new TableDesign(
			tableName, tableName, "ほげほげ",
			cols,
			indexes);
	}
}
