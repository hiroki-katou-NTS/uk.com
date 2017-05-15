package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.util.List;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.primitive.Memo;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Value
@EqualsAndHashCode(callSuper = false)
public class PersonCostCalculation extends AggregateRoot {

	private String companyID;

	private String historyID;

	private Memo memo;

	private UnitPrice unitprice;

	private GeneralDate startDate;

	private GeneralDate endDate;
	
	private List<PremiumSetting> premiumSettings;

	public PersonCostCalculation(String companyID, String historyID, Memo memo, UnitPrice unitprice,
			GeneralDate startDate, GeneralDate endDate, List<PremiumSetting> premiumSettings) {
		super();
		this.companyID = companyID;
		this.historyID = historyID;
		this.memo = memo;
		this.unitprice = unitprice;
		this.startDate = startDate;
		this.endDate = endDate;
		this.premiumSettings = premiumSettings;
	} 

}