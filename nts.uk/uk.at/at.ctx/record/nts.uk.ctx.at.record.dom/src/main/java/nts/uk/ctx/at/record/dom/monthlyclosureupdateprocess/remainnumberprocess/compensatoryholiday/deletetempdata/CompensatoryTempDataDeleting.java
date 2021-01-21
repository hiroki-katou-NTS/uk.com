package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday.deletetempdata;

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
 * @author HungTT - <<Work>> 代休暫定データ削除
 *
 */
public class CompensatoryTempDataDeleting {

	// 暫定データ削除
	public static AtomTask deleteTempDataProcess(RequireM3 require, AggrPeriodEachActualClosure period, String empId) {
		
		return deleteTempLeaveMngData(require, period.getPeriod(), empId)
				.then(deleteTempCompensatoryData(require, period.getPeriod(), empId));
	}
	
	// 休出暫定データの削除
	private static AtomTask deleteTempLeaveMngData(RequireM2 require, DatePeriod period, String empId) {
		AtomTask atomTask = AtomTask.of(() -> {});
		
		List<InterimRemain> listTempRemain = require.interimRemain(empId, period, RemainType.BREAK);
		
		if (CollectionUtil.isEmpty(listTempRemain)) return atomTask;
		
		List<String> listBreakId = listTempRemain.stream().map(b -> b.getRemainManaID()).collect(Collectors.toList());
		
		return atomTask.then(() -> require.deleteInterimBreakMng(listBreakId));
	}
	
	// 代休暫定データの削除
	private static AtomTask deleteTempCompensatoryData(RequireM1 require, DatePeriod period, String empId) {
		AtomTask atomTask = AtomTask.of(() -> {});
		
		List<InterimRemain> listTempRemain = require.interimRemain(empId, period, RemainType.SUBHOLIDAY);
		
		if (CollectionUtil.isEmpty(listTempRemain)) return atomTask;
		
		List<String> listDayOffId = listTempRemain.stream().map(b -> b.getRemainManaID()).collect(Collectors.toList());
		
		return atomTask.then(() -> require.deleteInterimDayOffMng(listDayOffId));
	}
	
	public static interface RequireM3 extends RequireM2, RequireM1 {
		
	}
	
	public static interface RequireM2 extends RequireM0 {
		
		void deleteInterimBreakMng(List<String> listBreakId);
	}
	
	public static interface RequireM1 extends RequireM0 {
		
		void deleteInterimDayOffMng(List<String> mngIds);
	}
	
	public static interface RequireM0 {
		
		List<InterimRemain> interimRemain(String employeeId, DatePeriod dateData, RemainType remainType);
	}
}
