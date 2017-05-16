package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class PremiumItem {
	
	private String companyID;
	
	private Integer iD;
	
	private Integer attendanceID;
	
	private PremiumName name;

	private Integer displayNumber;

	private UseAttribute useAtr;

	public PremiumItem(String companyID, Integer iD, Integer attendanceID, PremiumName name,
			Integer displayNumber, UseAttribute useAtr) {
		super();
		this.companyID = companyID;
		this.iD = iD;
		this.attendanceID = attendanceID;
		this.name = name;
		this.displayNumber = displayNumber;
		this.useAtr = useAtr;
	}
}
