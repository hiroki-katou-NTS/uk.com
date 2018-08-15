package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.CheckWkTypeSpecHdEventOutput;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.MaxDaySpecHdOutput;
@Getter
@AllArgsConstructor
public class ChangeRelationShipDto {
	
	private CheckWkTypeSpecHdEventOutput checkSpecHd;
	private MaxDaySpecHdOutput maxDayObj;
}
