package nts.uk.ctx.core.app.insurance.labor.accidentrate;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;

@Setter
@Getter
public class InsuBizRateItemDto extends InsuBizRateItem {
	private String insuranceBusinessType;

	public InsuBizRateItemDto() {
		super();
	}
}
