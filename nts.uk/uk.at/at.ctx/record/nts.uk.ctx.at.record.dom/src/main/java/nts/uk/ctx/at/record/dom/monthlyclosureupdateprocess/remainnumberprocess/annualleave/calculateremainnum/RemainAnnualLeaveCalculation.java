package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.calculateremainnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.CreateInterimAnnualMngData;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpReserveLeaveMngWork;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT - <<Work>> 年休残数計算
 *
 */

@Stateless
public class RemainAnnualLeaveCalculation {

	@Inject
	private GetAnnAndRsvRemNumWithinPeriod getRemainNum;
	
	/** 暫定年休管理データを作成する */
	@Inject
	private CreateInterimAnnualMngData createInterimAnnual;

	/**
	 * 年休残数計算
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @param interimRemainMngMap 暫定管理データリスト
	 * @param attTimeMonthly 月別実績の勤怠時間
	 * @return 年休積立年休の集計結果
	 */
	public AggrResultOfAnnAndRsvLeave calculateRemainAnnualHoliday(AggrPeriodEachActualClosure period, String empId,
			Map<GeneralDate, DailyInterimRemainMngData> interimRemainMngMap, AttendanceTimeOfMonthly attTimeMonthly) {
		
		String companyId = AppContexts.user().companyId();
		
		// 暫定残数データを年休・積立年休に絞り込む
		List<TmpAnnualLeaveMngWork> tmpAnnualLeaveMngs = new ArrayList<>();
		List<TmpReserveLeaveMngWork> tmpReserveLeaveMngs = new ArrayList<>();
		boolean isOverWriteAnnual = false;
		boolean isOverWriteReserve = false;
		for (val interimRemainMng : interimRemainMngMap.values()){
			if (interimRemainMng.getRecAbsData().size() <= 0) continue;
			val master = interimRemainMng.getRecAbsData().get(0);
			
			// 年休
			if (interimRemainMng.getAnnualHolidayData().isPresent()){
				val data = interimRemainMng.getAnnualHolidayData().get();
				tmpAnnualLeaveMngs.add(TmpAnnualLeaveMngWork.of(master, data));
				isOverWriteAnnual = true;
			}
			
			// 積立年休
			if (interimRemainMng.getResereData().isPresent()){
				val data = interimRemainMng.getResereData().get();
				tmpReserveLeaveMngs.add(TmpReserveLeaveMngWork.of(master, data));
				isOverWriteReserve = true;
			}
		}

		// 月別実績の計算結果が存在するかチェック
		if (attTimeMonthly != null){
			
			// 年休控除日数分の年休暫定残数データを作成する
			val compensFlexWorkOpt = this.createInterimAnnual.ofCompensFlexToWork(
					attTimeMonthly, period.getPeriod().end());
			if (compensFlexWorkOpt.isPresent()){
				tmpAnnualLeaveMngs.add(compensFlexWorkOpt.get());
				isOverWriteAnnual = true;
			}
		}
		
		// 「期間中の年休積休残数を取得」を実行する　→　「年休積立年休の集計結果」を返す
		return getRemainNum.algorithm(companyId, empId, period.getPeriod(), InterimRemainMngMode.MONTHLY,
				period.getPeriod().end(), true, true,
				Optional.of(isOverWriteAnnual || isOverWriteReserve),
				Optional.of(tmpAnnualLeaveMngs), Optional.of(tmpReserveLeaveMngs),
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
	}
}
