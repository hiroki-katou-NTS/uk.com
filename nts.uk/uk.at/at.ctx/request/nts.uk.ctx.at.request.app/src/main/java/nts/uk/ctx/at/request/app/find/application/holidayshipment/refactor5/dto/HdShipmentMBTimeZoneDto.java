package nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.VacationCheckOutput;
import nts.uk.ctx.at.shared.app.find.common.TimeZoneWithWorkNoDto;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class HdShipmentMBTimeZoneDto {
	
	private List<TimeZoneWithWorkNoDto> timeZoneLst;
	
	private VacationCheckOutput vacationCheckOutput;
}
