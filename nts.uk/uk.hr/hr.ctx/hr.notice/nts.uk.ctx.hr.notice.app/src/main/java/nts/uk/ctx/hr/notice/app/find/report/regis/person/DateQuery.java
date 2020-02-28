package nts.uk.ctx.hr.notice.app.find.report.regis.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDateTime;

@AllArgsConstructor
@Data
public class DateQuery {
	
	private GeneralDateTime startDate;
	private GeneralDateTime endDate;
}
