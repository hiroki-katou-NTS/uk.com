package nts.uk.screen.at.app.shift.workcycle;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.WeeklyWorkDay.WeeklyWorkDayPattern;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.CreateWorkCycleAppImage;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.RefImageEachDay;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.WorkCycleRefSetting;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.algorithm.HolidayWorkTypeService;
import nts.uk.screen.at.app.ksm003.find.GetWorkCycle;
import nts.uk.screen.at.app.ksm003.find.WorkCycleDto;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * 勤務サイクル反映ダイアログ
 * @author: khai.dh
 */
@Stateless
public class WorkCycleReflectionDialog {
	@Inject private GetWorkCycle getWorkCycle;
	@Inject private HolidayWorkTypeService holidayWorkTypeService;
	@Inject private CreateWorkCycleAppImage createWorkCycleAppImage;
	@Inject private CreateWorkCycleAppImage.Require require;
	@Inject private WorkInformation workInformation;

	public WorkCycleReflectionDto getStartupInfo(String companyId, String startMode, DatePeriod creationPeriod, Optional<String> workCycleCode){
		// 1. 勤務サイクル一覧を取得する [Get a list of work cycles]
		List<WorkCycleDto> workCycleList = getWorkCycle.getDataStartScreen();

		// 起動モードは実行モードの場合、
		// Query「務サイクル一覧を取得する」の戻り値から「勤務サイクルコード」はList<務サイクルコード、
		// 勤務サイクル名称>の最初項目
		String workCycleCode2 = workCycleList.get(0).getCode();

		// 2. 休日系の勤務種類を取得する [Get holiday type of work]
		List<WorkType> workTypes = holidayWorkTypeService.acquiredHolidayWorkType();

		// 3. 作成する(Require, 期間, 勤務サイクルの反映設定)
		// [Create (Require, period, work cycle reflection setting)]
		// param:require, 作成期間, 設定 [require, creation period, setting]
		// return: List<一日分の反映イメージ> [List <Image of one day's reflection>]
		// TODO Where to get data?
		WorkCycleRefSetting config = new WorkCycleRefSetting(
				workCycleCode.get(),
				null,
				1,
				null,
				null,
				null
		);
		// CreateWorkCycleAppImage.Require
		List<RefImageEachDay> refImageEachDayList = createWorkCycleAppImage.create(require,creationPeriod,config);


		// 4. 出勤・休日系の判定(Require)
		// paran Require
		// return: Optional<出勤休日区分>
		WorkInformationRequireImpl wiRequire = new WorkInformationRequireImpl();
		Optional<WorkStyle> optWorkStyle = workInformation.getWorkStyle(wiRequire);

		WorkCycleReflectionDto dto = new WorkCycleReflectionDto();
		dto.setWorkTypes(workTypes);
		dto.setRefImageEachDayList(refImageEachDayList);
		dto.setWorkStyle(optWorkStyle.get());
		dto.setWorkCycleList(workCycleList);
		return new WorkCycleReflectionDto();
	}

	private static class WorkInformationRequireImpl implements WorkInformation.Require {
		@Inject private BasicScheduleService basicScheduleService;

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