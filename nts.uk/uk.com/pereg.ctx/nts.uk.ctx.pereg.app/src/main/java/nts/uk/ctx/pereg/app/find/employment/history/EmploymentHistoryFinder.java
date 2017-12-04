package nts.uk.ctx.pereg.app.find.employment.history;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistory;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryRepository;
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
	public PeregDomainDto getSingleData(PeregQuery query) {
		Optional<EmploymentHistory> optHis = this.empHistRepo.getByEmployeeId(query.getEmployeeId());

		if (!optHis.isPresent()) {
			return null;
		}
		return EmploymentHistoryDto.createFromDomain(optHis.get());

	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
