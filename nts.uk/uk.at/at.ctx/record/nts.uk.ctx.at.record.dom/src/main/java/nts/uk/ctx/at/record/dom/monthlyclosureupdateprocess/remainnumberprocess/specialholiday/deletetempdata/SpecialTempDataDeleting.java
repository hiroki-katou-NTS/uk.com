package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.specialholiday.deletetempdata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;

/**
 * 特別休暇暫定データ削除
 * @author shuichi_ishida
 */
public class SpecialTempDataDeleting {

	/**
	 * 特別休暇暫定データ削除
	 * @param empId 社員ID
	 * @param period 期間
	 */
	public static AtomTask deleteTempDataProcess(RequireM1 require, String empId, DatePeriod period) {

		List<AtomTask> atomTask = new ArrayList<>();
		
		// 「特別休暇暫定データを削除する」
		List<InterimRemain> interimRemains = require.interimRemain(empId, period, RemainType.SPECIAL);
		if (CollectionUtil.isEmpty(interimRemains)) return AtomTask.bundle(atomTask);
		
		List<String> ids = interimRemains.stream().map(b -> b.getRemainManaID()).collect(Collectors.toList());
		
		for (String id : ids) atomTask.add(AtomTask.of(() -> require.deleteSpecialHolidayInterim(id)));
		
		atomTask.add(AtomTask.of(() -> require.deleteInterimRemain(empId, period, RemainType.SPECIAL)));
		
		return AtomTask.bundle(atomTask);
	}
	
	public static interface RequireM1 {
		
		List<InterimRemain> interimRemain(String employeeId, DatePeriod dateData, RemainType remainType);
		
		void deleteSpecialHolidayInterim(String specialId);
		
		void deleteInterimRemain(String employeeId, DatePeriod dateData, RemainType remainType);
	}
}
