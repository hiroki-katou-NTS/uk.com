package nts.uk.ctx.at.schedule.dom.appreflectprocess.service.absenceleave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.CommonReflectParamSche;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.UpdateScheCommonAppRelect;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.StartEndTimeReflectScheService;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.servicedto.TimeReflectScheDto;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;

@Stateless
public class AbsenceLeaveReflectScheImpl implements AbsenceLeaveReflectSche{
	@Inject
	private UpdateScheCommonAppRelect updateSche;
	@Inject
	private BasicScheduleService basicService;
	@Inject
	private StartEndTimeReflectScheService startEndTimeScheService;
	@Override
	public boolean absenceLeaveReflect(CommonReflectParamSche param) {
		try {
			//勤種・就時の反映
			updateSche.updateScheWorkTimeType(param.getEmployeeId(), 
					param.getDatePara(), 
					param.getWorktypeCode(), 
					param.getWorkTimeCode());//就業時間帯クリア->000
			//開始予定・終了予定の置き換え
			this.absenceLeaveStartEndTimeReflect(param.getEmployeeId(), param.getDatePara(), param.getWorktypeCode(), param.getStartTime(), param.getEndTime());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void absenceLeaveStartEndTimeReflect(String employeeId, GeneralDate baseDate, String workTypeCode,
			Integer startTime, Integer endTime) {
		//1日半日出勤・1日休日系の判定
		WorkStyle checkworkDay = basicService.checkWorkDay(workTypeCode);
		//日休日の場合
		if(checkworkDay == WorkStyle.ONE_DAY_REST) {
			//勤務開始終了のクリア
			startEndTimeScheService.updateStartTimeRflect(new TimeReflectScheDto(employeeId, baseDate, 0, 0, 1, true, true));
		}
		//午前出勤/午後出勤の場合
		//TODO lam tiep khi co EAP
		
	}
	

}
