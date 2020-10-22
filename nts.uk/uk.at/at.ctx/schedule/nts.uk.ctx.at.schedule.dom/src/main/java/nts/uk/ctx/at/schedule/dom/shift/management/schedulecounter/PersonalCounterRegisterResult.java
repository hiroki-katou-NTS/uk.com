package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import java.util.List;

import lombok.Value;
import nts.arc.task.tran.AtomTask;

/**
 * 個人計の登録結果
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.スケジュール集計.個人計の登録結果
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
