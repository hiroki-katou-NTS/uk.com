package nts.uk.ctx.at.shared.dom.scherec.application.furiapp;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;

/**
 * @author thanhpv
 * @name 振休振出申請
 */
@Getter
public class ApplicationForHolidaysShare extends ApplicationShare {

	private TypeApplicationHolidaysShare typeApplicationHolidays;

	public ApplicationForHolidaysShare(TypeApplicationHolidaysShare typeApplicationHolidays, ApplicationShare application) {
		super(application);
		this.typeApplicationHolidays = typeApplicationHolidays;
	}
}
