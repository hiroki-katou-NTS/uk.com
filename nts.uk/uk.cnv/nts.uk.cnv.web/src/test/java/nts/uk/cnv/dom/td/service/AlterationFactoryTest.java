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
import nts.uk.cnv.dom.td.alteration.content.ChangeTableJpName;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableName;
import nts.uk.cnv.dom.td.alteration.content.RemoveTable;
import nts.uk.cnv.dom.td.alteration.content.column.AddColumn;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnComment;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnJpName;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnName;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnType;
import nts.uk.cnv.dom.td.alteration.content.column.RemoveColumn;
import nts.uk.cnv.dom.td.alteration.content.constraint.ChangeIndex;
import nts.uk.cnv.dom.td.alteration.content.constraint.ChangePK;
import nts.uk.cnv.dom.td.alteration.content.constraint.ChangeUnique;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspect;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.PrimaryKey;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableIndex;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.UniqueConstraint;

@RunWith(JMockit.class)
public class AlterationFactoryTest {

	//private final String alterationId = IdentifierUtil.randomUniqueId();
	private final String featureId = "root";
	private final String userName = "ai_muto";
	private final String tableName = "KRCDT_FOO_BAR";
	private final AlterationMetaData meta = new AlterationMetaData(
			userName,
			"おるたこめんと"
		);

	TableSnapshot ss;
	Optional<TableDesign> base;
	Alteration alt;

	@Before
	public void initialize() {
		ss = createSnapshot();
		base = Optional.of((TableDesign) ss);
		alt = new Alteration(userName, featureId, GeneralDateTime.now(), tableName, meta, new ArrayList<>());
	}

	@Test
	public void test_AddTable() {
		Optional<TableProspect> empty = TableSnapshot.empty().apply(new ArrayList<>());
		val alterd = Optional.of((TableDesign) ss);
		alt.getContents().addAll(AlterationType.TABLE_CREATE.createContent(empty, alterd));

		val result = Alteration.create(featureId, tableName, meta, Optional.empty(), alterd);

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_RenameTable() {
		alt.getContents().add(new ChangeTableName("KRCDT_BAZ_QUX"));
		Optional<TableDesign> alterd = createAltered(ss, alt);

		val result = Alteration.create(featureId, tableName, meta, base, alterd);

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_RenameTableJpName() {
		alt.getContents().add(new ChangeTableJpName("ふがふが"));
		Optional<TableDesign> alterd = createAltered(ss, alt);

		val result = Alteration.create(featureId, tableName, meta, base, alterd);

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_ChangePK() {
		alt.getContents().add(new ChangePK(Arrays.asList("0", "1", "2"), false));
		Optional<TableDesign> alterd = createAltered(ss, alt);

		Assert.assertTrue(alterd.isPresent());
		val pk = alterd.get().getConstraints().getPrimaryKey();
		Assert.assertTrue(pk.getColumnIds().contains("2"));

		val result = Alteration.create(featureId, tableName, meta, base, alterd);

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_ChangeUnique() {
		alt.getContents().add(new ChangeUnique("UK1", Arrays.asList("0", "1", "2"), false));
		Optional<TableDesign> alterd = createAltered(ss, alt);

		Assert.assertTrue(alterd.isPresent());
		val uk = alterd.get().getConstraints().getUniqueConstraints().get(0);
		Assert.assertTrue(uk.getColumnIds().contains("2"));

		val result = Alteration.create(featureId, tableName, meta, base, alterd);

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_ChangeIndex() {
		alt.getContents().add(new ChangeIndex("IX1", Arrays.asList("0", "1", "2"), true));
		Optional<TableDesign> alterd = createAltered(ss, alt);

		Assert.assertTrue(alterd.isPresent());
		val index = alterd.get().getConstraints().getIndexes().get(0);
		Assert.assertTrue(index.getColumnIds().contains("2"));

		val result = Alteration.create(featureId, tableName, meta, base, alterd);

		Assert.assertTrue(result.isPresent());
		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	@Test
	public void test_RemoveTable() {
		alt.getContents().add(new RemoveTable());
		Optional<TableDesign> alterd = createAltered(ss, alt);

		Assert.assertFalse(alterd.isPresent());

		val result = Alteration.create(featureId, tableName, meta, base, alterd);

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

		val result = Alteration.create(featureId, tableName, meta, base, alterd);

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

		DefineColumnType resultColtype = new DefineColumnType(column.get().getType());
		Assert.assertEquals(resultColtype, coltype);

		val result = Alteration.create(featureId, tableName, meta, base, alterd);

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

		val result = Alteration.create(featureId, tableName, meta, base, alterd);

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

		val result = Alteration.create(featureId, tableName, meta, base, alterd);

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

		val result = Alteration.create(featureId, tableName, meta, base, alterd);

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

		val result = Alteration.create(featureId, tableName, meta, base, alterd);

		Assert.assertTrue(result.get().equalsExcludingId(alt));
	}

	private TableSnapshot createSnapshot() {
		return new TableSnapshot(
					"root",
					createDummy()
				);
	}

	private Optional<TableDesign> createAltered(TableSnapshot base, Alteration alt) {
		Optional<TableProspect> altered = base.apply(Arrays.asList(alt));
		return altered.isPresent()
				? Optional.of((TableDesign)altered.get())
				: Optional.empty();
	}

	private TableDesign createDummy() {
		List<ColumnDesign> cols = new ArrayList<>();
		DefineColumnType sidType = new DefineColumnType(DataType.CHAR, 36, 0, false, "", "");
		DefineColumnType ymdType = new DefineColumnType(DataType.DATE, 0, 0, false, "", "");
		DefineColumnType itemCdType = new DefineColumnType(DataType.CHAR, 0, 0, false, "", "");
		cols.add(new ColumnDesign("0", "SID", "社員ID", sidType, "", 0));
		cols.add(new ColumnDesign("1", "YMD", "年月日", ymdType, "", 1));
		cols.add(new ColumnDesign("2", "ITEM_CD", "項目コード", itemCdType, "", 2));
		
		return new TableDesign(
			tableName, tableName, "ほげほげ",
			cols,
			createTableConstraints());
	}
	
	private static TableConstraints createTableConstraints() {
		
		val pk = new PrimaryKey(Arrays.asList("SID", "YMD"), true);
		val unique = Arrays.asList(new UniqueConstraint("UK1", Arrays.asList("SID", "YMD"), false));
		val index = Arrays.asList(new TableIndex("IX1", Arrays.asList("SID", "YMD"), false));
		
		return new TableConstraints(pk, unique, index);
	}
}
