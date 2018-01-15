package nts.uk.ctx.at.record.app.find.dailyperformanceformat.businesstype;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployee;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeOfEmployeeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
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
	public PeregDomainDto getSingleData(PeregQuery query) {
		// lịch sử liên tục nên 1 historyId chỉ nằm trong 1 khoảng startdate và
		// enddate duy nhất.
		String sId = query.getEmployeeId();
		GeneralDate baseDate = query.getStandardDate();
		Optional<BusinessTypeOfEmployeeHistory> optional;
		if (query.getInfoId() != null) {
			optional = typeEmployeeOfHistoryRepos.findByHistoryId(query.getInfoId());
		} else {
			optional = typeEmployeeOfHistoryRepos.findByBaseDate(baseDate, sId);
		}

		if (optional.isPresent()) {
			DateHistoryItem dateHistoryItem = optional.get().getHistory().get(0);
			Optional<BusinessTypeOfEmployee> optionalType = typeOfEmployeeRepos
					.findByHistoryId(dateHistoryItem.identifier());
			return new BusinessTypeDto(query.getInfoId(), dateHistoryItem.start(), dateHistoryItem.end(),
					optionalType.get().getBusinessTypeCode().v());
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
							x.end().equals(GeneralDate.max()) && query.getCtgType() == 3 ? "" : x.end().toString()))
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
