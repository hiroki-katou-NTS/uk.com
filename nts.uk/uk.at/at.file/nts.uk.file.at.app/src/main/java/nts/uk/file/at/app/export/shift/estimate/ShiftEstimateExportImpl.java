package nts.uk.file.at.app.export.shift.estimate;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

@Stateless
@DomainID("JobInfo")
public class ShiftEstimateExportImpl implements MasterListData{
	
	@Inject
	private ShiftEstimateRepository repository;
	
	
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
//		columns.add(
//				new MasterHeaderColumn(ShiftEstimateColumn., TextResource.localize("CAS014_41"), 
//						ColumnTextAlign.LEFT, "", true));
//		columns.add(
//				new MasterHeaderColumn(ShiftEstimateColumn., TextResource.localize("CAS014_42"), 
//						ColumnTextAlign.LEFT, "", true));
//		columns.add(
//				new MasterHeaderColumn(ShiftEstimateColumn., TextResource.localize("CAS014_43"), 
//						ColumnTextAlign.LEFT, "", true));
//		columns.add(
//				new MasterHeaderColumn(ShiftEstimateColumn., TextResource.localize("CAS014_44"), 
//						ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	//sheet 1
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		String date = query.getOption().toString();
		datas = repository.getDataExport(date);
		return datas;
	}
	
	// header sheet2
	private List<MasterHeaderColumn> getHeaderColumnsTwo() {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		// columns.add(new MasterHeaderColumn(ShiftEstimateColumn.,
		// TextResource.localize(""),
		// ColumnTextAlign.LEFT, "", true));

		return columns;
	}
	
	//sheet 2
	private List<MasterData> getMasterDatasTwo(String date) {
		return repository.getDataExport(date);
	}

	// header sheet3
	private List<MasterHeaderColumn> getHeaderColumnsThree() {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		// columns.add(new MasterHeaderColumn(ShiftEstimateColumn.,
		// TextResource.localize(""),
		// ColumnTextAlign.LEFT, "", true));

		return columns;
	}
	// sheet 3
	private List<MasterData> getMasterDatasThree(String date) {
		return repository.getDataExport(date);
	}
	
	@Override
    public List<SheetData> extraSheets(MasterListExportQuery query) {
		String date = query.getOption().toString();
        List<SheetData> sheetDatas = new ArrayList<>();
        SheetData sheetData2 = SheetData.builder()
                .mainData(this.getMasterDatasTwo(date))
                .mainDataColumns(this.getHeaderColumnsTwo())
                .sheetName(TextResource.localize("KSM001_197"))
                .build();

        SheetData sheetData3 = SheetData.builder()
                .mainData(this.getMasterDatasThree(date))
                .mainDataColumns(this.getHeaderColumnsThree())
                .sheetName(TextResource.localize("KSM001_198"))
                .build();

        sheetDatas.add(sheetData2);
        sheetDatas.add(sheetData3);
        return sheetDatas;
    }
	
	@Override
	public String mainSheetName(){
		return TextResource.localize("KSM001_196");
	}
	
}
