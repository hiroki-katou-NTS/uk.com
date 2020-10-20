package nts.uk.ctx.at.record.dom.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.GetAnnLeaRemNumWithinPeriodProc;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
//import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfReserveLeave;
//import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpReserveLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.AnnualLeaveErrorSharedImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.GetAnnLeaRemNumWithinPeriodSharedImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.ReserveLeaveErrorImport;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.erroralarm.ReserveLeaveError;
@Stateless
public class GetAnnLeaRemNumPeriodSharedImportImpl implements GetAnnLeaRemNumWithinPeriodSharedImport{
	
	@Inject 
	private RecordDomRequireService requireService;
	
	@Override
	public List<AnnualLeaveErrorSharedImport> annualLeaveErrors(String companyId, String employeeId,
			DatePeriod aggrPeriod, boolean mode, GeneralDate criteriaDate, boolean isGetNextMonthData,
			boolean isCalcAttendanceRate, Optional<Boolean> isOverWrite,
			Optional<List<TmpAnnualLeaveMngWork>> forOverWriteList, Optional<Boolean> noCheckStartDate) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
	
		Optional<AggrResultOfAnnualLeave> outResult = GetAnnLeaRemNumWithinPeriodProc.algorithm(
				require, cacheCarrier, companyId,
				employeeId, 
				aggrPeriod, 
				mode ? InterimRemainMngMode.MONTHLY : InterimRemainMngMode.OTHER,
						criteriaDate, 
						isGetNextMonthData, 
						isCalcAttendanceRate,
						Optional.of(true),
						forOverWriteList, 
						Optional.empty(), 
						noCheckStartDate);
		if(!outResult.isPresent()) {
			return Collections.emptyList();
		}
		List<AnnualLeaveErrorSharedImport> outputData = new ArrayList<>();
		outResult.get().getAnnualLeaveErrors().stream().forEach(x -> {
			outputData.add(EnumAdaptor.valueOf(x.value, AnnualLeaveErrorSharedImport.class));
		});
				
		return outputData;
	}

	@Override
	public List<ReserveLeaveErrorImport> reserveLeaveErrors(String companyId, String employeeId, DatePeriod aggrPeriod,
			boolean mode, GeneralDate criteriaDate, boolean isGetNextMonthData, boolean isCalcAttendanceRate,
			Optional<Boolean> isOverWrite, Optional<List<TmpAnnualLeaveMngWork>> tempAnnDataforOverWriteList,
			Optional<List<TmpReserveLeaveMngWork>> tempRsvDataforOverWriteList, Optional<Boolean> isOutputForShortage,
			Optional<Boolean> noCheckStartDate) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
	
		AggrResultOfAnnAndRsvLeave algorithm = GetAnnAndRsvRemNumWithinPeriod.algorithm(
				require, cacheCarrier, companyId, 
				employeeId,
				aggrPeriod, 
				mode ? InterimRemainMngMode.MONTHLY : InterimRemainMngMode.OTHER, 
				criteriaDate, 
				isGetNextMonthData,
				isCalcAttendanceRate,
				Optional.of(true), 
				tempAnnDataforOverWriteList, 
				tempRsvDataforOverWriteList, 
				isOutputForShortage, 
				noCheckStartDate,
				Optional.empty(), Optional.empty());
		Optional<AggrResultOfReserveLeave> optResult = algorithm.getReserveLeave();
		if(!optResult.isPresent()) {
			return Collections.emptyList();
		}
		List<ReserveLeaveError> lstResult = optResult.get().getReserveLeaveErrors();
		
		return lstResult.stream().map(x -> EnumAdaptor.valueOf(x.value, ReserveLeaveErrorImport.class))
				.collect(Collectors.toList());
	}
}
