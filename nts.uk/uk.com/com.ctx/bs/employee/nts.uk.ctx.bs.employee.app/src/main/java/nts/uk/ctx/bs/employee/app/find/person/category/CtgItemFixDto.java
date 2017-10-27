package nts.uk.ctx.bs.employee.app.find.person.category;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.item.CtgItemType;
import nts.uk.ctx.bs.employee.app.find.person.item.CurrentJobPosDto;
import nts.uk.ctx.bs.employee.app.find.person.item.FamilyCareDto;
import nts.uk.ctx.bs.employee.app.find.person.item.FamilySocialInsuranceDto;
import nts.uk.ctx.bs.employee.app.find.person.item.IncomeTaxDto;
import nts.uk.ctx.bs.employee.app.find.person.item.SetCurJobPos;

/**
 * category item fix dto: IncomeTax, FamilySocialInsurance, FamilyCare, CurrentJobPosition
 * @author xuan vinh
 *
 */

@NoArgsConstructor
public class CtgItemFixDto {
	@Getter
	protected CtgItemType ctgItemType; 
	
	public static CtgItemFixDto createIncomeTax(String incomeTaxID, String familyMemberId, String sid, GeneralDate startDate, GeneralDate endDate, 
			boolean supporter, int disabilityType, int deductionTargetType){
		return IncomeTaxDto.createFromJavaType(incomeTaxID, familyMemberId, sid, startDate, endDate, 
				supporter, disabilityType, deductionTargetType);
	}
	public static CtgItemFixDto createFamilySocialInsurance(String familyMemberId, String sid, String socailInsuaranceId, GeneralDate startDate,
			GeneralDate endDate, boolean nursingCare, boolean healthInsuranceDependent, boolean nationalPensionNo3, String basicPensionNumber){
		return FamilySocialInsuranceDto.createFromJavaType(familyMemberId, sid, socailInsuaranceId, startDate,
				endDate, nursingCare, healthInsuranceDependent, nationalPensionNo3, basicPensionNumber);
	}
	public static CtgItemFixDto createFamilyCare(String familyCareId, String familyId, String sid, 
			GeneralDate startDate, GeneralDate endDate, int careClassifi){
		return FamilyCareDto.createFromJavaType(familyCareId, familyId, sid, startDate, endDate, careClassifi);
	}
	public static CtgItemFixDto createSetCurJobPos(List<CurrentJobPosDto> lstCurrentJobPosDto){
		return SetCurJobPos.createFromJavaType(lstCurrentJobPosDto);
	}
}
