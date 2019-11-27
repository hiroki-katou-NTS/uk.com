package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * 要素の組み合わせで支払う金額
 */
@AllArgsConstructor
@Getter
public class ElementsCombinationPaymentAmount extends DomainObject {

	@Setter
	private String id;
	
	/**
	 * 賃金テーブル支給金額
	 */
	private WageTablePaymentAmount wageTablePaymentAmount;

	/**
	 * 要素属性
	 */
	private ContentElementAttribute elementAttribute;

	public ElementsCombinationPaymentAmount(String id, long wageTablePaymentAmount, String firstMasterCode,
			Long firstFrameNumber, BigDecimal firstFrameLowerLimit, BigDecimal firstFrameUpperLimit,
			String secondMasterCode, Long secondFrameNumber, BigDecimal secondFrameLowerLimit,
			BigDecimal secondFrameUpperLimit, String thirdMasterCode, Long thirdFrameNumber,
			BigDecimal thirdFrameLowerLimit, BigDecimal thirdFrameUpperLimit) {
		this.id = id;
		this.wageTablePaymentAmount = new WageTablePaymentAmount(wageTablePaymentAmount);
		this.elementAttribute = new ContentElementAttribute(firstMasterCode, firstFrameNumber, firstFrameLowerLimit,
				firstFrameUpperLimit, secondMasterCode, secondFrameNumber, secondFrameLowerLimit, secondFrameUpperLimit,
				thirdMasterCode, thirdFrameNumber, thirdFrameLowerLimit, thirdFrameUpperLimit);
	}

}
