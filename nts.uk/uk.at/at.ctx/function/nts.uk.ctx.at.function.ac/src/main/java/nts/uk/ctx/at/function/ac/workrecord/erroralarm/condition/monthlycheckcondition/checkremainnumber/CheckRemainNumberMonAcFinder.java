package nts.uk.ctx.at.function.ac.workrecord.erroralarm.condition.monthlycheckcondition.checkremainnumber;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber.CheckConValueRemainNumberImport;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber.CheckRemainNumberMonFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber.CheckRemainNumberMonFunImport;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber.CompareRangeImport;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber.CompareSingleValueImport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.checkremainnumber.CheckConValueRemainingNumberEx;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.checkremainnumber.CheckRemainNumberMonPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.checkremainnumber.CheckRemainNumberMonPubEx;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.checkremainnumber.CompareRangeEx;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.checkremainnumber.CompareSingleValueEx;

@Stateless
public class CheckRemainNumberMonAcFinder implements CheckRemainNumberMonFunAdapter {

	@Inject
	private CheckRemainNumberMonPub repo;
	
	@Override
	public CheckRemainNumberMonFunImport getByEralCheckID(String errorAlarmID) {
		CheckRemainNumberMonPubEx data = repo.getByEralCheckID(errorAlarmID);
		if(data == null) {
			return null;
		}
		return convertToExport(data);
	}
	
	
	private CheckRemainNumberMonFunImport convertToExport(CheckRemainNumberMonPubEx export) {
		return new CheckRemainNumberMonFunImport(
				export.getErrorAlarmCheckID(),
				export.getCheckVacation(),
				export.getCheckOperatorType(),
				export.getCompareRangeEx()==null?null : convertToCompareRangeEx(export.getCompareRangeEx()),//export.getCompareRangeEx(),
				export.getCompareSingleValueEx()==null?null:convertToCompareSingleValueEx(export.getCompareSingleValueEx()),//ex
				export.getListItemID()
				
				);
	}
	
	private CompareRangeImport convertToCompareRangeEx(CompareRangeEx export) {
		return new CompareRangeImport(
				export.getCompareOperator(),
				converToCheckConValueRemainNumberEx(export.getStartValue()),
				converToCheckConValueRemainNumberEx(export.getEndValue())
				);
	}
	private CompareSingleValueImport convertToCompareSingleValueEx(CompareSingleValueEx export) {
		return new CompareSingleValueImport(
				export.getCompareOpertor(),
				converToCheckConValueRemainNumberEx(export.getValue())
				);
	}
	
	private CheckConValueRemainNumberImport converToCheckConValueRemainNumberEx(CheckConValueRemainingNumberEx export) {
		return new CheckConValueRemainNumberImport(
				export.getDaysValue(),
				export.getTimeValue()
				);
	}

}
