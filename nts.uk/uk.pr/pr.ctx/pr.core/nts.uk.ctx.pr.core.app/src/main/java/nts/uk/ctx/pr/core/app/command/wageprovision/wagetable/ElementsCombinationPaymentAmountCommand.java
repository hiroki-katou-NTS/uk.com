package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementsCombinationPaymentAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTablePaymentAmount;

/**
 * 要素の組み合わせで支払う金額
 */
@Data
@NoArgsConstructor
public class ElementsCombinationPaymentAmountCommand {

	private String id;
	
	/**
	 * 賃金テーブル支給金額
	 */
	private Long wageTablePaymentAmount;

	/**
	 * 要素属性
	 */
	private ContentElementAttributeCommand elementAttribute;

	public ElementsCombinationPaymentAmount fromCommandToDomain() {
		return new ElementsCombinationPaymentAmount(id == null || id.isEmpty() ? IdentifierUtil.randomUniqueId() : id,
				new WageTablePaymentAmount(wageTablePaymentAmount), elementAttribute.fromCommandToDomain());
	}

}
