package nts.uk.ctx.at.record.app.find.dailyperformanceformat.businesstype;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployee;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeOfEmployeeRepository;
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
		// lịch sử liên tục nên 1 historyId chỉ nằm trong 1 khoảng startdate và enddate duy nhất.
		BusinessTypeOfEmployeeHistory bHistory = new BusinessTypeOfEmployeeHistory();
		Optional<BusinessTypeOfEmployeeHistory> optional = typeEmployeeOfHistoryRepos
				.findByHistoryId(query.getInfoId());

		if (optional.isPresent()) {
			bHistory = optional.get();

		} else {
			String sId = query.getEmployeeId();
			GeneralDate baseDate = query.getStandardDate();
			Optional<BusinessTypeOfEmployeeHistory> optionalHistory = typeEmployeeOfHistoryRepos
					.findByBaseDate(baseDate, sId);
			if (!optionalHistory.isPresent()) {
				return null;
			}
			bHistory = optional.get();

		}

		Optional<BusinessTypeOfEmployee> optionalType = typeOfEmployeeRepos.findByHistoryId(query.getInfoId());
		if (!optionalType.isPresent()) {
			return null;
		}
		BusinessTypeDto dto = new BusinessTypeDto(bHistory.getHistory().get(0).start(),
				bHistory.getHistory().get(0).end(), query.getInfoId());

		return dto;
	}


	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
