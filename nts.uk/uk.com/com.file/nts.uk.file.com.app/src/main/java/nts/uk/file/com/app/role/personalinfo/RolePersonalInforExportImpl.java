package nts.uk.file.com.app.role.personalinfo;

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
@DomainID(value = "RolePersonalInfor")
public class RolePersonalInforExportImpl implements MasterListData {
    @Inject
    private RolePersonalInforRepository mRolePersonalInforRepository;

    public static final String CAS009_23 = "コード";
    public static final String CAS009_24 = "名称";
    public static final String CAS009_25 = "担当区分";
    public static final String CAS009_26 = "社員１参照範囲";
    public static final String CAS009_27 = "未来日参照権限";
    public static final String FUNCTION_NO_ = "FUNCTION_NO_";
    private static final int ROLE_TYPE_CAS009 = 8;


    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(CAS009_23, TextResource.localize("CAS009_23"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CAS009_24, TextResource.localize("CAS009_24"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CAS009_25, TextResource.localize("CAS009_25"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CAS009_26, TextResource.localize("CAS009_26"),
                ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CAS009_27, TextResource.localize("CAS009_27"),
                ColumnTextAlign.LEFT, "", true));
        Map<Integer,String> listFunctionNo = mRolePersonalInforRepository.findAllFunctionNo();
        for(int key : listFunctionNo.keySet()){
            columns.add(
                    new MasterHeaderColumn(FUNCTION_NO_ +key ,listFunctionNo.get(key),
                            ColumnTextAlign.LEFT, "", true));
        }

        return columns;
    }

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        String companyId = AppContexts.user().companyId();
        return mRolePersonalInforRepository.findAllRolePersonalInfor(ROLE_TYPE_CAS009,companyId);
    }
    
	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}
}
