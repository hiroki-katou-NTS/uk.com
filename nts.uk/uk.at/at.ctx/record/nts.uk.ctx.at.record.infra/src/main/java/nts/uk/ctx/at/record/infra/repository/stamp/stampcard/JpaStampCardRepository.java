package nts.uk.ctx.at.record.infra.repository.stamp.stampcard;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.infra.entity.stamp.stampcard.KwkdtStampCard;

@Stateless
public class JpaStampCardRepository extends JpaRepository implements StampCardRepository {


	private static final String GET_ALL_BY_SID = "SELECT a FROM KwkdtStampCard a WHERE a.sid = :sid ORDER BY a.registerDate, a.cardNo ASC";

	private static final String GET_ALL_BY_CONTRACT_CODE = "SELECT a FROM KwkdtStampCard a WHERE a.contractCd = :contractCode ";
	
	private static final String GET_LST_STAMPCARD_BY_LST_SID= "SELECT a FROM KwkdtStampCard a WHERE a.sid IN :sids ";

	private static final String GET_LST_STAMPCARD_BY_LST_SID_CONTRACT_CODE= "SELECT a FROM KwkdtStampCard a WHERE a.sid IN :sids AND a.contractCd = :contractCode ";

	private static final String GET_BY_CARD_ID = "SELECT a FROM KwkdtStampCard a WHERE a.cardId = :cardid";
	
	private static final String GET_BY_CONTRACT_CODE = "SELECT a.cardNo FROM KwkdtStampCard a WHERE a.contractCd = :contractCd";

	private static final String GET_BY_CARD_NO_AND_CONTRACT_CODE = "SELECT a FROM KwkdtStampCard a"
			+ " WHERE a.cardNo = :cardNo and a.contractCd = :contractCd";
	
	public static final String GET_LAST_CARD_NO = "SELECT c.cardNo FROM KwkdtStampCard c"
			+ " WHERE c.contractCd = :contractCode AND c.cardNo LIKE CONCAT(:cardNo, '%')"
			+ " ORDER BY c.cardNo DESC";

	@Override
	public List<StampCard> getListStampCard(String sid) {
		List<KwkdtStampCard> entities = this.queryProxy().query(GET_ALL_BY_SID, KwkdtStampCard.class)
				.setParameter("sid", sid).getList();
		if (entities.isEmpty())
			return Collections.emptyList();

		return entities.stream()
				.map(x -> StampCard.createFromJavaType(x.cardId, x.sid, x.cardNo, x.registerDate, x.contractCd))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<String> getListStampCardByContractCode(String contractCd) {
		List<String> lstCardNo = this.queryProxy().query(GET_BY_CONTRACT_CODE, String.class)
				.setParameter("contractCd", contractCd).getList();
		if (lstCardNo.isEmpty())
			return Collections.emptyList();

		return lstCardNo;
	}

	@Override
	public Optional<StampCard> getByStampCardId(String stampCardId) {
		Optional<StampCard> entity = this.queryProxy().query(GET_BY_CARD_ID, KwkdtStampCard.class)
				.setParameter("cardid", stampCardId).getSingle(x -> toDomain(x));
		if (entity.isPresent())
			return entity;
		else
			return Optional.empty();
	}

	@Override
	public Optional<StampCard> getByCardNoAndContractCode(String cardNo, String contractCd) {
		Optional<StampCard> entity = this.queryProxy().query(GET_BY_CARD_NO_AND_CONTRACT_CODE, KwkdtStampCard.class)
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
		Optional<KwkdtStampCard> entityOpt = this.queryProxy().find(domain.getStampCardId(), KwkdtStampCard.class);
		if (entityOpt.isPresent()) {
			KwkdtStampCard entity = entityOpt.get();
			updateDetail(entity, domain);
			this.commandProxy().update(entity);
		}
	}

	@Override
	public void delete(String stampCardId) {
		Optional<KwkdtStampCard> entityOpt = this.queryProxy().find(stampCardId, KwkdtStampCard.class);
		if (entityOpt.isPresent()) {
			KwkdtStampCard entity = entityOpt.get();
			this.commandProxy().remove(entity);
		}

	}
	
	@Override
	public void deleteBySid(String sid) {
		List<KwkdtStampCard> entities = this.queryProxy().query(GET_ALL_BY_SID, KwkdtStampCard.class)
				.setParameter("sid", sid).getList();
		if (!entities.isEmpty())
			this.commandProxy().removeAll(entities);
	}

	private StampCard toDomain(KwkdtStampCard e) {
		return StampCard.createFromJavaType(e.cardId, e.sid, e.cardNo, e.registerDate, e.contractCd);
	}

	private KwkdtStampCard toEntity(StampCard domain) {
		KwkdtStampCard entity = new KwkdtStampCard();
		entity.cardId = domain.getStampCardId();
		entity.sid = domain.getEmployeeId();
		entity.cardNo = domain.getStampNumber().v();
		entity.registerDate = domain.getRegisterDate();
		entity.contractCd = domain.getContractCd().v();
		return entity;
	}

	private void updateDetail(KwkdtStampCard entity, StampCard data) {
		entity.cardId = data.getStampCardId();
		entity.sid = data.getEmployeeId();
		entity.cardNo = data.getStampNumber().v();
		entity.registerDate = data.getRegisterDate();
		entity.contractCd = data.getContractCd().v();
	}

	@Override
	public Optional<String> getLastCardNo(String contractCode, String startCardNoLetters, int length) {
		List<String> cardNoList = this.queryProxy().query(GET_LAST_CARD_NO, String.class)
				.setParameter("contractCode", contractCode)
				.setParameter("cardNo", startCardNoLetters).getList()
				.stream().filter(cardNo -> cardNo.length() == length)
				.collect(Collectors.toList()); 
		if (cardNoList.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(cardNoList.get(0));
	}

	@Override
	public List<StampCard> getLstStampCardByContractCode(String contractCode) {
		List<KwkdtStampCard> entities = this.queryProxy().query(GET_ALL_BY_CONTRACT_CODE, KwkdtStampCard.class)
				.setParameter("contractCode", contractCode).getList();
		if (entities.isEmpty())
			return Collections.emptyList();

		return entities.stream()
				.map(x -> StampCard.createFromJavaType(x.cardId, x.sid, x.cardNo, x.registerDate, x.contractCd))
				.collect(Collectors.toList());
	}

	@Override
	public List<StampCard> getLstStampCardByLstSid(List<String> sids) {
		List<KwkdtStampCard> entities = this.queryProxy().query(GET_LST_STAMPCARD_BY_LST_SID, KwkdtStampCard.class)
				.setParameter("sids", sids).getList();
		if (entities.isEmpty())
			return Collections.emptyList();

		return entities.stream()
				.map(x -> StampCard.createFromJavaType(x.cardId, x.sid, x.cardNo, x.registerDate, x.contractCd))
				.collect(Collectors.toList());
	}

	@Override
	public List<StampCard> getLstStampCardByLstSidAndContractCd(List<String> sids, String contractCode) {
		List<KwkdtStampCard> entities = this.queryProxy().query(GET_LST_STAMPCARD_BY_LST_SID_CONTRACT_CODE, KwkdtStampCard.class)
				.setParameter("sids", sids)
				.setParameter("contractCode", contractCode).getList();
		if (entities.isEmpty())
			return Collections.emptyList();

		return entities.stream()
				.map(x -> StampCard.createFromJavaType(x.cardId, x.sid, x.cardNo, x.registerDate, x.contractCd))
				.collect(Collectors.toList());
	}
}
