package nts.uk.cnv.dom.td.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableName;
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
	private final String userName = "ai_muto";
	private final String tableName = "KRCDT_FOO_BAR";
	private final AlterationMetaData meta = new AlterationMetaData(
			featureId,
			userName,
			"おるたこめんと"
		);

	@Test
	public void test_AddTable() {

		Optional<TableDesign> base = createNewstSnapshot();
		Alteration alt = Alteration.createEmpty(tableName, meta);
		alt.getContents().addAll(AlterationType.TABLE_CREATE.createContent(Optional.empty(), base));

		new Expectations() {{
			require.getNewest((String) featureId);
			result = Optional.empty();

			require.getMetaData();
			result = meta;

			factory.create(tableName, meta, Optional.empty(), base);
			result = alt;

			require.add((Alteration) alt);
		}};

		val atomTask = target.alter(require, tableName, base);

	}

	@Test
	public void test_RenameTable() {

		Optional<TableDesign> base = createNewstSnapshot();
		Alteration alt = Alteration.createEmpty(tableName, meta);
		alt.getContents().add(new ChangeTableName(tableName + "_NEW"));

		Optional<TableDesign> altered = createAltered(base, alt);

		new Expectations() {{
			require.getNewest((String) featureId);
			result = createNewstSnapshot();

			require.getMetaData();
			result = meta;

			factory.create(tableName, meta, Optional.empty(), base);
			result = alt;

			require.add((Alteration) any);
		}};

		val atomTask = target.alter(require, tableName, altered);

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
		TableDesign altered = base.get().applyAlteration(Arrays.asList(alt))
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("変更がありません")));
		return Optional.of(altered);
	}

	private TableDesign createDummy() {
		List<ColumnDesign> cols = new ArrayList<>();
		List<Indexes> indexes = new ArrayList<>();
		DefineColumnType sidType = new DefineColumnType(DataType.CHAR, 36, 0, false, "", "");
		DefineColumnType ymdType = new DefineColumnType(DataType.DATE, 0, 0, false, "", "");
		cols.add(new ColumnDesign("0", "SID", "社員ID", sidType, true, 1, false, 0, "", 0));
		cols.add(new ColumnDesign("1", "YMD", "年月日", ymdType, true, 2, false, 0, "", 1));
		indexes.add(Indexes.createPk(new TableName(tableName), Arrays.asList("SID", "YMD"), true));
		indexes.add(Indexes.createIndex("KRCDI_FOO_BAR", Arrays.asList("SID", "YMD"), false));

		return new TableDesign(
			"KRCDT_FOO_BAR", "KRCDT_FOO_BAR", "",
			cols,
			indexes);
	}
}
