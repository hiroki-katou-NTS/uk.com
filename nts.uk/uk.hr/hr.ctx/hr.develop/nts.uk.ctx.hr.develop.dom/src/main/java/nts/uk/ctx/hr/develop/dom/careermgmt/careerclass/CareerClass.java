package nts.uk.ctx.hr.develop.dom.careermgmt.careerclass;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.hr.develop.dom.careermgmt.careertype.MasterMapping;
import nts.uk.ctx.hr.shared.dom.primitiveValue.Code_AlphaNumeric_3;
import nts.uk.ctx.hr.shared.dom.primitiveValue.String_Any_20;

/**キャリア*/
@AllArgsConstructor
@Getter
public class CareerClass extends AggregateRoot{

	private String companyId;
	
	private String historyId;
	
	private String careerClassId;
	
	private Code_AlphaNumeric_3 careerClassCode;
	
	private String_Any_20 careerClassName;
	
	private boolean isDisable;
	
	private List<MasterMapping> masterMappingList;
	
	public static CareerClass createFromJavaType(String companyId, String historyId, String careerClassId, String careerClassCode, String careerClassName, boolean isDisable, List<MasterMapping> masterMappingList) {
		return new CareerClass(
				companyId,
				historyId,
				careerClassId,
				new Code_AlphaNumeric_3(careerClassCode),
				new String_Any_20(careerClassName),
				isDisable,
				masterMappingList
				);
	}
	
}
