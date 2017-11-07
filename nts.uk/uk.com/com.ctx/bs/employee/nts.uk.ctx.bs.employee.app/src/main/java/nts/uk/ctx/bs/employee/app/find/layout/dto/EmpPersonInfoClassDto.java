/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.layout.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import nts.uk.ctx.bs.person.dom.person.layout.classification.LayoutPersonInfoClassification;

/**
 * @author danpv
 *
 */
@Data
public class EmpPersonInfoClassDto {

	private int layoutItemType;

	private String layoutID;

	private String personInfoCategoryID;

	private int dispOrder;

	private String className;

	private List<EmpPersonInfoItemDto> infoItems;
	
	/*
	 * 1.Single Classification Item
	 * One:		 SaveDataDto
	 * Set<>: 	 SaveDataDto SaveDataDto SaveDataDto
	 * 
	 * 2. List Classification Item
	 * 		SaveDataDto SaveDataDto SaveDataDto 
	 * 		SaveDataDto SaveDataDto SaveDataDto
	 * 		SaveDataDto SaveDataDto SaveDataDto
	 */
	private List<Object> dataItems;

	public static EmpPersonInfoClassDto createfromDomain(LayoutPersonInfoClassification domain) {
		EmpPersonInfoClassDto dto = new EmpPersonInfoClassDto();
		dto.setLayoutID(domain.getLayoutID());
		dto.setDispOrder(domain.getDispOrder().v());
		dto.setPersonInfoCategoryID(domain.getPersonInfoCategoryID());
		dto.setLayoutItemType(domain.getLayoutItemType().value);
		dto.dataItems = new ArrayList<>();
		return dto;
	}

}
