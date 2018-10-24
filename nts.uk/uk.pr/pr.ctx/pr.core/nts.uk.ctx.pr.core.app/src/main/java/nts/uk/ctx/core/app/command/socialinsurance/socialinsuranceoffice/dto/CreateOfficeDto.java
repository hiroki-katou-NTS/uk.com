package nts.uk.ctx.core.app.command.socialinsurance.socialinsuranceoffice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateOfficeDto {
	
	private String name;
	private String code;
	private List<CusSociaInsuOfficeDto> dataOffice;
	private String msg;
	public CreateOfficeDto() {
		super();
	}
	
	
	
}
