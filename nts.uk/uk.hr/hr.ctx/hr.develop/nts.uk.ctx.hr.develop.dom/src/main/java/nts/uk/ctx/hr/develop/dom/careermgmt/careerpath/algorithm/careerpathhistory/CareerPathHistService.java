package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.algorithm.careerpathhistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPathHistory;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerPathHistoryRepository;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.DateHistoryItem;

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
		return his.get().getCareerPathHistory().stream().sorted((x, y) -> x.getPeriod().start().compareTo(y.getPeriod().start())).collect(Collectors.toList());
	}
}
