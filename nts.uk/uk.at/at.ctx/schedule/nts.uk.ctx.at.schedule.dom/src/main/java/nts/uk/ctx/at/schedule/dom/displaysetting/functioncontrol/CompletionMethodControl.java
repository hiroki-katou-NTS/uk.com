package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

import java.util.HashSet;
import java.util.List;

import lombok.Value;
import nts.arc.error.BusinessException;
import nts.arc.i18n.I18NText;
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
	 * アラームチェックコードリスト
	 */
	private final List<String> alarmCheckCodeList;
	
	/**
	 * @param completionExecutionMethod 完了実行方法
	 * @param completionMethodControl 完了方法制御
	 * @param alarmCheckCodeList アラームチェックコードリスト
	 * @return
	 */
	public static CompletionMethodControl create(
			FuncCtrlCompletionExecutionMethod completionExecutionMethod,
			List<FuncCtrlCompletionMethod> completionMethodControl,
			List<String> alarmCheckCodeList ) {
		
		if ( completionMethodControl.size() != new HashSet<>(completionMethodControl).size() ) {
			throw new RuntimeException();
		}
		
		if ( completionExecutionMethod == FuncCtrlCompletionExecutionMethod.SettingBefore ) {
			
			if ( completionMethodControl.isEmpty() ) {
				throw new BusinessException("Msg_1690", I18NText.getText("KSM011_82"));
			}
			
			if ( completionMethodControl.contains(FuncCtrlCompletionMethod.AlarmCheck) && 
					alarmCheckCodeList.isEmpty() ) {
				throw new BusinessException("Msg_1690", I18NText.getText("KSM011_87"));
			}
		}
		
		return new CompletionMethodControl(completionExecutionMethod, completionMethodControl, alarmCheckCodeList);
	}

	public boolean isCompletionMethodControl (FuncCtrlCompletionMethod opt) {
		return this.completionMethodControl.contains(opt);
	}
	
}
