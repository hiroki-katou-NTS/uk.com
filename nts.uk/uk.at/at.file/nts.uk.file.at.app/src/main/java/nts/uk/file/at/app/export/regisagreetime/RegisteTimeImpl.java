package nts.uk.file.at.app.export.regisagreetime;

import java.util.ArrayList;
import java.util.List;

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
@DomainID("RegisterTime")
public class RegisteTimeImpl implements MasterListData {
	
	@Inject
	private RegistTimeRepository registTimeRepository;
	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		 List <MasterHeaderColumn> columns = new ArrayList<>();

	        columns.add(new MasterHeaderColumn(RegistTimeColumn.KMK008_80, TextResource.localize("KMK008_80"),
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RegistTimeColumn.HEADER_NONE1,"",
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RegistTimeColumn.HEADER_NONE2, "",
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RegistTimeColumn.KMK008_81, TextResource.localize("KMK008_81"),
	                ColumnTextAlign.LEFT, "", true));
	        return columns;
	}
	
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		return registTimeRepository.getDataExportSheet1();
	}
	
	@Override
	public String mainSheetName() {
		return TextResource.localize("KMK008_70");
	}
	
	public List<MasterHeaderColumn> getHeaderColumnsSheet2() {
		 List <MasterHeaderColumn> columns = new ArrayList<>();

	        columns.add(new MasterHeaderColumn(RegistTimeColumn.KMK008_89, TextResource.localize("KMK008_89"),
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RegistTimeColumn.KMK008_90,TextResource.localize("KMK008_90"),
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RegistTimeColumn.KMK008_91, TextResource.localize("KMK008_91"),
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RegistTimeColumn.KMK008_92, TextResource.localize("KMK008_92"),
	                ColumnTextAlign.LEFT, "", true));
	        return columns;
	}
	
	public List<MasterData> getMasterDatasSheet2() {
		return registTimeRepository.getDataExportSheet2();
	}
	
	public List<MasterHeaderColumn> getHeaderColumnsSheet3() {
		 List <MasterHeaderColumn> columns = new ArrayList<>();

	        columns.add(new MasterHeaderColumn(RegistTimeColumn.KMK008_100, TextResource.localize("KMK008_100"),
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RegistTimeColumn.KMK008_101,TextResource.localize("KMK008_101"),
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RegistTimeColumn.KMK008_89, TextResource.localize("KMK008_89"),
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RegistTimeColumn.KMK008_90, TextResource.localize("KMK008_90"),
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RegistTimeColumn.KMK008_91, TextResource.localize("KMK008_91"),
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RegistTimeColumn.KMK008_92, TextResource.localize("KMK008_92"),
	                ColumnTextAlign.LEFT, "", true));
	        return columns;
	}
	
	public List<MasterData> getMasterDatasSheet3() {
		return registTimeRepository.getDataExportSheet3();
	}
	
	@Override
    public List<SheetData> extraSheets(MasterListExportQuery query) {
        List<SheetData> sheetDatas = new ArrayList<>();
        SheetData sheetData1 = SheetData.builder()
        		 .mainData(this.getMasterDatasSheet2())
                .mainDataColumns(this.getHeaderColumnsSheet2())
                .sheetName(TextResource.localize("KMK008_71"))
                .build();
        SheetData sheetData2 = SheetData.builder()
       		 .mainData(this.getMasterDatasSheet3())
               .mainDataColumns(this.getHeaderColumnsSheet3())
               .sheetName(TextResource.localize("KMK008_72"))
               .build();

        sheetDatas.add(sheetData1);
        sheetDatas.add(sheetData2);
        return sheetDatas;
    }
	
}
