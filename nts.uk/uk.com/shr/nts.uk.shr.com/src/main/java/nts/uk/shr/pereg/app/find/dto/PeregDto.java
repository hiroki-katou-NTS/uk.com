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

	private List<OptionalItemDataDto> optionalItemData;

	public PeregDto(PeregDomainDto dto, Class<?> dtoClass) {
		this.domainDto = dto;
		this.dtoClass = dtoClass;
	}

	public PeregDto(PeregDomainDto domainDto, Class<?> dtoClass, List<OptionalItemDataDto> optionalItemData) {
		super();
		this.domainDto = domainDto;
		this.dtoClass = dtoClass;
		this.optionalItemData = optionalItemData;
	}

}
