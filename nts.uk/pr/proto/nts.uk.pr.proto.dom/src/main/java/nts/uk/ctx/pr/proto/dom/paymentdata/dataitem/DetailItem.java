package nts.uk.ctx.pr.proto.dom.paymentdata.dataitem;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.proto.dom.layout.detail.ItemCode;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.position.DetailItemPosition;

/**
 * 明細データ明細
 * 
 * @author vunv
 *
 */
public class DetailItem extends DomainObject {

	/**
	 * 項目コード
	 */
	private final ItemCode itemCode;

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

	@Getter
	private DetailItemPosition itemPostion;

	/**
	 * Constructor
	 * 
	 * @param value
	 * @param correctFlag
	 * @param socialInsuranceAtr
	 * @param laborInsuranceAtr
	 */
	public DetailItem(ItemCode itemCode, Double value, CorrectFlag correctFlag, InsuranceAtr socialInsuranceAtr,
			InsuranceAtr laborInsuranceAtr) {
		super();
		this.itemCode = itemCode;
		this.value = value;
		this.correctFlag = correctFlag;
		this.socialInsuranceAtr = socialInsuranceAtr;
		this.laborInsuranceAtr = laborInsuranceAtr;
	}

}
