package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecMngInPeriodParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpReserveLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.ComplileInPeriodOfSpecialLeaveParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.InPeriodOfSpecialLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveError;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveManagementService;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.CompanyHolidayMngSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.AnnualLeaveErrorSharedImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.GetAnnLeaRemNumWithinPeriodSharedImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.ReserveLeaveErrorImport;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
@Stateless
public class InterimRemainDataMngCheckRegisterImpl implements InterimRemainDataMngCheckRegister{
	@Inject
	private BreakDayOffMngInPeriodQuery breakDayOffMngService;
	@Inject
	private AbsenceReruitmentMngInPeriodQuery absRecMngService;
	@Inject
	private InterimRemainOffPeriodCreateData interimCreateData;
	@Inject
	private ComSubstVacationRepository subRepos;
	@Inject
	private CompensLeaveComSetRepository leaveSetRepos;
	@Inject
	private SpecialLeaveManagementService speLeaveSevice;
	@Inject
	private GetAnnLeaRemNumWithinPeriodSharedImport annualService;
	@Override
	public EarchInterimRemainCheck checkRegister(InterimRemainCheckInputParam inputParam) {
		//代休不足区分、振休不足区分、年休不足区分、積休不足区分、特休不足区分、公休不足区分、超休不足区分をfalseにする(初期化)
		EarchInterimRemainCheck outputData = new EarchInterimRemainCheck(false, false, false, false, false, false, false);
		//暫定管理データをメモリ上で作成する
		InterimRemainCreateDataInputPara dataCreate = new InterimRemainCreateDataInputPara(inputParam.getCid(),
				inputParam.getSid(),
				inputParam.getRegisterDate(),
				inputParam.getRecordData(),
				inputParam.getScheData(),
				inputParam.getAppData(),
				false);
		Optional<ComSubstVacation> comSetting = subRepos.findById(inputParam.getCid());
		CompensatoryLeaveComSetting leaveComSetting = leaveSetRepos.find(inputParam.getCid());
		CompanyHolidayMngSetting comHolidaySetting = new CompanyHolidayMngSetting(inputParam.getCid(), comSetting, leaveComSetting);
		Map<GeneralDate, DailyInterimRemainMngData> mapDataOutput = interimCreateData.createInterimRemainDataMng(dataCreate, comHolidaySetting);
		List<InterimRemain> interimMngAbsRec = new ArrayList<>();
		List<InterimAbsMng> useAbsMng = new ArrayList<>();
		List<InterimRecMng> useRecMng = new ArrayList<>();
		List<InterimRemain> interimMngBreakDayOff = new ArrayList<>();
		List<InterimBreakMng> breakMng = new ArrayList<>();
		List<InterimDayOffMng> dayOffMng = new ArrayList<>();
		List<InterimSpecialHolidayMng> specialHolidayData = new ArrayList<>();
		List<InterimRemain> interimSpecial = new ArrayList<>();
		List<TmpAnnualHolidayMng> annualHolidayData = new ArrayList<>();
		List<InterimRemain> annualMng = new ArrayList<>();
		List<TmpResereLeaveMng> resereLeaveData = new ArrayList<>();
		List<InterimRemain> resereMng = new ArrayList<>();
		mapDataOutput.forEach((x,y) -> {
			//積立年休
			y.getResereData().ifPresent(z -> {
				resereLeaveData.add(z);
				List<InterimRemain> lstTmp = y.getRecAbsData().stream().filter(w -> w.getRemainManaID().equals(z.getResereId()))
						.collect(Collectors.toList());
				for (InterimRemain mngData : lstTmp) {
					resereMng.add(mngData);
				}
				
			});
			//年休
			y.getAnnualHolidayData().ifPresent(z -> {
				annualHolidayData.add(z);
				List<InterimRemain> lstTmp = y.getRecAbsData().stream().filter(w -> w.getRemainManaID().equals(z.getAnnualId()))
						.collect(Collectors.toList());
				for (InterimRemain mngData : lstTmp) {
					annualMng.add(mngData);
				}
			});
			//特別休暇
			y.getSpecialHolidayData().stream().forEach(a -> {
				specialHolidayData.add(a);
				List<InterimRemain> interimMngSpe = y.getRecAbsData().stream()
						.filter(b -> b.getRemainManaID().equals(a.getSpecialHolidayId()))
						.collect(Collectors.toList());
				if(!interimMngSpe.isEmpty()) {
					interimSpecial.addAll(y.getRecAbsData());
				}
			});
			
			//休出代休
			y.getBreakData().ifPresent(z -> {
				breakMng.add(z);
				List<InterimRemain> lstTmp = y.getRecAbsData().stream().filter(a -> z.getBreakMngId().equals(a.getRemainManaID()))
				.collect(Collectors.toList());
				interimMngBreakDayOff.addAll(lstTmp);
			});
			y.getDayOffData().ifPresent(a -> {
				dayOffMng.add(a);
				List<InterimRemain> lstTmp = y.getRecAbsData().stream().filter(b -> b.getRemainManaID().equals(a.getDayOffManaId()))
						.collect(Collectors.toList());
				interimMngBreakDayOff.addAll(lstTmp);
			});
			//振出振休
			y.getRecData().ifPresent(b -> {
				useRecMng.add(b);
				List<InterimRemain> lstTmp = y.getRecAbsData().stream().filter(a -> b.getRecruitmentMngId().equals(a.getRemainManaID()))
						.collect(Collectors.toList());
				interimMngAbsRec.addAll(lstTmp);
			});
			y.getInterimAbsData().ifPresent(c -> {
				useAbsMng.add(c);
				List<InterimRemain> lstTmp = y.getRecAbsData().stream().filter(a -> c.getAbsenceMngId().equals(a.getRemainManaID()))
						.collect(Collectors.toList());
				interimMngAbsRec.addAll(lstTmp);
			});
		});
		//代休チェック区分をチェックする
		if(inputParam.isChkSubHoliday()) {
			
			//期間内の休出代休残数を取得する
			BreakDayOffRemainMngParam mngParam = new BreakDayOffRemainMngParam(inputParam.getCid(),
					inputParam.getSid(),
					inputParam.getDatePeriod(),
					inputParam.isMode(),
					inputParam.getBaseDate(),
					true,
					interimMngBreakDayOff, 
					breakMng, 
					dayOffMng);
			BreakDayOffRemainMngOfInPeriod remainMng = breakDayOffMngService.getBreakDayOffMngInPeriod(mngParam);
			if(remainMng.getRemainDays() < 0) {
				outputData.setChkSubHoliday(true);
			}
		}
		//振休不足区分をチェックする
		if(inputParam.isChkPause()) {
			//振出振休残数を取得する
			AbsRecMngInPeriodParamInput mngParam = new AbsRecMngInPeriodParamInput(inputParam.getCid(),
					inputParam.getSid(),
					inputParam.getDatePeriod(),
					inputParam.getBaseDate(),
					inputParam.isMode(),
					true,
					useAbsMng,
					interimMngAbsRec,
					useRecMng);
			AbsRecRemainMngOfInPeriod remainMng = absRecMngService.getAbsRecMngInPeriod(mngParam);
			if(remainMng.getRemainDays() < 0) {
				outputData.setChkPause(true);
			}
		}
		//特休チェック区分をチェックする
		if(inputParam.isChkSpecial()) {
			//暫定残数管理データ(output)に「特別休暇暫定データ」が存在するかチェックする
			specialHolidayData.stream().forEach(a -> {
				ComplileInPeriodOfSpecialLeaveParam speParam = new ComplileInPeriodOfSpecialLeaveParam(inputParam.getCid(),
						inputParam.getSid(),
						inputParam.getDatePeriod(),
						inputParam.isMode(),
						inputParam.getBaseDate(),
						a.getSpecialHolidayCode(),
						false,
						true,
						interimSpecial,
						specialHolidayData);
				InPeriodOfSpecialLeave speOutCheck = speLeaveSevice.complileInPeriodOfSpecialLeave(speParam);
				for (SpecialLeaveError speError : speOutCheck.getLstError()) {
					if(speError == SpecialLeaveError.AFTERGRANT
							|| speError == SpecialLeaveError.BEFOREGRANT) {
						outputData.setChkSpecial(true);
						break;
					}
				}
				
			});
		}
		//年休チェック区分をチェックする
		List<TmpAnnualLeaveMngWork> mngWork = new ArrayList<>();
		if(inputParam.isChkAnnual()) {
			mngWork = annualHolidayData.stream()
					.map(o -> {
						GeneralDate annalDate = annualMng.stream().filter(a -> a.getRemainManaID() == o.getAnnualId())
								.collect(Collectors.toList()).get(0).getYmd();
						return TmpAnnualLeaveMngWork.of(o.getAnnualId(), annalDate, o.getWorkTypeCode(), o.getUseDays());
					}).collect(Collectors.toList());
			List<AnnualLeaveErrorSharedImport> lstError = annualService.annualLeaveErrors(inputParam.getCid(),
					inputParam.getSid(),
					inputParam.getDatePeriod(),
					inputParam.isMode(),
					inputParam.getBaseDate(),
					false,
					false, Optional.of(true),
					Optional.of(mngWork),
					Optional.empty());
			for (AnnualLeaveErrorSharedImport errorcheck : lstError) {
				if(errorcheck == AnnualLeaveErrorSharedImport.SHORTAGE_AL_OF_UNIT_DAY_AFT_GRANT
						|| errorcheck == AnnualLeaveErrorSharedImport.SHORTAGE_AL_OF_UNIT_DAY_BFR_GRANT
						|| errorcheck == AnnualLeaveErrorSharedImport.SHORTAGE_TIMEAL_AFTER_GRANT
						|| errorcheck == AnnualLeaveErrorSharedImport.SHORTAGE_TIMEAL_BEFORE_GRANT) {
					outputData.setChkAnnual(true);
					break;
				}
			}
		}
		//期間中の年休積休残数を取得
		if(inputParam.isChkFundingAnnual()) {
			List<TmpReserveLeaveMngWork> lstReserve = resereLeaveData.stream()
					.map(l -> {
						GeneralDate annalDate = resereMng.stream().filter(a -> a.getRemainManaID() == l.getResereId())
								.collect(Collectors.toList()).get(0).getYmd();
						return TmpReserveLeaveMngWork.of(l.getResereId(), annalDate, l.getUseDays());
					}).collect(Collectors.toList());
			List<ReserveLeaveErrorImport> reserveLeaveErrors = annualService.reserveLeaveErrors(inputParam.getCid(),
					inputParam.getSid(),
					inputParam.getDatePeriod(), 
					inputParam.isMode(), 
					inputParam.getBaseDate(), 
					false, false, Optional.of(true), 
					Optional.of(mngWork), 
					Optional.of(lstReserve), Optional.empty(),
					Optional.empty());
			for (ReserveLeaveErrorImport errorCheck : reserveLeaveErrors) {
				if(errorCheck == ReserveLeaveErrorImport.SHORTAGE_RSVLEA_AFTER_GRANT
						|| errorCheck == ReserveLeaveErrorImport.SHORTAGE_RSVLEA_BEFORE_GRANT) {
					outputData.setChkFundingAnnual(true);
					break;
				}
			}
		}

		return outputData;
	}

}
