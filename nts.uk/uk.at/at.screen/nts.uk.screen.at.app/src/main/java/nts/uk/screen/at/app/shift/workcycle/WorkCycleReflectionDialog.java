package nts.uk.screen.at.app.shift.workcycle;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.*;
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

 	/**
	 * 起動情報を取得する
	 * @param bootMode
	 * @param creationPeriod
	 * @return WorkCycleReflectionDto
	 */
	public WorkCycleReflectionDto getStartupInfo(
			BootMode bootMode,
			DatePeriod creationPeriod,
			String workCycleCode,
			List<WorkCreateMethod> refOrder,
			int numOfSlideDays){

		WorkCycleReflectionDto dto = new WorkCycleReflectionDto();

		// 1. 勤務サイクル一覧を取得する [Get a list of work cycles]
		if (bootMode == BootMode.EXEC_MODE){
			List<WorkCycleDto> workCycleList = getWorkCycle.getDataStartScreen();
			workCycleCode = workCycleList.get(0).getCode();
			dto.setWorkCycleList(workCycleList);
		}

		// 2. 休日系の勤務種類を取得する [Get holiday type of work]
		List<WorkType> workTypes = holidayWorkTypeService.acquiredHolidayWorkType();

		// 3. 作成する(Require, 期間, 勤務サイクルの反映設定)

		WorkCycleRefSetting config = new WorkCycleRefSetting(
				workCycleCode,
				refOrder,
				numOfSlideDays,
				null,
				null,
				null
		);

		List<RefImageEachDay> refImageEachDayList = createWorkCycleAppImage.create(require, creationPeriod, config);
 		ReflectionImage reflectionImage = new ReflectionImage();
		refImageEachDayList.stream().forEach(ref -> {
			ref.getDate();
			ref.getWorkInformation();
			reflectionImage.addInWorkCycle(ref.getDate(), ref.getWorkInformation());
		});

		// 4. 出勤・休日系の判定(Require)
		WorkInformationRequireImpl wiRequire = new WorkInformationRequireImpl();
		Optional<WorkStyle> optWorkStyle = workInformation.getWorkStyle(wiRequire);

		dto.setWorkTypes(workTypes); // List<勤務種類>：休日系の勤務種類
		dto.setReflectionImage(reflectionImage); // 反映イメージ
		dto.setWorkStyle(optWorkStyle.isPresent()? optWorkStyle.get(): null); // 出勤休日区分

		return dto;
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