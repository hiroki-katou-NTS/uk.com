package nts.uk.ctx.at.shared.infra.repository.bonuspay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstBonusPayTimeItem;
import nts.uk.ctx.at.shared.infra.entity.bonuspay.KbpstBonusPayTimeItemPK;

@Stateless
public class JpaBPTimeItemRepository extends JpaRepository implements BPTimeItemRepository {
	private static final String CHECK_INIT = "SELECT count(c) " + " FROM KbpstBonusPayTimeItem c "
			+ " WHERE c.kbpstBonusPayTimeItemPK.companyId = :companyId " + " AND  c.useAtr = 1 ";
	private static final String SELECT_BPTIMEITEM_BY_COMPANYID = "SELECT c " + " FROM KbpstBonusPayTimeItem c "
			+ " WHERE c.kbpstBonusPayTimeItemPK.companyId = :companyId "
			+ " AND c.kbpstBonusPayTimeItemPK.timeItemTypeAtr = 0 "
			+ " ORDER BY c.kbpstBonusPayTimeItemPK.timeItemNo  ASC ";
	private static final String SELECT_BPTIMEITEM_ONLY_BY_COMPANYID = "SELECT c " + " FROM KbpstBonusPayTimeItem c "
			+ " WHERE c.kbpstBonusPayTimeItemPK.companyId = :companyId "
			+ " ORDER BY c.kbpstBonusPayTimeItemPK.timeItemNo  ASC ";
	private static final String SELECT_BPTIMEITEM_INUSE_BY_COMPANYID = "SELECT c " + " FROM KbpstBonusPayTimeItem c "
			+ " WHERE c.kbpstBonusPayTimeItemPK.companyId = :companyId "
			+ " AND c.kbpstBonusPayTimeItemPK.timeItemTypeAtr = 0 " + " AND c.useAtr = 1 "
			+ " ORDER BY c.kbpstBonusPayTimeItemPK.timeItemNo  ASC ";
	private static final String SELECT_SPEC_BPTIMEITEM_BY_COMPANYID = "SELECT c " + " FROM KbpstBonusPayTimeItem c "
			+ " WHERE c.kbpstBonusPayTimeItemPK.companyId = :companyId "
			+ " AND c.kbpstBonusPayTimeItemPK.timeItemTypeAtr = 1 "
			+ " ORDER BY c.kbpstBonusPayTimeItemPK.timeItemNo  ASC ";
	private static final String SELECT_SPEC_BPTIMEITEM_INUSE_BY_COMPANYID = "SELECT c " + " FROM KbpstBonusPayTimeItem c "
			+ " WHERE c.kbpstBonusPayTimeItemPK.companyId = :companyId "
			+ " AND c.kbpstBonusPayTimeItemPK.timeItemTypeAtr = 1 " + " AND c.useAtr = 1 "
			+ " ORDER BY c.kbpstBonusPayTimeItemPK.timeItemNo ASC ";
	private static final String SELECT_BPTIMEITEM_BY_KEY = "SELECT c " + " FROM KbpstBonusPayTimeItem c "
			+ " WHERE c.kbpstBonusPayTimeItemPK.companyId = :companyId "
			+ " AND c.kbpstBonusPayTimeItemPK.timeItemNo = :timeItemNo "
			+ " AND c.kbpstBonusPayTimeItemPK.timeItemTypeAtr = 0 "
			+ " ORDER BY c.kbpstBonusPayTimeItemPK.timeItemNo ASC ";
	private static final String SELECT_SPEC_BPTIMEITEM_BY_KEY = "SELECT c " + " FROM KbpstBonusPayTimeItem c "
			+ " WHERE c.kbpstBonusPayTimeItemPK.companyId = :companyId "
			+ " AND c.kbpstBonusPayTimeItemPK.timeItemNo = :timeItemNo "
			+ " AND c.kbpstBonusPayTimeItemPK.timeItemTypeAtr = 1 "
			+ " ORDER BY c.kbpstBonusPayTimeItemPK.timeItemNo ASC ";
	private static final String SELECT_BP_TIME_ITEM_NAME = "SELECT c " + " FROM KbpstBonusPayTimeItem c "
			+ " WHERE c.kbpstBonusPayTimeItemPK.companyId = :companyId"
			+ " AND c.kbpstBonusPayTimeItemPK.timeItemNo IN :timeItemNos "
			+ " AND c.kbpstBonusPayTimeItemPK.timeItemTypeAtr = 0 ";
	private static final String SELECT_SPEC_BP_TIME_ITEM_NAME = "SELECT c " + " FROM KbpstBonusPayTimeItem c "
			+ " WHERE c.kbpstBonusPayTimeItemPK.companyId = :companyId"
			+ " AND c.kbpstBonusPayTimeItemPK.timeItemNo IN :timeItemNos "
			+ " AND c.kbpstBonusPayTimeItemPK.timeItemTypeAtr = 1 ";
	
	 @TransactionAttribute(TransactionAttributeType.SUPPORTS)
		@Override
		public List<BonusPayTimeItem> getListBonusPayTimeByCid(String companyId) {
			return this.queryProxy().query(SELECT_BPTIMEITEM_ONLY_BY_COMPANYID, KbpstBonusPayTimeItem.class)
					.setParameter("companyId", companyId).getList(x -> this.toBonusPayTimeItemDomain(x));
		}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<BonusPayTimeItem> getListBonusPayTimeItem(String companyId) {
		return this.queryProxy().query(SELECT_BPTIMEITEM_BY_COMPANYID, KbpstBonusPayTimeItem.class)
				.setParameter("companyId", companyId).getList(x -> this.toBonusPayTimeItemDomain(x));
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
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
		lstTimeItem.forEach(c -> {
			Optional<KbpstBonusPayTimeItem> kbpstBonusPayTimeItemOptional = this.queryProxy()
					.find(new KbpstBonusPayTimeItemPK(c.getCompanyId().toString(), new BigDecimal(c.getId()),
							new BigDecimal(c.getTimeItemTypeAtr().value)), KbpstBonusPayTimeItem.class);
			if (kbpstBonusPayTimeItemOptional.isPresent()) {
				KbpstBonusPayTimeItem kbpstBonusPayTimeItem = kbpstBonusPayTimeItemOptional.get();
				kbpstBonusPayTimeItem.timeItemName = c.getUseAtr().value == 1
						? c.getTimeItemName().v() : kbpstBonusPayTimeItem.timeItemName;
				kbpstBonusPayTimeItem.useAtr = new BigDecimal(c.getUseAtr().value);
				this.commandProxy().update(kbpstBonusPayTimeItem);
			}
		});

	}

	private BonusPayTimeItem toBonusPayTimeItemDomain(KbpstBonusPayTimeItem kbpstBonusPayTimeItem) {

		return BonusPayTimeItem.createFromJavaType(kbpstBonusPayTimeItem.kbpstBonusPayTimeItemPK.getCompanyId(),
				kbpstBonusPayTimeItem.useAtr.intValue(), kbpstBonusPayTimeItem.timeItemName,
				kbpstBonusPayTimeItem.kbpstBonusPayTimeItemPK.timeItemNo.intValue(),
				kbpstBonusPayTimeItem.kbpstBonusPayTimeItemPK.timeItemTypeAtr.intValue());
	}

	private KbpstBonusPayTimeItem toBonusPayTimeItemEntity(BonusPayTimeItem bonusPayTimeItem) {
		return new KbpstBonusPayTimeItem(new KbpstBonusPayTimeItemPK(bonusPayTimeItem.getCompanyId().toString(),
				new BigDecimal(bonusPayTimeItem.getId()), new BigDecimal(bonusPayTimeItem.getTimeItemTypeAtr().value)),
				new BigDecimal(bonusPayTimeItem.getUseAtr().value), bonusPayTimeItem.getTimeItemName().toString());
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<BonusPayTimeItem> getBonusPayTimeItem(String companyId, BigDecimal timeItemNo) {

		Optional<KbpstBonusPayTimeItem> kbpstBonusPayTimeItem = this.queryProxy()
				.query(SELECT_BPTIMEITEM_BY_KEY, KbpstBonusPayTimeItem.class).setParameter("companyId", companyId)
				.setParameter("timeItemNo", timeItemNo).getSingle();
		if (kbpstBonusPayTimeItem.isPresent()) {
			return Optional.ofNullable(this.toBonusPayTimeItemDomain(this.queryProxy()
					.query(SELECT_BPTIMEITEM_BY_KEY, KbpstBonusPayTimeItem.class).setParameter("companyId", companyId)
					.setParameter("timeItemNo", timeItemNo).getSingle().get()));
		}
		return Optional.empty();

	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<BonusPayTimeItem> getSpecialBonusPayTimeItem(String companyId, BigDecimal timeItemNo) {

		Optional<KbpstBonusPayTimeItem> KbpstBonusPayTimeItem = this.queryProxy()
				.query(SELECT_SPEC_BPTIMEITEM_BY_KEY, KbpstBonusPayTimeItem.class).setParameter("companyId", companyId)
				.setParameter("timeItemNo", timeItemNo).getSingle();
		if (KbpstBonusPayTimeItem.isPresent()) {
			return Optional.ofNullable(this.toBonusPayTimeItemDomain(this.queryProxy()
					.query(SELECT_SPEC_BPTIMEITEM_BY_KEY, KbpstBonusPayTimeItem.class)
					.setParameter("companyId", companyId).setParameter("timeItemNo", timeItemNo).getSingle().get()));
		}
		return Optional.empty();

	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public int checkInit(String companyId) {
		return this.queryProxy().query(CHECK_INIT, Long.class).setParameter("companyId", companyId).getSingle().get()
				.intValue();
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<BonusPayTimeItem> getListBonusPayTimeItemInUse(String companyId) {
		return this.queryProxy().query(SELECT_BPTIMEITEM_INUSE_BY_COMPANYID, KbpstBonusPayTimeItem.class)
				.setParameter("companyId", companyId).getList(x -> this.toBonusPayTimeItemDomain(x));
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<BonusPayTimeItem> getListSpecialBonusPayTimeItemInUse(String companyId) {
		return this.queryProxy().query(SELECT_SPEC_BPTIMEITEM_INUSE_BY_COMPANYID, KbpstBonusPayTimeItem.class)
				.setParameter("companyId", companyId).getList(x -> this.toBonusPayTimeItemDomain(x));
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<BonusPayTimeItem> getListBonusPayTimeItemName(String companyId, List<Integer> timeItemNos) {
		List<BonusPayTimeItem> resultList = new ArrayList<>();
		CollectionUtil.split(timeItemNos, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(SELECT_BP_TIME_ITEM_NAME, KbpstBonusPayTimeItem.class)
				.setParameter("companyId", companyId)
				.setParameter("timeItemNos", subList)
				.getList(f -> toBonusPayTimeItemDomain(f)));
		});
		return resultList;
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<BonusPayTimeItem> getListSpecialBonusPayTimeItemName(String companyId, List<Integer> timeItemNos) {
		List<BonusPayTimeItem> resultList = new ArrayList<>();
		CollectionUtil.split(timeItemNos, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(SELECT_SPEC_BP_TIME_ITEM_NAME, KbpstBonusPayTimeItem.class)
					.setParameter("companyId", companyId)
					.setParameter("timeItemNos", subList)
					.getList(f -> toBonusPayTimeItemDomain(f)));
		});
		return resultList;
	}

}
