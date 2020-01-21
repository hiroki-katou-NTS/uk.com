package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.employee.GrpCmmMastItImport;

@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ParamAddManRetireRegDto {

	private String historyId;
	
	private String baseDate;
	
	private List<RetirePlanCourceParamDto> retirePlanCourseList;
	
	private List<GrpCmmMastItImport> commonMasterItems;

	public String getHistoryId() {
		return historyId;
	}

	public GeneralDate getBaseDate() {
		return GeneralDate.fromString(baseDate, "yyyy/MM/dd");
	}
	
	public Optional<RetirePlanCourceParamDto> getMaxRetirePlanCource() {
		List<RetirePlanCourceParamDto> t = this.retirePlanCourseList.stream().filter(c->{ 
			return c.getRetirePlanCourseClass() == 0 && c.getDurationFlg() == 0;
		}).collect(Collectors.toList());
		if(t.isEmpty()) {
			return Optional.empty();
		}
		return t.stream().max(Comparator.comparingInt(RetirePlanCourceParamDto::getRetirementAge));
	}
	
	public GrpCmmMastItImport getMinOrderCmmMastIt() {
		return this.commonMasterItems.stream().min(Comparator.comparingInt(GrpCmmMastItImport::getDisplayNumber)).get();
	}
}
