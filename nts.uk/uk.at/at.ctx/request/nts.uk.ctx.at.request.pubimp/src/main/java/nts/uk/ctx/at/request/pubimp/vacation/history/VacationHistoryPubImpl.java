package nts.uk.ctx.at.request.pubimp.vacation.history;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.settting.worktype.history.PlanVacationHistory;
import nts.uk.ctx.at.request.dom.settting.worktype.history.VacationHistoryRepository;
import nts.uk.ctx.at.request.pub.vacation.history.export.HistoryExport;
import nts.uk.ctx.at.request.pub.vacation.history.export.MaxDayExport;
import nts.uk.ctx.at.request.pub.vacation.history.export.VacationHistoryExport;
import nts.uk.ctx.at.request.pub.vacation.history.export.VacationHistoryPub;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class VacationHistoryPubImpl.
 */
public class VacationHistoryPubImpl implements VacationHistoryPub{
	
	/** The history repository. */
	@Inject
	private VacationHistoryRepository historyRepository;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.pub.vacation.history.export.VacationHistoryPub#getVacationHistory(java.lang.String)
	 */
	@Override
	public List<VacationHistoryExport> getVacationHistory(String workTypeCode){
		// Get companyId;
		String companyId = AppContexts.user().companyId();

		// Get history list
		List<PlanVacationHistory> historyList = this.historyRepository.findByWorkTypeCode(companyId, workTypeCode);

		// convert to Dto
		return this.toDto(historyList);
	}
	
	/**
	 * To dto.
	 *
	 * @param historyList the history list
	 * @return the list
	 */
	//Convert to Dto
	public List<VacationHistoryExport> toDto(List<PlanVacationHistory> historyList) {
		return historyList.stream().map(item -> {
			VacationHistoryExport dto = new VacationHistoryExport();
			dto.setCompanyId(item.getCompanyId());
			dto.setWorkTypeCode(item.getWorkTypeCode());
			dto.setMaxDay(item.getMaxDay().v());
			
			HistoryExport hist = new HistoryExport();
			hist.setHistoryId(item.identifier());
			hist.setStartDate(item.span().start());
			hist.setEndDate(item.span().end());
			
			dto.getHistoryList().add(hist);
			
			return dto;
		}).collect(Collectors.toList());
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.pub.vacation.history.export.VacationHistoryPub#getMaxDay(java.lang.String)
	 */
	//Get MaxDay
	public MaxDayExport getMaxDay(String historyId){
		// Get companyId;
		String companyId = AppContexts.user().companyId();
		
		List<PlanVacationHistory> history = this.historyRepository.findHistory(companyId, historyId);
		
		MaxDayExport maxDay = new MaxDayExport();
		
		maxDay.setCompanyId(companyId);
		maxDay.setHistoryId(historyId);
		maxDay.setMaxDay(history.get(0).getMaxDay().v());
		maxDay.setWorkTypeCode(history.get(0).getWorkTypeCode());
		
		return maxDay;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.pub.vacation.history.export.VacationHistoryPub#getPlanVacationHistory(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<VacationHistoryExport> getPlanVacationHistory(String companyId, GeneralDate baseDate){

		// Get history list
		List<PlanVacationHistory> historyList = this.historyRepository.findHistoryByBaseDate(companyId, baseDate);

		// convert to Dto
		return this.toDto(historyList);
	}
}
