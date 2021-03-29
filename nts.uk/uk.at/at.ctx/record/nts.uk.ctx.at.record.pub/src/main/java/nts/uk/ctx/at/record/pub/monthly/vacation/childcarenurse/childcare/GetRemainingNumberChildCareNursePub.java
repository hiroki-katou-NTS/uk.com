package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.GetRemainingNumberChildCareNurseService.Require;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareNurseManagement;

/**
 * 期間中の子の看護休暇残数を取得
 * @author yuri_tamakoshi
 */

public interface  GetRemainingNumberChildCareNursePub {

	/**
	 * 期間中の子の看護休暇残数を取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 集計期間
	 * @param performReferenceAtr 実績のみ参照区分(月次モード orその他)
	 * @param criteriaDate 基準日
	 * @param isOverWrite 上書きフラグ(Optional)
	 * @param tempChildCareDataforOverWriteList List<上書き用の暫定管理データ>(Optional)
	 * @param prevChildCareLeave 前回の子の看護休暇の集計結果<Optional>
	 * @param createAtr 作成元区分(Optional)
	 * @param periodOverWrite 上書き対象期間(Optional)
	 * @return 子の看護介護休暇集計結果
	 */
	 // RequestList206
	ChildCareNursePeriodExport getChildCareRemNumWithinPeriod(
			String companyId,
			String employeeId,
			DatePeriod period,
			InterimRemainMngMode performReferenceAtr,
			GeneralDate criteriaDate,
			Optional<Boolean> isOverWrite,
			List<TmpChildCareNurseMngWorkExport> tempChildCareDataforOverWriteList,
			Optional<ChildCareNursePeriodExport> prevChildCareLeave,
			Optional<CreateAtr> createAtr,
			Optional<DatePeriod> periodOverWrite);

}
