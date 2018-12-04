package nts.uk.file.com.app.grantadminrole;

import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

@Stateless
@DomainID("GrantAdminRole")
public class GrantAdminRoleExportImpl implements MasterListData {

    @Inject
    GrantAdminRoleRepository grantAdminRoleRepository;

    private static final String COMPANY_ID_SYSADMIN = "000000000000-0000";

    private static final String TABLE_ONE = "table one";

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        LinkedHashMap<String, String> data = (LinkedHashMap<String, String>)query.getData();
        String companyId = data.get("companyId");
        String strRoleType = data.get("roleType");
        int roleType = Integer.parseInt(strRoleType);
        if ( roleType != RoleType.COMPANY_MANAGER.value)
            companyId = COMPANY_ID_SYSADMIN;
        return grantAdminRoleRepository.getDataExport(companyId,roleType);
    }

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(GrantAdminRoleColumn.CAS012_43, TextResource.localize("CAS012_43"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(GrantAdminRoleColumn.CAS012_44, TextResource.localize("CAS012_44"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(GrantAdminRoleColumn.CAS012_45, TextResource.localize("CAS012_45"),
                ColumnTextAlign.CENTER, "", true));
        columns.add(new MasterHeaderColumn(GrantAdminRoleColumn.CAS012_46, TextResource.localize("CAS012_46"),
                ColumnTextAlign.CENTER, "", true));
        return columns;
    }

    @Override
    public Map<String, List<MasterData>> getExtraMasterData(MasterListExportQuery query) {
        Map<String, Object> data = new HashMap<>();
        List<MasterData> datas = new ArrayList<>();
        Map<String, List<MasterData>> mapTableData = new LinkedHashMap<>();
        LinkedHashMap<String, String> param = (LinkedHashMap<String, String>)query.getData();
        String companyName = param.get("companyName");
        String roleTypeName = param.get("roleTypeName");
        data.put(GrantAdminRoleColumn.CAS012_41, roleTypeName);
        data.put(GrantAdminRoleColumn.CAS012_42, companyName);
        datas.add(new MasterData(data, null, ""));
        mapTableData.put(TABLE_ONE,datas);
        return mapTableData;
    }

    @Override
    public Map<String, List<MasterHeaderColumn>> getExtraHeaderColumn(MasterListExportQuery query) {
        List <MasterHeaderColumn> columns = new ArrayList<>();
        Map<String, List<MasterHeaderColumn>> mapHeaderColumn = new LinkedHashMap<>();
        columns.add(new MasterHeaderColumn(GrantAdminRoleColumn.CAS012_41, TextResource.localize("CAS012_41"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(GrantAdminRoleColumn.CAS012_42, TextResource.localize("CAS012_42"),
                ColumnTextAlign.CENTER, "", true));
        mapHeaderColumn.put(TABLE_ONE,columns);
        return mapHeaderColumn;
    }
}
