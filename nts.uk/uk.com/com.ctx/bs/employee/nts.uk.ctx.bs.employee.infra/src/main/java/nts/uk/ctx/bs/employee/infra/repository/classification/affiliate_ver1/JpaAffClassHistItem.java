/**
 * 
 */
package nts.uk.ctx.bs.employee.infra.repository.classification.affiliate_ver1;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistItemRepository_ver1;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistItem_ver1;
import nts.uk.ctx.bs.employee.infra.entity.classification.affiliate_ver1.BsymtAffClassHistItem_Ver1;

/**
 * @author danpv
 *
 */
@Stateless
public class JpaAffClassHistItem extends JpaRepository implements AffClassHistItemRepository_ver1 {

	private final String GET_BY_SID_DATE = "select hi from BsymtAffClassHistItem_Ver1 hi"
			+ " inner join BsymtAffClassHistory_Ver1 h on hi.historyId = h.historyId"
			+ " where hi.sid = :sid and h.startDate <= :referDate and h.endDate >= :referDate";

	@Override
	public Optional<AffClassHistItem_ver1> getByEmpIdAndReferDate(String employeeId, GeneralDate referenceDate) {
		Optional<BsymtAffClassHistItem_Ver1> optionData = this.queryProxy()
				.query(GET_BY_SID_DATE, BsymtAffClassHistItem_Ver1.class).setParameter("sid", employeeId)
				.setParameter("referDate", referenceDate).getSingle();
		if (optionData.isPresent()) {
			BsymtAffClassHistItem_Ver1 ent = optionData.get();
			return Optional
					.of(AffClassHistItem_ver1.createFromJavaType(ent.sid, ent.historyId, ent.classificationCode));
		}
		return Optional.empty();
	}

	@Override
	public Optional<AffClassHistItem_ver1> getByHistoryId(String historyId) {
		Optional<BsymtAffClassHistItem_Ver1> optionData = this.queryProxy().find(historyId,
				BsymtAffClassHistItem_Ver1.class);
		if (optionData.isPresent()) {
			return Optional.of(toDomain(optionData.get()));
		}
		return Optional.empty();
	}

	private AffClassHistItem_ver1 toDomain(BsymtAffClassHistItem_Ver1 entity) {
		return AffClassHistItem_ver1.createFromJavaType(entity.sid, entity.historyId, entity.classificationCode);
	}

	@Override
	public void add(AffClassHistItem_ver1 item) {
		BsymtAffClassHistItem_Ver1 entity = new BsymtAffClassHistItem_Ver1(item.getHistoryId(), item.getEmployeeId(),
				item.getClassificationCode().v());
		this.commandProxy().insert(entity);

	}

	@Override
	public void update(AffClassHistItem_ver1 item) {
		Optional<BsymtAffClassHistItem_Ver1> entityOpt = this.queryProxy().find(item.getHistoryId(),
				BsymtAffClassHistItem_Ver1.class);

		if (!entityOpt.isPresent()) {
			throw new RuntimeException("invalid TempAbsenceHisItem");
		}
		BsymtAffClassHistItem_Ver1 ent = entityOpt.get();
		ent.classificationCode = item.getClassificationCode().v();
		this.commandProxy().update(ent);

	}

	@Override
	public void delete(String historyId) {
		Optional<BsymtAffClassHistItem_Ver1> entityOpt = this.queryProxy().find(historyId,
				BsymtAffClassHistItem_Ver1.class);

		if (!entityOpt.isPresent()) {
			throw new RuntimeException("invalid TempAbsenceHisItem");
		}
		this.commandProxy().update(entityOpt.get());
	}

}
