package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.algorithm.careerpathhistory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPathHistory;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPathHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
		return his.get().getCareerPathHistory().stream().sorted((x, y) -> x.start().compareTo(y.start())).collect(Collectors.toList());
	}
	
	//キャリアパスの履歴の追加
	public String addCareerPathHist(GeneralDate startDate) {
		String hisId = IdentifierUtil.randomUniqueId(); 
		careerPathHistoryRepo.addCareerPathHist(new CareerPathHistory(AppContexts.user().companyId(), new ArrayList<DateHistoryItem>(Arrays.asList(new DateHistoryItem(hisId, new DatePeriod(startDate, GeneralDate.fromString("9999/12/31", "yyyy/MM/dd")))))));
		return hisId;
	}
	
	//キャリアパスの履歴の更新
	public void updateCareerPathHist(String hisId, GeneralDate startDate) { 
		careerPathHistoryRepo.updateCareerPathHist(new CareerPathHistory(AppContexts.user().companyId(), new ArrayList<DateHistoryItem>(Arrays.asList(new DateHistoryItem(hisId, new DatePeriod(startDate, GeneralDate.fromString("9999/12/31", "yyyy/MM/dd")))))));
	}
	
	//キャリアパスの履歴の削除
	public void removeCareerPathHist(String hisId) {
		careerPathHistoryRepo.removeCareerPathHist(AppContexts.user().companyId(), hisId);
	}
}
