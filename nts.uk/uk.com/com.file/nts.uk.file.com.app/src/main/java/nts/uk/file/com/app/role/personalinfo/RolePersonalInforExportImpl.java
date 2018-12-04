package nts.uk.file.com.app.role.personalinfo;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
@DomainID(value = "RolePersonalInfor")
public class RolePersonalInforExportImpl implements MasterListData {
    @Inject
    private RolePersonalInforRepository mRolePersonalInforRepository;
    @Inject
    private List<MasterData> masterData;

    public RolePersonalInforExportImpl() {
        String companyId = AppContexts.user().companyId();
        masterData = mRolePersonalInforRepository.findAllRolePersonalInfor(ROLE_TYPE_CAS009,companyId);
    }
    //    @Inject
//    private PersonInfoAuthDescription mPersonInfoAuthDescriptions;

    private static final String CAS009_23 = "コードカラム";
    private static final String CAS009_24 = "名称カラム";
    private static final String CAS009_25 = "担当区分カラム";
    private static final String CAS009_26 = "社員１参照範囲カラム";
    private static final String CAS009_27 = "未来日参照権限カラム";
    private static final String FUNCTION_NO_ = "FUNCTION_NO_";
    private static final int ROLE_TYPE_CAS009 = 8;

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
//        mPersonInfoAuthDescriptions = mPerInfoAuthDescRepository.getListDesc();
        columns.add(
                new MasterHeaderColumn(CAS009_23, TextResource.localize("CAS009_23"), ColumnTextAlign.LEFT, "", true));
        columns.add(
                new MasterHeaderColumn(CAS009_24, TextResource.localize("CAS009_24"), ColumnTextAlign.LEFT, "", true));
        columns.add(
                new MasterHeaderColumn(CAS009_25, TextResource.localize("CAS009_25"), ColumnTextAlign.LEFT, "", true));
        columns.add(
                new MasterHeaderColumn(CAS009_26, TextResource.localize("CAS009_26"), ColumnTextAlign.LEFT, "", true));
        columns.add(
                new MasterHeaderColumn(CAS009_27, TextResource.localize("CAS009_27"), ColumnTextAlign.CENTER, "", true));
        for (int i = masterData.size() -5 ; i < masterData.size() ; i++ ) {
            columns.add(
                    new MasterHeaderColumn(FUNCTION_NO_ +i , masterData.get(i).getDatas().get(i)[i],
                            ColumnTextAlign.CENTER, "", true));
        }
        return columns;
    }

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        String companyId = AppContexts.user().companyId();
        List<MasterData> datas = new ArrayList<>();
//        List<Role> mRoles = mRoleRepository.findByType(companyId, ROLE_TYPE_CAS009);
//        mRoles.get(0).getR
//        List<PersonInfoAuthority> authorityMap = mPersonInfoAuthorityRepository.getListOfRoleByCid(companyId);
//        if (mRoles.isEmpty()) {
//            throw new BusinessException("Msg_7");
//        } else {
//            mRoles.stream().forEach(itemRole -> {
//                Map<String, Object> data = new HashMap<>();
//                data.put(CAS009_23, itemRole.getRoleId());
//                data.put(CAS009_24, itemRole.getRoleCode());
//                data.put(CAS009_25, itemRole.getRoleType());
//                data.put(CAS009_26, itemRole.getEmployeeReferenceRange());
//                data.put(CAS009_27, (mPersonRoleAdapter.find(itemRole.getRoleId()).get().getReferFutureDate() == true) ? "○" : "ー");
//
//                if (!mPersonInfoAuthDescriptions.isEmpty()) {
//                    List<WorkPlaceFunction> workPlaceFunction = workPlaceFunctionRepository.getAllWorkPlaceFunction();
//                    if (!workPlaceFunction.isEmpty()) {
//                        for (WorkPlaceFunction item : workPlaceFunction) {
//                            Boolean availability = authorityMap.stream()
//                                    .filter(x -> x.getRoleId().equals(itemRole.getRoleId()) && x.getFunctionNo() == item.getFunctionNo().v()).findFirst().get().isAvailable();
//                            if (Objects.isNull(availability) || !availability) {
//                                data.put(FUNCTION_NO_ + item.getFunctionNo().v(), "ー");
//                            } else {
//                                data.put(FUNCTION_NO_ + item.getFunctionNo().v(), "○");
//                            }
//                        }
//                    }
//                }
//
//                datas.add(new MasterData(data, null, ""));
//            });
//        }
        return datas;
    }
}
