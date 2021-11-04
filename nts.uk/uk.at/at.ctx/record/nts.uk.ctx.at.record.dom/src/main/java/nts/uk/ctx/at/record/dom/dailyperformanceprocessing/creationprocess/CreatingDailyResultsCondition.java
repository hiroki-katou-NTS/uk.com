package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.creationprocess;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.日別実績処理.作成処理.日別実績を作成する条件
 */
@Value
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class CreatingDailyResultsCondition extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 未来日を作成する
	 */
	private NotUseAtr isCreatingFutureDay;

	/**
	 * [1] 日別実績を作成するか判断する
	 * 
	 * @param date 処理日
	 * @return 作成するかどうか
	 */
	public boolean isCreatingDailyResults(GeneralDate date) {
		if (this.isCreatingFutureDay.isUse()) {
			return true;
		}
		return date.beforeOrEquals(GeneralDate.today());
	}
}
