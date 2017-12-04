/**
 * 
 */
package nts.uk.shr.pereg.app.find.dto;

import java.util.List;

import lombok.Data;

/**
 * @author danpv
 *
 */
@Data
public class PeregDto {

	private PeregDomainDto domainDto;
	/**
	 * class of DTO
	 */
	private Class<?> dtoClass;
	
	private DataClassification dataType;

	private List<PersonOptionalDto> perOptionalData;

	private List<EmpOptionalDto> empOptionalData;
	
	public PeregDto(PeregDomainDto dto, Class<?> dtoClass, DataClassification dataType){
		this.domainDto = dto;
		this.dtoClass = dtoClass;
		this.dataType = dataType;
	}

	public PeregDto(PeregDomainDto dto, Class<?> dtoClass, List<PersonOptionalDto> perOptionalData,
			List<EmpOptionalDto> empOptionalDatas) {
		this.domainDto = dto;
		this.dtoClass = dtoClass;
		this.perOptionalData = perOptionalData;
		this.empOptionalData = empOptionalDatas;
	}
	
}
