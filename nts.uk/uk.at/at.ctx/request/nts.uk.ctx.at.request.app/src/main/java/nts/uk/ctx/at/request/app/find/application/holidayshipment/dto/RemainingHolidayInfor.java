package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;

@Getter
public class RemainingHolidayInfor extends AbsRecRemainMngOfInPeriodDto {

	private GeneralDate closestDueDate;

	public RemainingHolidayInfor(AbsRecRemainMngOfInPeriod absRecRemainMng, GeneralDate closestDueDate) {
		super(absRecRemainMng);
		this.closestDueDate = closestDueDate;
	}

}
