package nts.uk.screen.at.app.ksm008.query.i;

import lombok.val;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.ConsecutiveWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeCompanyRepository;
import nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto.WorkTimeDisplayNameDto;
import nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto.WorkTimeDivisionDto;
import nts.uk.ctx.at.shared.app.command.worktime.worktimeset.dto.WorkTimeSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
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

    public WorkingHourListDto get() {
        //就業時間帯情報リストを取得する
        //TODO generate parameters here

        List<WorkTimeSetting> workTimeSettingList = workTimeRepo.getListWorkTimeSetByListCode("demoID", Arrays.asList(
                new String("code1"),
                new String("code2")
                )
        );
        List<WorkTimeSettingDto> workTimeSetting = workTimeSettingList.stream()
                //TODO  filter condition here
                //.filter(condition)
                .collect(
                        Collectors.mapping(
                                p -> new WorkTimeSettingDto(
                                        p.getWorktimeCode() == null ? "" : p.getWorktimeCode().toString(),
                                        new WorkTimeDivisionDto(
                                                p.getWorkTimeDivision().getWorkTimeDailyAtr() == null ? 0 : p.getWorkTimeDivision().getWorkTimeDailyAtr().value,
                                                p.getWorkTimeDivision().getWorkTimeMethodSet() == null ? 0 : p.getWorkTimeDivision().getWorkTimeMethodSet().value),
                                        p.isAbolish(),
                                        p.getColorCode() == null ? "" : p.getColorCode().toString(),
                                        new WorkTimeDisplayNameDto(
                                                p.getWorkTimeDisplayName().getWorkTimeName() == null ? "" : p.getWorkTimeDisplayName().getWorkTimeName().toString(),
                                                p.getWorkTimeDisplayName().getWorkTimeAbName() == null ? "" : p.getWorkTimeDisplayName().getWorkTimeAbName().toString(),
                                                p.getWorkTimeDisplayName().getWorkTimeSymbol() == null ? "" : p.getWorkTimeDisplayName().getWorkTimeSymbol().toString()
                                        ),
                                        p.getMemo() == null ? "" : p.getMemo().toString(),
                                        p.getNote() == null ? "" : p.getNote().toString()
                                ),
                                Collectors.toList()));
        /*
         *会社の就業時間帯の連続勤務できる上限日数
         */

        //TODO generate parameters here

        val maxDaysOfContinuousWorkTimeCompany =
                maxDaysOfContinuousWorkTimeCompanyRepository.get("companyID", new ConsecutiveWorkTimeCode(""));

        return new WorkingHourListDto(workTimeSetting, new MaxDaysOfContinuousWorkTimeCompanyDto(
                maxDaysOfContinuousWorkTimeCompany.orElse(null).getCode().toString(),
                maxDaysOfContinuousWorkTimeCompany.orElse(null).getName().toString(),
                Integer.parseInt(maxDaysOfContinuousWorkTimeCompany.orElse(null).getMaxDaysContiWorktime().getNumberOfDays().toString())
        ));
    }
}
