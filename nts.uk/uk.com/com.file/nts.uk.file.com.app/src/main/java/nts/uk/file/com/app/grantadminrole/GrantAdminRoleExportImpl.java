package nts.uk.file.com.app.grantadminrole;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.*;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.sql.Date;
import java.util.*;

@Stateless
@DomainID("GrantAdminRole")
public class GrantAdminRoleExportImpl implements MasterListData {

    @Inject
    private GrantAdminRoleRepository grantAdminRoleRepository;

    private static final String COMPANY_ID_SYSADMIN = "000000000000-0000";

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        GeneralDate date = query.getBaseDate();
        return grantAdminRoleRepository.getDataExport(COMPANY_ID_SYSADMIN,RoleType.SYSTEM_MANAGER.value,new Date(date.date().getTime()));
    }

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
        List <MasterHeaderColumn> columns = new ArrayList<>();

        columns.add(new MasterHeaderColumn(GrantAdminRoleColumn.CAS012_37, TextResource.localize("CAS012_37"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(GrantAdminRoleColumn.CAS012_38, TextResource.localize("CAS012_38"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(GrantAdminRoleColumn.CAS012_39, TextResource.localize("CAS012_39"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(GrantAdminRoleColumn.CAS012_40, TextResource.localize("CAS012_40"),
                ColumnTextAlign.LEFT, "", true));

        return columns;
    }

    @Override
    public String mainSheetName(){
		return TextResource.localize("CAS012_44");
	}

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.BASE_DATE;
	}
	
    private List<MasterData> getMasterDatasCompanyManager(MasterListExportQuery query){
        GeneralDate date = query.getBaseDate();
        return grantAdminRoleRepository.getDataExportCompanyManagerMode(RoleType.COMPANY_MANAGER.value,new Date(date.date().getTime()));
    }

    private List<MasterHeaderColumn> getHeaderColumnsCompanyManager(){
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(GrantAdminRoleColumn.CAS012_41, TextResource.localize("CAS012_41"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(GrantAdminRoleColumn.CAS012_42, TextResource.localize("CAS012_42"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(GrantAdminRoleColumn.CAS012_37, TextResource.localize("CAS012_37"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(GrantAdminRoleColumn.CAS012_38, TextResource.localize("CAS012_38"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(GrantAdminRoleColumn.CAS012_39, TextResource.localize("CAS012_39"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(GrantAdminRoleColumn.CAS012_40, TextResource.localize("CAS012_40"),
                ColumnTextAlign.LEFT, "", true));

        return columns;
    }

    private List<MasterData> getMasterDatasGroupCompanyManager(MasterListExportQuery query){
        GeneralDate date = query.getBaseDate();
        return grantAdminRoleRepository.getDataExport(COMPANY_ID_SYSADMIN,RoleType.GROUP_COMAPNY_MANAGER.value,new Date(date.date().getTime()));
    }

    @Override
    public List<SheetData> extraSheets(MasterListExportQuery query) {
    	
        List<SheetData> sheetDatas = new ArrayList<>();
   
        SheetData sheetData1 = SheetData.builder()
                .mainData(this.getMasterDatasCompanyManager(query))
                .mainDataColumns(this.getHeaderColumnsCompanyManager())
                .sheetName(TextResource.localize("CAS012_45"))
                .mode(MasterListMode.BASE_DATE)
                .build();

        SheetData sheetData2 = SheetData.builder()
                .mainData(this.getMasterDatasGroupCompanyManager(query))
                .mainDataColumns(this.getHeaderColumns(query))
                .sheetName(TextResource.localize("CAS012_46"))
                .mode(MasterListMode.BASE_DATE)
                .build();

        sheetDatas.add(sheetData1);
        sheetDatas.add(sheetData2);
        return sheetDatas;
    }
}
