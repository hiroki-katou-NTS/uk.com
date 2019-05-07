package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**キャリア*/
@AllArgsConstructor
@Getter
public class Career {

	private String careerTypeItem;
	
	private Integer_1_10 careerLevel;
	
	private String careerClassItem;
	
	private Optional<String_Any_100> careerClassRole;
	
	private List<CareerRequirement> careerRequirementList;
	
	public static Career createFromJavaType(String careerTypeItem, int careerLevel, String careerClassItem, String careerClassRole, List<CareerRequirement> careerRequirementList) {
		return new Career(
				careerTypeItem,
				new Integer_1_10(careerLevel),
				careerClassItem,
				careerClassRole.equals("")?Optional.empty() : Optional.of(new String_Any_100(careerClassRole)),
				careerRequirementList
				);
	}
	
}
