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

	private List<PersonOptionalDto> perOptionalDatas;

	private List<EmpOptionalDto> empOptionalDatas;

	public PeregDto(PeregDomainDto dto, List<PersonOptionalDto> perOptionalData,
			List<EmpOptionalDto> empOptionalDatas) {
		this.domainDto = dto;
		this.perOptionalDatas = perOptionalData;
		this.empOptionalDatas = empOptionalDatas;
	}

	public static PeregDto createWithPersonOptionData(PeregDomainDto dto, List<PersonOptionalDto> perOptionalData) {
		return new PeregDto(dto, perOptionalData, null);
	}

	public static PeregDto createWithEmpOptionData(PeregDomainDto dto, List<EmpOptionalDto> empOptionalDatas) {
		return new PeregDto(dto, null, empOptionalDatas);
	}

}
