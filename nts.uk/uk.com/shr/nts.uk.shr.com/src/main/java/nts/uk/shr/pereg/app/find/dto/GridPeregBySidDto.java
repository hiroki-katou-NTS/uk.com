package nts.uk.shr.pereg.app.find.dto;

import java.util.List;

import lombok.Data;
@Data
public class GridPeregBySidDto {
	private String employeeId;
	private String personId;
	
	private List<PeregDto> peregDto;
	
	public GridPeregBySidDto(String employeeId, String personId, List<PeregDto> peregDto) {
		this.employeeId = employeeId;
		this.personId = personId;
		
		this.peregDto.addAll(peregDto);
	}
}
