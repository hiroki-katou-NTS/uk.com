package nts.uk.ctx.at.schedule.dom.appreflectprocess.service.gobacksche;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.ScheduleEditState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleStateRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.service.WorkTypeIsClosedService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkTypeHoursReflectScheImpl implements WorkTypeHoursReflectSche{
	@Inject
	private BasicScheduleRepository basicSche;
	@Inject
	private WorkTypeRepository workTypeRepo;
	@Inject
	private WorkScheduleStateRepository workScheReposi;
	@Inject
	private WorkTypeIsClosedService workTypeService;
	@Override
	public boolean isReflectFlag(GobackReflectParam gobackPara) {
		//ドメインモデル「勤務予定基本情報」を取得する
		//ドメインモデル「勤務予定基本情報」を取得する
		Optional<BasicSchedule> optBasicScheOpt = basicSche.find(gobackPara.getEmployeeId(), gobackPara.getDatePara());		
		if(!optBasicScheOpt.isPresent()) {
			return false;
		}
		BasicSchedule basicScheOpt = optBasicScheOpt.get();
		if(this.isCheckReflect(gobackPara, basicScheOpt)) {
			//ドメインモデル「勤務予定基本情報」を編集する			
			basicSche.changeWorkTypeTime(gobackPara.getEmployeeId(), gobackPara.getDatePara(), gobackPara.getAppInfor().getWorkType(), gobackPara.getAppInfor().getWorkTime());
			//ドメインモデル「勤務予定項目状態」を編集する id = 1
			WorkScheduleState scheData = new WorkScheduleState(ScheduleEditState.REFLECT_APPLICATION,
					1,
					gobackPara.getDatePara(),
					gobackPara.getEmployeeId());
			workScheReposi.updateOrInsert(scheData);
			//就業時間帯の編集状態を更新する
			WorkScheduleState scheDataTime = new WorkScheduleState(ScheduleEditState.REFLECT_APPLICATION,
					2,
					gobackPara.getDatePara(),
					gobackPara.getEmployeeId());
			workScheReposi.updateOrInsert(scheDataTime);
			return true;
		}
		return false;
	}

	@Override
	public boolean isCheckReflect(GobackReflectParam gobackPara, BasicSchedule basicScheOpt) {
		boolean isFlag = false;
		//INPUT．勤務を変更するをチェックする
		if(gobackPara.getAppInfor().getChangeAtrAppGoback() == ChangeAtrAppGoback.CHANGE) {
			//INPUT．振出・休出時反映する区分をチェックする
			if(gobackPara.isOutsetBreakReflectAtr()) {
				isFlag = true;
			}else {				
				//勤務種類が休出振出かの判断
				if(workTypeService.checkWorkTypeIsClosed(basicScheOpt.getWorkTypeCode())) {
					isFlag = false;
				} else {
					isFlag = true;
				}
			}
		}
		return isFlag;
	}
	
}
