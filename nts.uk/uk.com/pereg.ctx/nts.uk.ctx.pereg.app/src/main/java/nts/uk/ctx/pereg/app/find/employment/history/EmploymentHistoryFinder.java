package nts.uk.ctx.pereg.app.find.employment.history;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistory;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItem;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

/**
 * @author sonnlb
 *
 */
@Stateless
public class EmploymentHistoryFinder implements PeregFinder<EmploymentHistoryDto> {

	@Inject
	private EmploymentHistoryItemRepository empHistItemRepo;

	@Inject
	private EmploymentHistoryRepository empHistRepo;

	@Override
	public String targetCategoryCode() {
		return "CS00014";
	}

	@Override
	public Class<EmploymentHistoryDto> dtoClass() {

		return EmploymentHistoryDto.class;

	}

	@Override
	public DataClassification dataType() {

		return DataClassification.EMPLOYEE;
	}

	@Override
	public EmploymentHistoryDto getSingleData(PeregQuery query) {
		Optional<DateHistoryItem> optHis = Optional.empty();
		if (query.getInfoId() != null) {
			optHis = this.empHistRepo.getByHistoryId(query.getInfoId());
		} else if (query.getStandardDate() != null){
			optHis = this.empHistRepo.getByEmployeeIdAndStandardDate(query.getEmployeeId(), query.getStandardDate());
		}
		if (optHis.isPresent()) {
			Optional<EmploymentHistoryItem> hisItemOpt = empHistItemRepo.getByHistoryId(optHis.get().identifier());
			if (hisItemOpt.isPresent()) {
				return EmploymentHistoryDto.createFromDomain(optHis.get(), hisItemOpt.get());
			}

		}
		return null;
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		Optional<EmploymentHistory> optHis = this.empHistRepo.getByEmployeeIdDesc(AppContexts.user().companyId(),
				query.getEmployeeId());
		if (optHis.isPresent()) {
			return optHis.get().getHistoryItems().stream()
					.map(x -> ComboBoxObject.toComboBoxObject(x.identifier(), x.start().toString(), 
							x.end().equals(GeneralDate.max()) 
							//&& query.getCtgType() == 3 
							? "" : x.end().toString()))
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@Override
	public List<EmploymentHistoryDto> getAllData(List<PeregQuery> query) {
		String cid = AppContexts.user().companyId();
		List<String> sids = query.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		GeneralDate standardDate  = query.size() > 0? query.get(0).getStandardDate() : GeneralDate.today();
		Map<String, DateHistoryItem> dateHistLst = this.empHistRepo.getByEmployeeIdAndStandardDate(cid, sids,standardDate);
		List<String> historyIds = dateHistLst.values().stream().map(c -> c.identifier()).collect(Collectors.toList());
		List<EmploymentHistoryItem> employmentHist = this.empHistItemRepo.getByListHistoryId(historyIds);
		List<EmploymentHistoryDto> result = employmentHist.stream().map( c -> {
			DateHistoryItem date =  dateHistLst.get(c.getEmployeeId());
			return EmploymentHistoryDto.createFromDomain(date, c );
		}).collect(Collectors.toList());
		if(query.size() > result.size()) {
			for(int i  = result.size(); i < query.size() ; i++) {
				result.add(new EmploymentHistoryDto("", null, null,
						null, null));
			}
		}
		return result;
	}

}
