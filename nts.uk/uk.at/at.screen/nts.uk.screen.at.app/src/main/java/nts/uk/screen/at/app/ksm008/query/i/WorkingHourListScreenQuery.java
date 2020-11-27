package nts.uk.screen.at.app.ksm008.query.i;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.ConsecutiveWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeCompany;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeCompanyRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Screen I : 会社の就業時間帯の連続勤務上限明細を取得する
 * author :Md RafiqulIslam
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class WorkingHourListScreenQuery {

    @Inject
    private WorkTimeSettingRepository workTimeRepo;

    @Inject
    private MaxDaysOfContinuousWorkTimeCompanyRepository maxDaysOfContinuousWorkTimeCompanyRepository;

    public MaxDaysOfContinuousWorkTimeListDto get(String code) {
        /*就業時間帯情報リストを取得する*/
        Optional<MaxDaysOfContinuousWorkTimeCompany> maxDaysOfContinuousWorkTimeCompany = maxDaysOfContinuousWorkTimeCompanyRepository.get(AppContexts.user().companyId(), new ConsecutiveWorkTimeCode(code));
        if (!maxDaysOfContinuousWorkTimeCompany.isPresent()) {
            return new MaxDaysOfContinuousWorkTimeListDto();
        }
        /*就業時間帯コードリスト */
        List<String> workHourCodeList = new ArrayList<>();
        if (!maxDaysOfContinuousWorkTimeCompany.get().getMaxDaysContiWorktime().getWorkTimeCodes().isEmpty()) {
            workHourCodeList = maxDaysOfContinuousWorkTimeCompany
                    .get()
                    .getMaxDaysContiWorktime()
                    .getWorkTimeCodes()
                    .stream()
                    .map(item -> item.v())
                    .collect(Collectors.toList());
        }
        //就業時間帯情報を取得する
        List<WorkTimeSetting> workTimeSettingList = workTimeRepo
                .getListWorkTimeSetByListCode(AppContexts.user().companyId(), workHourCodeList);
        // working hours list
        List<WorkingHoursDTO> workhourList = workTimeSettingList
                .stream()
                .map(item -> new WorkingHoursDTO(item.getWorktimeCode().v(), item.getWorkTimeDisplayName().getWorkTimeName().v()))
                .collect(Collectors.toList());
        MaxDaysOfContinuousWorkTimeListDto dto = new MaxDaysOfContinuousWorkTimeListDto(
                maxDaysOfContinuousWorkTimeCompany.get().getCode().v(),
                maxDaysOfContinuousWorkTimeCompany.get().getName().v(),
                maxDaysOfContinuousWorkTimeCompany.get().getMaxDaysContiWorktime().getNumberOfDays().v(),
                workhourList
        );
        return dto;
    }

    public List<MaxDaysOfContinuousWorkTimeDto> getWortimeList() {
        List<MaxDaysOfContinuousWorkTimeCompany> list = maxDaysOfContinuousWorkTimeCompanyRepository.getAll(AppContexts.user().companyId());
        return list
                .stream()
                .map(item -> new MaxDaysOfContinuousWorkTimeDto(
                        item.getCode().v(),
                        item.getName().v(),
                        item.getMaxDaysContiWorktime().getNumberOfDays().v()
                ))
                .collect(Collectors.toList());
    }
}
