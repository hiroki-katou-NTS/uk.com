package nts.uk.screen.at.app.kmk.kmk017.worktimeworkplace;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplace;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplaceRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 職場で利用できる就業時間帯を表示する
 */
@Stateless
public class ChooseWorkplaceProcessor {

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepository;

    @Inject
    private WorkTimeWorkplaceRepository workTimeWorkplaceRepository;

    @Inject
    private PredetemineTimeSettingRepository settingRepository;

    public List<ChooseWorkplaceDto> getWorkingHoursAtWork(RequestPrams requestPrams) {

        String cid = AppContexts.user().companyId();
        //1: get(ログイン会社ID,職場ID) : 職場割り当て就業時間帯
        Optional<WorkTimeWorkplace> workTimeWorkplace = workTimeWorkplaceRepository.getByCIdAndWkpId(cid, requestPrams.getWorkplaceId());

        //2: [Not 職場割り当て就業時間帯.Empty] : <call> 職場割り当て就業時間帯.利用就業時間帯
        if (!workTimeWorkplace.isPresent())
            return null;

        List<String> lstCode = workTimeWorkplace.get().getWorkTimeCodes().stream().map(x -> x.v()).collect(Collectors.toList());

        //2.1 : get ログイン会社ID,就業時間帯コード（List）
        List<WorkTimeSetting> workTimeSettings = workTimeSettingRepository.findByCodes(cid, lstCode);

        //2.2 : get ログイン会社ID,就業時間帯コード（List）
        List<PredetemineTimeSetting> predetemineTimeSettings = settingRepository.findByCodeList(cid, lstCode);

        return lstCode.stream().map(x -> {
            WorkTimeSetting workTimeSetting = workTimeSettings.stream().filter(i -> i.getWorktimeCode().v().equals(x)).findFirst().orElse(null);
            TimezoneUseDto timezoneUse1 = null;
            TimezoneUseDto timezoneUse2 = null;
            PredetemineTimeSetting timeSetting = predetemineTimeSettings.stream().filter(i -> i.getWorkTimeCode().v().equals(x)).findFirst().orElse(null);
            if (timeSetting != null) {
                timezoneUse1 = timeSetting.getPrescribedTimezoneSetting().getLstTimezone().stream().
                    filter(i -> i.getWorkNo() == 1).findFirst().map(TimezoneUseDto::fromDomain).orElse(null);

                timezoneUse2 = timeSetting.getPrescribedTimezoneSetting().getLstTimezone().stream().
                    filter(i -> i.getWorkNo() == 2).findFirst().map(TimezoneUseDto::fromDomain).orElse(null);
            }
            return new ChooseWorkplaceDto(
                x,
                workTimeSetting == null ? null : workTimeSetting.getWorkTimeDisplayName().getWorkTimeName().v(),
                timezoneUse1,
                timezoneUse2,
                workTimeSetting == null ? null : workTimeSetting.getNote().v()
            );
        }).collect(Collectors.toList());
    }
}
