package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.hr.shared.dom.primitiveValue.Integer_1_6;

/**キャリア条件*/
@AllArgsConstructor
@Getter
public class CareerRequirement {

	private Integer_1_6 displayNumber;
	
	private RequirementType requirementType;
	
	private Optional<YearRequirement> yearRequirement;
	
	private Optional<MasterRequirement> masterRequirement;
	
	private Optional<InputRequirement> inputRequirement;
	
	public static CareerRequirement createFromJavaType(int displayNumber, int requirementType, Optional<YearRequirement> yearRequirement, Optional<MasterRequirement> masterRequirement, Optional<String> inputRequirement) {
		return new CareerRequirement(
				new Integer_1_6(displayNumber),
				EnumAdaptor.valueOf(requirementType, RequirementType.class), 
				yearRequirement,
				masterRequirement,
				inputRequirement.isPresent()? Optional.of(new InputRequirement(inputRequirement.get())):Optional.empty()
				);
	}
}
