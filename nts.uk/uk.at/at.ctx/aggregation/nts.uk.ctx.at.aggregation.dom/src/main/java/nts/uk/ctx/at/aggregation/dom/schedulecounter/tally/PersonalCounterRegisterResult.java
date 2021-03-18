package nts.uk.ctx.at.aggregation.dom.schedulecounter.tally;

import java.util.List;

import lombok.Value;
import nts.arc.task.tran.AtomTask;

/**
 * 個人計の登録結果
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.職場計・個人計.個人計の登録結果
 * @author dan_pv
 *
 */
@Value
public class PersonalCounterRegisterResult {

	/**
	 * insertかupdateかの登録のAtomTask
	 */
	private AtomTask atomTask;

	/**
	 * 個人計を登録できたが、詳細情報の登録していないカテゴリ一覧
	 */
	private List<PersonalCounterCategory> notDetailSettingList;

}
