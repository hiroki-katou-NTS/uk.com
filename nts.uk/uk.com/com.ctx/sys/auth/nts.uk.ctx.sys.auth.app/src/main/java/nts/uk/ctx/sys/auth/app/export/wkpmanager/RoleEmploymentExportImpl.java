package nts.uk.ctx.sys.auth.app.export.wkpmanager;

import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.auth.dom.adapter.role.employment.RoleEmploymentAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.role.employment.EmploymentRolePubDto;
import nts.uk.ctx.sys.auth.dom.adapter.webmenu.WebMenuAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.webmenu.WebMenuExport;
import nts.uk.ctx.sys.auth.dom.export.wkpmanager.RoleEmploymentExportData;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleByRoleAdapter;
import nts.uk.ctx.sys.auth.dom.wplmanagementauthority.WorkPlaceAuthority;
import nts.uk.ctx.sys.auth.dom.wplmanagementauthority.WorkPlaceAuthorityRepository;
import nts.uk.ctx.sys.auth.dom.wplmanagementauthority.WorkPlaceFunction;
import nts.uk.ctx.sys.auth.dom.wplmanagementauthority.WorkPlaceFunctionRepository;
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
import java.util.*;

@Stateless
@DomainID(value = "RoleEmployment")
public class RoleEmploymentExportImpl implements MasterListData {


    @Inject
    private RoleRepository mRoleRepository;

    @Inject
    private RoleEmploymentAdapter employmentAdapter;

    @Inject
    private WorkPlaceFunctionRepository workPlaceFunctionRepository;

    @Inject
    private WebMenuAdapter menuRepository;

    @Inject
    private WorkPlaceAuthorityRepository mWorkPlaceAuthorityRepository;

    @Inject
    private RoleByRoleAdapter mRoleByRoleAdapter;

    @Inject
    private WorkPlaceAuthorityRepository workPlaceAuthorityRepository;

    private static final String CAS005_122 = "コードカラム";
    private static final String CAS005_123 = "名称カラム";
    private static final String CAS005_124 = "担当区分カラム";
    private static final String CAS005_125 = "社員１参照範囲カラム";
    private static final String CAS005_126 = "未来日参照権限カラム";
    private static final String CAS005_127 = "メニュー設定カラム";
    private static final String CAS005_128 = "スケジュール画面社員１参照カラム";

    private static final String FUNCTION_NO_ = "FUNCTION_NO_";

    private static final int ROLE_TYPE_CAS005 = 3;

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        List<WorkPlaceFunction> workPlaceFunction = workPlaceFunctionRepository.getAllWorkPlaceFunction();
        columns.add(
                new MasterHeaderColumn(CAS005_122, TextResource.localize("CAS005_122"), ColumnTextAlign.LEFT, "", true));
        columns.add(
                new MasterHeaderColumn(CAS005_123, TextResource.localize("CAS005_123"), ColumnTextAlign.LEFT, "", true));
        columns.add(
                new MasterHeaderColumn(CAS005_124, TextResource.localize("CAS005_124"), ColumnTextAlign.LEFT, "", true));
        columns.add(
                new MasterHeaderColumn(CAS005_125, TextResource.localize("CAS005_125"), ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CAS005_126, TextResource.localize("CAS005_126"), ColumnTextAlign.CENTER, "",
                true));
        columns.add(new MasterHeaderColumn(CAS005_127, TextResource.localize("CAS005_127"), ColumnTextAlign.CENTER, "",
                true));
        columns.add(new MasterHeaderColumn(CAS005_128, TextResource.localize("CAS005_128"), ColumnTextAlign.CENTER, "",
                true));
        for (WorkPlaceFunction item : workPlaceFunction) {
            columns.add(new MasterHeaderColumn(FUNCTION_NO_ + item.getFunctionNo().v(), item.getDisplayName().v(),
                    ColumnTextAlign.CENTER, "", true));
        }
        return columns;
    }

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        String languageId = query.getLanguageId();
        String companyId = AppContexts.user().companyId();
        String workplaceId = query.getData().toString();

        List<MasterData> datas = new ArrayList<>();
        List<RoleEmploymentExportData> employmentExportDataList = new ArrayList<RoleEmploymentExportData>();
        List<Role> mRoles = mRoleRepository.findByType(companyId,ROLE_TYPE_CAS005);
        List<EmploymentRolePubDto> mEmploymentRolePubDtos = employmentAdapter.getAllByCompanyId(companyId);
        List<WebMenuExport> menuExports = menuRepository.findByCompanyId(companyId);
        List<WorkPlaceAuthority> mWorkPlaceAuthorities = mWorkPlaceAuthorityRepository.getAllWorkPlaceAuthority(companyId);
        if (mRoles.isEmpty() || mEmploymentRolePubDtos.isEmpty() || menuExports.isEmpty() || mWorkPlaceAuthorities.isEmpty()) {
            throw new BusinessException("Msg_7");
        } else {
            mRoles.stream().forEach(itemRole -> {
                Map<String, Object> data = new HashMap<>();
                data.put(CAS005_122, itemRole.getRoleId());
                data.put(CAS005_123, itemRole.getRoleCode());
                data.put(CAS005_124, itemRole.getRoleType());
                data.put(CAS005_125, itemRole.getEmployeeReferenceRange());
                data.put(CAS005_126, mEmploymentRolePubDtos.stream().filter(x -> x.getRoleId().equals(itemRole.getRoleId())).findFirst().get().getFutureDateRefPermit());
                data.put(CAS005_127, menuExports.stream().filter(x -> x.getWebMenuCode().equals(mRoleByRoleAdapter.findByWebCodeByRoleId(itemRole.getRoleId()).get().getWebMenuCd())).findFirst().get().getWebMenuName());
                data.put(CAS005_128, mEmploymentRolePubDtos.stream().filter(x -> x.getRoleId().equals(itemRole.getRoleId())).findFirst().get().getScheduleEmployeeRef());
                List<WorkPlaceAuthority> workPlaceAuthority = workPlaceAuthorityRepository
                        .getAllWorkPlaceAuthorityByRoleId(companyId, itemRole.getRoleId());
                if (!workPlaceAuthority.isEmpty()) {
                    List<WorkPlaceFunction> workPlaceFunction = workPlaceFunctionRepository.getAllWorkPlaceFunction();
                    if (!workPlaceFunction.isEmpty()) {
                        for (WorkPlaceFunction item : workPlaceFunction) {
                            Boolean availability = workPlaceAuthority.stream()
                                    .filter(x -> x.getFunctionNo().v().equals(item.getFunctionNo().v())).findFirst()
                                    .map(x1 -> x1.isAvailability()).orElse(null);
                            if (Objects.isNull(availability) || !availability) {
                                data.put(FUNCTION_NO_ + item.getFunctionNo().v(), "ー");
                            } else {
                                data.put(FUNCTION_NO_ + item.getFunctionNo().v(), "○");
                            }
                        }
                    }
                }

                datas.add(new MasterData(data, null, ""));
            });
        }
        return datas;
    }
}
