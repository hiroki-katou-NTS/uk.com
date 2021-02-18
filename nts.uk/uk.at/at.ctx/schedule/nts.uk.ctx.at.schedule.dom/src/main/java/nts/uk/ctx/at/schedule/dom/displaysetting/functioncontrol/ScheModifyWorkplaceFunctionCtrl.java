package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * スケジュール修正職場別の機能制御
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.機能制御.スケジュール修正職場別の機能制御
 * @author dan_pv
 *
 */
@AllArgsConstructor
@Getter
public class ScheModifyWorkplaceFunctionCtrl implements DomainAggregate {
	
	/**
	 * 使用する表示期間
	 */
	private List<FuncCtrlDisplayPeriod> useDisplayPeriod;
	
	/**
	 * 使用する表示形式
	 */
	private List<FuncCtrlDisplayFormat> useDisplayFormat;
	
	/**
	 * 起動できる画面
	 */
	private List<FuncCtrlStartControl> pageCanBeStarted;
	
	/**
	 * 完了利用する区分
	 */
	private NotUseAtr useCompletionAtr;
	
	/**
	 * 完了方法制御
	 */
	private Optional<CompletionMethodControl> completionMethodControl;
	
	/**
	 * @param useDisplayPeriod 使用する表示期間
	 * @param useDisplayFormat 使用する表示形式
	 * @param pageCanBeStarted 動できる画面
	 * @param useCompletionAtr 完了利用する区分
	 * @param completionMethodControl 完了方法制御
	 * @return
	 */
	public static ScheModifyWorkplaceFunctionCtrl create(
			List<FuncCtrlDisplayPeriod> useDisplayPeriod,
			List<FuncCtrlDisplayFormat> useDisplayFormat,
			List<FuncCtrlStartControl> pageCanBeStarted,
			NotUseAtr useCompletionAtr,
			Optional<CompletionMethodControl> completionMethodControl) {
		
		if ( useDisplayPeriod.size() != new HashSet<>(useDisplayPeriod).size()) {
			throw new RuntimeException("Attribute 'useDisplayPeriod' of ScheModifyWorkplaceFunctionCtrl is duplicate");
		}
		
		if ( useDisplayFormat.size() != new HashSet<>(useDisplayFormat).size()) {
			throw new RuntimeException("Attribute 'useDisplayFormat' of ScheModifyWorkplaceFunctionCtrl is duplicate");
		}
		
		if ( pageCanBeStarted.size() != new HashSet<>(pageCanBeStarted).size()) {
			throw new RuntimeException("Attribute 'pageCanBeStarted' of ScheModifyWorkplaceFunctionCtrl is duplicate");
		}
		
		if ( useDisplayFormat.isEmpty() ) {
			throw new BusinessException("Msg_1690", "KSM011_76");
		}
		
		if ( useCompletionAtr.isUse() && !completionMethodControl.isPresent() ) {
			throw new BusinessException("Msg_1690", "KSM011_78");
		}
		
		return new ScheModifyWorkplaceFunctionCtrl(useDisplayPeriod, useDisplayFormat, pageCanBeStarted, useCompletionAtr, completionMethodControl);
		
	}

}
