package nts.uk.ctx.at.record.dom.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnLeaRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TempAnnualLeaveMngMode;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.AggrResultOfReserveLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.ReserveLeaveError;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpReserveLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.AnnualLeaveErrorSharedImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.GetAnnLeaRemNumWithinPeriodSharedImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.ReserveLeaveErrorImport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class GetAnnLeaRemNumPeriodSharedImportImpl implements GetAnnLeaRemNumWithinPeriodSharedImport{
	@Inject
	private GetAnnLeaRemNumWithinPeriod annleaRemNumService;
	@Inject
	private GetAnnAndRsvRemNumWithinPeriod fundingAnnualService;
	@Override
	public List<AnnualLeaveErrorSharedImport> annualLeaveErrors(String companyId, String employeeId,
			DatePeriod aggrPeriod, boolean mode, GeneralDate criteriaDate, boolean isGetNextMonthData,
			boolean isCalcAttendanceRate, Optional<Boolean> isOverWrite,
			Optional<List<TmpAnnualLeaveMngWork>> forOverWriteList, Optional<Boolean> noCheckStartDate) {
		Optional<AggrResultOfAnnualLeave> outResult = annleaRemNumService.algorithm(companyId,
				employeeId, 
				aggrPeriod, 
				mode ? TempAnnualLeaveMngMode.MONTHLY : TempAnnualLeaveMngMode.OTHER,
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
		AggrResultOfAnnAndRsvLeave algorithm = fundingAnnualService.algorithm(companyId, 
				employeeId,
				aggrPeriod, 
				mode ? TempAnnualLeaveMngMode.MONTHLY : TempAnnualLeaveMngMode.OTHER, 
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
