package nts.uk.shr.pereg.app.find.dto;

import java.util.List;

import lombok.Data;

@Data
public class GridPeregDomainBySidDto {
	private String employeeId;
	private String personId;
	private List<PeregDomainDto> peregDomainDto;
	
	public GridPeregDomainBySidDto(String sid, String pid, List<PeregDomainDto> domains) {
		this.employeeId = sid;
		this.personId = pid;
		this.peregDomainDto.addAll(domains);
	}
}
