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
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.infra.entity.classification.affiliate_ver1.KmnmtAffClassHistItem_Ver1;
import nts.uk.ctx.bs.employee.infra.entity.temporaryabsence.BsymtTempAbsHisItem;

/**
 * @author danpv
 *
 */
@Stateless
public class JpaAffClassHistItem extends JpaRepository implements AffClassHistItemRepository_ver1 {

	private final String GET_BY_SID_DATE = "select hi from KmnmtAffClassHistItem_Ver1 hi"
			+ " join in KmnmtAffClassHistory_Ver1 h on hi.histId = h.histId"
			+ " where hi.sid = :sid and h.startDate <= :referDate and h.endDate >= referDate";

	@Override
	public Optional<AffClassHistItem_ver1> getByEmpIdAndReferDate(String employeeId, GeneralDate referenceDate) {
		Optional<KmnmtAffClassHistItem_Ver1> optionData = this.queryProxy()
				.query(GET_BY_SID_DATE, KmnmtAffClassHistItem_Ver1.class).setParameter("sid", employeeId)
				.setParameter("referDate", referenceDate).getSingle();
		if (optionData.isPresent()) {
			KmnmtAffClassHistItem_Ver1 ent = optionData.get();
			return Optional
					.of(AffClassHistItem_ver1.createFromJavaType(ent.sid, ent.historyId, ent.classificationCode));
		}
		return Optional.empty();
	}

	@Override
	public void add(AffClassHistItem_ver1 item) {
		KmnmtAffClassHistItem_Ver1 entity = new KmnmtAffClassHistItem_Ver1(item.getHistoryId(), item.getEmployeeId(),
				item.getClassificationCode().v());
		this.commandProxy().insert(entity);

	}

	@Override
	public void update(AffClassHistItem_ver1 item) {
		Optional<KmnmtAffClassHistItem_Ver1> entityOpt = this.queryProxy().find(item.getHistoryId(),
				KmnmtAffClassHistItem_Ver1.class);

		if (!entityOpt.isPresent()) {
			throw new RuntimeException("invalid TempAbsenceHisItem");
		}
		KmnmtAffClassHistItem_Ver1 ent = entityOpt.get(); 
		ent.classificationCode = item.getClassificationCode().v();
		this.commandProxy().update(ent);
		
	}
	

}
