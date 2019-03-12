package nts.uk.file.com.app.role.employment;


import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

@Stateless
@DomainID(value = "RoleEmployment")
public class RoleEmploymentExportImpl implements MasterListData {
    @Inject
    private RoleEmpExportRepository mRoleEmpExportRepository;

    public static final String CAS005_122 = "コード";
    public static final String CAS005_123 = "名称";
    public static final String CAS005_124 = "担当区分";
    public static final String CAS005_125 = "社員１参照範囲";
    public static final String CAS005_126 = "未来日参照権限";
    public static final String CAS005_127 = "メニュー設定";
    public static final String CAS005_128 = "スケジュール画面社員１参照";
    public static final String FUNCTION_NO_ = "FUNCTION_NO_";
    private static final int ROLE_TYPE_CAS005 = 3;

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(CAS005_122, TextResource.localize("CAS005_122"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CAS005_123, TextResource.localize("CAS005_123"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CAS005_124, TextResource.localize("CAS005_124"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CAS005_125, TextResource.localize("CAS005_125"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CAS005_126, TextResource.localize("CAS005_126"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CAS005_127, TextResource.localize("CAS005_127"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CAS005_128, TextResource.localize("CAS005_128"),
                ColumnTextAlign.LEFT, "", true));
        Map<Integer,String>  allFunctionNo = mRoleEmpExportRepository.findAllFunctionNo();
        for(int key : allFunctionNo.keySet()){
            columns.add(
                    new MasterHeaderColumn(FUNCTION_NO_ +key ,allFunctionNo.get(key),
                            ColumnTextAlign.LEFT, "", true));
        }
        return columns;
    }

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        String companyId = AppContexts.user().companyId();
        return mRoleEmpExportRepository.findAllRoleEmployment(ROLE_TYPE_CAS005,companyId);
    }
    
	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}
}
