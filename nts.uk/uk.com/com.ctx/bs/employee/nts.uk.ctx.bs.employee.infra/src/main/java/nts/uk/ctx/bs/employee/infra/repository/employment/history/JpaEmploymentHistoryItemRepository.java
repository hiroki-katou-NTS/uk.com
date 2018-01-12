package nts.uk.ctx.bs.employee.infra.repository.employment.history;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentInfo;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItem;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItemRepository;
import nts.uk.ctx.bs.employee.infra.entity.employment.history.BsymtEmploymentHistItem;
import nts.uk.ctx.bs.person.dom.person.common.ConstantUtils;

@Stateless
public class JpaEmploymentHistoryItemRepository extends JpaRepository implements EmploymentHistoryItemRepository {

	private static String SEL_HIS_ITEM = " SELECT a.bsymtEmploymentPK.code ,a.name FROM BsymtEmployment a"
			+ " INNER JOIN BsymtEmploymentHist h" 
			+ " ON a.bsymtEmploymentPK.cid = h.companyId"
			+ " INNER JOIN BsymtEmploymentHistItem i"
			+ " ON  h.hisId = i.hisId " 
			+ " AND h.sid  = i.sid"
			+ " AND a.bsymtEmploymentPK.code =  i.empCode" 
			+ " WHERE h.sid =:sid" + " AND h.strDate <= :date"
			+ " AND h.endDate >= :date " 
			+ " AND a.bsymtEmploymentPK.cid =:companyId";

	
	@Override
	public Optional<EmploymentInfo> getDetailEmploymentHistoryItem(String companyId, String sid, GeneralDate date) {
		Optional<EmploymentInfo> employee = this.queryProxy().query(SEL_HIS_ITEM, Object[].class)
				.setParameter("sid", sid).setParameter("date", date).setParameter("companyId", companyId)
				.getSingle(c -> toDomainEmployee(c));
		return employee;
	}
	
	@Override
	public Optional<EmploymentHistoryItem> getByHistoryId(String historyId) {
		Optional<BsymtEmploymentHistItem> hiDataOpt = this.queryProxy().find(historyId, BsymtEmploymentHistItem.class);
		if (hiDataOpt.isPresent()) {
			BsymtEmploymentHistItem ent = hiDataOpt.get();
			return Optional.of( EmploymentHistoryItem.createFromJavaType(ent.hisId, ent.sid, ent.empCode, ent.salarySegment));
		}
		return null;
	}

	/**
	 * Convert from domain to entity
	 * 
	 * @param domain
	 * @return
	 */
	private BsymtEmploymentHistItem toEntity(EmploymentHistoryItem domain) {
		return new BsymtEmploymentHistItem(domain.getHistoryId(), domain.getEmployeeId(),
				domain.getEmploymentCode().v(), domain.getSalarySegment() !=null? domain.getSalarySegment().value: 0);
	}

	private EmploymentInfo toDomainEmployee(Object[] entity) {
		EmploymentInfo emp = new EmploymentInfo();
		if (entity[0] != null) {
			emp.setEmploymentCode(entity[0].toString());
		}
		if (entity[1] != null) {
			emp.setEmploymentName(entity[1].toString());
		}
		return emp;
	}

	/**
	 * Update entity from domain
	 * 
	 * @param domain
	 * @param entity
	 */
	private void updateEntity(EmploymentHistoryItem domain, BsymtEmploymentHistItem entity) {
		if (domain.getEmploymentCode() != null){
			entity.empCode = domain.getEmploymentCode().v();
		}
		if (domain.getSalarySegment() != null){
			entity.salarySegment = domain.getSalarySegment().value;
		}
	}

	@Override
	public void add(EmploymentHistoryItem domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(EmploymentHistoryItem domain) {
		Optional<BsymtEmploymentHistItem> existItem = this.queryProxy().find(domain.getHistoryId(),
				BsymtEmploymentHistItem.class);
		if (!existItem.isPresent()) {
			throw new RuntimeException("Invalid BsymtEmploymentHistItem");
		}
		updateEntity(domain, existItem.get());
		this.commandProxy().update(existItem.get());
	}

	@Override
	public void delete(String histId) {
		Optional<BsymtEmploymentHistItem> existItem = this.queryProxy().find(histId, BsymtEmploymentHistItem.class);
		if (!existItem.isPresent()) {
			throw new RuntimeException("Invalid BsymtEmploymentHistItem");
		}
		this.commandProxy().remove(BsymtEmploymentHistItem.class, histId);
	}


}
