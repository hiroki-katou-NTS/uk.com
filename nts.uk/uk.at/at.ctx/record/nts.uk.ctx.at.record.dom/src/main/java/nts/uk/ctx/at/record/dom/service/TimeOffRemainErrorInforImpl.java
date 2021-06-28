package nts.uk.ctx.at.record.dom.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.ComplileInPeriodOfSpecialLeaveParam;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.InPeriodOfSpecialLeaveResultInfor;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export.SpecialLeaveManagementService;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement.InterimRemainDataMngCheckRegister;
import nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement.RemainChildCareCheck;
import nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement.RemainChildCareCheckParam;
import nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement.RemainLongTermCareCheck;
import nts.uk.ctx.at.record.dom.workrecord.remainingnumbermanagement.RemainLongTermCareCheckParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.NumberCompensatoryLeavePeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimEachData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainCreateDataInputPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffPeriodCreateData;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.RecordRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.CreateInterimAnnualMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.NumberRemainVacationLeaveRangeProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByRecordData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export.pererror.CreatePerErrorsFromLeaveErrors;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.erroralarm.EmployeeMonthlyPerError;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Stateless
public class TimeOffRemainErrorInforImpl implements TimeOffRemainErrorInfor{
	@Inject
	private InterimRemainDataMngCheckRegister checkRegisterService;
	@Inject
	private SpecialHolidayRepository holidayRepo;
	@Inject
	private RecordDomRequireService requireService;
	@Inject
	private NumberRemainVacationLeaveRangeProcess numberRemainVacationLeaveRangeProcess;
	@Inject
	private RemainCreateInforByRecordData remainCreateInforByRecordData;
	@Inject
	private RemainChildCareCheck remainChildCareCheck;
	@Inject
	private RemainLongTermCareCheck remainLongTermCareCheck;
	@Override
	public List<EmployeeMonthlyPerError> getErrorInfor(TimeOffRemainErrorInputParam param) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();

		//残数作成元情報を作成する
		List<RecordRemainCreateInfor> recordInfor = remainCreateInforByRecordData.lstResultFromRecord(
				param.getSid(),
				DailyResultCreator.create(param.getLstWorkInfor(), param.getLstAttendanceTimeData()));
		//指定期間の暫定残数管理データを作成する（差分のみ）
		InterimRemainCreateDataInputPara createInterimDataParam = new InterimRemainCreateDataInputPara(param.getCid(),
				param.getSid(),
				param.getObjDate(),
				recordInfor,
				Collections.emptyList(),
				Collections.emptyList(),
				param.isUseDayoff());
		Map<GeneralDate, DailyInterimRemainMngData> interimRemainData = InterimRemainOffPeriodCreateData
				.createInterimRemainByScheRecordApp(require, cacheCarrier, createInterimDataParam);
		Optional<DailyInterimRemainMngData> optDaily = Optional.empty();

		/** 大塚モードかを確認する */
		if(param.getOptMonthlyData().isPresent() && AppContexts.optionLicense().customize().ootsuka()) {
			//月別実績(Work)から年休フレックス補填分の暫定年休管理データを作成する
			optDaily =  CreateInterimAnnualMngData.ofCompensFlex(param.getOptMonthlyData().get(), param.getObjDate().end());

		}
		InterimEachData eachData = checkRegisterService.interimInfor(interimRemainData);
		List<InterimRemain> interimMngAbsRec = eachData.getInterimMngAbsRec();
		List<InterimAbsMng> useAbsMng = eachData.getUseAbsMng();
		List<InterimRecMng> useRecMng = eachData.getUseRecMng();
		List<InterimRemain> interimMngBreakDayOff = eachData.getInterimMngBreakDayOff();
		List<InterimBreakMng> breakMng = eachData.getBreakMng();
		List<InterimDayOffMng> dayOffMng = eachData.getDayOffMng();
		List<InterimSpecialHolidayMng> specialHolidayData = eachData.getSpecialHolidayData();
		List<InterimRemain> interimSpecial = eachData.getInterimSpecial();
		List<TempAnnualLeaveMngs> annualHolidayData = eachData.getAnnualHolidayData();
		List<InterimRemain> annualMng = eachData.getAnnualMng();
		List<TempChildCareManagement> childCareData = eachData.getChildCareData();
        List<TempCareManagement> careData = eachData.getCareData();
		if(optDaily.isPresent()
				&& !optDaily.get().getAnnualHolidayData().isEmpty()) {
			TempAnnualLeaveMngs flexAnnual = optDaily.get().getAnnualHolidayData().stream().filter(c->c.getUsedNumber().isUseDay()).map(x-> x).findFirst().orElse(null);
			List<InterimRemain> lstAnnualMng = optDaily.get().getRecAbsData().stream()
					.filter(x -> x.getRemainManaID().equals(flexAnnual.getRemainManaID()))
					.collect(Collectors.toList());
			if(!lstAnnualMng.isEmpty()) {
				annualMng.add(lstAnnualMng.get(0));
				annualHolidayData.add(flexAnnual);
			}
		}
		List<TmpResereLeaveMng> resereLeaveData = eachData.getResereLeaveData();
		List<InterimRemain> resereMng = eachData.getResereMng();
		List<EmployeeMonthlyPerError> lstOuput = new ArrayList<>();
		//年休残数のチェック
		List<EmployeeMonthlyPerError> lstAnnualData = this.annualData(param, annualMng, annualHolidayData, resereMng, resereLeaveData);
		lstOuput.addAll(lstAnnualData);
		//特休残数のチェック
		List<EmployeeMonthlyPerError> lstSpeData = this.specialData(param, interimSpecial, specialHolidayData);
		lstOuput.addAll(lstSpeData);
		//振休残数のチェック
		List<EmployeeMonthlyPerError> lstAbsRec = this.absRecData(param, interimMngAbsRec, useAbsMng, useRecMng);
		lstOuput.addAll(lstAbsRec);
		//代休残数のチェック
		List<EmployeeMonthlyPerError> lstDayoff = this.dayoffData(param, interimMngBreakDayOff, breakMng, dayOffMng);
		lstOuput.addAll(lstDayoff);
		//INPUT.子の看護チェック区分＝true
		if (param.getChkChildNursing().isPresent() && param.getChkChildNursing().get()) {
		    RemainChildCareCheckParam remainChildCareCheckParam = new RemainChildCareCheckParam(
		            param.getCid(), 
		            param.getSid(), 
		            GeneralDate.max().yearMonth(), 
		            param.getAggDate(), 
		            ClosureId.RegularEmployee, 
		            new ClosureDate(1, false), 
		            false, 
		            true, 
		            childCareData, 
		            Optional.of(CreateAtr.RECORD), 
		            Optional.of(param.getObjDate()));
		    // 子の看護残数のチェック
		    remainChildCareCheck.checkRemainChildCare(remainChildCareCheckParam);
		}
		//INPUT.介護チェック区分＝true
		if (param.getChkLongTermCare().isPresent() && param.getChkLongTermCare().get()) {
		    RemainLongTermCareCheckParam remainLongTermCareCheckParam = new RemainLongTermCareCheckParam(
		            param.getCid(), 
                    param.getSid(), 
                    GeneralDate.max().yearMonth(), 
                    param.getAggDate(), 
                    ClosureId.RegularEmployee, 
                    new ClosureDate(1, false), 
                    false, 
                    true, 
		            careData, 
		            Optional.of(CreateAtr.RECORD), 
                    Optional.of(param.getObjDate()));
		    // 介護残数のチェック
		    remainLongTermCareCheck.checkRemainLongTermCare(remainLongTermCareCheckParam);
		}
		return lstOuput;
	}

	@Override
	public List<EmployeeMonthlyPerError> annualData(TimeOffRemainErrorInputParam param,
			List<InterimRemain> annualMng, List<TempAnnualLeaveMngs> annualHolidayData,
			List<InterimRemain> resereMng,List<TmpResereLeaveMng> resereLeaveData) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();

		List<TempAnnualLeaveMngs> mngWork = new ArrayList<>();
		mngWork = annualHolidayData;
		List<TmpResereLeaveMng> lstReserve = resereLeaveData;
		//期間中の年休積休残数を取得
		AggrResultOfAnnAndRsvLeave chkAnnaualAndResere = GetAnnAndRsvRemNumWithinPeriod.algorithm(
				require, cacheCarrier, param.getCid(),
				param.getSid(),
				param.getAggDate(),
				InterimRemainMngMode.OTHER,
				param.getObjDate().end(),
				false,
				false,
				Optional.of(true),
				Optional.of(mngWork),
				Optional.of(lstReserve),
				Optional.empty(),
				Optional.empty(),
				Optional.empty(),
				Optional.empty(),
				Optional.of(param.getObjDate()));
		List<EmployeeMonthlyPerError> outPutData = new ArrayList<>();
		if(chkAnnaualAndResere.getAnnualLeave().isPresent()
				&& !chkAnnaualAndResere.getAnnualLeave().get().getAnnualLeaveErrors().isEmpty()) {
			//年休エラーから月別実績エラー一覧を作成する
			List<EmployeeMonthlyPerError> annualLeave = CreatePerErrorsFromLeaveErrors.fromAnnualLeave(param.getSid(),
					YearMonth.of(999912),
					ClosureId.RegularEmployee,
					new ClosureDate(1, false),
					chkAnnaualAndResere.getAnnualLeave().get().getAnnualLeaveErrors());
			outPutData.addAll(annualLeave);
		}
		if(chkAnnaualAndResere.getReserveLeave().isPresent()
				&& !chkAnnaualAndResere.getReserveLeave().get().getReserveLeaveErrors().isEmpty()) {
			//積立年休エラーから月別実績エラー一覧を作成する
			List<EmployeeMonthlyPerError> reserveLeave = CreatePerErrorsFromLeaveErrors.fromReserveLeave(param.getSid(), YearMonth.of(999912),
					ClosureId.RegularEmployee,
					new ClosureDate(1, false),
					chkAnnaualAndResere.getReserveLeave().get().getReserveLeaveErrors());
			outPutData.addAll(reserveLeave);
		}

		return outPutData;
	}

	@Override
	public List<EmployeeMonthlyPerError> specialData(TimeOffRemainErrorInputParam param,
			List<InterimRemain> interimSpecial, List<InterimSpecialHolidayMng> specialHolidayData) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();

		//特別休暇暫定データに、親ドメインの情報を更新する。　※暫定データの作成処理がまだ対応中のため、親ドメインと子ドメインが別々になっているので。

		//○ドメインモデル「特別休暇」を取得する
		List<SpecialHoliday> lstSpecial = holidayRepo.findByCompanyId(param.getCid());
		List<EmployeeMonthlyPerError> lstOutput = new ArrayList<>();
		lstSpecial.stream().forEach(x -> {
			ComplileInPeriodOfSpecialLeaveParam speParam
				= new ComplileInPeriodOfSpecialLeaveParam(
					param.getCid(),
					param.getSid(),
					param.getAggDate(),
					false,
					param.getObjDate().end(),
					x.getSpecialHolidayCode().v(),
					false,
					true,
					specialHolidayData,
					Optional.of(param.getObjDate())
					);
			//マイナスなしを含めた期間内の特別休暇残を集計する
			InPeriodOfSpecialLeaveResultInfor speLeaveInfor = SpecialLeaveManagementService
					.complileInPeriodOfSpecialLeave(require, cacheCarrier, speParam);

			//特別休暇エラーから月別実績エラー一覧を作成する
			List<EmployeeMonthlyPerError> lstSpeError
				= CreatePerErrorsFromLeaveErrors.fromSpecialLeave(
						param.getSid(),
						YearMonth.of(999912),
						ClosureId.RegularEmployee,
						new ClosureDate(1, false),
						x.getSpecialHolidayCode().v(),
						speLeaveInfor.getErrorlistSharedClass());
			lstOutput.addAll(lstSpeError);
		});
		return lstOutput;
	}

	@Override
	public List<EmployeeMonthlyPerError> absRecData(TimeOffRemainErrorInputParam param,
			List<InterimRemain> interimMngAbsRec, List<InterimAbsMng> useAbsMng, List<InterimRecMng> useRecMng) {
		val require = requireService.createRequire();

		// 期間内の振出振休残数を取得する
		val inputParam = new AbsRecMngInPeriodRefactParamInput(param.getCid(),
				param.getSid(),
				param.getAggDate(),
				param.getObjDate().end(),
				false,
				true,
				useAbsMng,
				interimMngAbsRec,
				useRecMng,
				Optional.empty(),
				Optional.of(CreateAtr.RECORD),
				Optional.of(param.getObjDate()),
				new FixedManagementDataMonth());
		val absRecCheck = NumberCompensatoryLeavePeriodQuery.process(require, inputParam);
		List<EmployeeMonthlyPerError> lstAbsRec = CreatePerErrorsFromLeaveErrors.fromPause(param.getSid(),
					YearMonth.of(999912),
					ClosureId.RegularEmployee,
					new ClosureDate(1, false),
					absRecCheck.getPError());
		return lstAbsRec;
	}

	@Override
	public List<EmployeeMonthlyPerError> dayoffData(TimeOffRemainErrorInputParam param,
			List<InterimRemain> interimMngBreakDayOff, List<InterimBreakMng> breakMng,
			List<InterimDayOffMng> dayOffMng) {

		//期間内の休出代休残数を取得する
		BreakDayOffRemainMngRefactParam inputParamBreak =  new BreakDayOffRemainMngRefactParam(
				param.getCid(), param.getSid(),
				param.getAggDate(),
				false,
				param.getObjDate().end(),
				true,
				interimMngBreakDayOff,
				Optional.of(CreateAtr.RECORD),
				Optional.of(param.getObjDate()),
				breakMng,
				dayOffMng,
				Optional.empty(), new FixedManagementDataMonth());
		val dataCheck = numberRemainVacationLeaveRangeProcess.getBreakDayOffMngInPeriod(inputParamBreak);
		List<EmployeeMonthlyPerError> lstDayoff = CreatePerErrorsFromLeaveErrors.fromDayOff(param.getSid(),
					YearMonth.of(999912),
					ClosureId.RegularEmployee,
					new ClosureDate(1, false),
					dataCheck.getDayOffErrors());
		return lstDayoff;
	}
}
