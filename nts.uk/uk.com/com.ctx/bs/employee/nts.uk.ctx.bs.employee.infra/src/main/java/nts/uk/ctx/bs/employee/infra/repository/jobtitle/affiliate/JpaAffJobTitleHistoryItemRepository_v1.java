package nts.uk.ctx.bs.employee.infra.repository.jobtitle.affiliate;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.AffJobTitleHistoryItem;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.AffJobTitleHistoryItemRepository_v1;
import nts.uk.ctx.bs.employee.infra.entity.jobtitle.affiliate.BsymtAffJobTitleHistItem;

@Stateless
public class JpaAffJobTitleHistoryItemRepository_v1 extends JpaRepository
		implements AffJobTitleHistoryItemRepository_v1 {
	
	private final String GET_BY_SID_DATE = "select hi from BsymtAffJobTitleHistItem hi"
			+ " inner join BsymtAffJobTitleHist h on hi.hisId = h.hisId"
			+ " where hi.sid = :sid and h.strDate <= :referDate and h.endDate >= :referDate";

	/**
	 * Convert from domain to entity
	 * 
	 * @param domain
	 * @return
	 */
	private BsymtAffJobTitleHistItem toEntity(AffJobTitleHistoryItem domain) {
		return new BsymtAffJobTitleHistItem(domain.getHistoryId(), domain.getEmployeeId(), domain.getJobTitleId(),
				domain.getNote().v());
	}

	/**
	 * Update entity
	 * 
	 * @param domain
	 * @param entity
	 */
	private void updateEntity(AffJobTitleHistoryItem domain, BsymtAffJobTitleHistItem entity) {
		entity.jobTitleId = domain.getJobTitleId();
		entity.note = domain.getNote().v();
	}

	@Override
	public void add(AffJobTitleHistoryItem domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(AffJobTitleHistoryItem domain) {

		Optional<BsymtAffJobTitleHistItem> existItem = this.queryProxy().find(domain.getHistoryId(),
				BsymtAffJobTitleHistItem.class);

		if (!existItem.isPresent()) {
			throw new RuntimeException("invalid BsymtAffJobTitleHistItem");
		}
		updateEntity(domain, existItem.get());

		this.commandProxy().update(existItem.get());
	}

	@Override
	public void delete(String histId) {
		Optional<BsymtAffJobTitleHistItem> existItem = this.queryProxy().find(histId, BsymtAffJobTitleHistItem.class);

		if (!existItem.isPresent()) {
			throw new RuntimeException("invalid BsymtAffJobTitleHistItem");
		}

		this.commandProxy().remove(BsymtAffJobTitleHistItem.class, histId);
	}

	private AffJobTitleHistoryItem toDomain(BsymtAffJobTitleHistItem ent) {
		return AffJobTitleHistoryItem.createFromJavaType(ent.hisId, ent.sid, ent.jobTitleId,
				ent.note);
	}

	@Override
	public Optional<AffJobTitleHistoryItem> findByHitoryId(String historyId) {
		Optional<BsymtAffJobTitleHistItem> optionData = this.queryProxy().find(historyId,
				BsymtAffJobTitleHistItem.class);
		if (optionData.isPresent()) {
			return Optional.of(toDomain(optionData.get()));
		}
		return Optional.empty();
	}
	
	@Override
	public Optional<AffJobTitleHistoryItem> getByEmpIdAndReferDate(String employeeId, GeneralDate referDate) {
		Optional<BsymtAffJobTitleHistItem> optionData = this.queryProxy()
				.query(GET_BY_SID_DATE, BsymtAffJobTitleHistItem.class).setParameter("sid", employeeId)
				.setParameter("referDate", referDate).getSingle();
		if (optionData.isPresent()) {
			BsymtAffJobTitleHistItem ent = optionData.get();
			return Optional.of(AffJobTitleHistoryItem.createFromJavaType(ent.hisId, ent.sid,
					ent.jobTitleId, ent.note));
		}
		return Optional.empty();
	}

}
