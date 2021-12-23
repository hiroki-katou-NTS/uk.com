package nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.adapter.AcquireWorkLocationEmplAdapter;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;

@AllArgsConstructor
public class StampingAreaRestrictionRequireImpl implements StampingAreaRestriction.Require {

	private WorkLocationRepository repository;
	private AcquireWorkLocationEmplAdapter adapter;

	@Override
	public Optional<String> getWorkPlaceOfEmpl(String employeeID, GeneralDate date) {
		String workplaceId = this.adapter.getAffWkpHistItemByEmpDate(employeeID, date);
		return Optional.ofNullable(workplaceId);
	}

	@Override
	public List<WorkLocation> findByWorkPlace(String contractCode, String cid, String workPlaceId) {
		return this.repository.findByWorkPlace(contractCode, cid, workPlaceId);
	}

	@Override
	public List<WorkLocation> findAll(String contractCode) {
		return this.repository.findAll(contractCode);
	}
}
