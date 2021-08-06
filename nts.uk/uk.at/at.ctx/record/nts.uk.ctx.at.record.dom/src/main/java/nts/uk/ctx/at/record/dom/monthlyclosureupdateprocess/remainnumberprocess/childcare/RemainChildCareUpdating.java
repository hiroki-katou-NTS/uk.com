package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.childcare;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;

/**
 * 子の看護情報更新
 * @author hayata_maekawa
 *
 */
public class RemainChildCareUpdating {

	/**
	 * 子の看護情報更新
	 * @param require
	 * @param employeeId
	 * @param output
	 * @return
	 */
	public static AtomTask updateRemainChildCare(Require require, String employeeId,AggrResultOfChildCareNurse output){
		
		List<AtomTask> atomTask = new ArrayList<>();
		
		//子の看護使用数更新
		atomTask.add(updateUseChildCare(require,employeeId,output));
		//子の看護上限数更新
		Optional<AtomTask> maxDayAtomTask = updateMaxDayChildCare(require,employeeId,output);
		
		if(maxDayAtomTask.isPresent()){
			atomTask.add(maxDayAtomTask.get());
		}
		
		return AtomTask.bundle(atomTask);
	}
	
	/**
	 * 子の看護使用数更新
	 * @param require
	 * @param period
	 * @param employeeId
	 * @param output
	 * @return
	 */
	private static AtomTask updateUseChildCare(Require require, String employeeId,AggrResultOfChildCareNurse output){
		
		return AtomTask.of(() -> require.persistAndUpdateUseChildCare(employeeId,
				new ChildCareUsedNumberData(employeeId, output.getAsOfPeriodEnd())));

	}
	
	private static Optional<AtomTask> updateMaxDayChildCare(Require require, String employeeId,AggrResultOfChildCareNurse output){
	
		//起算日を含む期間かどうかを確認
		if(!output.isStartDateAtr()){
			return Optional.empty();
		}
		
		//翌年のデータが存在するか
		if(!output.getStartdateDays().getNextYear().isPresent()){
			throw new RuntimeException();
		}
		
		//上限日数更新
		return Optional.of(AtomTask.of(() ->require.updateChildCareMaxDay(employeeId,
				output.getStartdateDays().getNextYear().get().getLimitDays())));
		
	}
	
	public static interface Require{
		void persistAndUpdateUseChildCare(String employeeId, ChildCareUsedNumberData domain);
		void updateChildCareMaxDay(String cId, ChildCareNurseUpperLimit ThisFiscalYear);
	}
}
