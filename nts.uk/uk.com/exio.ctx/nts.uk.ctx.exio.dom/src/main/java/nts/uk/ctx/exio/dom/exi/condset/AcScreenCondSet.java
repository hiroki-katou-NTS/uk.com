package nts.uk.ctx.exio.dom.exi.condset;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.exio.dom.input.DataItem;

/**
 * 受入選別条件設定
 */
@Getter
public class AcScreenCondSet extends DomainObject {

	/**
	 * 条件設定コード
	 */
	private AcceptanceConditionCode conditionSetCd;

	/**
	 * 受入項目の番号
	 */
	private AcceptanceLineNumber acceptItemNum;

	/**
	 * 受入可能な値の条件
	 */
	private Validation validation;

	public AcScreenCondSet(String conditionSetCd, int acceptItemNum, Validation validation) {
		super();
		this.conditionSetCd = new AcceptanceConditionCode(conditionSetCd);
		this.acceptItemNum = new AcceptanceLineNumber(acceptItemNum);
		this.validation = validation;
	}
	/**
	 * 受入条件の判定
	 * @param itemValue
	 * @param itemType
	 * @return
	 */
	public boolean checkCondNumber(DataItem target) {
		return validation.validate(target);
	}
}
