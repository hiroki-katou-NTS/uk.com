package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.uk.ctx.hr.shared.dom.primitiveValue.Integer_1_10;
import nts.uk.ctx.hr.shared.dom.primitiveValue.String_Any_100;

/**キャリア*/
@Getter
public class Career {

	private String careerTypeItem;
	
	private Integer_1_10 careerLevel;
	
	private String careerClassItem;
	
	private Optional<String_Any_100> careerClassRole;
	
	private List<CareerRequirement> careerRequirementList;
	
	public static Career createFromJavaType(String careerTypeItem, int careerLevel, String careerClassItem, Optional<String> careerClassRole, List<CareerRequirement> careerRequirementList) {
		return new Career(
				careerTypeItem,
				new Integer_1_10(careerLevel),
				careerClassItem,
				careerClassRole.isPresent()?Optional.of(new String_Any_100(careerClassRole.get())):Optional.empty(),
				careerRequirementList
				);
	}

	public Career(String careerTypeItem, Integer_1_10 careerLevel, String careerClassItem,
			Optional<String_Any_100> careerClassRole, List<CareerRequirement> careerRequirementList) {
		super();
		this.careerTypeItem = careerTypeItem;
		this.careerLevel = careerLevel;
		this.careerClassItem = careerClassItem;
		this.careerClassRole = careerClassRole;
		this.careerRequirementList = careerRequirementList;
		this.validate();
	}
	
	private void validate() {
		if(!this.careerRequirementList.isEmpty()) {
			List<Integer> yearType = new ArrayList<>();
			List<String> masterType = new ArrayList<>();
			for (CareerRequirement careerRequirement : this.careerRequirementList) {
				if(careerRequirement.getYearRequirement().isPresent()) {
					if(yearType.contains(careerRequirement.getYearRequirement().get().getYearType().value)) {
						throw new BusinessException("MsgJ_46");
					}
					yearType.add(careerRequirement.getYearRequirement().get().getYearType().value);
				}
				if(careerRequirement.getMasterRequirement().isPresent()) {
					if(masterType.contains(careerRequirement.getMasterRequirement().get().getMasterType())) {
						throw new BusinessException("MsgJ_47");
					}
					masterType.add(careerRequirement.getMasterRequirement().get().getMasterType());
				}
			}
		}
	}
}
