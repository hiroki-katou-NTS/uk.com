package nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.EmploymentRegulationHistory;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.dto.RegulationHistoryDto;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
@Stateless
public class EmploymentRegulationHistoryService {
	
	@Inject
	private EmploymentRegulationHistoryRepository repo;
	
	//最新履歴の履歴IDの取得
	public Optional<RegulationHistoryDto> getLatestEmpRegulationHist(String cId){
		return repo.getLatestEmpRegulationHist(cId);
	};
	
	//就業規則の履歴の取得
	public List<DateHistoryItem> getEmpRegulationHistList(String cId){
		return repo.getEmpRegulationHistList(cId);
	};

	//就業規則の履歴の追加
	public String addEmpRegulationHist(String cId, GeneralDate startDate) {
		Optional<EmploymentRegulationHistory> his = repo.get(cId);
		String newHistoryId = IdentifierUtil.randomUniqueId(); 
		DatePeriod period = new DatePeriod(startDate, GeneralDate.max());
		DateHistoryItem dateHistoryItem = new DateHistoryItem(newHistoryId, period);
		if(his.isPresent()){
			//UpdateEndDateItemBefore
			DateHistoryItem itemUpdateBefore = his.get().getDateHistoryItem().stream().max((d1, d2) -> d1.start().compareTo(d2.start())).get();
			//checks validate
			his.get().add(dateHistoryItem);
			repo.updateEmpRegulationHist(cId, itemUpdateBefore.identifier(), itemUpdateBefore.start(), itemUpdateBefore.end());
		}
		return repo.addEmpRegulationHist(cId, startDate);
	};
	
	//就業規則の履歴の更新
	public void updateEmpRegulationHist(String cId, String historyId, GeneralDate startDate) {
		Optional<EmploymentRegulationHistory> his = repo.get(cId);
		if(!his.isPresent() || his.get().getDateHistoryItem().isEmpty()) {
			return;
		}
		Optional<DateHistoryItem> dateHistoryItem = his.get().getDateHistoryItem().stream().filter(c->c.identifier().equals(historyId)).findFirst();
		if(!dateHistoryItem.isPresent()) {
			return;
		}
		//checks validate
		his.get().changeSpan(dateHistoryItem.get(), new DatePeriod(startDate, GeneralDate.max()));
		
		if(his.get().getDateHistoryItem().size() > 1) {
			//UpdateEndDateItemBefore
			DateHistoryItem itemUpdateBefore = his.get().getDateHistoryItem().stream().sorted((x, y) -> x.start().compareTo(y.start())*(-1)).collect(Collectors.toList()).get(1);
			repo.updateEmpRegulationHist(cId, itemUpdateBefore.identifier(), itemUpdateBefore.start(), itemUpdateBefore.end());
		}
		
		repo.updateEmpRegulationHist(cId, dateHistoryItem.get().identifier(), dateHistoryItem.get().start(), dateHistoryItem.get().end());
	};
	
	//就業規則の履歴の削除
	public void removeEmpRegulationHist(String cId, String historyId) {
		Optional<EmploymentRegulationHistory> his = repo.get(cId);
		if(!his.isPresent() || his.get().getDateHistoryItem().isEmpty()) {
			return;
		}
		//checks validate
		his.get().remove(his.get().getDateHistoryItem().stream().filter(x -> x.identifier().equals(historyId)).findFirst().get());
		
		//remove
		repo.removeEmpRegulationHist(cId, historyId);
		
		//UpdateEndDateItemBefore
		if(!his.get().getDateHistoryItem().isEmpty()) {
			//UpdateEndDateItemBefore
			DateHistoryItem itemUpdateBefore = his.get().getDateHistoryItem().stream().max((d1, d2) -> d1.start().compareTo(d2.start())).get();
			repo.updateEmpRegulationHist(cId, itemUpdateBefore.identifier(), itemUpdateBefore.start(), itemUpdateBefore.end());
		}
		 
	};
}
