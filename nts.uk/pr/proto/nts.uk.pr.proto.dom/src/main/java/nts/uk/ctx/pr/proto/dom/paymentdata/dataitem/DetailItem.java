package nts.uk.ctx.pr.proto.dom.paymentdata.dataitem;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.proto.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.proto.dom.itemmaster.ItemCode;
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
	@Getter
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
	
	@Getter
	private CategoryAtr categoryAttribute;
	
	/**
	 * Constructor
	 * 
	 * @param value
	 * @param correctFlag
	 * @param socialInsuranceAtr
	 * @param laborInsuranceAtr
	 */
	public DetailItem(ItemCode itemCode, Double value, CorrectFlag correctFlag, InsuranceAtr socialInsuranceAtr,
			InsuranceAtr laborInsuranceAtr, CategoryAtr categoryAttribute) {
		super();
		this.itemCode = itemCode;
		this.value = value;
		this.correctFlag = correctFlag;
		this.socialInsuranceAtr = socialInsuranceAtr;
		this.laborInsuranceAtr = laborInsuranceAtr;
	}

	public DetailItem createFromJavaType(String itemCode, Double value, int correctFlag, int socialInsuranceAtr,
			int laborInsuranceAtr, int categoryAttribute) {

		return new DetailItem(new ItemCode(itemCode), value.doubleValue(),
				EnumAdaptor.valueOf(correctFlag, CorrectFlag.class),
				EnumAdaptor.valueOf(socialInsuranceAtr, InsuranceAtr.class),
				EnumAdaptor.valueOf(laborInsuranceAtr, InsuranceAtr.class),
				EnumAdaptor.valueOf(categoryAttribute, CategoryAtr.class));
	}

}
