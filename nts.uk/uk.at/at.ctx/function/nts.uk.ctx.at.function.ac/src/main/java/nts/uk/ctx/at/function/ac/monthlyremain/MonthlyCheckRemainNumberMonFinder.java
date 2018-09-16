package nts.uk.ctx.at.function.ac.monthlyremain;

import java.util.Optional;

import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.adapter.monthlyremain.CheckRemainNumberMonImport;
//import nts.uk.ctx.at.function.dom.adapter.monthlyremain.GetCheckRemainNumberMonPub;
import nts.uk.ctx.at.function.dom.adapter.monthlyremain.MonthlyCheckRemainNumberMonAdapter;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckRemainNumberMon;

public class MonthlyCheckRemainNumberMonFinder implements MonthlyCheckRemainNumberMonAdapter{
	
//	@Inject 
//	GetCheckRemainNumberMonPub getCheckRemainNumberMonPub;
	
	@Override
	public Optional<CheckRemainNumberMonImport> getByEralCheckID(String errorAlarmID) {
//		CheckRemainNumberMon x = getCheckRemainNumberMonPub.getByEralCheckID(errorAlarmID);
//		return new Optional.of(CheckRemainNumberMonImport(x.getErrorAlarmCheckID(),
//				EnumAdaptor.valueOf(x.getCheckVacation().value,TypeCheckVacationImport.class),
//				EnumAdaptor.valueOf(x.getCheckOperatorType().value,CheckOperatorType.class),
//				x.getListAttdID()));
		return null;
	}

}
