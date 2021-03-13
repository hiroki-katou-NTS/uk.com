package nts.uk.cnv.dom.td.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.gul.text.IdentifierUtil;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.tabledefinetype.DataType;
import nts.uk.cnv.dom.td.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.td.tabledesign.DefineColumnType;
import nts.uk.cnv.dom.td.tabledesign.Indexes;
import nts.uk.cnv.dom.td.tabledesign.Snapshot;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableName;

@RunWith(JMockit.class)
public class TableDesignServiceTest {

	@Injectable
	private TableDesignService.Require require;

	@Injectable
	private AlterationFactory factory;

	@Tested
	private TableDesignService target;

	private final String featureId = "root";
	private final String tableId = IdentifierUtil.randomUniqueId();
	private final String userName = "ai_muto";
	private final String tableName = "KRCDT_FOO_BAR";
	private final AlterationMetaData meta = new AlterationMetaData(
			featureId,
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
				new Snapshot(
					IdentifierUtil.randomUniqueId(),
					"createfeature",
					createDummy()
				));
	}

	private Optional<TableDesign> createAltered(Optional<TableDesign> base, Alteration alt) {
		TableDesign altered = base.get().applyAlteration(Arrays.asList(alt))
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
		indexes.add(Indexes.createPk(new TableName(tableName), Arrays.asList("SID", "YMD"), true));
		indexes.add(Indexes.createIndex("KRCDI_FOO_BAR", Arrays.asList("SID", "YMD"), false));

		return new TableDesign(
			"KRCDT_FOO_BAR", "KRCDT_FOO_BAR", "",
			cols,
			indexes);
	}
}
