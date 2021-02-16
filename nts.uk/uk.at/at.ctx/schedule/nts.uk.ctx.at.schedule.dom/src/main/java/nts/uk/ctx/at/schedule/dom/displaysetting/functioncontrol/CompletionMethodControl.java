package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

import java.util.List;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * 完了方法制御
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.機能制御.完了方法制御
 * @author dan_pv
 *
 */
@Value
public class CompletionMethodControl implements DomainValue {
	
	/**
	 * 完了実行方法
	 */
	private final FuncCtrlCompletionExecutionMethod completionExecutionMethod;
	
	/**
	 * 完了方法制御
	 */
	private final List<FuncCtrlCompletionMethod> completionMethodControl;
	
	/**
	 * 	アラームチェックコードリスト
	 */
	private final List<String> alarmCheckCodeList;
	
}
