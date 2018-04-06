package nts.uk.ctx.at.schedule.dom.appreflectprocess.service.appforleave;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.CommonReflectParamSche;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.gobacksche.GobackReflectParam;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
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
	public void forlearveReflectSche(CommonReflectParamSche reflectParam) {
		//勤務種類を反映する
		//ドメインモデル「勤務予定基本情報」を取得する
		
		basicSche.changeWorkTypeTime(reflectParam.getEmployeeId(), reflectParam.getDatePara(), reflectParam.getWorktypeCode(), reflectParam.getWorkTimeCode());
		//勤務種類の編集状態を更新する
		getListItem().stream().forEach(x ->{
			WorkScheduleState scheData = new WorkScheduleState(ScheduleEditState.REFLECT_APPLICATION,
					x,
					reflectParam.getDatePara(),
					reflectParam.getEmployeeId());
			workScheReposi.updateScheduleEditState(scheData);
		});
		
	}
	private List<Integer> getListItem(){
		//予定項目ID=勤務種類の項目ID
		List<Integer> lstItemId = new ArrayList<>();
		lstItemId.add(1);
		lstItemId.add(2);
		lstItemId.add(3);
		lstItemId.add(4);
		lstItemId.add(5);
		lstItemId.add(6);
		lstItemId.add(7);
		lstItemId.add(8);
		lstItemId.add(9);
		lstItemId.add(10);
		lstItemId.add(11);
		lstItemId.add(12);
		lstItemId.add(13);
		lstItemId.add(14);
		lstItemId.add(15);
		lstItemId.add(16);
		lstItemId.add(17);
		lstItemId.add(18);
		lstItemId.add(19);
		lstItemId.add(20);
		lstItemId.add(21);
		lstItemId.add(22);
		lstItemId.add(23);
		lstItemId.add(24);
		lstItemId.add(25);
		lstItemId.add(26);
		lstItemId.add(27);		
		return lstItemId;
	}
}


