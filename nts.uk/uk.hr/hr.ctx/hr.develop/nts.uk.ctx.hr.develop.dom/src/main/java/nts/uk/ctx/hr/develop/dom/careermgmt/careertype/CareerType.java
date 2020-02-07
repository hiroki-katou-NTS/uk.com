package nts.uk.ctx.hr.develop.dom.careermgmt.careertype;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.hr.shared.dom.primitiveValue.Code_AlphaNumeric_3;
import nts.uk.ctx.hr.shared.dom.primitiveValue.String_Any_20;

/**職務*/
@AllArgsConstructor
@Getter
public class CareerType extends AggregateRoot{

	private String companyId;
	
	private String careerTypeId;
	
	private String historyId;
	
	private Code_AlphaNumeric_3 careerTypeCode;
	
	private String_Any_20 careerTypeName;
	
	private boolean isCommon;
	
	private boolean isDisable;
	
	private List<MasterMapping> masterMappingList;
	
	public static CareerType createFromJavaType(String companyId, String careerTypeId, String historyId, String careerTypeCode, String careerTypeName, boolean isCommon, boolean isDisable, List<MasterMapping> masterMappingList) {
		return new CareerType(
				companyId,
				careerTypeId,
				historyId,
				new Code_AlphaNumeric_3(careerTypeCode),
				new String_Any_20(careerTypeName), 
				isCommon, 
				isDisable,
				masterMappingList
				);
	}
}
