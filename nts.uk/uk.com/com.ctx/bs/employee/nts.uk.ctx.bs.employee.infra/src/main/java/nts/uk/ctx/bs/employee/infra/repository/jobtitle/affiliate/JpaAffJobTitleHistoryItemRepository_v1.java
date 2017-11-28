package nts.uk.ctx.bs.employee.infra.repository.jobtitle.affiliate;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.jobtile.affiliate.AffJobTitleHistoryItem;
import nts.uk.ctx.bs.employee.dom.jobtile.affiliate.AffJobTitleHistoryItemRepository_v1;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.affiliate.BsymtAffJobTitleHistItem;

@Stateless
public class JpaAffJobTitleHistoryItemRepository_v1 extends JpaRepository implements AffJobTitleHistoryItemRepository_v1{

	/**
	 * Convert from domain to entity
	 * @param domain
	 * @return
	 */
	private BsymtAffJobTitleHistItem toEntity(AffJobTitleHistoryItem domain){
		return new BsymtAffJobTitleHistItem(domain.getHistoryId(),domain.getEmployeeId(),domain.getJobTitleCode().v(),domain.getNote().v());
	}
	/**
	 * Update entity
	 * @param domain
	 * @param entity
	 */
	private void updateEntity(AffJobTitleHistoryItem domain, BsymtAffJobTitleHistItem entity){
		entity.setSid(domain.getEmployeeId());
		entity.setJobTitleCode(domain.getJobTitleCode().v());
		entity.setNote(domain.getNote().v());
	}
	@Override
	public void addJobTitleMain(AffJobTitleHistoryItem domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void updateJobTitleMain(AffJobTitleHistoryItem domain) {
		
		Optional<BsymtAffJobTitleHistItem> existItem = this.queryProxy().find(domain.getHistoryId(), BsymtAffJobTitleHistItem.class);
		
		if (!existItem.isPresent()){
			throw new RuntimeException("invalid BsymtAffJobTitleHistItem");
		}
		updateEntity(domain, existItem.get());
		
		this.commandProxy().update(existItem.get());
	}

	@Override
	public void deleteJobTitleMain(String histId) {
		Optional<BsymtAffJobTitleHistItem> existItem = this.queryProxy().find(histId, BsymtAffJobTitleHistItem.class);

		if (!existItem.isPresent()){
			throw new RuntimeException("invalid BsymtAffJobTitleHistItem");
		}
		
		this.commandProxy().remove(BsymtAffJobTitleHistItem.class, histId);
	}


}
