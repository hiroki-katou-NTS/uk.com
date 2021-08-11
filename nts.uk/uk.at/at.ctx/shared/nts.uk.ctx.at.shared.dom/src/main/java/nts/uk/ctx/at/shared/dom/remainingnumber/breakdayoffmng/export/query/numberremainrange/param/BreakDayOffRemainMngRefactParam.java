package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;

@Getter
public class BreakDayOffRemainMngRefactParam extends InterimMngParamCommon {

	/** 上書き用の暫定休出管理データ */
	private List<InterimBreakMng> breakMng;
	/** 上書き用の 暫定代休管理データ */
	private List<InterimDayOffMng> dayOffMng;
	/** 前回代休の集計結果 */
	private Optional<SubstituteHolidayAggrResult> optBeforeResult;

	public BreakDayOffRemainMngRefactParam(String cid, String sid, DatePeriod dateData, boolean mode,
			GeneralDate screenDisplayDate, boolean replaceChk, List<InterimRemain> interimMng,
			Optional<CreateAtr> creatorAtr, Optional<DatePeriod> processDate, List<InterimBreakMng> breakMng,
			List<InterimDayOffMng> dayOffMng, Optional<SubstituteHolidayAggrResult> optBeforeResult,
			FixedManagementDataMonth fixManaDataMonth) {
		super(cid, sid, dateData, mode, screenDisplayDate, replaceChk, interimMng, creatorAtr, processDate,
				fixManaDataMonth);
		this.breakMng = breakMng;
		this.dayOffMng = dayOffMng;
		this.optBeforeResult = optBeforeResult;
	}

	// モードが月次モード
	public boolean isModeMonth() {
		return isMode();
	}

}
