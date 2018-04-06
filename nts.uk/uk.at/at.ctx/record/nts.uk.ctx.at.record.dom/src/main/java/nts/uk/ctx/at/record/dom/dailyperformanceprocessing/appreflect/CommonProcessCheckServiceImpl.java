package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ScheWorkUpdateService;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeIsFluidWork;

@Stateless
public class CommonProcessCheckServiceImpl implements CommonProcessCheckService{
	@Inject
	private WorkInformationRepository workInforRepos;
	@Inject
	private WorkTimeIsFluidWork workTimeisFluidWork;
	@Inject
	private ScheWorkUpdateService workTimeUpdate;
	@Override
	public boolean commonProcessCheck(CommonCheckParameter para) {
		ReflectedStateRecord state;
		if(para.getExecutiontype() == ExecutionType.RETURN) {
			return true;
		}
		//実績反映状態
		if(para.getDegressAtr() == DegreeReflectionAtr.RECORD) {
			state = para.getStateReflectionReal();
		} else {
			state = para.getStateReflection();
		}
		if(state == ReflectedStateRecord.WAITREFLECTION) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<Integer> lstScheWorkItem() {
		List<Integer> lstItem = new ArrayList<>();
		lstItem.add(1);
		lstItem.add(2);
		lstItem.add(3);
		lstItem.add(4);
		lstItem.add(5);
		lstItem.add(6);
		lstItem.add(7);
		lstItem.add(8);
		lstItem.add(9);
		lstItem.add(10);
		lstItem.add(11);
		lstItem.add(12);
		lstItem.add(13);
		lstItem.add(14);
		lstItem.add(15);
		lstItem.add(16);
		lstItem.add(17);
		lstItem.add(18);
		lstItem.add(19);
		lstItem.add(20);
		lstItem.add(21);
		lstItem.add(22);
		lstItem.add(23);
		lstItem.add(24);
		lstItem.add(25);
		lstItem.add(26);
		lstItem.add(27);
		
		return lstItem;
	}
	
	@Override
	public void reflectScheWorkTimeWorkType(CommonReflectParameter commonPara, boolean isPre) {
		//予定勤種を反映できるかチェックする
		if(!this.checkReflectScheWorkTimeType(commonPara, isPre)) {
			return;
		}
		//予定勤種の反映
		ReflectParameter reflectPara = new ReflectParameter(commonPara.getEmployeeId(), commonPara.getBaseDate(), commonPara.getWorkTimeCode(), commonPara.getWorkTypeCode());
		workTimeUpdate.updateWorkTimeType(reflectPara, true);
	}

	@Override
	public boolean checkReflectScheWorkTimeType(CommonReflectParameter commonPara, boolean isPre) {
		//INPUT．予定反映区分をチェックする
		if((commonPara.isScheTimeReflectAtr() == true && isPre)
				|| commonPara.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.ALWAY) {
			return true;
		}
		//INPUT．予定と実績を同じに変更する区分をチェックする
		if(commonPara.getScheAndRecordSameChangeFlg() == ScheAndRecordSameChangeFlg.FLUIDWORK) {
			//ドメインモデル「日別実績の勤務情報」を取得する
			Optional<WorkInfoOfDailyPerformance> optWorkInfor = workInforRepos.find(commonPara.getEmployeeId(), commonPara.getBaseDate());
			if(!optWorkInfor.isPresent()) {
				return false;
			}
			WorkInfoOfDailyPerformance workInforData = optWorkInfor.get();
			WorkInformation recordWorkInformation = workInforData.getRecordInfo();
			//流動勤務かどうかの判断処理
			return workTimeisFluidWork.checkWorkTimeIsFluidWork(recordWorkInformation.getWorkTimeCode().v());
		}
		
		return false;
	}

}
