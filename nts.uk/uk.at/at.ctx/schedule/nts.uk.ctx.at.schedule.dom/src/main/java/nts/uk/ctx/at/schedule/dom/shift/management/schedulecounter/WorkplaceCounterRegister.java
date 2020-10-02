package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import java.util.ArrayList;
import java.util.List;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterType;

/**
 * 職場計を登録する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.スケジュール集計.職場計を登録する
 * @author dan_pv
 *
 */
public class WorkplaceCounterRegister {
	
	/**
	 * 登録
	 * @param require
	 * @param target 登録したい対象
	 * @return
	 */
	public static WorkplaceCounterRegisterResult register(Require require, WorkplaceCounter target) {
		
		List<WorkplaceCounterCategory> notDetailSettingList = new ArrayList<>();
		
		if ( target.isUsed(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME) && !require.existsLaborCostAndTime() ) {
			notDetailSettingList.add(WorkplaceCounterCategory.LABOR_COSTS_AND_TIME);
		}
		
		if ( target.isUsed(WorkplaceCounterCategory.TIMEZONE_PEOPLE) && !require.existsTimeZonePeople() ) {
			notDetailSettingList.add(WorkplaceCounterCategory.TIMEZONE_PEOPLE);
		}
		
		if ( target.isUsed(WorkplaceCounterCategory.TIMES_COUNTING) && !require.existsTimesCouting(TimesNumberCounterType.WORKPLACE) ) {
			notDetailSettingList.add(WorkplaceCounterCategory.TIMES_COUNTING);
		}
		
		Runnable task = () -> {
            if (require.existsWorkplaceCounter()) {
                require.updateWorkplaceCounter(target);
            } else {
                require.insertWorkplaceCounter(target);
            }
        }; 
        
        return new WorkplaceCounterRegisterResult(AtomTask.of(task), notDetailSettingList);
		
	}
	
	public static interface Require {
		
		/**
		 * 職場計の人件費・時間が既に登録されている	
		 * @return
		 */
		boolean existsLaborCostAndTime();
		
		/**
		 * 職場計の時間帯人数が既に登録されている
		 * @return
		 */
		boolean existsTimeZonePeople();
		
		/**
		 * 回数集計選択が既に登録されている
		 * @param type
		 * @return
		 */
		boolean existsTimesCouting(TimesNumberCounterType type);
		
		/**
		 * 職場計が既に登録されている
		 * @return
		 */
		boolean existsWorkplaceCounter();
		
		/**
		 * 職場計を変更する
		 * @param workplaceCounter
		 */
		void updateWorkplaceCounter(WorkplaceCounter workplaceCounter);
		
		/**
		 * 職場計を追加する
		 * @param workplaceCounter
		 */
		void insertWorkplaceCounter(WorkplaceCounter workplaceCounter);
	}

}
