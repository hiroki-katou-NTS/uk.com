package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.ReferenceAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：年休使用日数を取得
 * @author shuichi_ishida
 */
@Stateless
public class GetAnnLeaUsedDaysImpl implements GetAnnLeaUsedDays {

	/** ドメインサービス：締め */
	@Inject
	private ClosureService closureService;
	/** 指定した年月日を基準に、前回付与日から次回付与日までの期間を取得 */
	@Inject
	private GetPeriodFromPreviousToNextGrantDate getPeriodFromPreviousToNextGrantDate;
	/** 期間内の年休使用明細を取得する */
	@Inject
	private GetAnnualHolidayGrantInfor getAnnualHolidayGrantInfor;

	/** 社員の前回付与日から次回付与日までの年休使用日数を取得 */
	@Override
	public Optional<AnnualLeaveUsedDayNumber> ofGrantPeriod(String employeeId, GeneralDate criteria,
			ReferenceAtr referenceAtr) {
		
		// 社員の前回付与日から次回付与日までの年休使用日数を取得
		val tmpAnnLeaMngExportList = this.ofGrantPeriodProc(employeeId, criteria, referenceAtr);
		
		// 年休使用数を合計
		double annualLeaveUseDays = 0.0;
		for (val tmpAnnLeaMngExport : tmpAnnLeaMngExportList) {
			annualLeaveUseDays += tmpAnnLeaMngExport.getUseDays().v();
		}
		
		// 年休使用合計数を返す
		return Optional.of(new AnnualLeaveUsedDayNumber(annualLeaveUseDays));
	}
	
	/** 指定した期間の年休使用数を取得する */
	@Override
	public Optional<AnnualLeaveUsedDayNumber> ofPeriod(String employeeId, DatePeriod period,
			ReferenceAtr referenceAtr) {
		
		// 期間内の年休使用明細を取得する
		val domReferenceAtr = EnumAdaptor.valueOf(referenceAtr.value,
				nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.ReferenceAtr.class);
		val dailyInterimRemainMngDatas = this.getAnnualHolidayGrantInfor.lstRemainData(
				AppContexts.user().companyId(), employeeId, period, domReferenceAtr);
		
		// 年休使用数を合計
		double annualLeaveUseDays = 0.0;
		for (val dailyInterimRemainMngData : dailyInterimRemainMngDatas) {

			// 年休が存在するか確認する
			val interimRemainOpt = dailyInterimRemainMngData.getData().getRecAbsData().stream()
					.filter(c -> c.getRemainType() == RemainType.ANNUAL).findFirst();
			if (!interimRemainOpt.isPresent()) continue;
			if (!dailyInterimRemainMngData.getData().getAnnualHolidayData().isPresent()) continue;
			val tmpAnnLeaMng = dailyInterimRemainMngData.getData().getAnnualHolidayData().get();
			
			annualLeaveUseDays += tmpAnnLeaMng.getUseDays().v();
		}
		
		// 年休使用合計数を返す
		return Optional.of(new AnnualLeaveUsedDayNumber(annualLeaveUseDays));
	}
	
	/** 社員の前回付与日から次回付与日までの年休使用日数を取得 */
	private List<TmpAnnualLeaveMngExport> ofGrantPeriodProc(String employeeId, GeneralDate criteria,
			ReferenceAtr referenceAtr) {
		
		List<TmpAnnualLeaveMngExport> results = new ArrayList<>();
		
		// 社員に対応する締め期間を取得する
		DatePeriod closurePeriod = this.closureService.findClosurePeriod(employeeId, criteria);
		if (closurePeriod == null) return results;
		
		// 指定した年月日を基準に、前回付与日から次回付与日までの期間を取得
		val usedPeriodOpt = this.getPeriodFromPreviousToNextGrantDate.getPeriodYMDGrant(
				AppContexts.user().companyId(), employeeId, closurePeriod.end());
		if (!usedPeriodOpt.isPresent()) return results;
		val usedPeriod = usedPeriodOpt.get();
		
		// 期間内の年休使用明細を取得する
		val domReferenceAtr = EnumAdaptor.valueOf(referenceAtr.value,
				nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.ReferenceAtr.class);
		val dailyInterimRemainMngDatas = this.getAnnualHolidayGrantInfor.lstRemainData(
				AppContexts.user().companyId(), employeeId, usedPeriod, domReferenceAtr);

		// 暫定年休管理データ(List)を返す
		for (val dailyInterimRemainMngData: dailyInterimRemainMngDatas) {
			
			// 年休が存在するか確認する
			val interimRemainOpt = dailyInterimRemainMngData.getData().getRecAbsData().stream()
					.filter(c -> c.getRemainType() == RemainType.ANNUAL).findFirst();
			if (!interimRemainOpt.isPresent()) continue;
			if (!dailyInterimRemainMngData.getData().getAnnualHolidayData().isPresent()) continue;
			val tmpAnnLeaMng = dailyInterimRemainMngData.getData().getAnnualHolidayData().get();
			
			results.add(TmpAnnualLeaveMngExport.of(interimRemainOpt.get(), tmpAnnLeaMng));
		}
		return results;
	}
}
