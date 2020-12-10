package nts.uk.screen.at.app.ksm008.query.k;

import nts.gul.collection.CollectionUtil;
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
import java.util.*;
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
        if (!maxDayOfWorkTimeCompany.isPresent()) {
            return new MaxDaysOfWorkTimeComListDto();
        }
        /*就業時間帯コードリスト */
        List<String> workHourCodeList = new ArrayList<>();
        if (!maxDayOfWorkTimeCompany.get().getMaxDayOfWorkTime().getWorkTimeCodeList().isEmpty()) {
            workHourCodeList = maxDayOfWorkTimeCompany
                    .get()
                    .getMaxDayOfWorkTime()
                    .getWorkTimeCodeList()
                    .stream()
                    .map(item -> item.v())
                    .collect(Collectors.toList());
        }
        //就業時間帯情報を取得する
        Map<String, String> getCodeNameByListWorkTimeCd = workTimeRepo
                .getCodeNameByListWorkTimeCd(AppContexts.user().companyId(), workHourCodeList);
                // working hours list
        List<WorkingHoursDTO> workhourList = getCodeNameByListWorkTimeCd
                .entrySet()
                .stream()
                .map(item -> new WorkingHoursDTO(item.getKey(), item.getValue()))
                .collect(Collectors.toList());
        MaxDaysOfWorkTimeComListDto dto = new MaxDaysOfWorkTimeComListDto(
                maxDayOfWorkTimeCompany.get().getCode().v(),
                maxDayOfWorkTimeCompany.get().getName().v(),
                maxDayOfWorkTimeCompany.get().getMaxDayOfWorkTime().getMaxDay().v(),
                workhourList
        );
        return dto;
    }

    public List<MaxDayOfWorkTimeCompanyDto> getWortimeList() {
        List<MaxDayOfWorkTimeCompany> list = maxDayOfWorkTimeCompanyRepo.getAll(AppContexts.user().companyId());
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        return list
                .stream()
                .map(item -> new MaxDayOfWorkTimeCompanyDto(
                        item.getCode().v(),
                        item.getName().v(),
                        item.getMaxDayOfWorkTime().getMaxDay().v()
                ))
                .collect(Collectors.toList());
    }
}
