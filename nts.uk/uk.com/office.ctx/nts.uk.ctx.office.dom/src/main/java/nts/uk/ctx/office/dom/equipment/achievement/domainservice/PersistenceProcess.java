package nts.uk.ctx.office.dom.equipment.achievement.domainservice;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.task.tran.AtomTask;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.実績項目設定.永続化処理
 * @author NWS-DungDV
 *
 */
@Getter
@AllArgsConstructor
public class PersistenceProcess {
	// 設備の実績入力フォーマット設定の永続化処理
	private List<AtomTask> inputFormatSettingTasks;
	
	// 設備利用実績の項目設定の永続化処理
	private List<AtomTask> itemSettingTasks;
	
	// 設備帳票設定の永続化処理
	private List<AtomTask> formSettingTasks;
}
