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
	
	/**
	 * category type: 1 - person; 2 - employee
	 */
	private int ctgType;

	private List<PersonOptionalDto> perOptionalData;

	private List<EmpOptionalDto> empOptionalData;
	
	public PeregDto(PeregDomainDto dto, Class<?> dtoClass, int ctgType){
		this.domainDto = dto;
		this.dtoClass = dtoClass;
		this.ctgType = ctgType;
	}


	public PeregDto(PeregDomainDto dto, Class<?> dtoClass, List<PersonOptionalDto> perOptionalData,
			List<EmpOptionalDto> empOptionalDatas) {
		this.domainDto = dto;
		this.dtoClass = dtoClass;
		this.perOptionalData = perOptionalData;
		this.empOptionalData = empOptionalDatas;
	}

	public static PeregDto createWithPersonOptionData(PeregDomainDto dto, Class<?> dtoClass, List<PersonOptionalDto> perOptionalData) {
		return new PeregDto(dto, dtoClass, perOptionalData, null);
	}

	public static PeregDto createWithEmpOptionData(PeregDomainDto dto, Class<?> dtoClass, List<EmpOptionalDto> empOptionalDatas) {
		return new PeregDto(dto, dtoClass, null, empOptionalDatas);
	}

}
