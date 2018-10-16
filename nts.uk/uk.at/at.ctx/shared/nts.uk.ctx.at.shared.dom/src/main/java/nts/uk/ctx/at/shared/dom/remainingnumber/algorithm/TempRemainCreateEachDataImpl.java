package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.ManagermentAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.CompanyHolidayMngSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DayoffTranferInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.EmploymentHolidayMngSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.InforFormerRemainData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.OccurrenceUseDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.SpecialHolidayUseDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.TranferTimeInfor;
import nts.uk.ctx.at.shared.dom.vacation.service.UseDateDeadlineFromDatePeriod;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;

@Stateless
public class TempRemainCreateEachDataImpl implements TempRemainCreateEachData{
	@Inject
	private UseDateDeadlineFromDatePeriod useDateService;
	@Override
	public DailyInterimRemainMngData createInterimAnnualHoliday(InforFormerRemainData inforData,
			WorkTypeClassification workTypeClass, DailyInterimRemainMngData mngData) {
		//残数作成元情報のアルゴリズム「分類を指定して発生使用明細を取得する」を実行する
		Optional<OccurrenceUseDetail> occUseDetail = inforData.getOccurrenceUseDetail(workTypeClass);
		if(!occUseDetail.isPresent()) {
			return mngData;
		}
		List<InterimRemain> recAbsData = new ArrayList<>(mngData.getRecAbsData());
		OccurrenceUseDetail useDetail = occUseDetail.get();
		String mngId = IdentifierUtil.randomUniqueId();
		InterimRemain ramainData = new InterimRemain(mngId, 
				inforData.getSid(),
				inforData.getYmd(),
				inforData.getWorkTypeRemainInfor(workTypeClass).get().getCreateData(), 
				RemainType.ANNUAL,
				RemainAtr.SINGLE);
		recAbsData.add(ramainData);
		TmpAnnualHolidayMng annualMng = new TmpAnnualHolidayMng(mngId, 
				inforData.getWorkTypeRemainInfor(workTypeClass).get().getWorkTypeCode(), 
				new UseDay(useDetail.getDays()));
		mngData.setRecAbsData(recAbsData);
		mngData.setAnnualHolidayData(Optional.of(annualMng));
		return mngData;
	}

	@Override
	public DailyInterimRemainMngData createInterimReserveHoliday(InforFormerRemainData inforData,
			WorkTypeClassification workTypeClass, DailyInterimRemainMngData mngData) {
		//残数作成元情報のアルゴリズム「分類を指定して発生使用明細を取得する」を実行する
		Optional<OccurrenceUseDetail> occUseDetail = inforData.getOccurrenceUseDetail(workTypeClass);
		if(!occUseDetail.isPresent()) {
			return mngData;
		}
		List<InterimRemain> recAbsData = new ArrayList<>(mngData.getRecAbsData());
		String mngId = IdentifierUtil.randomUniqueId();
		InterimRemain ramainData = new InterimRemain(mngId, 
				inforData.getSid(),
				inforData.getYmd(),
				inforData.getWorkTypeRemainInfor(workTypeClass).get().getCreateData(), 
				RemainType.FUNDINGANNUAL,
				RemainAtr.SINGLE);
		recAbsData.add(ramainData);
		TmpResereLeaveMng resereData = new TmpResereLeaveMng(mngId, new UseDay(occUseDetail.get().getDays()));
		mngData.setResereData(Optional.of(resereData));
		mngData.setRecAbsData(recAbsData);
		return mngData;
	}
	@Override
	public DailyInterimRemainMngData createInterimAbsData(InforFormerRemainData inforData, WorkTypeClassification workTypeClass,
			DailyInterimRemainMngData mngData) {
		//残数作成元情報のアルゴリズム「分類を指定して発生使用明細を取得する」を実行する
		Optional<OccurrenceUseDetail> occUseDetail = inforData.getOccurrenceUseDetail(workTypeClass);
		List<InterimRemain> recAbsData = new ArrayList<>(mngData.getRecAbsData());
		if(occUseDetail.isPresent()) {
			String mngId = IdentifierUtil.randomUniqueId();
			InterimRemain mngDataRemain = new InterimRemain(mngId,
					inforData.getSid(),
					inforData.getYmd(),
					inforData.getWorkTypeRemainInfor(workTypeClass).get().getCreateData(),
					RemainType.PAUSE,
					RemainAtr.SINGLE);
			InterimAbsMng absData = new InterimAbsMng(mngId,
					new RequiredDay(occUseDetail.get().getDays()),
					new UnOffsetDay(occUseDetail.get().getDays()));
			mngData.setInterimAbsData(Optional.of(absData));
			recAbsData.add(mngDataRemain);
			mngData.setRecAbsData(recAbsData);
		}
		return mngData;
	}

	@Override
	public DailyInterimRemainMngData createInterimDayOffData(InforFormerRemainData inforData,
			WorkTypeClassification workTypeClass, DailyInterimRemainMngData mngData) {
		//残数作成元情報のアルゴリズム「分類を指定して発生使用明細を取得する」を実行する
		Optional<OccurrenceUseDetail> occUseDetail = inforData.getOccurrenceUseDetail(workTypeClass);
		List<InterimRemain> recAbsData = new ArrayList<>(mngData.getRecAbsData());
		if(occUseDetail.isPresent()) {
			if(!inforData.isDayOffTimeIsUse()) {
				String mngId = IdentifierUtil.randomUniqueId();
				InterimRemain mngDataRemain = new InterimRemain(mngId, inforData.getSid(), inforData.getYmd(), 
						inforData.getWorkTypeRemainInfor(workTypeClass).get().getCreateData(), RemainType.SUBHOLIDAY, RemainAtr.SINGLE);
				InterimDayOffMng dayoffMng = new InterimDayOffMng(mngId, 
						new RequiredTime(0),
						new RequiredDay(occUseDetail.get().getDays()),
						new UnOffsetTime(0), 
						new UnOffsetDay(occUseDetail.get().getDays()));
				mngData.setDayOffData(Optional.of(dayoffMng));
				recAbsData.add(mngDataRemain);
			} else {
				//TODO 2018.06.20 chua lam trong giai doan nay
			}
			mngData.setRecAbsData(recAbsData);
		}
		return mngData;
	}

	@Override
	public DailyInterimRemainMngData createInterimRecData(InforFormerRemainData inforData,
			WorkTypeClassification workTypeClass, DailyInterimRemainMngData mngData) {
		// 残数作成元情報のアルゴリズム「分類を指定して発生使用明細を取得する」を実行する
		Optional<OccurrenceUseDetail> occUseDetail = inforData.getOccurrenceUseDetail(workTypeClass);
		if(!occUseDetail.isPresent()) {
			return mngData;
		}
		//アルゴリズム「振休使用期限日の算出」を実行する
		GeneralDate useDate = this.getUseDays(inforData, true);
		String mngId = IdentifierUtil.randomUniqueId();
		InterimRemain remainMng = new InterimRemain(mngId,
				inforData.getSid(),
				inforData.getYmd(),
				inforData.getWorkTypeRemainInfor(workTypeClass).get().getCreateData(), 
				RemainType.PICKINGUP, 
				RemainAtr.SINGLE);
		List<OccurrenceUseDetail> occurrenceDetailData =  inforData.getWorkTypeRemainInfor(workTypeClass).get().getOccurrenceDetailData()
				.stream().filter(x -> x.getWorkTypeAtr() == workTypeClass)
				.collect(Collectors.toList());
		
		InterimRecMng recMng = new InterimRecMng(mngId,
				useDate,
				new OccurrenceDay(occurrenceDetailData.isEmpty() ? 0 : occurrenceDetailData.get(0).getDays()),
				StatutoryAtr.NONSTATURORY,
				new UnUsedDay(occurrenceDetailData.isEmpty() ? 0 : occurrenceDetailData.get(0).getDays()));
		mngData.setRecData(Optional.of(recMng));
		List<InterimRemain> recAbsData = new ArrayList<>(mngData.getRecAbsData());
		recAbsData.add(remainMng);
		mngData.setRecAbsData(recAbsData);
		return mngData;
	}
	/**
	 * 振休使用期限日を取得する, 代休使用期限日を取得する
	 * @param isAbs : True: 振休, False：　代休
	 * @return
	 */
	private GeneralDate getUseDays(InforFormerRemainData inforData, boolean isAbs) {
		//雇用別休暇管理設定の振休をチェックする
		EmploymentHolidayMngSetting employmentHolidaySetting = inforData.getEmploymentHolidaySetting();
		SubstVacationSetting subSetting = null;
		ExpirationTime expriTime = ExpirationTime.THIS_MONTH;
		if(employmentHolidaySetting != null
				&& (employmentHolidaySetting.getDayOffSetting() != null || employmentHolidaySetting.getAbsSetting().isPresent())) {
			if(isAbs && employmentHolidaySetting.getAbsSetting().isPresent()) {
				subSetting = employmentHolidaySetting.getAbsSetting().get().getSetting();
			} else if (!isAbs && employmentHolidaySetting.getDayOffSetting() != null) {
				expriTime = employmentHolidaySetting.getDayOffSetting().getCompensatoryAcquisitionUse().getExpirationTime();
			}
			
		} else {
			if (isAbs && inforData.getCompanyHolidaySetting().getAbsSetting().isPresent()) {
				ComSubstVacation companyHolidaySetting = inforData.getCompanyHolidaySetting().getAbsSetting().get();
				subSetting = companyHolidaySetting.getSetting();
			} else if (!isAbs && inforData.getCompanyHolidaySetting().getDayOffSetting() != null) {
				expriTime = inforData.getCompanyHolidaySetting().getDayOffSetting().getCompensatoryAcquisitionUse().getExpirationTime();
			}
		}
		if(isAbs && subSetting == null) {
			return GeneralDate.max();
		} else if(isAbs && subSetting != null){
			expriTime = subSetting.getExpirationDate();
		}
		
		//アルゴリズム「休暇使用期限から使用期限日を算出する」を実行する
		if(expriTime == ExpirationTime.END_OF_YEAR) {
			//TODO 
		} else if (expriTime == ExpirationTime.UNLIMITED) {
			return GeneralDate.max();
		} else {
			//期限指定のある使用期限日を作成する
			if(expriTime != null) {
				return useDateService.useDateDeadline(inforData.getEmploymentHolidaySetting().getEmploymentCode(),subSetting.getExpirationDate(), inforData.getYmd());
			}
		}
		return GeneralDate.max();
	}
	
	

	@Override
	public DailyInterimRemainMngData createInterimBreak(InforFormerRemainData inforData,
			WorkTypeClassification workTypeClass, DailyInterimRemainMngData mngData) {
		Integer tranferTime = 0;
		double tranferDay = 0;
		//代休振替情報のアルゴリズム「振替時間情報を取得する」を実行する
		for (DayoffTranferInfor x : inforData.getDayOffTranfer()) {
			tranferTime += x.getTranferTimeInfor().getTranferTime();
			if(x.getTranferTimeInfor().getDays().isPresent()) {
				tranferDay += x.getTranferTimeInfor().getDays().get();
			}
		}
		//代休振替情報をチェックする
		if(!inforData.getWorkTypeRemainInfor(workTypeClass).isPresent()
				|| (tranferTime == 0 && (tranferDay == 0))) {
			return mngData;
		}
		//代休使用期限日を取得する
		GeneralDate useDate = this.getUseDays(inforData, false);
		String mngId = IdentifierUtil.randomUniqueId();
		InterimRemain recAbsData = new InterimRemain(mngId,
				inforData.getSid(),
				inforData.getYmd(),
				inforData.getWorkTypeRemainInfor(workTypeClass).get().getCreateData(),
				RemainType.BREAK,
				RemainAtr.SINGLE);
		List<InterimRemain> lstRecAbsData = new ArrayList<>(mngData.getRecAbsData());
		lstRecAbsData.add(recAbsData);
		
		//時間代休を利用するかチェックする		
		if(inforData.isDayOffTimeIsUse()) {
			if(tranferTime == 0) {
				return mngData;
			}
			//振替時間をチェックする
			InterimBreakMng breakMng = new InterimBreakMng(mngId,
					new AttendanceTime(0),
					useDate,
					new OccurrenceTime(tranferTime),
					new OccurrenceDay(0.0),
					new AttendanceTime(0),
					new UnUsedTime(tranferTime), 
					new UnUsedDay(0.0));
			mngData.setBreakData(Optional.of(breakMng));
		} else {
			if(tranferDay == 0) {
				return mngData;
			}
			InterimBreakMng breakMng = new InterimBreakMng(mngId,
					new AttendanceTime(0),
					useDate,
					new OccurrenceTime(0),
					new OccurrenceDay(tranferDay),
					new AttendanceTime(0),
					new UnUsedTime(0), 
					new UnUsedDay(tranferDay));
			mngData.setBreakData(Optional.of(breakMng));
		}
		mngData.setRecAbsData(lstRecAbsData);
		return mngData;
	}

	@Override
	public DailyInterimRemainMngData createInterimSpecialHoliday(InforFormerRemainData inforData,
			WorkTypeClassification workTypeClass, DailyInterimRemainMngData mngData) {
		List<InterimSpecialHolidayMng> specialHolidayData = new ArrayList<>(mngData.getSpecialHolidayData()); 
		if(inforData.getWorkTypeRemainInfor(workTypeClass).get().getSpeHolidayDetailData().isEmpty()) {
			return mngData;
		}
		String mngId = IdentifierUtil.randomUniqueId();
		InterimRemain recAbsData = new InterimRemain(mngId,
				inforData.getSid(),
				inforData.getYmd(),
				inforData.getWorkTypeRemainInfor(workTypeClass).get().getCreateData(),
				RemainType.SPECIAL,
				RemainAtr.COMPOSITE);
		List<InterimRemain> lstRecAbsData = new ArrayList<>(mngData.getRecAbsData());
		lstRecAbsData.add(recAbsData);
		mngData.setRecAbsData(lstRecAbsData);
		for (SpecialHolidayUseDetail speHolidayDetail : inforData.getWorkTypeRemainInfor(workTypeClass).get().getSpeHolidayDetailData()) {
			InterimSpecialHolidayMng holidayMng = new InterimSpecialHolidayMng();
			holidayMng.setSpecialHolidayId(mngId);
			holidayMng.setSpecialHolidayCode(speHolidayDetail.getSpecialHolidayCode());
			holidayMng.setMngAtr(ManagermentAtr.DAYS);
			holidayMng.setUseDays(Optional.of(new UseDay(speHolidayDetail.getDays())));
			holidayMng.setUseTimes(Optional.of(new UseTime(0)));
			specialHolidayData.add(holidayMng);
		}
		mngData.setSpecialHolidayData(specialHolidayData);
		return mngData;
	}
	
	
}
