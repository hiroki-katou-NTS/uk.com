package nts.uk.ctx.at.record.infra.repository.stamp.stampcard;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stamp.card.StampCardItem;
import nts.uk.ctx.at.record.dom.stamp.card.StampCardRepository;
import nts.uk.ctx.at.record.infra.entity.stamp.stampcard.KwkdtStampCard;

@Stateless
public class JpaStampCardRepository extends JpaRepository implements StampCardRepository {

	private final String SELECT_BY_PERSON = "SELECT c FROM KwkdtStampCard c"
			+ " WHERE c.personId = :personId";
	
	private final String SELECT_BY_LIST_PERSON = "SELECT c FROM KwkdtStampCard c"
			+ " WHERE c.personId IN :listPersonId";

	private static StampCardItem toDomain(KwkdtStampCard entity) {
		StampCardItem domain = StampCardItem.createFromJavaType(
				entity.personId, 
				entity.kwkdtStampCardPK.cardNumber);
		return domain;
	}

	@Override
	public List<StampCardItem> findByPersonID(String personId) {
		return this.queryProxy().query(SELECT_BY_PERSON, KwkdtStampCard.class)
				.setParameter("personId", personId)
				.getList(c -> toDomain(c));
	}

	@Override
	public List<StampCardItem> findByListPersonID(List<String> LstPID) {
		return this.queryProxy().query(SELECT_BY_LIST_PERSON, KwkdtStampCard.class)
				.setParameter("listPersonId", LstPID)
				.getList(c -> toDomain(c));
	}
}
