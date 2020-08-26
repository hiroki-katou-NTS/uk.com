package nts.uk.screen.at.app.ksm005.find;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay.WeeklyWorkDayRepository;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
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
    private GetYearMonthScreenprocessor getYearMonthScreenprocessor;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepository;

    private static final String YYYYMMDD = "yyyy/MM/dd";


    public MonthlyPatternDto findDataMonthlyPattern(MonthlyPatternRequestPrams requestPrams) {

        String cid = AppContexts.user().companyId();

        // 1. get List<WorkMonthlySetting>
        List<WorkMonthlySetting> weeklyWorkSettings = workMonthlySettingRepository.findByPeriod(
                cid,requestPrams.getMonthlyPatternCode(),requestPrams.getDatePeriodDto().convertToDate(YYYYMMDD));

        // 2. get WorkStyle
        WorkInformation.Require require = new RequireImpl(basicScheduleService);
        List<WorkStyle> workStyle = new ArrayList<>();
        weeklyWorkSettings.forEach(x -> {
            WorkInformation information = new WorkInformation(x.getWorkInformation().getWorkTimeCode(), x.getWorkInformation().getWorkTypeCode());
            workStyle.add(information.getWorkStyle(require).get());
        });

        // 3.get work type name
        List<String> ListWorkTypeCd = new ArrayList<>();
        List<WorkTypeInfor> getPossibleWorkType = workTypeFinder.getPossibleWorkTypeKDL002(ListWorkTypeCd);
        List<String> workTypeName = new ArrayList<>();
        getPossibleWorkType.stream().forEach(x -> workTypeName.add(x.getName()));

        // 4.get work time name
        List<String> ListWorkTimeCd = new ArrayList<>();
        weeklyWorkSettings.forEach(x -> ListWorkTimeCd.add( x.getWorkInformation().getWorkTimeCode().v()));

        Map<String, String> listWorkTimeCodeName = ListWorkTimeCd.size() > 0
                ? workTimeSettingRepository.getCodeNameByListWorkTimeCd(cid, ListWorkTimeCd) : new HashMap<>();
        List<String> workTimeName = new ArrayList<>(listWorkTimeCodeName.values());

        // 5.get yearMonth
        int date = requestPrams.getDatePeriodDto().convertToDate(YYYYMMDD).start().year();
        List<String> listMonthYear = getYearMonthScreenprocessor.GetYearMonth(cid,requestPrams.getMonthlyPatternCode(),date);

        return new MonthlyPatternDto(weeklyWorkSettings,workTypeName,workTimeName,workStyle,listMonthYear);
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
