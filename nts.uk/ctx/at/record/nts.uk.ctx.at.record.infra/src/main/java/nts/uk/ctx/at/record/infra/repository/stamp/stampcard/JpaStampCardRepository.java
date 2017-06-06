package nts.uk.ctx.at.record.infra.repository.stamp.stampcard;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stamp.card.StampCardItem;
import nts.uk.ctx.at.record.dom.stamp.card.StampCardRepository;
import nts.uk.ctx.at.record.infra.entity.stamp.stampcard.KwkdtStampCard;

@Stateless
public class JpaStampCardRepository extends JpaRepository implements StampCardRepository {

	private final String SELECT_BY_PERSON = "SELECT c FROM KwkdtStampCard c"
			+ " WHERE c.kwkdtStampCardPK.companyId = :companyId" 
			+ " AND c.kwkdtStampCardPK.PID = :PID";

	private static StampCardItem toDomain(KwkdtStampCard entity) {
		StampCardItem domain = StampCardItem.createFromJavaType(entity.kwkdtStampCardPK.companyId,
				entity.kwkdtStampCardPK.cardNumber, entity.personId);
		return domain;
	}

	@Override
	public Optional<StampCardItem> findByPersonID(String companyId, String PID) {

		return this.queryProxy().query(SELECT_BY_PERSON, KwkdtStampCard.class)
				.setParameter("companyId", companyId)
				.setParameter("PID", PID)
				.getSingle(c -> toDomain(c));
	}

}
