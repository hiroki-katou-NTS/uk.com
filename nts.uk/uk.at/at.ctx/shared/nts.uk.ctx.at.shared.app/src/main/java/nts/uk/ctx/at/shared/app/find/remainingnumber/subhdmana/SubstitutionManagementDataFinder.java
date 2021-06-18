package nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.ExtraHolidayManagementDataDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.SubDataSearchConditionDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.SubstituteDataManagementDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.DisplayRemainingNumberDataInformation;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementDataDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service.ExtraHolidayManagementOutput;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service.ExtraHolidayManagementService;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service.SubstituteManagementOutput;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service.SubstitutionManagementService;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SubstitutionManagementDataFinder {
	
	@Inject
	private SubstitutionManagementService substitutionManagementService;
	
	@Inject
	private ExtraHolidayManagementService extraHolidayManagementService;
	@Inject
	private ClosureRepository closureRepo;
	public SubstituteDataManagementDto getSubstituteManagementData(SubDataSearchConditionDto dto){
		SubstituteManagementOutput subDataOutput = substitutionManagementService.activationProcess(dto.getStartDate(), dto.getEndDate());
		return SubstituteDataManagementDto.convertToDto(subDataOutput);
	}
	
	public ExtraHolidayManagementDataDto getExtraHolidayManagementData(SubDataSearchConditionDto dto){
		ExtraHolidayManagementOutput extraHolidayOutput = extraHolidayManagementService.dataExtractionProcessing(dto.getSearchMode(), dto.getEmployeeId());
		return ExtraHolidayManagementDataDto.convertToDto(extraHolidayOutput);
	}
	
	public DisplayRemainingNumberDataInformation getExtraHolidayManagementDataUpdate(SubDataSearchConditionDto dto) {
		String cid = AppContexts.user().companyId();
		// メッセージ表示区分　＝　1: 表示する
		return extraHolidayManagementService.dataExtractionProcessingUpdate(cid, dto.getEmployeeId(), dto.getSearchMode(), 1);
	}
	
	public List<LeaveManagementDataDto> getLeaveManaDataByIdAndUnUse(String sid, int closureId) {
		String cid = AppContexts.user().companyId();
		Optional<Closure> optClosure = closureRepo.findById(AppContexts.user().companyId(), closureId);
		Closure closure = optClosure.get();

		// Get Processing Ym 処理年月
		YearMonth processingYm = closure.getClosureMonth().getProcessingYm();

		DatePeriod closurePeriod = ClosureService.getClosurePeriod(closureId, processingYm, optClosure);
		return this.extraHolidayManagementService.getLeaveManaDataByIdAndUnUse(cid, sid, closurePeriod.start(), 0.5);
	}
}
