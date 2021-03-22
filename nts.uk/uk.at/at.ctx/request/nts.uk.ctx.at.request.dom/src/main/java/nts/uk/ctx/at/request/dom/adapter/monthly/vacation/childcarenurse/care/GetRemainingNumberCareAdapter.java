package nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.care;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.ChildCareNursePeriodImport;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.TmpChildCareNurseMngWorkImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;

import java.util.List;
import java.util.Optional;


/**
 * 期間中の介護休暇残数を取得
 * @author yuri_tamakoshi
 */
public interface GetRemainingNumberCareAdapter {

		/**
		 * 期間中の介護休暇残数を取得
		 * @param employeeId 社員ID
		 * @param period 集計期間
		 * @param performReferenceAtr 実績のみ参照区分(月次モード orその他)
		 * @param criteriaDate 基準日
		 * @param isOverWrite 上書きフラグ(Optional)
		 * @param tempCareDataforOverWriteList List<上書き用の暫定管理データ>(Optional)
		 * @param prevCareLeave 前回の子の看護休暇の集計結果<Optional>
		 * @param createAtr 作成元区分(Optional)
		 * @param periodOverWrite 上書き対象期間(Optional)
		 * @return 子の看護介護休暇集計結果
		 */
		 // RequestList207
		ChildCareNursePeriodImport getCareRemNumWithinPeriod(
				String employeeId,
				DatePeriod period,
				InterimRemainMngMode performReferenceAtr,
				GeneralDate criteriaDate,
				Optional<Boolean> isOverWrite,
				List<TmpChildCareNurseMngWorkImport> tempCareDataforOverWriteList,
				Optional<ChildCareNursePeriodImport> prevCareLeave,
				Optional<CreateAtr> createAtr,
				Optional<DatePeriod> periodOverWrite
		);
}