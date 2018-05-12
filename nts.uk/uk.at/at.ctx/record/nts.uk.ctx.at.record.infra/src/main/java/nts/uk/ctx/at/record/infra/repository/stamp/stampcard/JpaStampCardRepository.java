package nts.uk.ctx.at.record.infra.repository.stamp.stampcard;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.infra.entity.stamp.stampcard.KrcmtStamcard;

@Stateless
public class JpaStampCardRepository  extends JpaRepository implements StampCardRepository{
	
	private String GET_ALL_BY_SID = "SELECT a FROM KrcmtStamcard a WHERE a.sid = :sid ";

	private String GET_BY_CARD_ID = "SELECT a FROM KrcmtStamcard a WHERE a.cardId = :cardid";


	@Override
	public List<StampCard> getListStampCard(String sid) {
		List<KrcmtStamcard> entities = this.queryProxy()
				.query(GET_ALL_BY_SID, KrcmtStamcard.class)
				.setParameter("sid", sid).getList();
		if(entities.isEmpty())
			return Collections.emptyList();

		return entities.stream()
				.map(x -> StampCard.createFromJavaType(x.cardId, x.sid, x.stampNumber, x.registerDate))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<StampCard> getByStampCardId(String stampCardId) {
		Optional<StampCard> entity = this.queryProxy()
				.query(GET_BY_CARD_ID, KrcmtStamcard.class).setParameter("cardid", stampCardId)
				.getSingle(x -> toDomain(x));
		return entity;
	}

	@Override
	public void add(StampCard domain) {
		this.commandProxy().insert(toEntity(domain));
	}


	@Override
	public void update(StampCard domain) {
		Optional<KrcmtStamcard> entityOpt = this.queryProxy().find(domain.getStampCardId(),
				KrcmtStamcard.class);
		if (entityOpt.isPresent()) {
			KrcmtStamcard entity = entityOpt.get();
			updateDetail(entity, domain);
			this.commandProxy().update(entity);
		}
	}

	@Override
	public void delete(String stampCardId) {
		Optional<KrcmtStamcard> entityOpt = this.queryProxy().find(stampCardId, KrcmtStamcard.class);
		if (entityOpt.isPresent()) {
			KrcmtStamcard entity = entityOpt.get();
			this.commandProxy().remove(entity);
		}

	}
	
	private StampCard toDomain(KrcmtStamcard e) {
		return StampCard.createFromJavaType(e.cardId, e.sid, e.stampNumber, e.registerDate);
	}
	
	private KrcmtStamcard toEntity(StampCard domain) {
		KrcmtStamcard entity = new KrcmtStamcard();
		entity.cardId  = domain.getStampCardId();
		entity.sid = domain.getEmployeeId();
		entity.stampNumber = domain.getStampNumber().v();
		entity.registerDate = domain.getRegisterDate();
		return entity;
	}
	
	private void updateDetail(KrcmtStamcard entity, StampCard data) {
		entity.cardId  = data.getStampCardId();
		entity.sid = data.getEmployeeId();
		entity.stampNumber = data.getStampNumber().v();
		entity.registerDate = data.getRegisterDate();
	}

}
