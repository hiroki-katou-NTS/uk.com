package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.algorithm.careerpathhistory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPathHistory;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPathHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class CareerPathHistService {

	@Inject
	private CareerPathHistoryRepository careerPathHistoryRepo;
	
	//キャリアパスの履歴の取得
	public List<DateHistoryItem> getCareerPathHistList(String cId) {
		Optional<CareerPathHistory> his = careerPathHistoryRepo.getCareerPathHist(cId);
		if(!his.isPresent()) {
			return new ArrayList<>();
		}
		return his.get().getCareerPathHistory().stream().sorted((x, y) -> x.start().compareTo(y.start())*(-1)).collect(Collectors.toList());
	}
	
	//開始日の取得
	public GeneralDate getCareerPathStartDate(String companyId, String hisId) {
		Optional<CareerPathHistory> his = careerPathHistoryRepo.getCareerPathHist(companyId);
		if(!his.isPresent()) {
			throw new BusinessException("Missing Data CareerPathHist");
		}
		return his.get().getCareerPathHistory().stream().filter(c -> c.identifier().equals(hisId)).findFirst().get().start();
	}
	
	//キャリアパスの履歴の追加
	@Transactional
	public String addCareerPathHist(GeneralDate startDate) {
		String cId = AppContexts.user().companyId();
		Optional<CareerPathHistory> his = careerPathHistoryRepo.getCareerPathHist(cId);
		String newHistoryId = IdentifierUtil.randomUniqueId(); 
		DatePeriod period = new DatePeriod(startDate, GeneralDate.max());
		DateHistoryItem dateHistoryItem = new DateHistoryItem(newHistoryId, period);
		if(his.isPresent()) {
			//UpdateEndDateItemBefore
			DateHistoryItem itemUpdateBefore = his.get().getCareerPathHistory().stream().sorted((x, y) -> x.start().compareTo(y.start())*(-1)).collect(Collectors.toList()).get(0);
			//checks validate
			his.get().add(dateHistoryItem);
			careerPathHistoryRepo.update(new CareerPathHistory(cId, new ArrayList<DateHistoryItem>(Arrays.asList(itemUpdateBefore))));
		}
		//insertCareerPathHist
		careerPathHistoryRepo.add(new CareerPathHistory(AppContexts.user().companyId(), new ArrayList<DateHistoryItem>(Arrays.asList(dateHistoryItem))));
		return newHistoryId;
	}
	
	//キャリアパスの履歴の更新
	@Transactional
	public void updateCareerPathHist(String hisId, GeneralDate startDate) { 
		String cId = AppContexts.user().companyId();
		Optional<CareerPathHistory> his = careerPathHistoryRepo.getCareerPathHist(cId);
		if(!his.isPresent() || his.get().getCareerPathHistory().isEmpty()) {
			return;
		}
		Optional<DateHistoryItem> dateHistoryItem = his.get().getCareerPathHistory().stream().filter(c->c.identifier().equals(hisId)).findFirst();
		if(!dateHistoryItem.isPresent()) {
			return;
		}
		
		//checks validate
		his.get().changeSpan(dateHistoryItem.get(), new DatePeriod(startDate, GeneralDate.max()));
		
		if(his.get().getCareerPathHistory().size() > 1) {
			//UpdateEndDateItemBefore
			DateHistoryItem itemUpdateBefore = his.get().getCareerPathHistory().stream().sorted((x, y) -> x.start().compareTo(y.start())*(-1)).collect(Collectors.toList()).get(1);
			careerPathHistoryRepo.update(new CareerPathHistory(cId, new ArrayList<DateHistoryItem>(Arrays.asList(itemUpdateBefore))));
		}
		//UpdateItem
		careerPathHistoryRepo.update(new CareerPathHistory(cId, new ArrayList<DateHistoryItem>(Arrays.asList(dateHistoryItem.get()))));
		
	}
	
	//キャリアパスの履歴の削除
	public void removeCareerPathHist(String hisId) {
		String cId = AppContexts.user().companyId();
		Optional<CareerPathHistory> his = careerPathHistoryRepo.getCareerPathHist(cId);
		if(!his.isPresent() || his.get().getCareerPathHistory().isEmpty()) {
			return;
		}
		//checks validate
		his.get().remove(his.get().getCareerPathHistory().stream().filter(x -> x.identifier().equals(hisId)).findFirst().get());
		
		//careerPathRepo.removeCareerPath(cId, hisId);
		careerPathHistoryRepo.delete(cId, hisId);
		
		//UpdateEndDateItemBefore
		if(!his.get().getCareerPathHistory().isEmpty()) {
			DateHistoryItem itemUpdateBefore = his.get().getCareerPathHistory().stream().sorted((x, y) -> x.start().compareTo(y.start())*(-1)).collect(Collectors.toList()).get(0);
			careerPathHistoryRepo.update(new CareerPathHistory(cId, new ArrayList<DateHistoryItem>(Arrays.asList(itemUpdateBefore))));
		}
		
	}
	
	//最新履歴の履歴IDの取得
	public String getLatestCareerPathHist(String cId) {
		return careerPathHistoryRepo.getLatestCareerPathHist(cId);
	}
}
