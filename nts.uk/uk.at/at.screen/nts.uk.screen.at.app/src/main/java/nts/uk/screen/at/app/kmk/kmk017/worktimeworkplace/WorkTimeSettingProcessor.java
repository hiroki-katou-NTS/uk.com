package nts.uk.screen.at.app.kmk.kmk017.worktimeworkplace;

import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.screen.at.app.kmk.kmk008.company.RequestCompany;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 会社で利用できる就業時間帯を取得する
 */
@Stateless
public class WorkTimeSettingProcessor {

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepository;

    public List<WorkTimeSettingDto> findWorkTimeSetting() {

        //1: get(ログイン会社ID) : 就業時間帯の設定
        List<WorkTimeSetting> workMultiple = workTimeSettingRepository.findByCId(AppContexts.user().companyId());

        return workMultiple.stream().map(WorkTimeSettingDto::setData).collect(Collectors.toList());
    }
}
