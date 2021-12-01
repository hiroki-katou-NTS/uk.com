package nts.uk.ctx.at.record.pubimp.monthly.vacation.childcarenurse.care;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.care.GetRemainingNumberCareService;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseRequireImplFactory;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseAggrPeriodDaysInfoExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseAggrPeriodInfoExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseErrorsExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNursePeriodExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseRemainingNumberExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseStartdateDaysInfoExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseStartdateInfoExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseUsedNumberExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.GetRemainingNumberCarePub;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.TempChildCareNurseManagementExport;
import nts.uk.ctx.at.record.pubimp.monthly.vacation.childcarenurse.ChildCareNurseConverter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagement;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseUsedInfo;

/**
 * 実装：期間中の介護休暇残数を取得
 * @author yuri_tamakoshi
 */
@Stateless
public class GetRemainingNumberCarePubImpl  implements GetRemainingNumberCarePub {


	@Inject
	private ChildCareNurseRequireImplFactory childCareNurseRequireImplFactory;



	/**
	 * 期間中の介護休暇残数を取得
	 * @param companyId 会社ID
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
	@Override
	public ChildCareNursePeriodExport getCareRemNumWithinPeriod(
			String companyId,
			String employeeId,
			DatePeriod period,
			InterimRemainMngMode performReferenceAtr,
			GeneralDate criteriaDate,
			Optional<Boolean> isOverWrite,
			List<TempChildCareNurseManagementExport> tempCareDataforOverWriteList,
			Optional<ChildCareNursePeriodExport> prevCareLeave,
			Optional<CreateAtr> createAtr,
			Optional<DatePeriod> periodOverWrite
			) {

		val require = childCareNurseRequireImplFactory.createRequireImpl();
		val cacheCarrier = new CacheCarrier();

		List<TempCareManagement>domChildCareNurseManagemenList =
				tempCareDataforOverWriteList.stream().map(c->new TempCareManagement(ChildCareNurseConverter.toDomain(c))).collect(Collectors.toList());

		Optional<AggrResultOfChildCareNurse> domPrevCareLeave = Optional.empty();


		AggrResultOfChildCareNurse result = GetRemainingNumberCareService.getCareRemNumWithinPeriod(
				companyId, employeeId, period, performReferenceAtr, criteriaDate, isOverWrite, domChildCareNurseManagemenList, domPrevCareLeave, createAtr, periodOverWrite, cacheCarrier, require);

		return mapToPub(result);
	}

	// Exportから変換
	private ChildCareNursePeriodExport mapToPub(AggrResultOfChildCareNurse c) {

		return new ChildCareNursePeriodExport(

				createError(c.getChildCareNurseErrors()),

				ChildCareNurseUsedNumberExport.of(
						c.getAsOfPeriodEnd().getUsedDay().v(),
						c.getAsOfPeriodEnd().getUsedTimes().map(ny -> ny.v())),

//				ChildCareNurseStartdateDaysInfo.of(
//						mapToPub(c.getStartdateDays().getThisYear()),
//						c.getStartdateDays().getNextYear().map(ny -> mapToPub(ny))),
				ChildCareNurseStartdateDaysInfoExport.of(
						mapToPub(c.getStartdateDays().getThisYear()),
						c.getStartdateDays().getNextYear().map(ny -> mapToPub(ny))),

				c.isStartDateAtr(),

				ChildCareNurseAggrPeriodDaysInfoExport.of(
						mapToPubAggrPeriodInfo(c.getAggrperiodinfo().getThisYear()),
						c.getAggrperiodinfo().getNextYear().map(ny -> mapToPubAggrPeriodInfo(ny)))

				);
	}

	//  起算日からの休暇情報
	private ChildCareNurseStartdateInfoExport mapToPub(nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseStartdateInfo domain) {

		return ChildCareNurseStartdateInfoExport.of(
				ChildCareNurseUsedNumberExport.of(
						domain.getUsedDays().getUsedDay().v(),
						domain.getUsedDays().getUsedTimes().map(t -> t.v())),
					ChildCareNurseRemainingNumberExport.of(
							domain.getRemainingNumber().getRemainDay().v(),
							domain.getRemainingNumber().getRemainTimes().map(t -> t.v())),
					domain.getLimitDays().v());
	}

	// 集計期間の休暇情報
	private ChildCareNurseAggrPeriodInfoExport mapToPubAggrPeriodInfo(ChildCareNurseUsedInfo domain) {
		return ChildCareNurseAggrPeriodInfoExport.of(
							domain.getUsedTimes().v(),
							domain.getUsedDays().v(),
							ChildCareNurseUsedNumberExport.of(
									domain.getUsedNumber().getUsedDay().v(),
									domain.getUsedNumber().getUsedTimes().map(t -> t.v())));
	}

	// 介護休暇エラー情報
	private List<ChildCareNurseErrorsExport> createError(List<nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service.ChildCareNurseErrors> childCareNurseErrors) {

//		return childCareNurseErrors.stream().map(c ->
//																ChildCareNurseErrors.of(
//																		ChildCareNurseUsedNumber.of(
//																				c.getUsedNumber().getUsedDay(),
//																				c.getUsedNumber().getUsedTimes()),
//																		c.getLimitDays().v(),
//																		c.getYmd()))
//														.collect(Collectors.toList());

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
