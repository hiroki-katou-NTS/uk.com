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

	public static PersonCostCalculation createFromJavaType(String companyID, Memo memo,
			UnitPrice unitPrice, GeneralDate startDate, GeneralDate endDate, List<PremiumSetting> premiumSettings) {
		if(PersonCostCalculationDomainService.validateHistory(companyID, startDate)) throw new BusinessException("ER065");
		return new PersonCostCalculation(companyID, UUID.randomUUID().toString(), memo, unitPrice, startDate, endDate, premiumSettings);
	}

}