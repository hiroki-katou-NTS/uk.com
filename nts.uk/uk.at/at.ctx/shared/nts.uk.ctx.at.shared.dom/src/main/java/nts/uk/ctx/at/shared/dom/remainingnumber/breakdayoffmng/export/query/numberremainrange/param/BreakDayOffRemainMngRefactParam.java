package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;

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
			List<InterimDayOffMng> dayOffMng, Optional<SubstituteHolidayAggrResult> optBeforeResult) {
		super(cid, sid, dateData, mode, screenDisplayDate, replaceChk, interimMng, creatorAtr, processDate);
		this.breakMng = breakMng;
		this.dayOffMng = dayOffMng;
		this.optBeforeResult = optBeforeResult;
	}

	// モードが月次モード
	public boolean isModeMonth() {
		return isMode();
	}

	public BreakDayOffRemainMngParam convert() {

		Optional<BreakDayOffRemainMngOfInPeriod> optBeforeResultTemp = Optional.empty();
		SubstituteHolidayAggrResult subHoli = this.getOptBeforeResult().orElse(null);
		if (subHoli != null) {
			List<BreakDayOffDetail> lstDetailData = subHoli.getVacationDetails().getLstAcctAbsenDetail().stream()
					.map(x -> x.convertDefault()).collect(Collectors.toList());
			BreakDayOffRemainMngOfInPeriod breakDay = new BreakDayOffRemainMngOfInPeriod(lstDetailData,
					subHoli.getRemainDay().v(), subHoli.getRemainTime().v(), subHoli.getUnusedDay().v(),
					subHoli.getUnusedTime().v(), subHoli.getOccurrenceDay().v(), subHoli.getOccurrenceTime().v(),
					subHoli.getDayUse().v(), subHoli.getTimeUse().v(), subHoli.getCarryoverDay().v(),
					subHoli.getCarryoverTime().v(), subHoli.getDayOffErrors(), subHoli.getNextDay());
			optBeforeResultTemp = Optional.of(breakDay);

		}

		BreakDayOffRemainMngParam param = new BreakDayOffRemainMngParam(this.getCid(), this.getSid(),
				this.getDateData(), this.isMode(), this.getScreenDisplayDate(), this.isReplaceChk(),
				this.getInterimMng(), this.getBreakMng(), this.getDayOffMng(), optBeforeResultTemp,
				this.getCreatorAtr(), this.getProcessDate());

		return param;
	}

}
