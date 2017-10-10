package nts.uk.ctx.bs.employee.dom.familyrelatedinformation.incometax;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.care.Period;

@Getter
@AllArgsConstructor
public class IncomeTax extends AggregateRoot {

	private String IncomeTaxID;

	private String familyMemberId;

	private String sid;

	private Period period;

	private boolean supporter;

	private DisabilityType disabilityType;

	private DeductionTargetType deductionTargetType;

	public static IncomeTax createFromJavaType(String IncomeTaxID, String familyMemberId, String sid,
			GeneralDate startDate, GeneralDate endDate, boolean supporter, int disabilityType,
			int deductionTargetType) {
		return new IncomeTax(
				IncomeTaxID,
				familyMemberId, 
				sid, 
				new Period(startDate, endDate), 
				supporter, 
				EnumAdaptor.valueOf(disabilityType, DisabilityType.class), 
				EnumAdaptor.valueOf(deductionTargetType, DeductionTargetType.class));

	}

}
