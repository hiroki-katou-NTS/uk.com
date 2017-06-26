package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstBonusPayTimeItem;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstBonusPayTimeItemPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaBPTimeItemRepository extends JpaRepository implements BPTimeItemRepository {
	private final String SELECT_BPTIMEITEM_BY_COMPANYID = "SELECT c FROM KbpstBonusPayTimeItem c WHERE c.KbpstBonusPayTimeItem.companyId = :companyId AND  c.timeItemTypeAtr = 0";
	private final String SELECT_SPEC_BPTIMEITEM_BY_COMPANYID = "SELECT c FROM KbpstBonusPayTimeItem c WHERE c.KbpstBonusPayTimeItem.companyId = :companyId AND  c.timeItemTypeAtr = 1";

	@Override
	public List<BonusPayTimeItem> getListBonusPayTimeItem() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().query(SELECT_BPTIMEITEM_BY_COMPANYID, KbpstBonusPayTimeItem.class)
				.setParameter("companyId", companyId).getList(x -> this.toBonusPayTimeItemDomain(x));
	}

	@Override
	public List<BonusPayTimeItem> getListSpecialBonusPayTimeItem() {
		String companyId = AppContexts.user().companyId();
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
		List<KbpstBonusPayTimeItem> lstKbpstBonusPayTimeItem = lstTimeItem.stream()
				.map(c -> toBonusPayTimeItemEntity(c)).collect(Collectors.toList());
		this.commandProxy().updateAll(lstKbpstBonusPayTimeItem);
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

}
