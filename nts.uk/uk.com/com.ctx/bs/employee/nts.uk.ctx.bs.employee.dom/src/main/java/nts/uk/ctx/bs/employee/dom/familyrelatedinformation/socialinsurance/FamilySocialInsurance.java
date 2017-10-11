package nts.uk.ctx.bs.employee.dom.familyrelatedinformation.socialinsurance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class FamilySocialInsurance extends AggregateRoot {

	private String familyMemberId;

	private String sid;

	private String socailInsuaranceId;

	private GeneralDate startDate;

	private GeneralDate endDate;

	private boolean nursingCare;

	private boolean healthInsuranceDependent;

	private boolean nationalPensionNo3;

	private BasicPensionNumber basicPensionNumber;

	public static FamilySocialInsurance createFromJavaType(
			String familyMemberId,
			String sid,
			String socailInsuaranceId,
			GeneralDate startDate, 
			GeneralDate endDate, 
			boolean nursingCare,
			boolean healthInsuranceDependent,
			boolean nationalPensionNo3,
			String basicPensionNumber) {
		return new FamilySocialInsurance(familyMemberId, 
										sid, 
										socailInsuaranceId, 
										startDate,
										endDate, 
										nursingCare,
										healthInsuranceDependent,
										nationalPensionNo3, 
										new BasicPensionNumber(basicPensionNumber));}

}
