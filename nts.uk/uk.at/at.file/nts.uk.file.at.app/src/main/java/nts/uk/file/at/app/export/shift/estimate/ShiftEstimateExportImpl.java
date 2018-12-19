package nts.uk.file.at.app.export.shift.estimate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

@Stateless
@DomainID("ShiftEstimate")
public class ShiftEstimateExportImpl implements MasterListData {

	@Inject
	private ShiftEstimateRepository repository;

	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_101, TextResource.localize("KSM001_101"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_101_1, TextResource.localize("KSM001_101_1"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_101_2, TextResource.localize("KSM001_101_2"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_102, TextResource.localize("KSM001_102"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}

	// sheet 1
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		// String date = query.getOption().toString();
		datas = repository.getDataExport();
		return datas;
	}

	// header sheet2
	private List<MasterHeaderColumn> getHeaderColumnsTwo() {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_112, TextResource.localize("KSM001_112"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_112_1, TextResource.localize("KSM001_112_1"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_112_2, TextResource.localize("KSM001_112_2"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_113, TextResource.localize("KSM001_113"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}

	// sheet 2
	private List<MasterData> getMasterDatasTwo() {
		return repository.getDataSheetTwoExport();
	}

	// header sheet3
	private List<MasterHeaderColumn> getHeaderColumnsThree() {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_141, TextResource.localize("KSM001_141"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_142, TextResource.localize("KSM001_142"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_143, TextResource.localize("KSM001_143"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_144, TextResource.localize("KSM001_144"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_145, TextResource.localize("KSM001_145"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_146, TextResource.localize("KSM001_146"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_147, TextResource.localize("KSM001_147"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_148, TextResource.localize("KSM001_148"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_149, TextResource.localize("KSM001_149"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_150, TextResource.localize("KSM001_150"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_151, TextResource.localize("KSM001_151"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_152, TextResource.localize("KSM001_152"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_153, TextResource.localize("KSM001_153"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_154, TextResource.localize("KSM001_154"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_155, TextResource.localize("KSM001_155"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_156, TextResource.localize("KSM001_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(ShiftEstimateColumn.KSM001_157, TextResource.localize("KSM001_157"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}

	// sheet 3
	private List<MasterData> getMasterDatasThree(String startDate, String endDate) {
		return repository.getDataSheetThreeExport(startDate, endDate);
	}

	@Override
	public List<SheetData> extraSheets(MasterListExportQuery query) {
		@SuppressWarnings("unchecked")
		Map<String, String> period = (HashMap<String, String>)query.getOption();
		String startDate = period.get("startDate");
		String endDate = period.get("endDate");
		List<SheetData> sheetDatas = new ArrayList<>();
		SheetData sheetData2 = SheetData.builder().mainData(this.getMasterDatasTwo())
				.mainDataColumns(this.getHeaderColumnsTwo()).sheetName(TextResource.localize("KSM001_197")).build();

		SheetData sheetData3 = SheetData.builder().mainData(this.getMasterDatasThree(startDate, endDate))
				.mainDataColumns(this.getHeaderColumnsThree()).sheetName(TextResource.localize("KSM001_198")).build();

		sheetDatas.add(sheetData2);
		sheetDatas.add(sheetData3);
		return sheetDatas;
	}

	@Override
	public String mainSheetName() {
		return TextResource.localize("KSM001_196");
	}

}
