package nts.uk.ctx.pr.proto.dom.paymentdata.valueobjects;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 明細データ明細
 * 
 * @author vunv
 *
 */
public class DetailItem extends DomainObject {

	/**
	 * 値
	 */
	@Getter
	private final Double value;

	/**
	 * 修正フラグ
	 */
	@Getter
	private final CorrectFlag correctFlag;

	/**
	 * 社保対象区分
	 */
	@Getter
	private final InsuranceAtr socialInsuranceAtr;

	/**
	 * 労保対象区分
	 */
	@Getter
	private final InsuranceAtr laborInsuranceAtr;

	/**
	 * Constructor
	 * 
	 * @param value
	 * @param correctFlag
	 * @param socialInsuranceAtr
	 * @param laborInsuranceAtr
	 */
	public DetailItem(Double value, CorrectFlag correctFlag, InsuranceAtr socialInsuranceAtr,
			InsuranceAtr laborInsuranceAtr) {
		super();
		this.value = value;
		this.correctFlag = correctFlag;
		this.socialInsuranceAtr = socialInsuranceAtr;
		this.laborInsuranceAtr = laborInsuranceAtr;
	}

}
