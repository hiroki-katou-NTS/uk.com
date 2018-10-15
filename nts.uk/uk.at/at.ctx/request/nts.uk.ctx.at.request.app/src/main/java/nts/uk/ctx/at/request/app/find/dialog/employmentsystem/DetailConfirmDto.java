package nts.uk.ctx.at.request.app.find.dialog.employmentsystem;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.find.vacation.setting.subst.dto.SubstVacationSettingDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AsbRemainTotalInfor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;


@Data
public class DetailConfirmDto {
	public DatePeriod closingPeriod;
	
	public List<BreakDayOffHistoryDto> lstHistory;
	
	public AsbRemainTotalInfor totalInfor;
	
	public SubstVacationSettingDto setting;

	public AbsRecRemainMngOfInPeriod absRecMng;
	
}
