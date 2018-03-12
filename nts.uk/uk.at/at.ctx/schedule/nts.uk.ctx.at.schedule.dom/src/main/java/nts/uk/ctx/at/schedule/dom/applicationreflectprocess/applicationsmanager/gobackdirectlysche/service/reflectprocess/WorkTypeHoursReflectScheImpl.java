package nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.gobackdirectlysche.service.reflectprocess;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;


import nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.gobackdirectlysche.ChangeAtrAppGoback;
import nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.gobackdirectlysche.GoBackDirectlyReflectParam;
import nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager.gobackdirectlysche.OutsetBreakReflectScheAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkTypeHoursReflectScheImpl implements WorkTypeHoursReflectSche{
	@Inject
	private BasicScheduleRepository basicSche;
	@Inject
	private WorkTypeRepository workTypeRepo;
	@Override
	public boolean isReflectFlag(GoBackDirectlyReflectParam gobackPara) {
		//ドメインモデル「勤務予定基本情報」を取得する
		Optional<BasicSchedule> optBasicScheOpt = basicSche.find(gobackPara.getEmployeeId(), gobackPara.getDatePara());		
		if(!optBasicScheOpt.isPresent()) {
			return false;
		}
		BasicSchedule basicScheOpt = optBasicScheOpt.get();
		if(this.isCheckReflect(gobackPara, basicScheOpt)) {
			//ドメインモデル「勤務予定基本情報」を編集する
			BasicSchedule dataUpdate = new BasicSchedule(gobackPara.getEmployeeId(), 
					gobackPara.getDatePara(), 
					gobackPara.getAppInfor().getWorkType(), 
					gobackPara.getAppInfor().getWorkTime(), 
					basicScheOpt.getConfirmedAtr());
			basicSche.update(dataUpdate);
			//ドメインモデル「勤務予定項目状態」を編集する
			//TODO viet khi ben Chinh lam xong
			
			return true;
		}
		return false;
	}

	@Override
	public boolean isCheckReflect(GoBackDirectlyReflectParam gobackPara, BasicSchedule basicScheOpt) {
		boolean isFlag = false;
		//INPUT．勤務を変更するをチェックする
		if(gobackPara.getAppInfor().getChangeAtrAppGoback() == ChangeAtrAppGoback.CHANGE) {
			//INPUT．振出・休出時反映する区分をチェックする
			if(gobackPara.getOutsetBreakReflectAtr() == OutsetBreakReflectScheAtr.REFLECT) {
				isFlag = true;
			}else {				
				//勤務種類が休出振出かの判断
				if(this.outsetBreakJudgment(basicScheOpt.getWorkTypeCode())) {
					isFlag = false;
				} else {
					isFlag = true;
				}
			}
		}
		return isFlag;
	}
	/**
	 * 勤務種類が休出振出かの判断
	 * @param workTypeCode
	 * @return	true：休出振出である	false：休出振出でない
	 */
	private boolean outsetBreakJudgment(String workTypeCode) {
		boolean isFlag = false;
		String companyId = AppContexts.user().companyId();
		//ドメインモデル「勤務種類」を取得する
		Optional<WorkType> optWorkTypeData = workTypeRepo.findByPK(companyId, workTypeCode);
		if(!optWorkTypeData.isPresent()) {
			return false;
		}
		WorkType workTypeData = optWorkTypeData.get();
		//「1日の勤務」．1日 in (休日出勤, 振出) ||
		//「1日の勤務」．午前 = 振出 ||
		//「1日の勤務」．午後 = 振出
		if(workTypeData.getDailyWork().getOneDay() == WorkTypeClassification.HolidayWork
				||workTypeData.getDailyWork().getOneDay() ==WorkTypeClassification.Shooting
				||workTypeData.getDailyWork().getMorning() == WorkTypeClassification.Shooting
				||workTypeData.getDailyWork().getAfternoon() == WorkTypeClassification.Shooting) {
			isFlag = true;
		} else {
			isFlag = false;
		}
		return isFlag;
	}
}
