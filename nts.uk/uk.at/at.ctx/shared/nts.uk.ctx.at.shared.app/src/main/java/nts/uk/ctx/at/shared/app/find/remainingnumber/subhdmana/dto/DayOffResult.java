package nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto;

import java.util.List;

import lombok.Data;

@Data
public class DayOffResult {
	
	private List<DayOffManagementDto> listDayOff;
	private String errorCode;
	
}
