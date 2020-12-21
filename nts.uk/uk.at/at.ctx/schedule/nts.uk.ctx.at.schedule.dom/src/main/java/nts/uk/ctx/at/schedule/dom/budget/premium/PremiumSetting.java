package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.validate.Validatable;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.ExtraTimeItemNo;

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

	private ExtraTimeItemNo iD;

    private PremiumRate rate;

    private UnitPrice unitPrice;
    
    private List<Integer> attendanceItems;

    public PremiumSetting(String companyID, String historyID, ExtraTimeItemNo iD, PremiumRate rate, UnitPrice unitPrice,
		 List<Integer> attendanceItems) {
		super();
		this.companyID = companyID;
		this.historyID = historyID;
		this.iD = iD;
		this.rate = rate;
		this.unitPrice = unitPrice;
		this.attendanceItems = attendanceItems;
	}

	@Override
	public void validate() {
		this.rate.validate();
	}
}
