package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;
@Data
public class ApplicationPeriodDto {
	GeneralDate startDate;
	GeneralDate endDate;
}
