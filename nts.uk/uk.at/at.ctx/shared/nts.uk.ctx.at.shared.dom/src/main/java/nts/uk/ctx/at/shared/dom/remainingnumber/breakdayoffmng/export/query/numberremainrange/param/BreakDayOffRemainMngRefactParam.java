package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;

@Getter
public class BreakDayOffRemainMngRefactParam extends InterimMngParamCommon {

	/** 前回代休の集計結果 */
	private Optional<SubstituteHolidayAggrResult> optBeforeResult;

	public BreakDayOffRemainMngRefactParam(String cid, String sid, DatePeriod dateData, boolean mode,
			GeneralDate screenDisplayDate, boolean replaceChk, List<InterimRemain> interimMng,
			Optional<CreateAtr> creatorAtr, Optional<DatePeriod> processDate, List<InterimBreakMng> breakMng,
			List<InterimDayOffMng> dayOffMng, Optional<SubstituteHolidayAggrResult> optBeforeResult,
			FixedManagementDataMonth fixManaDataMonth) {
		this(cid, sid, dateData, mode, screenDisplayDate, replaceChk, createFull(dayOffMng, breakMng, interimMng),
				creatorAtr, processDate, optBeforeResult, fixManaDataMonth);
	}

	public BreakDayOffRemainMngRefactParam(String cid, String sid, DatePeriod dateData, boolean mode,
			GeneralDate screenDisplayDate, boolean replaceChk, List<InterimRemain> interimMng,
			Optional<CreateAtr> creatorAtr, Optional<DatePeriod> processDate,
			Optional<SubstituteHolidayAggrResult> optBeforeResult, FixedManagementDataMonth fixManaDataMonth) {
		super(cid, sid, dateData, mode, screenDisplayDate, replaceChk, interimMng, creatorAtr, processDate,
				fixManaDataMonth);
		this.optBeforeResult = optBeforeResult;
	}

	// モードが月次モード
	public boolean isModeMonth() {
		return isMode();
	}

	private static List<InterimRemain> createFull(List<InterimDayOffMng> dayOffMng, List<InterimBreakMng> breakMng,
			List<InterimRemain> lstCommon) {
		List<InterimRemain> lstAll = new ArrayList<>();
		lstAll.addAll(dayOffMng.stream().map(x -> {
			val common = lstCommon.stream().filter(c -> c.getRemainManaID().equals(x.getRemainManaID())).findFirst()
					.orElse(null);
			return createFullInterimDayoff(x, common);
		}).collect(Collectors.toList()));

		lstAll.addAll(breakMng.stream().map(x -> {
			val common = lstCommon.stream().filter(c -> c.getRemainManaID().equals(x.getRemainManaID())).findFirst()
					.orElse(null);
			return createFullInterimBreak(x, common);
		}).collect(Collectors.toList()));
		return lstAll;
	}

	private static InterimDayOffMng createFullInterimDayoff(InterimDayOffMng dayoff, InterimRemain common) {
		return new InterimDayOffMng(common.getRemainManaID(), common.getSID(), common.getYmd(), common.getCreatorAtr(),
				common.getRemainType(), dayoff.getRequiredTime(), dayoff.getRequiredDay(), dayoff.getUnOffsetTimes(),
				dayoff.getUnOffsetDay(), dayoff.getAppTimeType());
	}

	private static InterimBreakMng createFullInterimBreak(InterimBreakMng breakMng, InterimRemain common) {
		return new InterimBreakMng(common.getRemainManaID(), common.getSID(), common.getYmd(), common.getCreatorAtr(),
				common.getRemainType(), breakMng.getOnedayTime(), breakMng.getExpirationDate(),
				breakMng.getOccurrenceTimes(), breakMng.getOccurrenceDays(), breakMng.getHaftDayTime(),
				breakMng.getUnUsedTimes(), breakMng.getUnUsedDays());
	}

	/** 上書き用の暫定代休管理データ */
	public List<InterimDayOffMng> getDayOffMng() {
		return this.getInterimMng().stream().filter(x -> x.getRemainType() == RemainType.SUBHOLIDAY)
				.map(x -> (InterimDayOffMng) x).collect(Collectors.toList());
	}

	/**
	 * 上書き用の暫定休出管理データ
	 */
	public List<InterimBreakMng> getBreakMng() {
		return this.getInterimMng().stream().filter(x -> x.getRemainType() == RemainType.BREAK)
				.map(x -> (InterimBreakMng) x).collect(Collectors.toList());
	}
}
