package nts.uk.screen.at.app.ksm005.find;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.WeeklyWorkDayRepository;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingSetMemento;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeFinder;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeInfor;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.pub.worktime.worktimeset.WorkTimeSettingPub;
import nts.uk.screen.at.app.ksm015.find.WorkStyleScreenQuery;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 月間パターンの明細を表示する.
 */
@Stateless
public class MonthlyPatternScreenProcessor {

    @Inject
    private WorkMonthlySettingRepository workMonthlySettingRepository;

    @Inject
    private BasicScheduleService basicScheduleService;

    @Inject
    private WorkTypeFinder workTypeFinder;

    @Inject
    private WorkTypeRepository workTypeRepository;

    @Inject
    private WorkTimeSettingRepository workTimeRepository;

    @Inject
    private GetYearMonthScreenprocessor getYearMonthScreenprocessor;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepository;

    public MonthlySettingPatternDto findDataMonthlyPattern(MonthlyPatternRequestPrams requestPrams) {

        String cid = AppContexts.user().companyId();

        // 1. get List<WorkMonthlySetting>

        DatePeriod date = new DatePeriod(requestPrams.getStartDate(),requestPrams.getEndDate());
        List<MonthlyPatternDto> workMonthlySettings = this.workMonthlySettingRepository
                .findByPeriod(cid, requestPrams.getMonthlyPatternCode(),date).stream()
                .map(domain -> {
                    MonthlyPatternDto dto = new MonthlyPatternDto();
                    domain.saveToMemento(dto);
                    return dto;
                }).collect(Collectors.toList());


        WorkInformation.Require require = new RequireImpl(basicScheduleService);
        workMonthlySettings.forEach(x -> {
            // 2. set WorkStyle
            WorkInformation information = new WorkInformation(x.getWorkingCode(), x.getWorkTypeCode());
            Integer workStyle = information.getWorkStyle(require).isPresent() ? information.getWorkStyle(require).get().value : null;
            Integer typeColor = -1;
            //check output for frontend
            if (workStyle != null)
            if (workStyle == WorkStyle.ONE_DAY_WORK.value){
                typeColor = 1;
            }else if (workStyle == WorkStyle.ONE_DAY_REST.value){
                typeColor = 0;
            }else {
                typeColor = 2;
            }
            x.setTypeColor(typeColor);

            // 3. set work type name
            List<WorkTypeInfor> getPossibleWorkType = workTypeFinder.getPossibleWorkTypeKDL002(Arrays.asList(x.getWorkTypeCode()));
            List<String> workTypeName = new ArrayList<>();
            getPossibleWorkType.forEach(c -> workTypeName.add(c.getName()));
            x.setWorkTypeName(workTypeName.size() == 0 ? null : workTypeName.get(0));

            // 4. set work time name
            Map<String, String> listWorkTimeCodeName = workTimeSettingRepository
                    .getCodeNameByListWorkTimeCd(cid, Arrays.asList(x.getWorkingCode()));
            List<String> workTimeName = new ArrayList<>(listWorkTimeCodeName.values());
            x.setWorkingName(workTimeName.size() == 0 ? null :workTimeName.get(0));
        });

        // 5.get yearMonth
        List<Integer> listMonthYear = getYearMonthScreenprocessor.GetYearMonth(cid,requestPrams.getMonthlyPatternCode(),requestPrams.getStartDate().year());
        return new MonthlySettingPatternDto(workMonthlySettings, listMonthYear);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequireImpl implements WorkInformation.Require{

        @Inject
        private BasicScheduleService basicScheduleService;

        @Override
        public Optional<WorkType> findByPK(String workTypeCd) {
            return Optional.empty();
        }

        @Override
        public Optional<WorkTimeSetting> findByCode(String workTimeCode) {
            return Optional.empty();
        }

        @Override
        public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
            return null;
        }

        @Override
        public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTimeCd, String workTypeCd, Integer workNo) {
            return null;
        }

        @Override
        public WorkStyle checkWorkDay(String workTypeCode) {
            return basicScheduleService.checkWorkDay(workTypeCode);
        }

    }

}
