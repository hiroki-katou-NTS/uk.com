package nts.uk.ctx.at.request.app.command.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.overtime.ExcessState;
import nts.uk.ctx.at.request.dom.application.overtime.ExcessStateMidnight;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;

@AllArgsConstructor
@NoArgsConstructor
public class ExcessStateMidnightCommand {
	// 超過状態
	public Integer excessState;
	// 法定区分
	public Integer legalCfl;
	
	public ExcessStateMidnight toDomain() {
		return new ExcessStateMidnight(
				EnumAdaptor.valueOf(excessState, ExcessState.class),
				StaturoryAtrOfHolidayWork.deicisionAtrByHolidayAtr(EnumAdaptor.valueOf(legalCfl, HolidayAtr.class)));
	}
}
