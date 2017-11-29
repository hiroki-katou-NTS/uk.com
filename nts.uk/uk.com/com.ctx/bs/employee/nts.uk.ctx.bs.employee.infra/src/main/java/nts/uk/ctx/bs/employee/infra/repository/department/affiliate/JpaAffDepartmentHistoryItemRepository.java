package nts.uk.ctx.bs.employee.infra.repository.department.affiliate;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.department.DepartmentCode;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryItem;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.department.affiliate.DistributionRatio;
import nts.uk.ctx.bs.employee.infra.entity.department.BsymtAffiDepartmentHistItem;

@Stateless
public class JpaAffDepartmentHistoryItemRepository extends JpaRepository implements AffDepartmentHistoryItemRepository{

	private static final String SELECT_BY_HISTID = "SELECT adh BsymtAffiDepartmentHistItem adh"
			+ " WHERE adh.hisId = :historyId";
	
	private AffDepartmentHistoryItem toDomain(BsymtAffiDepartmentHistItem entity){
		return AffDepartmentHistoryItem.createFromJavaType(entity.getHisId(), entity.getSid(), entity.getDepCode(), 
				entity.getAffHistTranfsType(), entity.getDistrRatio());				
	}
	
	/**
	 * Convert from domain to entity
	 * @param domain
	 * @return
	 */
	private BsymtAffiDepartmentHistItem toEntity(AffDepartmentHistoryItem domain){
		return new BsymtAffiDepartmentHistItem(domain.getHistoryId(),domain.getEmployeeId(),domain.getDepartmentCode().v(), domain.getAffHistoryTranfsType(),domain.getDepartmentCode().v());
	}
	
	private void updateEntity(AffDepartmentHistoryItem domain, BsymtAffiDepartmentHistItem entity){
		entity.setSid(domain.getEmployeeId());
		entity.setDepCode(domain.getDepartmentCode().v());
		entity.setAffHistTranfsType(domain.getAffHistoryTranfsType());
		entity.setDepCode(domain.getDistributionRatio().v());
	}
	
	
	@Override
	public void add(AffDepartmentHistoryItem domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(AffDepartmentHistoryItem domain) {
		Optional<BsymtAffiDepartmentHistItem> existItem = this.queryProxy().find(domain.getHistoryId(), BsymtAffiDepartmentHistItem.class);
		if (!existItem.isPresent()){
			throw new RuntimeException("invalid BsymtAffiDepartmentHistItem");
		}
		updateEntity(domain,existItem.get());
		
		this.commandProxy().update(existItem.get());
	}

	@Override
	public void delete(String histId) {
		Optional<BsymtAffiDepartmentHistItem> existItem = this.queryProxy().find(histId, BsymtAffiDepartmentHistItem.class);
		if (!existItem.isPresent()){
			throw new RuntimeException("invalid BsymtAffiDepartmentHistItem");
		}
		this.commandProxy().remove(BsymtAffiDepartmentHistItem.class, histId);
	}

	@Override
	public Optional<AffDepartmentHistoryItem> getByHistId(String historyId) {
		return this.queryProxy().query(SELECT_BY_HISTID, BsymtAffiDepartmentHistItem.class)
				.setParameter("historyId", historyId).getSingle(x->toDomain(x));
	}


}
