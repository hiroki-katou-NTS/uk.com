package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.care;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseAggrPeriodDaysInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseAggrPeriodInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseErrors;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseRemainingNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseStartdateDaysInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseStartdateInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseUpperLimit;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.TmpChildCareNurseMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;

/**
 * 実装：期間中の介護休暇残数を取得
 * @author yuri_tamakoshi
 */
@Stateless
public class GetRemainingNumberCareService {

	/**
	 * 期間中の介護休暇残数を取得
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
	public AggrResultOfChildCareNurse getCareRemNumWithinPeriod(String employeeId,DatePeriod period,
			InterimRemainMngMode performReferenceAtr,
			GeneralDate criteriaDate,
			Optional<Boolean> isOverWrite,
			Optional<List<TmpChildCareNurseMngWork>> tempCareDataforOverWriteList,
			Optional<AggrResultOfChildCareNurse> prevCareLeave,
			Optional<CreateAtr> createAtr,
			Optional<GeneralDate> periodOverWrite) {

		// 固定値を返す（一時対応）
		List<ChildCareNurseErrors> childCareNurseErrors = Arrays.asList(createError());
		AggrResultOfChildCareNurse result = createEmpty(childCareNurseErrors);

		return result;
	}

	// 介護休暇エラー情報
	private ChildCareNurseErrors createError() {
		return ChildCareNurseErrors.of(createUseNumber(),
															new ChildCareNurseUpperLimit(0.0),
															GeneralDate.today());
	}
	private AggrResultOfChildCareNurse createEmpty(List<ChildCareNurseErrors> childCareNurseErrors) {
		return AggrResultOfChildCareNurse.of(childCareNurseErrors,
																			createUseNumber(),
																			ChildCareNurseStartdateDaysInfo.of(
																					ChildCareNurseStartdateInfo.of(
																							createUseNumber(),
																							createRemNumber(),
																							new ChildCareNurseUpperLimit(0d)),
																					Optional.empty()),
																			false,
																			ChildCareNurseAggrPeriodDaysInfo.of(
																					ChildCareNurseAggrPeriodInfo.of(new UsedTimes(0), new UsedTimes(0), createUseNumber())
																					,Optional.empty()));
	}

	// 介護休暇使用数
	private ChildCareNurseUsedNumber createUseNumber() {
		return ChildCareNurseUsedNumber.of(new DayNumberOfUse(0d), Optional.empty());
	}

	// 介護休暇残数
	private ChildCareNurseRemainingNumber createRemNumber() {
		return ChildCareNurseRemainingNumber.of(new DayNumberOfUse(0d), Optional.empty());
	}

}
