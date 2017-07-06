package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.primitives.TimeItemId;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstBonusPayTimeItem;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstBonusPayTimeItemPK;

@Stateless
public class JpaBPTimeItemRepository extends JpaRepository implements BPTimeItemRepository {
	private final String SELECT_BPTIMEITEM_BY_COMPANYID = "SELECT c FROM KbpstBonusPayTimeItem c WHERE c.kbpstBonusPayTimeItemPK.companyId = :companyId AND  c.timeItemTypeAtr = 0 ORDER BY c.timeItemNo  ASC";
	private final String SELECT_SPEC_BPTIMEITEM_BY_COMPANYID = "SELECT c FROM KbpstBonusPayTimeItem c WHERE c.kbpstBonusPayTimeItemPK.companyId = :companyId AND  c.timeItemTypeAtr = 1 ORDER BY c.timeItemNo  ASC";
	private final String SELECT_BPTIMEITEM_BY_COMPANYID_AND_TIMEITEMID = "SELECT c FROM KbpstBonusPayTimeItem c WHERE c.kbpstBonusPayTimeItemPK.companyId = :companyId  AND c.kbpstBonusPayTimeItemPK.timeItemId = :timeItemId   AND  c.timeItemTypeAtr = 0 ORDER BY c.timeItemNo  ASC";
	private final String SELECT_SPEC_BPTIMEITEM_BY_COMPANYID_AND_TIMEITEMID = "SELECT c FROM KbpstBonusPayTimeItem c WHERE c.kbpstBonusPayTimeItemPK.companyId = :companyId AND c.kbpstBonusPayTimeItemPK.timeItemId = :timeItemId AND  c.timeItemTypeAtr = 1 ORDER BY c.timeItemNo  ASC";
	@Override
	public List<BonusPayTimeItem> getListBonusPayTimeItem(String companyId) {
		return this.queryProxy().query(SELECT_BPTIMEITEM_BY_COMPANYID, KbpstBonusPayTimeItem.class)
				.setParameter("companyId", companyId).getList(x -> this.toBonusPayTimeItemDomain(x));
	}

	@Override
	public List<BonusPayTimeItem> getListSpecialBonusPayTimeItem(String companyId) {
		return this.queryProxy().query(SELECT_SPEC_BPTIMEITEM_BY_COMPANYID, KbpstBonusPayTimeItem.class)
				.setParameter("companyId", companyId).getList(x -> this.toBonusPayTimeItemDomain(x));
	}

	@Override
	public void addListBonusPayTimeItem(List<BonusPayTimeItem> lstTimeItem) {
		List<KbpstBonusPayTimeItem> lstKbpstBonusPayTimeItem = lstTimeItem.stream()
				.map(c -> toBonusPayTimeItemEntity(c)).collect(Collectors.toList());
		this.commandProxy().insertAll(lstKbpstBonusPayTimeItem);
	}

	@Override
	public void updateListBonusPayTimeItem(List<BonusPayTimeItem> lstTimeItem) {
		lstTimeItem.forEach(c->{
			Optional<KbpstBonusPayTimeItem> kbpstBonusPayTimeItemOptional  = this.queryProxy().find(new KbpstBonusPayTimeItemPK(c.getCompanyId().toString(), c.getTimeItemId().v()), KbpstBonusPayTimeItem.class);
			if(kbpstBonusPayTimeItemOptional.isPresent()){
				KbpstBonusPayTimeItem kbpstBonusPayTimeItem = kbpstBonusPayTimeItemOptional.get();
				kbpstBonusPayTimeItem.timeItemName=c.getTimeItemName().v();
				kbpstBonusPayTimeItem.timeItemNo= new BigDecimal(c.getId());
				kbpstBonusPayTimeItem.timeItemTypeAtr= new BigDecimal(c.getTimeItemTypeAtr().value);
				kbpstBonusPayTimeItem.useAtr= new BigDecimal(c.getUseAtr().value);
				this.commandProxy().update(kbpstBonusPayTimeItem);
			}
		});
		
	}

	private BonusPayTimeItem toBonusPayTimeItemDomain(KbpstBonusPayTimeItem kbpstBonusPayTimeItem) {

		return BonusPayTimeItem.createFromJavaType(kbpstBonusPayTimeItem.kbpstBonusPayTimeItemPK.getCompanyId(),
				kbpstBonusPayTimeItem.kbpstBonusPayTimeItemPK.getTimeItemId(), kbpstBonusPayTimeItem.useAtr.intValue(),
				kbpstBonusPayTimeItem.timeItemName, kbpstBonusPayTimeItem.timeItemNo.intValue(),
				kbpstBonusPayTimeItem.timeItemTypeAtr.intValue());
	}

	private KbpstBonusPayTimeItem toBonusPayTimeItemEntity(BonusPayTimeItem bonusPayTimeItem) {
		return new KbpstBonusPayTimeItem(
				new KbpstBonusPayTimeItemPK(bonusPayTimeItem.getCompanyId().toString(),
						bonusPayTimeItem.getTimeItemId().toString()),
				new BigDecimal(bonusPayTimeItem.getUseAtr().value), bonusPayTimeItem.getTimeItemName().toString(),
				new BigDecimal(bonusPayTimeItem.getId()), new BigDecimal(bonusPayTimeItem.getTimeItemTypeAtr().value));
	}

	@Override
	public Optional<BonusPayTimeItem> getBonusPayTimeItem(String companyId, TimeItemId timeItemId) {
		
		Optional<KbpstBonusPayTimeItem> kbpstBonusPayTimeItem = this.queryProxy().query(SELECT_BPTIMEITEM_BY_COMPANYID_AND_TIMEITEMID, KbpstBonusPayTimeItem.class)
		.setParameter("companyId", companyId).setParameter("timeItemId", timeItemId, TimeItemId.class)
		.getSingle();
		if(kbpstBonusPayTimeItem.isPresent()){
			return Optional.ofNullable(this.toBonusPayTimeItemDomain(
					this.queryProxy().query(SELECT_BPTIMEITEM_BY_COMPANYID_AND_TIMEITEMID, KbpstBonusPayTimeItem.class)
							.setParameter("companyId", companyId).setParameter("timeItemId", timeItemId, TimeItemId.class)
							.getSingle().get()));
		}
		return Optional.empty();
		
	}

	@Override
	public Optional<BonusPayTimeItem> getSpecialBonusPayTimeItem(String companyId, TimeItemId timeItemId) {
	
		Optional<KbpstBonusPayTimeItem> KbpstBonusPayTimeItem = this.queryProxy().query(SELECT_SPEC_BPTIMEITEM_BY_COMPANYID_AND_TIMEITEMID, KbpstBonusPayTimeItem.class)
		.setParameter("companyId", companyId).setParameter("timeItemId", timeItemId, TimeItemId.class)
		.getSingle();
		if(KbpstBonusPayTimeItem.isPresent()){
			return Optional.ofNullable(this.toBonusPayTimeItemDomain(
					this.queryProxy().query(SELECT_SPEC_BPTIMEITEM_BY_COMPANYID_AND_TIMEITEMID, KbpstBonusPayTimeItem.class)
							.setParameter("companyId", companyId).setParameter("timeItemId", timeItemId, TimeItemId.class)
							.getSingle().get()));
		}
		return Optional.empty();
		
		
	}

}
