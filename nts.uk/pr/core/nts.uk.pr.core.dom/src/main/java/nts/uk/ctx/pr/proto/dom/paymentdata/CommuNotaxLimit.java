package nts.uk.ctx.pr.core.dom.paymentdata;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;

public class CommuNotaxLimit extends AggregateRoot {
	
	@Getter
	private CompanyCode ccd;
	
	@Getter
	private ExclusiveVersion exclusiveVersion;
	
	@Getter
	private CommuNotaxLimitCode commuNotaxLimitCode;
	
	@Getter
	private CommuNotaxLimitName commuNotaxLimitName;
	
	@Getter
	private CommuNotaxLimitValue commuNotaxLimitValue;
	
	public CommuNotaxLimit(CompanyCode ccd, ExclusiveVersion exclusiveVersion, CommuNotaxLimitCode commuNotaxLimitCode, CommuNotaxLimitName commuNotaxLimitName,
			CommuNotaxLimitValue commuNotaxLimitValue) {
		super();
		this.ccd = ccd;
		this.exclusiveVersion = exclusiveVersion;
		this.commuNotaxLimitCode = commuNotaxLimitCode;
		this.commuNotaxLimitName = commuNotaxLimitName;
		this.commuNotaxLimitValue = commuNotaxLimitValue;
	}

	public static CommuNotaxLimit createFromJavaType(String ccd, int exclusiveVersion, String commuNotaxLimitCode, String commuNotaxLimitName,
			int commuNotaxLimitValue) {

		return new CommuNotaxLimit(new CompanyCode(ccd),
									new ExclusiveVersion(exclusiveVersion),
									new CommuNotaxLimitCode(commuNotaxLimitCode),
									new CommuNotaxLimitName(commuNotaxLimitName),
									new CommuNotaxLimitValue(commuNotaxLimitValue));
	}
}
