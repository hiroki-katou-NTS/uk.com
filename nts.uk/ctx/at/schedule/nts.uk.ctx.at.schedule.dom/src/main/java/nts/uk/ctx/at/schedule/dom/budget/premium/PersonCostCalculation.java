package nts.uk.ctx.at.schedule.dom.budget.premium;

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

	String CID;

	String HID;

	Memo memo;

	UnitPrice unitprice;

	GeneralDate startDate;

	GeneralDate endDate;

	public static PersonCostCalculation createFromJavaType(String CID, Memo memo,
			UnitPrice unitPrice, GeneralDate startDate, GeneralDate endDate) {
		if(PersonCostCalculationDomainService.validateHistory(CID, startDate)) throw new BusinessException("ER065");
		return new PersonCostCalculation(CID, UUID.randomUUID().toString(), memo, unitPrice, startDate, endDate);
	}

}