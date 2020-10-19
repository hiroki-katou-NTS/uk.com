package nts.uk.screen.at.app.ksm008.query.k;

import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeCompany;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeCompanyRepo;
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
 * KSM008 K : 会社の就業時間帯の上限一覧を選択する
 *
 * @author rafiqul.islam
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class WorkingHourListCompanyScreenQuery {

    @Inject
    private WorkTimeSettingRepository workTimeRepo;

    @Inject
    private MaxDayOfWorkTimeCompanyRepo maxDayOfWorkTimeCompanyRepo;

    public MaxDaysOfWorkTimeComListDto get(String code) {

        /*就業時間帯情報リストを取得する*/
        Optional<MaxDayOfWorkTimeCompany> maxDayOfWorkTimeCompany = maxDayOfWorkTimeCompanyRepo.get(AppContexts.user().companyId(), new MaxDayOfWorkTimeCode(code));

        /*就業時間帯コードリスト */
        List<String> workHourCodeList = new ArrayList<>();
        if (maxDayOfWorkTimeCompany.isPresent() && !maxDayOfWorkTimeCompany.get().getMaxDayOfWorkTime().getWorkTimeCodeList().isEmpty()) {

            workHourCodeList = maxDayOfWorkTimeCompany
                    .get()
                    .getMaxDayOfWorkTime()
                    .getWorkTimeCodeList()
                    .stream()
                    .map(item -> item.v())
                    .collect(Collectors.toList());
        }
        //就業時間帯情報を取得する
        List<WorkTimeSetting> workTimeSettingList = workTimeRepo
                .getListWorkTimeSetByListCode(AppContexts.user().companyId(), workHourCodeList);
        // working hours list
        List<WorkingHoursDTO> workhourList = workTimeSettingList.stream().map(item -> new WorkingHoursDTO(item.getWorktimeCode().v(), item.getWorkTimeDisplayName().getWorkTimeName().v())).collect(Collectors.toList());


        MaxDaysOfWorkTimeComListDto dto = new MaxDaysOfWorkTimeComListDto(
                maxDayOfWorkTimeCompany.orElseGet(null).getCode().v(),
                maxDayOfWorkTimeCompany.orElseGet(null).getName().v(),
                maxDayOfWorkTimeCompany.orElseGet(null).getMaxDayOfWorkTime().getMaxDay().v(),
                workhourList
        );
        return dto;
    }

    public List<MaxDayOfWorkTimeCompanyDto> getWortimeList() {
        List<MaxDayOfWorkTimeCompany> list = maxDayOfWorkTimeCompanyRepo.getAll(AppContexts.user().companyId());
        return list.stream().map(item -> new MaxDayOfWorkTimeCompanyDto(
                item.getCode().v(),
                item.getName().v(),
                item.getMaxDayOfWorkTime().getMaxDay().v()
        )).collect(Collectors.toList());
    }
}
