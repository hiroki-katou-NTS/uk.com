package nts.uk.ctx.at.record.app.find.monthly.nursingleave;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.monthly.nursingleave.dto.ChildCareNurseErrorsDto;
import nts.uk.ctx.at.record.app.find.monthly.nursingleave.dto.ChildCareNurseUsedNumberDto;
import nts.uk.ctx.at.record.app.find.monthly.nursingleave.dto.InterimRemainDto;
import nts.uk.ctx.at.record.app.find.monthly.nursingleave.dto.KDL051ProcessDto;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.GetRemainingNumberChildCareService;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;

public class ChildCareNusingLeaveFinder {
	
	@Inject
	private RecordDomRequireService requireService;
	
	@Inject
	private GetRemainingNumberChildCareService getChildSevice;
	
	@Inject
	private InterimRemainRepository interimRemainRepo;
	
	public KDL051ProcessDto changeEmployee(String eId) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		
		//	アルゴリズム「社員に対応する締め開始日を取得する」を実行する。
		val closureStartOpt = GetClosureStartForEmployee.algorithm(require, cacheCarrier, eId);
		
		DatePeriod datePeriod = new DatePeriod(closureStartOpt.get(), closureStartOpt.get().addYears(1));
		List<AggrResultOfChildCareNurse> resultOfChildCareNurse = getChildSevice.getChildCareRemNumWithinPeriod(
				eId, datePeriod, InterimRemainMngMode.OTHER, GeneralDate.today(), 
				Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty());
		
		List<InterimRemain> lstInterimRemain = interimRemainRepo.findByEmployeeID(eId);
		List<InterimRemainDto> lstInterimRemainResult = lstInterimRemain.stream().map(item -> InterimRemainDto.builder()
				.remainManaID(item.getRemainManaID())
				.sID(item.getSID())
				.ymd(item.getYmd().toString())
				.creatorAtr(item.getCreatorAtr().value)
				.remainType(item.getRemainType().value)
				.remainAtr(item.getRemainAtr().value)
				.build())
				.collect(Collectors.toList());
		List<ChildCareNurseErrorsDto> listEr = resultOfChildCareNurse.get(0).getChildCareNurseErrors().stream().map(item -> ChildCareNurseErrorsDto.builder()
				.usedNumber(ChildCareNurseUsedNumberDto.builder()
								.usedDay(item.getUsedNumber().getUsedDay().v())
								.usedTimes(item.getUsedNumber().getUsedTimes().get().v())
								.build())
				.limitDays(item.getLimitDays().v())
				.ymd(item.getYmd().toString())
				.build()).collect(Collectors.toList());
//		List<AggrResultOfChildCareNurseDto> listData = resultOfChildCareNurse.stream().map(item -> AggrResultOfChildCareNurseDto.builder()
//				.childCareNurseErrors(listEr)
//				.asOfPeriodEnd(item.ge)
//				)
		return null;
	}
}
