package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;

/**
 * 実装：暫定年休管理データを作成する
 * @author shuichi_ishida
 */
@Stateless
public class CreateInterimAnnualMngDataImpl implements CreateInterimAnnualMngData {

	/** 月別実績の勤怠時間からフレックス補填の暫定年休管理データを作成する */
	@Override
	public Optional<TmpAnnualHolidayMng> ofCompensFlex(AttendanceTimeOfMonthly timeMonth) {
		
		// 「暫定年休管理データ」を作成
		if (timeMonth == null) return Optional.empty();
		val flexTime = timeMonth.getMonthlyCalculation().getFlexTime();
		double deductDays = flexTime.getFlexShortDeductTime().getAnnualLeaveDeductDays().v();
		String dataGuid = IdentifierUtil.randomUniqueId();
		TmpAnnualHolidayMng result = new TmpAnnualHolidayMng(dataGuid, "000", new UseDay(deductDays));
		
		// 「暫定年休管理データ」を返す
		return Optional.of(result);
	}
	
	/** 月別実績の勤怠時間からフレックス補填の暫定年休管理データを作成する */
	@Override
	public Optional<DailyInterimRemainMngData> ofCompensFlex(AttendanceTimeOfMonthly timeMonth, GeneralDate targetYmd) {
		
		val tmpAnnualHolidayMngOpt = this.ofCompensFlex(timeMonth);
		if (!tmpAnnualHolidayMngOpt.isPresent()) return Optional.empty();
		val tmpAnnualHolidayMng = tmpAnnualHolidayMngOpt.get();
		InterimRemain interimRemain = new InterimRemain(
				tmpAnnualHolidayMng.getAnnualId(),
				timeMonth.getEmployeeId(),
				targetYmd,
				CreateAtr.FLEXCOMPEN,
				RemainType.ANNUAL,
				RemainAtr.SINGLE);
		
		return Optional.of(new DailyInterimRemainMngData(
				Optional.empty(),
				new ArrayList<>(Arrays.asList(interimRemain)),
				Optional.empty(),
				Optional.empty(),
				Optional.of(tmpAnnualHolidayMng),
				Optional.empty(),
				Optional.empty(),
				new ArrayList<>()));
	}

	/** 月別実績の勤怠時間からフレックス補填の暫定年休管理データを作成する */
	@Override
	public Optional<TmpAnnualLeaveMngWork> ofCompensFlexToWork(AttendanceTimeOfMonthly timeMonth, GeneralDate targetYmd) {
		
		val dailyInterimRemainMngDataOpt = this.ofCompensFlex(timeMonth, targetYmd);
		if (!dailyInterimRemainMngDataOpt.isPresent()) return Optional.empty();
		val mngData = dailyInterimRemainMngDataOpt.get();
		return Optional.of(TmpAnnualLeaveMngWork.of(
				mngData.getRecAbsData().get(0),
				mngData.getAnnualHolidayData().get()));
	}
}
