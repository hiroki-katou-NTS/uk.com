package nts.uk.screen.at.app.kmk.kmk017.worktimeworkplace;

import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;
import nts.uk.screen.at.app.kmk.kmk008.company.AgreementTimeOfCompanyDto;
import nts.uk.screen.at.app.kmk.kmk008.company.RequestCompany;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * 就業時間帯の割り付けの初期画面を表示する
 */
@Stateless
public class InitiaDisplayScreenProcessor {

    @Inject
    private WorkManagementMultipleRepository workMultipleRepository;

    @Inject
    private WorkTimeSettingProcessor workTimeSettingProcessor;

    @Inject
    private WorkTimeWorkplaceProcessor workTimeWorkplaceProcessor;

    public AgreementTimeOfCompanyDto findAgreeTimeOfCompany(RequestCompany requestCompany) {

        String cid = AppContexts.user().companyId();

        //1: get(ログイン会社ID) : 複数回勤務管理
        Optional<WorkManagementMultiple> workMultiple = workMultipleRepository.findByCode(cid);

        //2: call 会社で利用できる就業時間帯を取得する
        List<WorkTimeSettingDto> workTimeSettingDtos = workTimeSettingProcessor.findWorkTimeSetting();

        //3: call 職場リストを表示する
        List<WorkTimeWorkplaceDto> workTimeWorkplaceDtos = workTimeWorkplaceProcessor.findAgreeTimeOfCompany();

        //TODO
        return null;
    }
}
