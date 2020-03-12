package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.validate.Validatable;
/**
 * 割増設定
 * @author Doan Duy Hung
 *
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class PremiumSetting implements Validatable{
	
	private String companyID;
	
	private String historyID;
	
	private Integer displayNumber;
	
    private PremiumRate rate;
    
    private PremiumName name;
    
    private UseAttribute useAtr;
    
    private List<Integer> attendanceItems;

    public PremiumSetting(String companyID, String historyID, Integer displayNumber, PremiumRate rate, PremiumName name,
			UseAttribute useAtr, List<Integer> attendanceItems) {
		super();
		this.companyID = companyID;
		this.historyID = historyID;
		this.displayNumber = displayNumber;
		this.rate = rate;
		this.name = name;
		this.useAtr = useAtr;
		this.attendanceItems = attendanceItems;
	}

	@Override
	public void validate() {
		this.rate.validate();
	}
}
