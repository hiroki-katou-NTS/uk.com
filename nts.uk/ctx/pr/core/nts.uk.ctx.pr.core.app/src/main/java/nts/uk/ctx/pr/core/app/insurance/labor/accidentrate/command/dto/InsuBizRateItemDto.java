package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command.dto;

import lombok.Data;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;

@Data
public class InsuBizRateItemDto {
	/** The insu biz type. */
	private Integer insuBizType;

	/** The insu rate. */
	private Double insuRate;

	/** The insu round. */
	private Integer insuRound;

	public InsuBizRateItem toDomain() {
		InsuBizRateItem insuBizRateItem = new InsuBizRateItem();
		insuBizRateItem.setInsuBizType(BusinessTypeEnum.valueOf(this.insuBizType));
		insuBizRateItem.setInsuRate(this.insuRate);
		insuBizRateItem.setInsuRound(RoundingMethod.valueOf(this.insuRound));
		return insuBizRateItem;
	}
}
