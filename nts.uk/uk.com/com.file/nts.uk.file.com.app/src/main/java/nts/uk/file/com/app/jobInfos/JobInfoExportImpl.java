package nts.uk.file.com.app.jobInfos;

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
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

@Stateless
@DomainID("JobInfo")
public class JobInfoExportImpl implements MasterListData{
	
	@Inject
	private JobInfoRepository repository;
	
	
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(
				new MasterHeaderColumn(JobInfoColumn.CAS014_41, TextResource.localize("CAS014_41"), 
						ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(JobInfoColumn.CAS014_42, TextResource.localize("CAS014_42"), 
						ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(JobInfoColumn.CAS014_43, TextResource.localize("CAS014_43"), 
						ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(JobInfoColumn.CAS014_44, TextResource.localize("CAS014_44"), 
						ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	public List<MasterHeaderColumn> getHeaderColumnsRoleSetEmp() {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(
				new MasterHeaderColumn(JobInfoColumn.CAS014_45, TextResource.localize("CAS014_45"), 
						ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(JobInfoColumn.CAS014_46, TextResource.localize("CAS014_46"), 
						ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(JobInfoColumn.CAS014_47, TextResource.localize("CAS014_47"), 
						ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(JobInfoColumn.CAS014_48, TextResource.localize("CAS014_48"), 
						ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(JobInfoColumn.CAS014_49, TextResource.localize("CAS014_49"), 
						ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(JobInfoColumn.CAS014_50, TextResource.localize("CAS014_50"), 
						ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		datas = repository.getDataRoleSetPosExport();
		return datas;
	}
	
	public List<MasterData> getMasterDatasRoleSetEmp(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		String baseDate =  query.getBaseDate().toString();
		datas = repository.getDataRoleSetEmpExport(baseDate);
		return datas;
	}
	
	@Override
    public List<SheetData> extraSheets(MasterListExportQuery query) {
    	
        List<SheetData> sheetDatas = new ArrayList<>();
        
        SheetData sheetData = SheetData.builder()
                .mainData(this.getMasterDatasRoleSetEmp(query))
                .mainDataColumns(this.getHeaderColumnsRoleSetEmp())
                .sheetName(TextResource.localize("CAS014_52"))
                .mode(MasterListMode.BASE_DATE)
                .build();

        sheetDatas.add(sheetData);
        return sheetDatas;
    }
	
	@Override
	public String mainSheetName(){
		return TextResource.localize("CAS014_51");
	}

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}
}
