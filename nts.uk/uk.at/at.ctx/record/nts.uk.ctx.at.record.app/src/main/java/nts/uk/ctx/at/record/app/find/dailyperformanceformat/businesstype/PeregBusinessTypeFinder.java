package nts.uk.ctx.at.record.app.find.dailyperformanceformat.businesstype;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployee;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeOfEmployeeRepository;
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

@Stateless
public class PeregBusinessTypeFinder implements PeregFinder<BusinessTypeDto> {

	@Inject
	private BusinessTypeOfEmployeeRepository typeOfEmployeeRepos;

	@Inject
	private BusinessTypeEmpOfHistoryRepository typeEmployeeOfHistoryRepos;

	@Override
	public String targetCategoryCode() {
		return "CS00021";
	}

	@Override
	public Class<BusinessTypeDto> dtoClass() {
		return BusinessTypeDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public BusinessTypeDto getSingleData(PeregQuery query) {
		// lịch sử liên tục nên 1 historyId chỉ nằm trong 1 khoảng startdate và
		// enddate duy nhất.
		String sId = query.getEmployeeId();
		GeneralDate baseDate = query.getStandardDate();

		Optional<BusinessTypeOfEmployeeHistory> oneHitoryOpt;

		Optional<BusinessTypeOfEmployeeHistory> fullHistoryOpt = typeEmployeeOfHistoryRepos
				.findByEmployee(AppContexts.user().companyId(), sId);

		if (query.getInfoId() != null) {
			oneHitoryOpt = typeEmployeeOfHistoryRepos.findByHistoryId(query.getInfoId());
		} else {
			oneHitoryOpt = typeEmployeeOfHistoryRepos.findByBaseDate(baseDate, sId);
		}

		if (oneHitoryOpt.isPresent() && fullHistoryOpt.isPresent()) {
			DateHistoryItem dateHistoryItem = oneHitoryOpt.get().getHistory().get(0);
			DateHistoryItem latestItem = fullHistoryOpt.get().latestStartItem().get();
			boolean isLatestHistory = dateHistoryItem.identifier().equals(latestItem.identifier());

			Optional<BusinessTypeOfEmployee> optionalType = typeOfEmployeeRepos
					.findByHistoryId(dateHistoryItem.identifier());

			return new BusinessTypeDto(dateHistoryItem.identifier(), dateHistoryItem.start(), dateHistoryItem.end(),
					optionalType.get().getBusinessTypeCode().v(), isLatestHistory);
		}
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		Optional<BusinessTypeOfEmployeeHistory> optional = typeEmployeeOfHistoryRepos
				.findByEmployeeDesc(AppContexts.user().companyId(), query.getEmployeeId());
		if (optional.isPresent()) {
			return optional.get().getHistory().stream()
					.filter(x -> typeOfEmployeeRepos.findByHistoryId(x.identifier()).isPresent())
					.map(x -> ComboBoxObject.toComboBoxObject(x.identifier(), x.start().toString(), 
							x.end().equals(GeneralDate.max()) 
							//&& query.getCtgType() == 3 
							? "" : x.end().toString()))
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp query) {
		String cid = AppContexts.user().companyId();

		List<GridPeregDomainDto> result = new ArrayList<>();

		List<String> sids = query.getEmpInfos().stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());

		query.getEmpInfos().forEach(c -> {
			result.add(new GridPeregDomainDto(c.getEmployeeId(), c.getPersonId(), null));
		});

		List<DateHistoryItem> dateHistItems = typeEmployeeOfHistoryRepos.getDateHistItemByCidAndSidsAndBaseDate(cid,
				sids, query.getStandardDate());
		
		List<BusinessTypeOfEmployee> hisItemLst = typeOfEmployeeRepos
				.findAllByHistIds(dateHistItems.stream().map(c -> c.identifier()).collect(Collectors.toList()));

		result.stream().forEach(c -> {
			Optional<BusinessTypeOfEmployee> histItemOpt = hisItemLst.stream()
					.filter(emp -> emp.getSId().equals(c.getEmployeeId())).findFirst();
			if (histItemOpt.isPresent()) {
				Optional<DateHistoryItem> dateHistItemOpt = dateHistItems.stream()
						.filter(date -> date.identifier().equals(histItemOpt.get().getHistoryId())).findFirst();
				c.setPeregDomainDto(
						dateHistItemOpt.isPresent() == true
								? new BusinessTypeDto(dateHistItemOpt.get().identifier(), dateHistItemOpt.get().start(),
										dateHistItemOpt.get().end(), histItemOpt.get().getBusinessTypeCode().v())
								: null);
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
		
		List<DateHistoryItem> histories = typeEmployeeOfHistoryRepos.getListByListSidsNoWithPeriod(cid, sids);
		
		List<String> histIds = histories.stream().map(c -> c.identifier()).collect(Collectors.toList());
		
		List<BusinessTypeOfEmployee> histItems = typeOfEmployeeRepos.findAllByHistIds(histIds);
		
		result.stream().forEach(c -> {
			List<BusinessTypeOfEmployee> listHistItem = histItems.stream()
					.filter(emp -> emp.getSId().equals(c.getEmployeeId())).collect(Collectors.toList());
			
			if (!listHistItem.isEmpty()) {
				List<PeregDomainDto> listPeregDomainDto = new ArrayList<>();
				listHistItem.forEach(h -> {
					Optional<DateHistoryItem> dateHistoryItem = histories.stream().filter(i -> i.identifier().equals(h.getHistoryId())).findFirst();
					if (dateHistoryItem.isPresent()) {
						listPeregDomainDto.add(BusinessTypeDto.createFromDomain(h, dateHistoryItem.get()));
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
