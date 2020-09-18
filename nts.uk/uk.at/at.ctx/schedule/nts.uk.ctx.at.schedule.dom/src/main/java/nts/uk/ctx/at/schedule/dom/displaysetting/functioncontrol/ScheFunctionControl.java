package nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol;

import java.util.HashSet;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;

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
	
	/**
	 * 作る
	 * @param lstChangeable
	 * @param displayAcual
	 * @return ScheFunctionControl
	 */
	public static ScheFunctionControl create (List<WorkTimeForm> lstChangeable, boolean displayAcual) {
		if (lstChangeable.size() > 3) {
			throw new RuntimeException();
		}
		
		if (lstChangeable.size() != new HashSet<>(lstChangeable).size()) {
			throw new RuntimeException();
		}
		
		return new ScheFunctionControl(lstChangeable, displayAcual);
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