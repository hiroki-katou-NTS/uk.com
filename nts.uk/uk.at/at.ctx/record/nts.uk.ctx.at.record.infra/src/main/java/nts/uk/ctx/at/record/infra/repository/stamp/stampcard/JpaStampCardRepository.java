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
public class JpaStampCardRepository extends JpaRepository implements StampCardRepository {


	private String GET_ALL_BY_SID = "SELECT a FROM KrcmtStamcard a WHERE a.sid = :sid ORDER BY a.registerDate, a.cardNo ASC";

	private String GET_BY_CARD_ID = "SELECT a FROM KrcmtStamcard a WHERE a.cardId = :cardid";

	private String GET_BY_CARD_NO_AND_CONTRACT_CODE = "SELECT a FROM KrcmtStamcard a WHERE a.cardNo = :cardNo and a.contractCd = :contractCd";

	@Override
	public List<StampCard> getListStampCard(String sid) {
		List<KrcmtStamcard> entities = this.queryProxy().query(GET_ALL_BY_SID, KrcmtStamcard.class)
				.setParameter("sid", sid).getList();
		if (entities.isEmpty())
			return Collections.emptyList();

		return entities.stream()
				.map(x -> StampCard.createFromJavaType(x.cardId, x.sid, x.cardNo, x.registerDate, x.contractCd))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<StampCard> getByStampCardId(String stampCardId) {
		Optional<StampCard> entity = this.queryProxy().query(GET_BY_CARD_ID, KrcmtStamcard.class)
				.setParameter("cardid", stampCardId).getSingle(x -> toDomain(x));
		if (entity.isPresent())
			return entity;
		else
			return Optional.empty();
	}

	@Override
	public Optional<StampCard> getByCardNoAndContractCode(String cardNo, String contractCd) {
		Optional<StampCard> entity = this.queryProxy().query(GET_BY_CARD_NO_AND_CONTRACT_CODE, KrcmtStamcard.class)
				.setParameter("cardNo", cardNo).setParameter("contractCd", contractCd).getSingle(x -> toDomain(x));
		if (entity.isPresent())
			return entity;
		else
			return Optional.empty();
	}

	@Override
	public void add(StampCard domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(StampCard domain) {
		Optional<KrcmtStamcard> entityOpt = this.queryProxy().find(domain.getStampCardId(), KrcmtStamcard.class);
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
		return StampCard.createFromJavaType(e.cardId, e.sid, e.cardNo, e.registerDate, e.contractCd);
	}

	private KrcmtStamcard toEntity(StampCard domain) {
		KrcmtStamcard entity = new KrcmtStamcard();
		entity.cardId = domain.getStampCardId();
		entity.sid = domain.getEmployeeId();
		entity.cardNo = domain.getStampNumber().v();
		entity.registerDate = domain.getRegisterDate();
		entity.contractCd = domain.getContractCd().v();
		return entity;
	}

	private void updateDetail(KrcmtStamcard entity, StampCard data) {
		entity.cardId = data.getStampCardId();
		entity.sid = data.getEmployeeId();
		entity.cardNo = data.getStampNumber().v();
		entity.registerDate = data.getRegisterDate();
		entity.contractCd = data.getContractCd().v();
	}

}
