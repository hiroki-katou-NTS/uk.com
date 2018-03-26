package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ScheAndRecordSameChangeFlg;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeIsFluidWork;

@Stateless
public class AfterOvertimeReflectProcessImpl implements AfterOvertimeReflectProcess {
	@Inject
	private WorkTimeIsFluidWork workTimeService;
	@Override
	public boolean checkScheReflect(String employeeId, GeneralDate baseDate,
			ScheAndRecordSameChangeFlg scheAndRecordSameChangeFlg, String workTimeCode) {
		//INPUT．予定と実績を同じに変更する区分をチェックする
		if(scheAndRecordSameChangeFlg == ScheAndRecordSameChangeFlg.NOTAUTO) {
			return false;
		} else if (scheAndRecordSameChangeFlg == ScheAndRecordSameChangeFlg.ALWAY) {
			return true;
		}
		//INPUT．予定と実績を同じに変更する区分が「流動勤務のみ自動変更する」
		//流動勤務かどうかの判断処理
		return workTimeService.checkWorkTimeIsFluidWork(workTimeCode);
	}

}
