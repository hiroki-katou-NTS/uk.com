package nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployee;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployeeHistory;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
public class BusinessTypeEmpServiceImpl implements BusinessTypeEmpService {
	@Inject
	private BusinessTypeOfEmployeeRepository busTypeOfEmpRepo;

	@Inject
	private BusinessTypeEmpOfHistoryRepository busTypeEmpOfHisRepo;

	@Override
	public List<BusinessTypeOfEmployee> getData(String sId, GeneralDate baseDate) {
		List<BusinessTypeOfEmployee> bOfEmployees = new ArrayList<>();
		/* 対応するドメインモデル「社員の勤務種別の履歴」を取得する */
		Optional<BusinessTypeOfEmployeeHistory> optional = busTypeEmpOfHisRepo.findByBaseDate(baseDate, sId);
		if (!optional.isPresent()) {
			return bOfEmployees;
		}
		BusinessTypeOfEmployeeHistory bHistory = optional.get();
		/* 対応するドメインモデル「社員の勤務種別」を取得する */
		List<DateHistoryItem> dateHistoryItems = bHistory.getHistory();
		dateHistoryItems.stream().forEach(dateHistoryItem -> {
			Optional<BusinessTypeOfEmployee> optionalType = busTypeOfEmpRepo
					.findByHistoryId(dateHistoryItem.identifier());
			if (optionalType.isPresent()) {
				bOfEmployees.add(optionalType.get());
			}
		});
		return bOfEmployees;
	}

}
