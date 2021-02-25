package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday.deletetempdata;

import java.util.List;
import java.util.stream.Collectors;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;

/**
 * 
 * @author HungTT -<<Work>> 振休暫定データ削除
 *
 */
public class SubstitutionTempDataDeleting {

	// 暫定データ削除
	public static AtomTask deleteTempSubstitutionData(RequireM3 require, 
			AggrPeriodEachActualClosure period, String empId) {
		
		return deleteTempPayoutMngData(require, period.getPeriod(), empId)
				.then(deleteTempSubstitutionData(require, period.getPeriod(), empId));
	}

	// 振出暫定データの削除
	private static AtomTask deleteTempPayoutMngData(RequireM2 require, DatePeriod period, String employeeId) {
		List<InterimRemain> listTempRemain = require.interimRemain(employeeId, period, RemainType.PICKINGUP);
		
		if (CollectionUtil.isEmpty(listTempRemain))
			return AtomTask.of(() -> {});
		
		List<String> listRecId = listTempRemain.stream().map(r -> r.getRemainManaID()).collect(Collectors.toList());
		
		return AtomTask.of(() -> require.deleteInterimRecMng(listRecId));
	}

	// 振休暫定データの削除
	private static AtomTask deleteTempSubstitutionData(RequireM1 require, DatePeriod period, String employeeId) {
		List<InterimRemain> listTempRemain = require.interimRemain(employeeId, period, RemainType.PAUSE);
		
		if (CollectionUtil.isEmpty(listTempRemain))
			return AtomTask.of(() -> {});
		
		List<String> listAbsId = listTempRemain.stream().map(r -> r.getRemainManaID()).collect(Collectors.toList());
		
		return AtomTask.of(() -> require.deleteInterimAbsMng(listAbsId));
	}

	public static interface RequireM3 extends RequireM1, RequireM2 {
		
	}

	public static interface RequireM2 extends RequireM0 {
		
		void deleteInterimRecMng(List<String> listRecId);
	}

	public static interface RequireM1 extends RequireM0 {  
		
		void deleteInterimAbsMng(List<String> listAbsMngId);
	}

	public static interface RequireM0 {
		
		List<InterimRemain> interimRemain(String employeeId, DatePeriod dateData, RemainType remainType);
	}
}
