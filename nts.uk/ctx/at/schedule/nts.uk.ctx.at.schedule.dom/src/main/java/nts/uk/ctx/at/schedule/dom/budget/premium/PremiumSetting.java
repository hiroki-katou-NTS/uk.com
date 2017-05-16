package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.math.BigDecimal;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class PremiumSetting {
	
	private String companyID;
	
	private String historyID;
	
	private BigDecimal premiumID;
	
    private PremiumRate rate;
    
    private BigDecimal attendanceID;
    
    private PremiumName name;
    
    private BigDecimal displayNumber;
    
    private UseAttribute useAtr;
    
    private List<BigDecimal> timeItemIDs;

	public PremiumSetting(String companyID, String historyID, BigDecimal premiumID, PremiumRate rate,
			BigDecimal attendanceID, PremiumName name, BigDecimal displayNumber, UseAttribute useAtr,
			List<BigDecimal> timeItemIDs) {
		super();
		this.companyID = companyID;
		this.historyID = historyID;
		this.premiumID = premiumID;
		this.rate = rate;
		this.attendanceID = attendanceID;
		this.name = name;
		this.displayNumber = displayNumber;
		this.useAtr = useAtr;
		this.timeItemIDs = timeItemIDs;
	}
    
}
