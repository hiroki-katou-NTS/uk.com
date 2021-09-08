/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.stampmanagement.support;

import java.util.List;
import java.util.Optional;

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

	@Override
	public Optional<SupportCard> get(String cid, int supportCardNo) {
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
		domains.stream().map(c -> KrcmtSupportCard.convert(c)).forEach(e -> {
			commandProxy().remove(e);
		});
	}
	
	private SupportCard toDomain(KrcmtSupportCard entity) {
		SupportCard dm = new SupportCard(entity.pk.cid, new SupportCardNumber(entity.pk.supportCardNo), entity.workPlaceId);
		return dm;
	}
	
}
