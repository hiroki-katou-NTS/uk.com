package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.care;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;

/**
 * 介護休暇残数更新
 * @author hayata_maekawa
 *
 */
public class RemainCareUpdating {
	
	/**
	 * 介護休暇残数更新
	 * @param require
	 * @param employeeId
	 * @param output
	 * @return
	 */
	public static AtomTask updateRemain(Require require, String employeeId,AggrResultOfChildCareNurse output){
		
		List<AtomTask> atomTask = new ArrayList<>();
	
		//介護使用数更新
		atomTask.add(updateUse(require,employeeId,output));
		//介護上限日数更新
		Optional<AtomTask> maxDayAtomTask = updateMaxDay(require,employeeId,output);
		
		if(maxDayAtomTask.isPresent()){
			atomTask.add(maxDayAtomTask.get());
		}
		
		return AtomTask.bundle(atomTask);
		
	}
	
	/**
	 * 介護使用数更新
	 * @param require
	 * @param employeeId
	 * @param output
	 * @return
	 */
	private static AtomTask updateUse(Require require, String employeeId,AggrResultOfChildCareNurse output){
		
		return AtomTask.of(() -> require.persistAndUpdateUseCare(employeeId,
				new CareUsedNumberData(employeeId, output.getAsOfPeriodEnd())));

	}
	
	/**
	 * 介護上限日数更新
	 * @param require
	 * @param employeeId
	 * @param output
	 * @return
	 */
	private static Optional<AtomTask> updateMaxDay(Require require, String employeeId,AggrResultOfChildCareNurse output){
		
		//起算日を含む期間かどうかを確認
		if(!output.isStartDateAtr()){
			return Optional.empty();
		}
		
		//翌年のデータが存在するか
		if(!output.getStartdateDays().getNextYear().isPresent()){
			throw new RuntimeException();
		}
		
		//上限日数更新
		return Optional.of(AtomTask.of(() ->require.updateCareMaxDay(employeeId,
				output.getStartdateDays().getNextYear().get().getLimitDays())));
		
	}
	
	public static interface Require{
		void persistAndUpdateUseCare(String employeeId, CareUsedNumberData domain);
		void updateCareMaxDay(String cId, ChildCareNurseUpperLimit ThisFiscalYear);
	}
		
		
}
