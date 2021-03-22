package nts.uk.ctx.at.record.pubimp.monthly.vacation.childcarenurse.childcare;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.GetRemainingNumberChildCareNurseService.Require;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.GetRemainingNumberChildCareService;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseAggrPeriodDaysInfo;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseAggrPeriodInfo;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseErrorsExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNursePeriodExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseRemainingNumber;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseStartdateDaysInfo;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseStartdateInfo;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseUsedNumberExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.GetRemainingNumberChildCareNursePub;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareNurseManagement;

/**
 * 実装：期間中の子の看護休暇残数を取得
 * @author yuri_tamakoshi
 */

@Stateless
public class GetRemainingNumberChildCareNursePubImpl implements GetRemainingNumberChildCareNursePub {

	@Inject
	private GetRemainingNumberChildCareService getRemainingNumberChildCareService;

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
	@Override
	public ChildCareNursePeriodExport getChildCareRemNumWithinPeriod(String companyId, String employeeId,DatePeriod period,
			InterimRemainMngMode performReferenceAtr,
			GeneralDate criteriaDate,
			Optional<Boolean> isOverWrite,
			Optional<TempChildCareNurseManagement> tempChildCareDataforOverWriteList,
			Optional<AggrResultOfChildCareNurse> prevChildCareLeave,
			Optional<CreateAtr> createAtr,
			Optional<DatePeriod> periodOverWrite,
			CacheCarrier cacheCarrier,
			Require require) {

		AggrResultOfChildCareNurse result = getRemainingNumberChildCareService.getChildCareRemNumWithinPeriod(
																	companyId, employeeId, period, performReferenceAtr, criteriaDate,
																	isOverWrite, tempChildCareDataforOverWriteList, prevChildCareLeave, createAtr, periodOverWrite, cacheCarrier, require);

		return mapToPub(result);
	}

	// Exportから変換
	private ChildCareNursePeriodExport mapToPub(AggrResultOfChildCareNurse c) {
		return new ChildCareNursePeriodExport(
					createError(c.getChildCareNurseErrors()) ,
					ChildCareNurseUsedNumberExport.of(
							c.getAsOfPeriodEnd().getUsedDay().v(),
							c.getAsOfPeriodEnd().getUsedTimes().map(ny -> ny.v())),
					ChildCareNurseStartdateDaysInfo.of(
							mapToPub(c.getStartdateDays().getThisYear()),
							c.getStartdateDays().getNextYear().map(ny -> mapToPub(ny))),
					c.isStartDateAtr(),
					ChildCareNurseAggrPeriodDaysInfo.of(
							mapToPubAggrPeriodInfo(c.getAggrperiodinfo().getThisYear()),
							c.getAggrperiodinfo().getNextYear().map(ny -> mapToPubAggrPeriodInfo(ny))));
	}

	//  起算日からの休暇情報
	private ChildCareNurseStartdateInfo mapToPub(nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseStartdateInfo domain) {
		return ChildCareNurseStartdateInfo.of(
					ChildCareNurseUsedNumberExport.of(
							domain.getUsedDays().getUsedDay().v(),
							domain.getUsedDays().getUsedTimes().map(t -> t.v())),
						ChildCareNurseRemainingNumber.of(
								domain.getRemainingNumber().getUsedDays().v(),
								domain.getRemainingNumber().getUsedTime().map(t -> t.v())),
						domain.getLimitDays().v());
	}

	// 集計期間の休暇情報
	private ChildCareNurseAggrPeriodInfo mapToPubAggrPeriodInfo(nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseAggrPeriodInfo domain) {
		return ChildCareNurseAggrPeriodInfo.of(
							domain.getUsedCount().v(),
							domain.getUsedDays().v(),
							ChildCareNurseUsedNumberExport.of(
									domain.getAggrPeriodUsedNumber().getUsedDay().v(),
									domain.getAggrPeriodUsedNumber().getUsedTimes().map(t -> t.v())));
	}


	// 子の看護休暇エラー情報
	private List<ChildCareNurseErrorsExport> createError(List<nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseErrors> childCareNurseErrors) {

		return childCareNurseErrors.stream().map(c ->
																ChildCareNurseErrorsExport.of(
																		ChildCareNurseUsedNumberExport.of(
																				c.getUsedNumber().getUsedDay().v(),
																				c.getUsedNumber().getUsedTimes().map(u -> u.v())),
																		c.getLimitDays().v(),
																		c.getYmd()))
				.collect(Collectors.toList());
	}

}
