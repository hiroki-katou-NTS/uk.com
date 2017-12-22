/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.affiliate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository_v1;
import nts.uk.ctx.bs.employee.infra.entity.workplace.affiliate.BsymtAffiWorkplaceHistItem;

@Stateless
public class JpaAffWorkplaceHistoryItemRepository_v1 extends JpaRepository implements AffWorkplaceHistoryItemRepository_v1{
	
	private static final String SELECT_BY_HISTID = "SELECT aw FROM BsymtAffiWorkplaceHistItem aw"
			+ " WHERE aw.hisId = :historyId";
	
	/** The Constant SELECT_BY_HISTIDS. */
	private static final String SELECT_BY_HISTIDS = "SELECT aw FROM BsymtAffiWorkplaceHistItem aw"
			+ " WHERE aw.hisId IN :historyId";
	
	private static final String SELECT_BY_WPLIDS = "SELECT aw FROM BsymtAffiWorkplaceHistItem aw"
			+ " WHERE aw.workPlaceId IN :wplIds";
	/**
	 * Convert from entity to domain
	 * 
	 * @param entity
	 * @return
	 */
	private AffWorkplaceHistoryItem toDomain(BsymtAffiWorkplaceHistItem entity){
		return AffWorkplaceHistoryItem.createFromJavaType(entity.getHisId(), entity.getSid(), entity.getWorkPlaceId(), 
				entity.getNormalWkpId());
	}
	
	/**
	 * Convert from domain to entity
	 * 
	 * @param domain
	 * @return
	 */
	private BsymtAffiWorkplaceHistItem toEntity(AffWorkplaceHistoryItem domain) {
		return new BsymtAffiWorkplaceHistItem(domain.getHistoryId(),domain.getEmployeeId(),domain.getWorkplaceId(),domain.getNormalWorkplaceId());
	}
	
	private void updateEntity(AffWorkplaceHistoryItem domain, BsymtAffiWorkplaceHistItem entity) {
		if (domain.getWorkplaceId() != null){
			entity.setWorkPlaceId(domain.getWorkplaceId());
		}
		if (domain.getNormalWorkplaceId() != null){
			entity.setNormalWkpId(domain.getNormalWorkplaceId());
		}
	}
	@Override
	public void add(AffWorkplaceHistoryItem domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void delete(String histID) {
		Optional<BsymtAffiWorkplaceHistItem> existItem = this.queryProxy().find(histID, BsymtAffiWorkplaceHistItem.class);
		if (!existItem.isPresent()){
			throw new RuntimeException("invalid BsymtAffiWorkplaceHistItem");
		}
		this.commandProxy().remove(BsymtAffiWorkplaceHistItem.class, histID);
	}

	@Override
	public void update(AffWorkplaceHistoryItem domain) {
		Optional<BsymtAffiWorkplaceHistItem> existItem = this.queryProxy().find(domain.getHistoryId(), BsymtAffiWorkplaceHistItem.class);
		if (!existItem.isPresent()){
			throw new RuntimeException("invalid BsymtAffiWorkplaceHistItem");
		}
		updateEntity(domain, existItem.get());
		this.commandProxy().update(existItem.get());
	}

	@Override
	public List<AffWorkplaceHistoryItem> getAffWrkplaHistItemByEmpId(String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<AffWorkplaceHistoryItem> getByHistId(String historyId) {
		return this.queryProxy().query(SELECT_BY_HISTID, BsymtAffiWorkplaceHistItem.class)
				.setParameter("historyId", historyId).getSingle(x -> toDomain(x));
	}

	@Override
	public List<AffWorkplaceHistoryItem> findByHistIds(List<String> hisIds) {
		return this.queryProxy().query(SELECT_BY_HISTIDS, BsymtAffiWorkplaceHistItem.class)
				.setParameter("historyId", hisIds).getList().stream().map(item -> toDomain(item))
				.collect(Collectors.toList());
	}

	@Override
	public List<AffWorkplaceHistoryItem> findeByWplIDs(List<String> wplIDs) {
		return this.queryProxy().query(SELECT_BY_WPLIDS, BsymtAffiWorkplaceHistItem.class)
				.setParameter("wplIds", wplIDs).getList().stream().map(item -> toDomain(item))
				.collect(Collectors.toList());
	}

}
