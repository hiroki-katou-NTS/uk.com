package nts.uk.cnv.dom.td.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.gul.text.IdentifierUtil;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.SaveAlteration;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;
import nts.uk.cnv.dom.td.schema.tabledesign.Indexes;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.PrimaryKey;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableIndex;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.UniqueConstraint;

@RunWith(JMockit.class)
public class TableDesignServiceTest {

	@Injectable
	private SaveAlteration.Require require;

	@Tested
	private SaveAlteration target;

	private final String featureId = "root";
	private final String tableId = IdentifierUtil.randomUniqueId();
	private final String userName = "ai_muto";
	private final String tableName = "KRCDT_FOO_BAR";
	private final AlterationMetaData meta = new AlterationMetaData(
			userName,
			"おるたこめんと"
		);

	@Test
	public void test_AddTable() {

//		Optional<TableDesign> base = createNewstSnapshot();
//		Alteration alt = Alteration.createEmpty(tableId, meta);
//		alt.getContents().addAll(AlterationType.TABLE_CREATE.createContent(Optional.empty(), base));
//
//		new Expectations() {{
//			require.getNewest(tableId);
//			result = Optional.empty();
//
//			factory.create(tableId, meta, Optional.empty(), base);
//			result = alt;
//
//			require.add(alt);
//		}};
//
//		val atomTask = target.alter(require, tableId, meta, base);

	}

	@Test
	public void test_RenameTable() {

//		Optional<TableDesign> base = createNewstSnapshot();
//		Alteration alt = Alteration.createEmpty(tableId, meta);
//		alt.getContents().add(new ChangeTableName(tableName + "_NEW"));
//
//		Optional<TableDesign> altered = createAltered(base, alt);
//
//		new Expectations() {{
//			require.getNewest((String) any);
//			result = createNewstSnapshot();
//
//			factory.create(tableId, meta, Optional.empty(), base);
//			result = alt;
//
//			require.add((Alteration) any);
//		}};
//
//		val atomTask = target.alter(require, tableId, meta, altered);

	}

	private Optional<TableDesign> createNewstSnapshot() {
		return Optional.of(
				new TableSnapshot(
					IdentifierUtil.randomUniqueId(),
					createDummy()
				));
	}

	private Optional<TableDesign> createAltered(TableSnapshot ss, Alteration alt) {
		TableDesign altered = ss.apply(Arrays.asList(alt))
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("変更がありません")));
		return Optional.of(altered);
	}

	private TableDesign createDummy() {
		List<ColumnDesign> cols = new ArrayList<>();
		List<Indexes> indexes = new ArrayList<>();
		DefineColumnType sidType = new DefineColumnType(DataType.CHAR, 36, 0, false, "", "");
		DefineColumnType ymdType = new DefineColumnType(DataType.DATE, 0, 0, false, "", "");
		cols.add(new ColumnDesign("0", "SID", "社員ID", sidType, "", 0));
		cols.add(new ColumnDesign("1", "YMD", "年月日", ymdType, "", 1));

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
