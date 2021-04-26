package nts.uk.ctx.at.function.dom.annualworkschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.年間勤務表.項目の算出式
 * 
 * @author LienPTK
 */
@Getter
@AllArgsConstructor
public class CalculationFormulaOfItem extends DomainObject {

	/** オペレーション. */
	private int operation;

	/** 勤怠項目. */
	private int attendanceItemId;
}
