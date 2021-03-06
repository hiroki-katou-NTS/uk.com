package nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseRequireImplFactory;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.ComplileInPeriodOfSpecialLeaveParam;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.SpecialLeaveManagementService;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.NumberCompensatoryLeavePeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenLeaveAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ApplicationType;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.EarchInterimRemainCheck;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimEachData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainCheckInputParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainCreateDataInputPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffPeriodCreateData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RemainErrors;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RemainInputParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.NumberRemainVacationLeaveRangeProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveError;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.CompanyHolidayMngSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.AnnualLeaveErrorSharedImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.GetAnnLeaRemNumWithinPeriodSharedImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.ReserveLeaveErrorImport;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
//import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.ComplileInPeriodOfSpecialLeaveParam;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Stateless
public class InterimRemainDataMngCheckRegisterImpl implements InterimRemainDataMngCheckRegister {

	@Inject
	private GetAnnLeaRemNumWithinPeriodSharedImport annualService;
//	@Inject
//	private ComplileInPeriodOfSpecialLeaveSharedImport specialLeaveService;

	/** REQUIRE?????? */
	@Inject
	private RemainNumberTempRequireService requireService;
	
	@Inject
	private RecordDomRequireService recordDomrequireService;

	@Inject
	private NumberRemainVacationLeaveRangeProcess numberRemainVacationLeaveRangeProcess;
	
	@Inject
    private ChildCareNurseRequireImplFactory childCareNurseRequireImplFactory;
	
	@Inject
    private RecordDomRequireService recordDomRequireService;
	
	@Inject
	private RemainingNumberCheck remainingNumberCheck;
	
	@Inject
	private SpecialHolidayRepository specialHolidayRepository;
	
	@Inject
	private RemainChildCareCheck remainChildCareCheck;
	
	@Inject
	private RemainLongTermCareCheck remainLongTermCareCheck;

	@Override
	public EarchInterimRemainCheck checkRegister(InterimRemainCheckInputParam inputParam) {
		// ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????false?????????(?????????) 
		EarchInterimRemainCheck outputData = new EarchInterimRemainCheck(false, false, false, false, false, false,
				false, false, false);
		
		// ?????????????????????????????????????????????    #118506
		RemainNumberClassification remainNumberClassification = remainingNumberCheck.determineCheckRemain(inputParam.getCid(), inputParam.getWorkTypeCds(), inputParam.getTimeDigestionUsageInfor(), inputParam.getSid());
		
		// ???????????????????????????????????????????????????
		Map<GeneralDate, DailyInterimRemainMngData> mapDataOutput = new HashMap<>();

		val require = recordDomrequireService.createRequire();
		CacheCarrier cacheCarrier = new CacheCarrier();

		inputParam.getAppData().stream().forEach(x -> {
			DatePeriod dateData = inputParam.getRegisterDate();
			if (x.getAppType() == ApplicationType.COMPLEMENT_LEAVE_APPLICATION) {
				dateData = new DatePeriod(x.getAppDate(), x.getAppDate());
			}
			InterimRemainCreateDataInputPara dataCreate = new InterimRemainCreateDataInputPara(inputParam.getCid(),
					inputParam.getSid(), dateData, inputParam.getRecordData(), inputParam.getScheData(),
					inputParam.getAppData());

			//input?????????????????????????????????????????????????????????
			Map<GeneralDate, DailyInterimRemainMngData> mapDataOutputTmp = InterimRemainOffPeriodCreateData
					.createInterimRemainByScheRecordApp(require, cacheCarrier, dataCreate);
			
			// ????????????????????????????????????????????????????????????
			if (x.getAppType() == ApplicationType.COMPLEMENT_LEAVE_APPLICATION
					&& mapDataOutputTmp.containsKey(x.getAppDate())) {
				DailyInterimRemainMngData data = mapDataOutputTmp.get(x.getAppDate());
				if (data.getRecAbsData().isEmpty()) {
					String mngId = IdentifierUtil.randomUniqueId();
					InterimAbsMng furikyuSinsei = new InterimAbsMng(mngId, inputParam.getSid(), x.getAppDate(),
							CreateAtr.APPBEFORE, RemainType.PAUSE, new RequiredDay(0.0), new UnOffsetDay(0.0));
					data.getRecAbsData().add(furikyuSinsei);
					data.setInterimAbsData(Optional.of(furikyuSinsei));
					mapDataOutputTmp.remove(x.getAppDate());
					mapDataOutputTmp.put(x.getAppDate(), data);
				}
			}
			mapDataOutputTmp.forEach((z, y) -> {
				if (!mapDataOutput.containsKey(z)) {
					mapDataOutput.put(z, y);
				}
			});

		});
		InterimEachData eachData = this.interimInfor(mapDataOutput);
		RemainInputParam inputParamForCheck = new RemainInputParam(inputParam.getCid(), inputParam.getSid(),
				inputParam.getDatePeriod(), inputParam.isMode(), inputParam.getBaseDate(), inputParam.getRegisterDate(),
				Optional.of(inputParam.getAppData().get(0).getPrePosAtr().value == 0 ? CreateAtr.APPBEFORE : CreateAtr.APPAFTER));
		RemainErrors errorCheck =  getError(inputParamForCheck, eachData, Optional.of(remainNumberClassification));
		// ?????????????????????????????????????????????
		if (!errorCheck.getDayOffErrors().isEmpty()) {
			outputData.setChkSubHoliday(true);
		}
		// ???????????????????????????????????????
		if (!errorCheck.getPErrors().isEmpty()) {
				outputData.setChkPause(true);
		}
		// ?????????????????????????????????????????????
		if (!errorCheck.getSpecialLeaveErrors().stream().flatMap(x -> x.getRight().stream())
				.collect(Collectors.toList()).isEmpty()) {
			outputData.setChkSpecial(true);
		}
		
		// ?????????????????????????????????????????????
		if (!errorCheck.getAnnualErrors().isEmpty()) {
			outputData.setChkAnnual(true);
		}
			
		// ???????????????????????????????????????
		if (!errorCheck.getReserveLeaveErrors().isEmpty()) {
					outputData.setChkFundingAnnual(true);
		}
		// ???????????????????????????????????????????????????
		    if (!errorCheck.getChildCareErrors().isEmpty()) {
		        outputData.setChkChildNursing(true);
		}
		// ?????????????????????????????????????????????
		if (!errorCheck.getNurseErrors().isEmpty()) {
			outputData.setChkLongTermCare(true);
		}

		return outputData;
	}

	@Override
	public InterimEachData interimInfor(Map<GeneralDate, DailyInterimRemainMngData> mapDataOutput) {
		/**
		 * ????????????????????????????????????
		 */
		List<InterimRemain> interimMngAbsRec = new ArrayList<>();
		/**
		 * ????????????????????????????????????
		 */
		List<InterimRemain> interimMngBreakDayOff = new ArrayList<>();
		/**
		 * ?????????????????????????????????
		 */
		List<InterimRemain> interimSpecial = new ArrayList<>();
		/**
		 * ???????????????????????????
		 */
		List<InterimRemain> annualMng = new ArrayList<>();
		/**
		 * ?????????????????????????????????
		 */
		List<InterimRemain> resereMng = new ArrayList<>();
		/**
	     * ?????????????????????????????????
	     */
	    List<InterimRemain> childCareData = new ArrayList<>();
	    /**
	     * ???????????????????????????
	     */
	    List<InterimRemain> careData = new ArrayList<>();
		mapDataOutput.forEach((x, y) -> {
			// ????????????
			y.getResereData().ifPresent(z -> {
					resereMng.add(z);

			});
			// ??????
			y.getAnnualHolidayData().forEach(c->annualMng.add(c));
			// ????????????
			y.getSpecialHolidayData().forEach(c->interimSpecial.add(c));
			// ??????
			y.getBreakData().ifPresent(z -> {
				interimMngBreakDayOff.add(z);
			});
			//??????
			interimMngBreakDayOff.addAll(y.getDayOffData());
			
			// ??????
			y.getRecData().ifPresent(b -> {
				interimMngAbsRec.add(b);
			});
			//??????
			y.getInterimAbsData().ifPresent(c -> {
				interimMngAbsRec.add(c);
			});
			y.getChildCareData().forEach(b -> childCareData.add(b));
			y.getCareData().forEach(b -> careData.add(b));
		});
		return new InterimEachData(interimMngAbsRec, interimMngBreakDayOff,
				interimSpecial, annualMng, resereMng, childCareData, careData);
	}

	@Override
	public RemainErrors getError(RemainInputParam inputParam, InterimEachData remainData, Optional<RemainNumberClassification> remainNumberClassification) {
		val require = requireService.createRequire();
		List<InterimRemain> interimMngAbsRec = remainData.getInterimMngAbsRec();
		List<InterimRemain> interimMngBreakDayOff = remainData.getInterimMngBreakDayOff();
		List<InterimRemain> interimSpecial = remainData.getInterimSpecial();
		List<InterimRemain> childCareData = remainData.getChildCareData();
		List<InterimRemain> careData = remainData.getCareData();
		RemainErrors outputData = new RemainErrors();
		CacheCarrier cache = new CacheCarrier();

		// ?????????????????????????????????????????????
		if (remainNumberClassification.map(x -> x.isChkSubHoliday()).orElse(true)) {

			// ?????????????????????????????????????????????
			BreakDayOffRemainMngRefactParam inputParamBreak =  new BreakDayOffRemainMngRefactParam(
					inputParam.getCid(), inputParam.getSid(),
					inputParam.getDatePeriod(),
					inputParam.isMode(),
					inputParam.getBaseDate(),
					true,
					interimMngBreakDayOff,
					Optional.of(CreateAtr.APPBEFORE),
					Optional.of(inputParam.getRegisterDate()),
					Optional.empty(), new FixedManagementDataMonth());
			val remainMng = numberRemainVacationLeaveRangeProcess.getBreakDayOffMngInPeriod(inputParamBreak);
			outputData.setDayOffErrors(remainMng.getDayOffErrors());
		}
		// ???????????????????????????????????????
		if (remainNumberClassification.map(x -> x.isChkPause()).orElse(true)) {
			// ?????????????????????????????????
			val mngParam = new AbsRecMngInPeriodRefactParamInput(inputParam.getCid(),
					inputParam.getSid(), inputParam.getDatePeriod(), inputParam.getBaseDate(), inputParam.isMode(),
					true, interimMngAbsRec,
					Optional.empty(),
					Optional.of(CreateAtr.APPBEFORE),
					Optional.of(inputParam.getRegisterDate()),
					new FixedManagementDataMonth());
			CompenLeaveAggrResult remainMng = NumberCompensatoryLeavePeriodQuery.process(require, mngParam);
			outputData.setPErrors(remainMng.getPError());
		}
		// ?????????????????????????????????????????????
		if (remainNumberClassification.map(x -> x.isChkSpecial()).orElse(true) && !interimSpecial.isEmpty()) {
			List<SpecialHoliday> lstSpecialHoliday = specialHolidayRepository.findByCompanyIdWithTargetItem(inputParam.getCid());
			// ???????????????????????????(output)????????????????????????????????????????????????????????????????????????
			 List<Pair<Integer, List<SpecialLeaveError>>> specialLeaveErrorAll = new ArrayList<>();
			for (SpecialHoliday a : lstSpecialHoliday) {
				List<InterimSpecialHolidayMng> specInterimRemain = interimSpecial.stream().map(x -> (InterimSpecialHolidayMng)x)
						.filter(x -> x.getSpecialHolidayCode() == a.getSpecialHolidayCode().v().intValue()).collect(Collectors.toList());
				val specialLeaveErrors = SpecialLeaveManagementService.complileInPeriodOfSpecialLeave(recordDomRequireService.createRequire(), cache, 
				        new ComplileInPeriodOfSpecialLeaveParam(
				                inputParam.getCid(), 
				                inputParam.getSid(), 
				                inputParam.getDatePeriod(), 
				                false, 
				                inputParam.getBaseDate(), 
				                a.getSpecialHolidayCode().v(), 
				                false, 
				                true, 
				                specInterimRemain, 
				                Optional.of(inputParam.getRegisterDate()))).getSpecialLeaveErrors();
				if(!specialLeaveErrors.isEmpty()) {
					specialLeaveErrorAll.add(Pair.of( a.getSpecialHolidayCode().v(), specialLeaveErrors));
				}
			}
			outputData.setSpecialLeaveErrors(specialLeaveErrorAll);
		}
		// ?????????????????????????????????????????????
		if (remainNumberClassification.map(x -> x.isChkAnnual()).orElse(true)) {
			List<TempAnnualLeaveMngs> mngWork = remainData.getAnnualMng().stream().map(x -> ((TempAnnualLeaveMngs)x)).collect(Collectors.toList());
			List<AnnualLeaveErrorSharedImport> lstError = annualService.annualLeaveErrors(inputParam.getCid(),
					inputParam.getSid(), inputParam.getDatePeriod(), inputParam.isMode(), inputParam.getBaseDate(),
					false, false, Optional.of(true), Optional.of(mngWork), Optional.empty(), inputParam.getRegisterDate());
			outputData.setAnnualErrors(lstError.stream().filter(errorcheck -> {
				if (errorcheck == AnnualLeaveErrorSharedImport.SHORTAGE_AL_OF_UNIT_DAY_AFT_GRANT
						|| errorcheck == AnnualLeaveErrorSharedImport.SHORTAGE_AL_OF_UNIT_DAY_BFR_GRANT
						|| errorcheck == AnnualLeaveErrorSharedImport.EXCESS_MAX_HALFDAY_AFTER_GRANT
						|| errorcheck == AnnualLeaveErrorSharedImport.EXCESS_MAX_HALFDAY_BEFORE_GRANT
						|| errorcheck == AnnualLeaveErrorSharedImport.EXCESS_MAX_TIMEAL_AFTER_GRANT
						|| errorcheck == AnnualLeaveErrorSharedImport.EXCESS_MAX_TIMEAL_BEFORE_GRANT) {
					return true;
				}else {
					return false;
				}
			}).collect(Collectors.toList()));
		 }
		// ???????????????????????????????????????
		if (remainNumberClassification.map(x -> x.isChkFundingAnnual()).orElse(true)) {
			List<TempAnnualLeaveMngs> mngWork = remainData.getAnnualMng().stream().map(x -> ((TempAnnualLeaveMngs)x)).collect(Collectors.toList());
			List<TmpResereLeaveMng> lstReserve = remainData.getResereMng().stream().map(x -> ((TmpResereLeaveMng)x)).collect(Collectors.toList());
			List<ReserveLeaveErrorImport> reserveLeaveErrors = annualService.reserveLeaveErrors(inputParam.getCid(),
					inputParam.getSid(), inputParam.getDatePeriod(), inputParam.isMode(), inputParam.getBaseDate(),
					false, false, Optional.of(true), Optional.of(mngWork), Optional.of(lstReserve), Optional.empty(),
					Optional.empty(), inputParam.getRegisterDate());
			outputData.setReserveLeaveErrors(reserveLeaveErrors.stream().filter(errorCheck -> {
				if (errorCheck == ReserveLeaveErrorImport.SHORTAGE_RSVLEA_AFTER_GRANT
						|| errorCheck == ReserveLeaveErrorImport.SHORTAGE_RSVLEA_BEFORE_GRANT) {
					return true;
				}else {
					return false;
				}
			}).collect(Collectors.toList()));
		}
		// ???????????????????????????????????????????????????
		if (remainNumberClassification.map(x -> x.isChkChildNursing()).orElse(true)) {
		    // [NO.206]?????????????????????????????????????????????
//		    AggrResultOfChildCareNurse resultChildcare =
//		            GetRemainingNumberChildCareService.getChildCareRemNumWithinPeriod(
//	                        inputParam.getCid(), 
//	                        inputParam.getSid(), 
//	                        inputParam.getDatePeriod(), 
//	                        InterimRemainMngMode.of(inputParam.isMode()), 
//	                        inputParam.getBaseDate(),
//	                        Optional.of(true), 
//	                        childCareData.stream().map(x -> ((TempChildCareManagement)x)).collect(Collectors.toList()),
//	                        Optional.empty(), 
//	                        inputParam.getCreateAtr(), 
//	                        Optional.of(inputParam.getRegisterDate()),
//	                        cache, 
//	                        childCareNurseRequireImplFactory.createRequireImpl());
		    RemainChildCareCheckParam param = new RemainChildCareCheckParam(
		                    inputParam.getCid(), 
                            inputParam.getSid(), 
                            YearMonth.of(9999, 12), 
                            inputParam.getDatePeriod(), 
                            ClosureId.RegularEmployee, 
                            new ClosureDate(1, false), 
                            inputParam.isMode(), 
                            true,
                            childCareData.stream().map(x -> ((TempChildCareManagement)x)).collect(Collectors.toList()), 
                            inputParam.getCreateAtr(), 
                            Optional.of(inputParam.getRegisterDate()));
		    
		        outputData.setChildCareErrors(remainChildCareCheck.checkRemainChildCare(param));
		}
		
		// ?????????????????????????????????????????????
		if (remainNumberClassification.map(x -> x.isChkLongTermCare()).orElse(true)) {
		    // [NO.207]???????????????????????????????????????
//		    AggrResultOfChildCareNurse resultNursing = GetRemainingNumberCareService.getCareRemNumWithinPeriod(
//		            inputParam.getCid(), 
//                    inputParam.getSid(), 
//                    inputParam.getDatePeriod(),
//                    InterimRemainMngMode.of(inputParam.isMode()), 
//                    inputParam.getBaseDate(),
//                    Optional.of(true), 
//	                careData.stream().map(x -> (TempCareManagement)x).collect(Collectors.toList()), 
//	                Optional.empty(),
//	                inputParam.getCreateAtr(), 
//	                Optional.of(inputParam.getRegisterDate()),
//                    cache, 
//                    childCareNurseRequireImplFactory.createRequireImpl());
		    RemainLongTermCareCheckParam param = new RemainLongTermCareCheckParam(
		            inputParam.getCid(), 
                    inputParam.getSid(), 
                    YearMonth.of(9999, 12), 
                    inputParam.getDatePeriod(), 
                    ClosureId.RegularEmployee, 
                    new ClosureDate(1, false), 
                    inputParam.isMode(), 
                    true,
                    careData.stream().map(x -> (TempCareManagement)x).collect(Collectors.toList()),
                    inputParam.getCreateAtr(), 
                    Optional.of(inputParam.getRegisterDate()));
		       outputData.setNurseErrors(remainLongTermCareCheck.checkRemainLongTermCare(param));
		}
		return outputData;
	}
	

}
