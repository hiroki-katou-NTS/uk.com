/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.stampmanagement.support;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCard;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.support.KrcmtSupportCard;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.support.KrcmtSupportCardPk;

/**
 * @author laitv
 *
 */
@Stateless
public class SupportCardRepoImpl extends JpaRepository implements SupportCardRepository{
	
	private static final String SELECT_ALL = " SELECT c FROM KrcmtSupportCard c ";
	private static final String SELECT_BY_SUPPORT_CARD_NO = SELECT_ALL + " WHERE c.pk.supportCardNo = :supportCardNo";

	@Override
	public Optional<SupportCard> get(String cid, String supportCardNo) {
		KrcmtSupportCard entiti = this.queryProxy().find(new KrcmtSupportCardPk(cid, supportCardNo ),KrcmtSupportCard.class).orElse(null);
		if(entiti == null)
			return Optional.empty();
		
		SupportCard dm = toDomain(entiti);
		return Optional.of(dm);
	}
	
	@Override
	public void update(List<SupportCard> domains) {
		domains.forEach(dm -> {
			KrcmtSupportCard entiti = this.queryProxy().find(new KrcmtSupportCardPk(dm.getCid().toString(), dm.getSupportCardNumber().v() ),KrcmtSupportCard.class).orElse(null);
			if(entiti != null){
				entiti.workPlaceId = dm.getWorkplaceId().toString();
				commandProxy().update(entiti);
			}
		});
	}
	
	@Override
	public void insert(List<SupportCard> domains) {
		domains.forEach(dm -> {
			KrcmtSupportCard isExit = this.queryProxy().find(new KrcmtSupportCardPk(dm.getCid().toString(), dm.getSupportCardNumber().v() ),KrcmtSupportCard.class).orElse(null);
			if(isExit == null){
				KrcmtSupportCard e = KrcmtSupportCard.convert(dm);
				commandProxy().insert(e);
			}
		});
	}

	@Override
	public void delete(List<SupportCard> domains) {
		List<KrcmtSupportCardPk> primaryKeys = domains.stream()
				.map(domain -> new KrcmtSupportCardPk(domain.getCid().toString(), domain.getSupportCardNumber().v()))
				.collect(Collectors.toList());
		commandProxy().removeAll(KrcmtSupportCard.class, primaryKeys);
	}
	
	private SupportCard toDomain(KrcmtSupportCard entity) {
		SupportCard dm = new SupportCard(entity.pk.cid, new SupportCardNumber(entity.pk.supportCardNo), entity.workPlaceId);
		return dm;
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	public List<SupportCard> getAll() {
		return this.queryProxy().query(SELECT_ALL, KrcmtSupportCard.class)
				.getList()
				.stream()
				.map(t -> toDomain(t))
				.collect(Collectors.toList());
	}

	/**
	 * Gets the by support card no.
	 *
	 * @param supportCardNo the support card no
	 * @return the by support card no
	 */
	@Override
	public Optional<SupportCard> getBySupportCardNo(String supportCardNo) {
		KrcmtSupportCard entiti = this.queryProxy().query(SELECT_BY_SUPPORT_CARD_NO, KrcmtSupportCard.class)
													.setParameter("supportCardNo", supportCardNo)
													.getSingleOrNull();
		if(entiti == null)
			return Optional.empty();
		
		SupportCard dm = toDomain(entiti);
		return Optional.of(dm);
	}

	@Override
	public void delete(String cid, String supportCardNo) {
		this.commandProxy().remove(KrcmtSupportCard.class, new KrcmtSupportCardPk(cid, supportCardNo));
	}
	
}
