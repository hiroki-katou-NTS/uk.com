package nts.uk.ctx.at.schedule.dom.appreflectprocess.service.appforleave;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.CommonReflectParamSche;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.ScheduleEditState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleStateRepository;

@Stateless
public class ForleaveReflectScheImpl implements ForleaveReflectSche{
	@Inject
	private BasicScheduleRepository basicSche;
	@Inject
	private WorkScheduleStateRepository workScheReposi;
	@Override
	public boolean forlearveReflectSche(CommonReflectParamSche reflectParam) {
		try {
			//勤務種類を反映する
			//ドメインモデル「勤務予定基本情報」を取得する			
			basicSche.changeWorkTypeTime(reflectParam.getEmployeeId(), reflectParam.getDatePara(), reflectParam.getWorktypeCode(), reflectParam.getWorkTimeCode());
			//勤務種類の編集状態を更新する
			WorkScheduleState scheData = new WorkScheduleState(ScheduleEditState.REFLECT_APPLICATION,
					1,
					reflectParam.getDatePara(),
					reflectParam.getEmployeeId());
			workScheReposi.updateScheduleEditState(scheData);
			return true;
		} catch (Exception e) {
			return false;
		}
		
		
	}	
}


