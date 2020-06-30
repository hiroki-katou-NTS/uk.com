package nts.uk.ctx.at.schedule.dom.appreflectprocess.service.gobacksche;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.UpdateScheCommonAppRelect;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleState;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.service.WorkTypeIsClosedService;

@Stateless
public class WorkTypeHoursReflectScheImpl implements WorkTypeHoursReflectSche{
	@Inject
	private BasicScheduleRepository basicSche;
	@Inject
	private WorkTypeRepository workTypeRepo;
	@Inject
	private UpdateScheCommonAppRelect commonReflect;
	@Override
	public boolean isReflectFlag(GobackReflectParam gobackPara, BasicSchedule scheData, List<WorkScheduleState> lstState) {
		//ドメインモデル「勤務予定基本情報」を取得する
		//ドメインモデル「勤務予定基本情報」を取得する		
		if(this.isCheckReflect(gobackPara, scheData)) {
			//ドメインモデル「勤務予定基本情報」を編集する			
			//basicSche.changeWorkTypeTime(gobackPara.getEmployeeId(), gobackPara.getDatePara(), gobackPara.getAppInfor().getWorkType(), gobackPara.getAppInfor().getWorkTime());
			scheData.setWorkTimeCode(gobackPara.getAppInfor().getWorkTime());
			scheData.setWorkTypeCode(gobackPara.getAppInfor().getWorkType());
			//ドメインモデル「勤務予定項目状態」を編集する id = 1
			commonReflect.setStateData(scheData, lstState, 1);
			//就業時間帯の編集状態を更新する
			commonReflect.setStateData(scheData, lstState, 2);
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
				if(WorkTypeIsClosedService.checkWorkTypeIsClosed(createRequireM1(), 
						basicScheOpt.getWorkTypeCode())) {
					isFlag = false;
				} else {
					isFlag = true;
				}
			}
		}
		return isFlag;
	}
	
	private WorkTypeIsClosedService.RequireM1 createRequireM1() {
		return new WorkTypeIsClosedService.RequireM1() {
			
			@Override
			public Optional<WorkType> workType(String companyId, String workTypeCd) {
				return workTypeRepo.findByPK(companyId, workTypeCd);
			}
		};
	}
	
}
