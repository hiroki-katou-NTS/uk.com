/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pubimp.vacation.setting.nursingleave;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.NursCareLevRemainDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.NursingCareLeaveRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursCareLevRemainInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.UpperLimitSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.temp.caredata.TempCareData;
import nts.uk.ctx.at.shared.dom.vacation.setting.temp.caredata.TempCareDataRepository;
import nts.uk.ctx.at.shared.pub.vacation.setting.nursingleave.ChildNursingRemainExport;
import nts.uk.ctx.at.shared.pub.vacation.setting.nursingleave.ChildNursingRemainInforExport;
import nts.uk.ctx.at.shared.pub.vacation.setting.nursingleave.ShNursingLeaveSettingPub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class ShNursingLeaveSettingPubImpl.
 */
@Stateless
public class ShNursingLeaveSettingPubImpl implements ShNursingLeaveSettingPub {

	/** The nursing leave setting repository. */
	@Inject
	private NursingLeaveSettingRepository nursingLeaveSettingRepository;

	@Inject
	private NursCareLevRemainInfoRepository nursCareLevRemainInfoRepository;

	@Inject
	private NursCareLevRemainDataRepository nursCareLevRemainDataRepository;

	@Inject
	private TempCareDataRepository tempCareDataRepository;

	@Override
	public ChildNursingRemainExport aggrChildNursingRemainPeriod(String companyId, String employeeId, DatePeriod period,
			Integer mode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChildNursingRemainExport aggrNursingRemainPeriod(String companyId, String employeeId, GeneralDate startDate,
			GeneralDate endDate, Boolean monthlyMode) {
		ChildNursingRemainExport childNursingRemainExport = ChildNursingRemainExport.builder().build();
		// get nursingLeaveSetting by companyId,nursingCategory = NURSING_CARE
		Optional<NursingLeaveSetting> _nursingLeaveSetting = this.findNursingLeaveSetting(companyId,
				NursingCategory.Nursing);
		if (!_nursingLeaveSetting.isPresent()) {
			childNursingRemainExport.setIsManage(false);
			return childNursingRemainExport;
		} else {
			// do something
			return dosomething(companyId, employeeId, startDate, endDate, monthlyMode, _nursingLeaveSetting.get());
		}
	}

	/**
	 * Find nursing leave setting.
	 *
	 * @param companyId
	 * @param nursingCategory
	 * 
	 * @return the nursing leave setting
	 */
	private Optional<NursingLeaveSetting> findNursingLeaveSetting(String companyId, NursingCategory nursingCategory) {
		List<NursingLeaveSetting> nursingLeaveSettings = this.nursingLeaveSettingRepository.findByCompanyId(companyId);
		return nursingLeaveSettings.stream().filter(e -> e.getNursingCategory().equals(nursingCategory)).findFirst();
	}

	private ChildNursingRemainExport dosomething(String companyId, String employeeId, GeneralDate startDate,
			GeneralDate endDate, Boolean monthlyMode, NursingLeaveSetting nursingLeaveSetting) {
		ChildNursingRemainExport childNursingRemainExport = ChildNursingRemainExport.builder().build();
		// algorithm 利用期間の算出
		childNursingRemainExport.setGrantPeriodFlag(false);
		GeneralDate useStartDateBeforeGrant, useEndDateBeforeGrant;
		Optional<GeneralDate> useStartDateAfterGrant = Optional.empty();
		Optional<GeneralDate> endDateUseAfterGrant = Optional.empty();
		boolean periodGrantFlag = false;

		int dayStartMonthDay = nursingLeaveSetting.getStartMonthDay().intValue() % 100;
		int monthStartMonthDay = nursingLeaveSetting.getStartMonthDay().intValue() / 100;
		GeneralDate commencementDate = GeneralDate.ymd(startDate.year(), monthStartMonthDay, dayStartMonthDay);
		useStartDateBeforeGrant = (commencementDate.compareTo(startDate) > 0) ? commencementDate.addYears(-1)
				: commencementDate;
		useEndDateBeforeGrant = useStartDateBeforeGrant.addYears(1).addDays(-1);
		boolean haveSubsidize = useStartDateBeforeGrant.addYears(1).compareTo(endDate) <= 0;
		if (haveSubsidize) {
			periodGrantFlag = true;
			useStartDateAfterGrant = Optional.of(useStartDateBeforeGrant.addYears(1));
			endDateUseAfterGrant = Optional.of(useStartDateAfterGrant.get().addYears(1).addDays(-1));
		}
		childNursingRemainExport.setGrantPeriodFlag(periodGrantFlag);

		// algorithm 個人情報の上限と使用の取得, once in params: useEndDateBeforeGrant,
		// endDate
		double grantedNumberThisTime = 0;
		double grantedNumberNextTime = 0;
		double useNumberPersonInfo = 0;
		boolean useClassification = false;
		Optional<NursingCareLeaveRemainingInfo> optionalNursingInfo = this.nursCareLevRemainInfoRepository
				.getChildCareByEmpId(employeeId);
		NursingCareLeaveRemainingInfo nursingInfo = optionalNursingInfo.get();
		useClassification = nursingInfo.isUseClassification();
		if (nursingInfo.getUpperlimitSetting().equals(UpperLimitSetting.FAMILY_INFO)) {
			int fakeTargetNumberFamily = 13; // (gọi request 440 và 441 ra,
												// param: useEndDateBeforeGrant)
			int vacationDays = 0;
			if (fakeTargetNumberFamily >= nursingLeaveSetting.getMaxPersonSetting().getNursingNumberPerson().v()) {
				vacationDays = nursingLeaveSetting.getMaxPersonSetting().getNursingNumberLeaveDay().v();
			}
			grantedNumberThisTime = vacationDays;

			if (periodGrantFlag) {
				int fakeTargetNumberFamily2 = 14; // (440,441 - param: endDate)
				if (nursingLeaveSetting != null && fakeTargetNumberFamily2 >= nursingLeaveSetting.getMaxPersonSetting()
						.getNursingNumberPerson().v()) {
					vacationDays = nursingLeaveSetting.getMaxPersonSetting().getNursingNumberLeaveDay().v();
				}
				grantedNumberNextTime = vacationDays;
			}
		} else {
			grantedNumberThisTime = nursingInfo.getMaxDayForThisFiscalYear().get().v();
			if (periodGrantFlag)
				grantedNumberNextTime = nursingInfo.getMaxDayForThisFiscalYear().get().v();
		}
		// setManage
		childNursingRemainExport.setIsManage(useClassification);
		// calculate overlap time
		Optional<NursingCareLeaveRemainingData> optionalNursingData = this.nursCareLevRemainDataRepository
				.getCareByEmpId(employeeId);
		useNumberPersonInfo = optionalNursingData.get().getNumOfUsedDay().v();
		GeneralDate startDateOverlapBeforeGrant, endDateOverlapBeforeGrant, startDateOverlapAfterGrant,
				endDateOverlapAfterGrant;
		startDateOverlapBeforeGrant = startDate;
		endDateOverlapBeforeGrant = useEndDateBeforeGrant;
		startDateOverlapAfterGrant = useStartDateAfterGrant.get();
		endDateOverlapAfterGrant = endDate;

		// algorithm 介護の使用数の取得
		double numberOfUseThisTime = 0;
		double preResidual = 0;
		if (monthlyMode) {
			// goi den ham cua chi Du
		}
		List<TempCareData> listTempCareData = this.tempCareDataRepository.findTempCareDataByEmpId(employeeId);
		List<TempCareData> listTempCareDataPre = new ArrayList<>();
		listTempCareData.stream().forEach(e -> {
			if (e.getYmd().compareTo(startDateOverlapBeforeGrant) >= 0
					&& e.getYmd().compareTo(endDateOverlapBeforeGrant) <= 0) {
				listTempCareDataPre.add(e);
			}
		});
		for (TempCareData e : listTempCareDataPre) {
			numberOfUseThisTime += e.getAnnualLeaveUse().v();
		}
		// set remainning number
		childNursingRemainExport.getPreGrantStatement().setNumberOfUse(numberOfUseThisTime);
		preResidual = grantedNumberThisTime - useNumberPersonInfo - numberOfUseThisTime;
		childNursingRemainExport.getPreGrantStatement().setResidual(preResidual);
		// check periodGrantFlag 期中付与フラグ(output)をチェックする
		if (periodGrantFlag) {
			double numberOfUseNextTime = 0;
			double afterResidual = 0;
			if (monthlyMode) {
				// goi den ham cua chi Du
			}
			List<TempCareData> listTempCareDataAfter = new ArrayList<>();
			listTempCareData.stream().forEach(e -> {
				if (e.getYmd().compareTo(startDateOverlapAfterGrant) >= 0
						&& e.getYmd().compareTo(endDateOverlapAfterGrant) <= 0) {
					listTempCareDataAfter.add(e);
				}
			});
			for (TempCareData e : listTempCareDataAfter) {
				numberOfUseNextTime += e.getAnnualLeaveUse().v();
			}
			ChildNursingRemainInforExport afterGrantStatement = ChildNursingRemainInforExport.builder().build();
			afterGrantStatement.setNumberOfUse(numberOfUseNextTime);
			afterResidual = grantedNumberNextTime - numberOfUseNextTime;
			afterGrantStatement.setResidual(afterResidual);
			Optional<ChildNursingRemainInforExport> optionalAfterResidual = Optional.of(afterGrantStatement);
			childNursingRemainExport.setAfterGrantStatement(optionalAfterResidual);
		}
		return childNursingRemainExport;
	}
	
	private Output1 calculationUsagePeriod(Integer startMonthDay, GeneralDate startDate, GeneralDate endDate) {
		Output1 ouput = new Output1();
		int dayStartMonthDay = startMonthDay % 100;
		int monthStartMonthDay = startMonthDay / 100;
		GeneralDate commencementDate = GeneralDate.ymd(startDate.year(), monthStartMonthDay, dayStartMonthDay);
		
		return ouput;
	}
	

}
