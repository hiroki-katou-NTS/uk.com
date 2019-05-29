package nts.uk.ctx.hr.develop.app.careermgmt.careerpath.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.Career;

@Data
@AllArgsConstructor
public class CareerDto {

	public String careerTypeItem;

	public Integer careerLevel;

	public String careerClassItem;

	public List<CareerRequirementDto> careerRequirementList;

	public CareerDto(Career career) {
		super();
		this.careerTypeItem = career.getCareerTypeItem();
		this.careerLevel = career.getCareerLevel().v();
		this.careerClassItem = career.getCareerClassItem();
		this.careerRequirementList = career.getCareerRequirementList().stream().map(c -> new CareerRequirementDto(c)).collect(Collectors.toList());
	}

}
