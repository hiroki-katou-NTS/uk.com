package nts.uk.ctx.bs.employee.infra.repository.employment.history;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItem;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItemRepository;
import nts.uk.ctx.bs.employee.infra.entity.employment.history.BsymtEmploymentHistItem;

@Stateless
public class JpaEmploymentHistoryItemRepository extends JpaRepository implements EmploymentHistoryItemRepository{

	/**
	 * Convert from domain to entity
	 * @param domain
	 * @return
	 */
	private BsymtEmploymentHistItem toEntity(EmploymentHistoryItem domain){
		return new BsymtEmploymentHistItem(domain.getHistoryId(), domain.getEmployeeId(), domain.getEmploymentCode().v(), domain.getSalarySegment().value);
	}
	/**
	 * Update entity from domain
	 * @param domain
	 * @param entity
	 */
	private void updateEntity(EmploymentHistoryItem domain, BsymtEmploymentHistItem entity){
		entity.setEmpCode(domain.getEmploymentCode().v());
		entity.setSalarySegment(domain.getSalarySegment().value);
//		entity.setSid(domain.getEmployeeId());
	}
	@Override
	public void adḍ̣̣̣(EmploymentHistoryItem domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(EmploymentHistoryItem domain) {
		Optional<BsymtEmploymentHistItem> existItem = this.queryProxy().find(domain.getHistoryId(), BsymtEmploymentHistItem.class);
		if (!existItem.isPresent()){
			throw new RuntimeException("Invalid BsymtEmploymentHistItem");
		}
		updateEntity(domain, existItem.get());
		this.commandProxy().update(existItem.get());
	}

	@Override
	public void delete(String histId) {
		Optional<BsymtEmploymentHistItem> existItem = this.queryProxy().find(histId, BsymtEmploymentHistItem.class);
		if (!existItem.isPresent()){
			throw new RuntimeException("Invalid BsymtEmploymentHistItem");
		}
		this.commandProxy().remove(BsymtEmploymentHistItem.class, histId);
	}

}
