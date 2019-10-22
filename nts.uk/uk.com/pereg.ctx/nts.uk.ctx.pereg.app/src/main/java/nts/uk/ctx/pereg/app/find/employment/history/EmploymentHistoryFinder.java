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
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainBySidDto;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainDto;
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
	public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp query) {
		String cid = AppContexts.user().companyId();

		List<GridPeregDomainDto> result = new ArrayList<>();

		List<String> sids = query.getEmpInfos().stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());

		query.getEmpInfos().forEach(c -> {
			result.add(new GridPeregDomainDto(c.getEmployeeId(), c.getPersonId(), null));
		});

		Map<String, List<DateHistoryItem>> dateHistLst = this.empHistRepo
				.getByEmployeeIdAndStandardDate(cid, sids, query.getStandardDate()).stream()
				.collect(Collectors.groupingBy(c -> c.identifier()));

		List<String> historyIds = dateHistLst.values().stream().map(c -> c.get(0).identifier())
				.collect(Collectors.toList());

		Map<String, List<EmploymentHistoryItem>> employmentHist = this.empHistItemRepo.getByListHistoryId(historyIds)
				.stream().collect(Collectors.groupingBy(c -> c.getEmployeeId()));

		result.stream().forEach(c -> {
			List<EmploymentHistoryItem> histItem = employmentHist.get(c.getEmployeeId());
			if (histItem != null) {
				EmploymentHistoryItem employeeMent = histItem.get(0);
				DateHistoryItem date = dateHistLst.get(employeeMent.getHistoryId()).get(0);
				c.setPeregDomainDto(EmploymentHistoryDto.createFromDomain(date, employeeMent));
			}
		});

		return result;
	}

	@Override
	public List<GridPeregDomainBySidDto> getListData(PeregQueryByListEmp query) {
		String cid = AppContexts.user().companyId();

		List<GridPeregDomainBySidDto> result = new ArrayList<>();

		List<String> sids = query.getEmpInfos().stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());

		query.getEmpInfos().forEach(c -> {
			result.add(new GridPeregDomainBySidDto(c.getEmployeeId(), c.getPersonId(), new ArrayList<>()));
		});
		
		List<DateHistoryItem> histories = empHistRepo.getByEmployeeIdAndNoStandardDate(cid, sids);
		
		List<String> histIds = histories.stream().map(c -> c.identifier()).collect(Collectors.toList());
		
		List<Object[]> histItemsAndListEnum = empHistItemRepo.getByListHistoryIdForCPS013(histIds);
				
		result.stream().forEach(c -> {
			List<Object[]> listHistItem = histItemsAndListEnum.stream()
					.filter(emp -> ((EmploymentHistoryItem) emp[0]).getEmployeeId().equals(c.getEmployeeId())).collect(Collectors.toList());
			
			if (!listHistItem.isEmpty()) {
				List<PeregDomainDto> listPeregDomainDto = new ArrayList<>();
				listHistItem.forEach(h -> {
					EmploymentHistoryItem emptHisItem = (EmploymentHistoryItem) h[0];
					Map<String, Integer> mapListEnum =  (Map<String, Integer>) h[1];
					
					Optional<DateHistoryItem> dateHistoryItem = histories.stream().filter(i -> i.identifier().equals(emptHisItem.getHistoryId())).findFirst();
					if (dateHistoryItem.isPresent()) {
						listPeregDomainDto.add(EmploymentHistoryDto.createFromDomain(dateHistoryItem.get(), emptHisItem, mapListEnum.get("IS00069")));
					}
				});
				if (!listPeregDomainDto.isEmpty()) {
					c.setPeregDomainDto(listPeregDomainDto);
				}
			}
		});
		return result.stream().distinct().collect(Collectors.toList());
	}
}
