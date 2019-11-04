package nts.uk.screen.at.app.dailyperformance.correction.mobile;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.MonthlyCorrectConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition.TimeItemCheckMonthly;

@Stateless
public class ErrorAlarmDetailMob {

	@Inject
	private ErrorAlarmWorkRecordRepository errAlarmRepo;
	@Inject
	private MonthlyCorrectConditionRepository msg;
	/**
	 * E：エラー参照（詳細）を起動する
	 * @param code
	 * @return
	 */
	public ErrorAlarmDtoMob getErrorAlarm(ErrorAlarmParamMob param){
		//コードを指定してエラー/アラームを取得する
		Optional<ErrorAlarmWorkRecord> errOp = errAlarmRepo.findByCode(param.getErrCode());
		if(!errOp.isPresent()) return null;
		ErrorAlarmWorkRecord err = errOp.get();
		Optional<TimeItemCheckMonthly> msgOp =  msg.findTimeItemCheckMonthlyById(err.getErrorAlarmCheckID(), param.getErrCode());
		String msg = msgOp.isPresent() && msgOp.get().getDisplayMessage().isPresent() ? msgOp.get().getDisplayMessage().get().v() : "";		
		
		return new ErrorAlarmDtoMob(param.getErrCode(), err.getName().v(), msg);
	}
}
