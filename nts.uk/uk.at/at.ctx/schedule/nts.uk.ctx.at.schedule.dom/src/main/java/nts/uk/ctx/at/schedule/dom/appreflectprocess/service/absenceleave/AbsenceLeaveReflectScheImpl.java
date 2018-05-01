package nts.uk.ctx.at.schedule.dom.appreflectprocess.service.absenceleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.CommonReflectParamSche;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.UpdateScheCommonAppRelect;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.StartEndTimeReflectScheService;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.servicedto.TimeReflectScheDto;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;

@Stateless
public class AbsenceLeaveReflectScheImpl implements AbsenceLeaveReflectSche{
	@Inject
	private UpdateScheCommonAppRelect updateSche;
	@Inject
	private BasicScheduleService basicService;
	@Inject
	private StartEndTimeReflectScheService startEndTimeScheService;
	@Inject
	private BasicScheduleRepository basicScheRepo;
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	@Override
	public boolean absenceLeaveReflect(CommonReflectParamSche param) {
		try {
			//勤種・就時の反映
			updateSche.updateScheWorkTimeType(param.getEmployeeId(), 
					param.getDatePara(), 
					param.getWorktypeCode(), 
					"000");//就業時間帯クリア->000
			//開始予定・終了予定の置き換え
			this.absenceLeaveStartEndTimeReflect(param);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void absenceLeaveStartEndTimeReflect(CommonReflectParamSche param) {
		//1日半日出勤・1日休日系の判定
		WorkStyle checkworkDay = basicService.checkWorkDay(param.getWorktypeCode());
		//日休日の場合
		if(checkworkDay == WorkStyle.ONE_DAY_REST) {
			//勤務開始終了のクリア
			startEndTimeScheService.updateStartTimeRflect(new TimeReflectScheDto(param.getEmployeeId(),
					param.getDatePara(), 0, 0, 1, true, true));
		} else if(checkworkDay == WorkStyle.AFTERNOON_WORK
				|| checkworkDay == WorkStyle.MORNING_WORK){ //出勤休日区分が午前出勤系又は、午後出勤系
			//就業時間帯が反映できるか
			WorkTimeIsReflect workTimeReflect = this.workTimeIsReflect(param.getEmployeeId(), 
					param.getDatePara(),
					param.getWorkTimeCode());
			if(workTimeReflect.isReflect()) {
				//就時の反映
				updateSche.updateScheWorkTimeType(param.getEmployeeId(), 
						param.getDatePara(), 
						null, 
						workTimeReflect.getWorkTimeCode());
			}
		}
			
	}

	@Override
	public WorkTimeIsReflect workTimeIsReflect(String employeeId, GeneralDate baseDate, String worktimeCode) {
		//反映できるフラグ=false、反映就業時間帯をクリア(初期化)
		WorkTimeIsReflect dataOutput = new WorkTimeIsReflect(false, "000");
		//INPUT．就業時間帯をチェックする
		if(worktimeCode.isEmpty()) {
			//ドメインモデル「勤務予定基本情報」を取得する
			Optional<BasicSchedule> optBasicSche = basicScheRepo.find(employeeId, baseDate);
			//ドメインモデル「勤務予定基本情報」．就業時間帯をチェックする
			if(optBasicSche.isPresent()) {
				return dataOutput;
			}
			//ドメインモデル「個人労働条件」を取得する
			//ドメインモデル「個人労働条件」を取得する(lay dieu kien lao dong ca nhan(個人労働条件))
			Optional<WorkingConditionItem> personalLablorCodition = workingConditionItemRepository.getBySidAndStandardDate(employeeId, baseDate);
			if(!personalLablorCodition.isPresent()) {
				return dataOutput;
			}
			WorkingConditionItem workingConditionData = personalLablorCodition.get();
			dataOutput.setReflect(true);
			dataOutput.setWorkTimeCode(workingConditionData.getWorkCategory().getWeekdayTime().getWorkTimeCode().isPresent() 
					? workingConditionData.getWorkCategory().getWeekdayTime().getWorkTimeCode().get().v() : "000");
		} else {
			dataOutput.setReflect(true);
			dataOutput.setWorkTimeCode(worktimeCode);
		}
		return dataOutput;
	}
	

}
