package nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class KrcmtDaiPerformanceAutPk {
	
	@Column(name = "ROLE_ID")
	public BigDecimal roleId;
	
	@Column(name = "FUNCTION_NO")
	public BigDecimal functionNo;

}
