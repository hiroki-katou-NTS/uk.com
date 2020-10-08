package nts.uk.screen.at.app.ksm008.query.i;

import lombok.val;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.ConsecutiveWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeCompany;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeCompanyRepository;
import nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto.WorkTimeDisplayNameDto;
import nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto.WorkTimeDivisionDto;
import nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto.WorkTimeSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
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

    public WorkingHourListDto get(String code) {

        /*就業時間帯情報リストを取得する*/
        val maxDaysOfContinuousWorkTimeCompany= maxDaysOfContinuousWorkTimeCompanyRepository.get(AppContexts.user().companyId(), new ConsecutiveWorkTimeCode(code));
        MaxDaysOfContinuousWorkTimeListDto maxDaysOfContinuousWorkTimeListDto = this.getMaxDaysOfContinuousWorkTime(maxDaysOfContinuousWorkTimeCompany);

        /*就業時間帯コードリスト */
        List<String> workHourCodeList =  new ArrayList<>();
        if(maxDaysOfContinuousWorkTimeCompany.isPresent() &&!maxDaysOfContinuousWorkTimeCompany.get().getMaxDaysContiWorktime().getWorkTimeCodes().isEmpty()){

            workHourCodeList =maxDaysOfContinuousWorkTimeCompany
                    .get()
                    .getMaxDaysContiWorktime()
                    .getWorkTimeCodes()
                    .stream()
                    .map(item->item.v())
                    .collect(Collectors.toList());
        }
        //就業時間帯情報を取得する
        List<WorkTimeSetting> workTimeSettingList = workTimeRepo
                .getListWorkTimeSetByListCode(AppContexts.user().companyId(), workHourCodeList );
        List<WorkTimeSettingDto> workTimeSetting = workTimeSettingList.stream()
                .collect(
                        Collectors.mapping(
                                p -> new WorkTimeSettingDto(
                                        p.getWorktimeCode().v(),
                                        new WorkTimeDivisionDto(
                                                p.getWorkTimeDivision().getWorkTimeDailyAtr().value,
                                                p.getWorkTimeDivision().getWorkTimeMethodSet().value),
                                        p.isAbolish(),
                                        p.getColorCode().v(),
                                        new WorkTimeDisplayNameDto(
                                                p.getWorkTimeDisplayName().getWorkTimeName().v(),
                                                p.getWorkTimeDisplayName().getWorkTimeAbName().v(),
                                                p.getWorkTimeDisplayName().getWorkTimeSymbol().v()
                                        ),
                                        p.getMemo().v(),
                                        p.getNote().v()
                                ),
                                Collectors.toList()));
        /*
         *会社の就業時間帯の連続勤務できる上限日数
         */
        return new WorkingHourListDto(workTimeSetting, maxDaysOfContinuousWorkTimeListDto);
    }

    private MaxDaysOfContinuousWorkTimeListDto getMaxDaysOfContinuousWorkTime(Optional<MaxDaysOfContinuousWorkTimeCompany> maxDaysOfContinuousWorkTimeCompany) {
        return new MaxDaysOfContinuousWorkTimeListDto(
                maxDaysOfContinuousWorkTimeCompany.orElse(null).getCode().v(),
                maxDaysOfContinuousWorkTimeCompany.orElse(null).getName().v(),
                new MaxDaysOfConsecutiveWorkTimeDTO(
                        maxDaysOfContinuousWorkTimeCompany
                                .orElse(null)
                                .getMaxDaysContiWorktime()
                                .getWorkTimeCodes()
                                .stream()
                                .map(item -> item.v())
                                .collect(Collectors.toList()),
                        maxDaysOfContinuousWorkTimeCompany
                                .orElse(null)
                                .getMaxDaysContiWorktime()
                                .getNumberOfDays()
                                .v()
                )
        );
    }
}
