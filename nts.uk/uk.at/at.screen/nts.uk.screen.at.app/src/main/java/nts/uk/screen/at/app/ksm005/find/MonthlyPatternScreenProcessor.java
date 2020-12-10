package nts.uk.screen.at.app.ksm005.find;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeFinder;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeInfor;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

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
        // 1:月間パターンの勤務情報を取得する(会社ID, 月間パターンコード, 期間)
        DatePeriod date = new DatePeriod(requestPrams.getStartDate(),requestPrams.getEndDate());
        List<MonthlyPatternDto> workMonthlySettings = this.workMonthlySettingRepository
                .findByPeriod(cid, requestPrams.getMonthlyPatternCode(),date).stream()
                .map(domain -> {
                    MonthlyPatternDto dto = new MonthlyPatternDto();
                    domain.saveToMemento(dto);
                    return dto;
                }).collect(Collectors.toList());


        WorkInformation.Require require = new RequireImpl(workTypeRepository);

        List<String> workTypeCodes = workMonthlySettings.stream().map(MonthlyPatternDto::getWorkTypeCode)
                .distinct().collect(Collectors.toList());
        Map<String, String> getPossibleWorkTypeAll = workTypeFinder.getPossibleWorkType(workTypeCodes)
                .stream().collect(Collectors.toMap(WorkTypeInfor::getWorkTypeCode, WorkTypeInfor::getName));

        List<String> workingCodes = workMonthlySettings.stream().map(MonthlyPatternDto::getWorkingCode)
                .distinct().collect(Collectors.toList());
        Map<String, String> workTimeAll = workTimeSettingRepository.getCodeNameByListWorkTimeCd(cid, workingCodes);

        workMonthlySettings.forEach(x -> {

            // 2. set WorkStyle
            // 2:出勤・休日系の判定(Require)
            WorkInformation information = new WorkInformation( x.getWorkTypeCode(),x.getWorkingCode());
            int workStyle = information.getWorkStyle(require).isPresent() ? information.getWorkStyle(require).get().value : -1;
            int typeColor = getInteger(workStyle);
            x.setTypeColor(typeColor);

            // 3. set work type name
            // 3:勤務種類名称を取得する(ログイン会社、List<勤務種類コード>)
            x.setWorkTypeName(getPossibleWorkTypeAll.getOrDefault(x.getWorkTypeCode(), null));

            // 4. set work time name
            // 4:就業時間帯名称を取得する(ログイン会社ID、List<就業時間帯コード>)
            x.setWorkingName(workTimeAll.getOrDefault(x.getWorkingCode(), null));
        });

        // 6.get yearMonth
        // 6:設定した年月一覧を取得する(ログイン会社ID、月間パターンコード、年)
        List<Integer> listMonthYear = getYearMonthScreenprocessor.GetYearMonth(cid,requestPrams.getMonthlyPatternCode(),requestPrams.getStartDate().year());
        return new MonthlySettingPatternDto(workMonthlySettings, listMonthYear);
    }

    public WorkStyleDto findDataWorkStype(WorkTypeRequestPrams requestPrams) {

        WorkInformation.Require require = new RequireImpl(workTypeRepository);

        WorkInformation information = new WorkInformation(requestPrams.getWorkTypeCode(),requestPrams.getWorkingCode());
        int workStyle = information.getWorkStyle(require).isPresent() ? information.getWorkStyle(require).get().value : -1;
        int typeColor = getInteger(workStyle);
        return new WorkStyleDto(requestPrams.getWorkingCode(), requestPrams.getWorkTypeCode(),typeColor);
    }

    private int getInteger(int workStyle) {
        if(workStyle < 0)
            return -1;
        if (workStyle == WorkStyle.ONE_DAY_WORK.value)
            return 0;
        if (workStyle == WorkStyle.ONE_DAY_REST.value)
            return 1;
        return 2;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequireImpl implements WorkInformation.Require{

		private final String companyId = AppContexts.user().companyId();

		@Inject
		private WorkTypeRepository workTypeRepository;

        @Override
        public Optional<WorkType> getWorkType(String workTypeCd) {
            return workTypeRepository.findByPK(companyId, workTypeCd);
        }

        @Override
        public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
            return Optional.empty();
        }

        @Override
        public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
            return null;
        }

        @Override
        public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTypeCd, String workTimeCd, Integer workNo) {
            return null;
        }

		@Override
		public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

    }

}
