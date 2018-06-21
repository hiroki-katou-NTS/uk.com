/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pubimp.vacation.setting.nursingleave;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	// private TempAnnualLeaveMngRepository

	/** The child nursing value. */
	private final int NURSING_CARE = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.pub.vacation.setting.nursingleave.
	 * ShNursingLeaveSettingPub#aggrChildNursingRemainPeriod(java.lang.String,
	 * java.lang.String, nts.uk.shr.com.time.calendar.period.DatePeriod,
	 * java.lang.Integer)
	 */
	@Override
	public ChildNursingRemainExport aggrChildNursingRemainPeriod(String companyId, String employeeId, DatePeriod period,
			Integer mode) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.pub.vacation.setting.nursingleave.
	 * ShNursingLeaveSettingPub#aggrNursingRemainPeriod(java.lang.String,
	 * java.lang.String, nts.uk.shr.com.time.calendar.period.DatePeriod,
	 * java.lang.Integer)
	 */
	@Override
	public ChildNursingRemainExport aggrNursingRemainPeriod(String companyId, String employeeId, GeneralDate startDate,
			GeneralDate endDate, Boolean monthlyMode) {
		ChildNursingRemainExport result = ChildNursingRemainExport.builder().build();
		NursingCategory nursingCategory = NursingCategory.valueOf(NURSING_CARE);
		//get nursingLeaveSetting by companyId,nursingCategory = NURSING_CARE
		NursingLeaveSetting nursingLeaveSetting = this.findNursingLeaveSetting(companyId, nursingCategory);
		if (nursingLeaveSetting == null || !nursingLeaveSetting.isManaged()) {
			result.setIsManage(false);
			return result;
		} else {
			// algorithm 利用期間の算出
			result.setGrantPeriodFlag(false);
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
			result.setGrantPeriodFlag(periodGrantFlag);

			// algorithm 個人情報の上限と使用の取得, once in params: useEndDateBeforeGrant, endDate
			double grantedNumberThisTime = 0;
			double grantedNumberNextTime = 0;
			double useNumberPersonInfo = 0;
			boolean useClassification = false;
			Optional<NursingCareLeaveRemainingInfo> optionalNursingInfo = this.nursCareLevRemainInfoRepository
					.getChildCareByEmpId(employeeId);
			NursingCareLeaveRemainingInfo nursingInfo = optionalNursingInfo.get();
			useClassification = nursingInfo.isUseClassification();
			result.setIsManage(useClassification); // thang nay phai doi ket thuc thuat toan moi set
			if (nursingInfo.getUpperlimitSetting().equals(UpperLimitSetting.FAMILY_INFO)) {
				int targetNumberFamily = 13; // (gọi request 440 và 441 ra, param: useEndDateBeforeGrant)
				int vacationDays = 0;
				NursingLeaveSetting nursingCareSetting = this.findNursingLeaveSetting(companyId, nursingCategory);
				if (nursingCareSetting != null && targetNumberFamily >= nursingCareSetting.getMaxPersonSetting()
						.getNursingNumberPerson().v()) {
					vacationDays = nursingCareSetting.getMaxPersonSetting().getNursingNumberLeaveDay().v();
				}
				grantedNumberThisTime = vacationDays;

				// List<NursingLeaveSetting> nursingLeaveSettings =
				// this.nursingLeaveSettingRepository
				// .findByCompanyId(companyId);
				// List<NursingLeaveSetting> nursingCareSettings = nursingLeaveSettings.stream()
				// .filter(e ->
				// e.getNursingCategory().equals(nursingCategory)).collect(Collectors.toList());
				//// NursingLeaveSetting nursingCareSetting = null;
				// if (nursingCareSettings != null && nursingCareSettings.size() != 0) {
				// nursingCareSetting = nursingCareSettings.get(0);
				// if (targetNumberFamily >=
				// nursingCareSetting.getMaxPersonSetting().getNursingNumberPerson().v()) {
				// vacationDays =
				// nursingCareSetting.getMaxPersonSetting().getNursingNumberLeaveDay().v();
				// }
				// }
				if (periodGrantFlag) {
					int targetNumberFamily2 = 14; // (440,441 - param: endDate)
					NursingLeaveSetting nursingCareSettingFlag = this.findNursingLeaveSetting(companyId,
							nursingCategory);
					if (nursingCareSettingFlag != null && targetNumberFamily2 >= nursingCareSettingFlag
							.getMaxPersonSetting().getNursingNumberPerson().v()) {
						vacationDays = nursingCareSetting.getMaxPersonSetting().getNursingNumberLeaveDay().v();
					}
					grantedNumberNextTime = vacationDays;
				}
				// gọi request 440 và 441 => trả về targetNumberFamily
				/*
				 * tham so: · INPUT. Employee ID · INPUT. Nursing care classification
				 * 
				 */
				/*
				 * co duoc vacationDay dua tren numberOfChildNursingPeople, sau khi chay thuat
				 * toan vacationDay = 0 (init) cid + nursingClassification(hien dang la 0) =>
				 * thu duoc xNursingLeaveSetting boolean condition = targetNumberFamily >=
				 * xNursingLeaveSetting.maxPersonSetting.nursingNumberPerson if(condition){
				 * vacationDay = xNursingLeaveSetting.maxPersonSetting.nursingNumberLeaveDay }
				 * 
				 * 
				 * check periodGrantFlag if(periodGrantFlag){ chạy 440,441 => thu được
				 * numberOfTargerFamilies co duoc vacationDay dua tren
				 * numberOfChildNursingPeople, sau khi chay thuat toan }
				 * 
				 */
			} else {
				grantedNumberThisTime = nursingInfo.getMaxDayForThisFiscalYear().get().v();
				if (periodGrantFlag)
					grantedNumberNextTime = nursingInfo.getMaxDayForThisFiscalYear().get().v();
			}

			Optional<NursingCareLeaveRemainingData> optionalNursingData = this.nursCareLevRemainDataRepository
					.getCareByEmpId(employeeId);
			useNumberPersonInfo = optionalNursingData.get().getNumOfUsedDay().v();
			// startDateOverlapBeforeGrant,
			// endDateOverlapBeforeGrant,startDateOverlapAfterGrant,
			// endDateOverlapAfterGrant
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
				// tạo data gì đó (la thang optionalNursingData)
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
			result.getPreGrantStatement().setNumberOfUse(numberOfUseThisTime);
			preResidual = grantedNumberThisTime - useNumberPersonInfo - numberOfUseThisTime;
			result.getPreGrantStatement().setResidual(preResidual);
			// dùng Employee ID = INPUT. Employee ID
			// INPUT. Start date <= Year Month Day <= INPUT. End date để trả về domain
			// TempAnnualLeaveMngRepository
			// trả về 1 list TempAnnualLeaveManagement
			// chay vòng lặp cho list TempAnnualLeaveManagement => numberOfUseThisTime +=
			// TempAnnualLeaveManagement.annualLeaveUse

			// result.getpreGrantStatement.setnumberOfUse(numberOfUseThisTime)
			// preResidual = grantedNumberThisTime - useNumberPersonInfo -
			// numberOfUseThisTime
			// result.getPreGrantStatement.setResidual(preResidual)

			if (periodGrantFlag) {
				double numberOfUseNextTime = 0;
				double afterResidual = 0;
				if (monthlyMode) {
					// tạo data gì đó (la thang optionalNursingData)
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
				result.setAfterGrantStatement(optionalAfterResidual);
			}

			// check periodGrantFlag
			/*
			 * if(periodGrantFlag){ co duoc useNumberNursing qua 1 thuật toán => gán vào cho
			 * result.getafterGrantStatement.usedNumber } afterResidual =
			 * grantedNumberNextTime - numberOfUseNextTime(thang nay chay trong vong lap co
			 * duoc)
			 */
			return result;
		}
	}

	/**
	 * Find nursing leave setting.
	 *
	 * @param companyId
	 *            the company id
	 * @param nursingCategory
	 *            the nursing category
	 * @return the nursing leave setting
	 */
	public NursingLeaveSetting findNursingLeaveSetting(String companyId, NursingCategory nursingCategory) {
		List<NursingLeaveSetting> nursingLeaveSettings = this.nursingLeaveSettingRepository.findByCompanyId(companyId);
		List<NursingLeaveSetting> nursingCareSettings = nursingLeaveSettings.stream()
				.filter(e -> e.getNursingCategory().equals(nursingCategory)).collect(Collectors.toList());
		if (nursingCareSettings == null || nursingCareSettings.size() == 0) {
			return null;
		}
		return nursingCareSettings.get(0);
	}

	public static void main(String[] args) {
		System.out.println(getMonth(702));

	}

	private static int getMonth(int value) {
		return value / 100;
	}

}
