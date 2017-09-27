package nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class KrcmtDaiPerformanceFunPk {
	
	@Column(name = "FUNCTION_NO")
	public BigDecimal functionNo;
	
	@Column(name = "DESCRIPTION_OF_FUNCTION")
	public String descriptionOfFunction;

}
