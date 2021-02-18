package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

import java.util.HashSet;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * スケジュール修正の機能制御
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.機能制御.スケジュール修正の機能制御
 * @author hiroko_miura
 *
 */
@Getter
@AllArgsConstructor
public class ScheFunctionControl implements DomainAggregate {
	
	// 時刻修正できる勤務形態
	private List<WorkTimeForm> changeableWorks;
	
	// 実績表示できるか
	private boolean isDisplayActual;
	
	// 表示可能勤務種類制御
	private NotUseAtr displayWorkTypeControl;
	
	// 表示可能勤務種類リスト
	private List<WorkTypeCode> displayableWorkTypeCodeList;
	
	/**
	 * 作る
	 * @param lstChangeable 時刻修正できる勤務形態
	 * @param displayAcual 実績表示できるか
	 * @param displayWorkTypeControl 表示可能勤務種類制御
	 * @param displayableWorkTypeCodeList 示可能勤務種類リスト
	 * @return
	 */
	public static ScheFunctionControl create (
			List<WorkTimeForm> lstChangeable, 
			boolean displayAcual, 
			NotUseAtr displayWorkTypeControl, 
			List<WorkTypeCode> displayableWorkTypeCodeList) {
		
		if (lstChangeable.size() > 3) {
			throw new RuntimeException();
		}
		
		if (lstChangeable.size() != new HashSet<>(lstChangeable).size()) {
			throw new RuntimeException();
		}
		
		if ( displayWorkTypeControl.isUse() && displayableWorkTypeCodeList.isEmpty() ) {
			throw new BusinessException("Msg_1690", "KSM011_47");
		}
		
		return new ScheFunctionControl(lstChangeable, displayAcual, displayWorkTypeControl, displayableWorkTypeCodeList);
	}
	
	/**
	 * 指定した勤務形態が修正可能か
	 * @param form
	 * @return
	 */
	public boolean isChangeableForm (WorkTimeForm form) {
		return this.changeableWorks.contains(form);
	}
}