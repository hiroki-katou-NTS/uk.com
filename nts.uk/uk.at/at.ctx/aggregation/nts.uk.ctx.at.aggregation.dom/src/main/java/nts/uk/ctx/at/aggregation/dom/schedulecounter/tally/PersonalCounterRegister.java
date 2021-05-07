package nts.uk.ctx.at.aggregation.dom.schedulecounter.tally;

import java.util.ArrayList;
import java.util.List;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.timescounting.TimesNumberCounterType;

/**
 * 個人計を登録する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.職場計・個人計.個人計を登録する
 * @author dan_pv
 *
 */
public class PersonalCounterRegister {

	/**
	 * 登録
	 * @param require
	 * @param target 登録したい対象
	 * @return
	 */
	public static PersonalCounterRegisterResult register(Require require, PersonalCounter target) {

		List<PersonalCounterCategory> notDetailSettingList = new ArrayList<>();

		if ( target.isUsed(PersonalCounterCategory.TIMES_COUNTING_1) && !require.existsTimesCouting(TimesNumberCounterType.PERSON_1) ) {
			notDetailSettingList.add(PersonalCounterCategory.TIMES_COUNTING_1);
		}

		if ( target.isUsed(PersonalCounterCategory.TIMES_COUNTING_2) && !require.existsTimesCouting(TimesNumberCounterType.PERSON_2) ) {
			notDetailSettingList.add(PersonalCounterCategory.TIMES_COUNTING_2);
		}

		if ( target.isUsed(PersonalCounterCategory.TIMES_COUNTING_3) && !require.existsTimesCouting(TimesNumberCounterType.PERSON_3) ) {
			notDetailSettingList.add(PersonalCounterCategory.TIMES_COUNTING_3);
		}

		Runnable task = () -> {
			if (require.existsOnePersonCounter()) {
				require.updateOnePersonCounter(target);
			} else {
				require.insertOnePersonCounter(target);
			}
		};

		return new PersonalCounterRegisterResult(AtomTask.of(task), notDetailSettingList);

	}

	public static interface Require {

		/**
		 * 回数集計選択が既に登録されている
		 * @param type
		 * @return
		 */
		boolean existsTimesCouting(TimesNumberCounterType type);

		/**
		 * 個人計が既に登録されている
		 * @return
		 */
		boolean existsOnePersonCounter();

		/**
		 * 個人計を変更する
		 * @param onePersonCounter
		 */
		void updateOnePersonCounter(PersonalCounter onePersonCounter);

		/**
		 * 個人計を追加する
		 * @param onePersonCounter
		 */
		void insertOnePersonCounter(PersonalCounter onePersonCounter);
	}

}
