package nts.uk.ctx.hr.develop.app.careermgmt.careerpath.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.YearRequirement;

@Getter
@AllArgsConstructor
@Data
public class YearRequirementDto {

	public Integer yearType;

	public Integer yearMinimumNumber;

	public Integer yearStandardNumber;

	public static YearRequirementDto fromDomain(Optional<YearRequirement> yearRequirement) {
		if(!yearRequirement.isPresent()) {
			return null;
		}
		return new YearRequirementDto(yearRequirement.get().getYearType().value, yearRequirement.get().getYearMinimumNumber().v(),yearRequirement.get().getYearStandardNumber().v());
	}
	
	public YearRequirement toDomain() {
		return YearRequirement.createFromJavaType(this.yearType, this.yearMinimumNumber, this.yearStandardNumber);
	}
}
