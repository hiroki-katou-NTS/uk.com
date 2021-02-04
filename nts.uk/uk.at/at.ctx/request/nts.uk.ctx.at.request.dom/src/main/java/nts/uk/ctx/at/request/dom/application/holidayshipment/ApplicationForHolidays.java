package nts.uk.ctx.at.request.dom.application.holidayshipment;

import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.Application;

/**
 * @author thanhpv
 * @name 振休振出申請
 */
@Getter
public class ApplicationForHolidays extends Application{

	private TypeApplicationHolidays typeApplicationHolidays;
	
	public ApplicationForHolidays(TypeApplicationHolidays typeApplicationHolidays, Application application) {
		super(application);
		this.typeApplicationHolidays = typeApplicationHolidays;
	}
}
