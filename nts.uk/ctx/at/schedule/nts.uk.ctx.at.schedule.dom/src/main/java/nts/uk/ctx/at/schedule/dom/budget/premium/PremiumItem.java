package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class PremiumItem {
	
	private String companyID;
	
	private BigDecimal iD;
	
	private BigDecimal attendanceID;
	
	private PremiumName name;

	private BigDecimal displayNumber;

	private UseAttribute useAtr;

	public PremiumItem(String companyID, BigDecimal iD, BigDecimal attendanceID, PremiumName name,
			BigDecimal displayNumber, UseAttribute useAtr) {
		super();
		this.companyID = companyID;
		this.iD = iD;
		this.attendanceID = attendanceID;
		this.name = name;
		this.displayNumber = displayNumber;
		this.useAtr = useAtr;
	}
}
