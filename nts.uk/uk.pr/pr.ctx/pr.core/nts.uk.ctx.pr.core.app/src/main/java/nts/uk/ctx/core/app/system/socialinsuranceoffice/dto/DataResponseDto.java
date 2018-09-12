package nts.uk.ctx.core.app.system.socialinsuranceoffice.dto;

import java.util.List;

import lombok.Data;

@Data
public class DataResponseDto {
	
	private List<CusSociaInsuOfficeDto> listCodeName;
	private SociaInsuOfficeDto sociaInsuOfficeDetail;
	private List<SociaInsuPreInfoDto> sociaInsuPreInfos;
	
}
