package nts.uk.ctx.at.schedule.infra.repository.shift.schedulehorizontal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriCalDaysSet;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriTotalCategory;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.TotalEvalItem;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.TotalEvalOrder;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.repository.HoriTotalCategoryRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscstHoriCalDaysSetItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscmtHoriTotalCategoryItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscmtHoriTotalCategoryPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscmtTotalEvalItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscmtTotalEvalOrderItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscmtTotalEvalOrderPK;
@Stateless
public class JpaHoriTotalCategoryRepository extends JpaRepository implements HoriTotalCategoryRepository{
	// hori total category
	private final String SELECT_CATE_NO_WHERE = "SELECT c FROM KscmtHoriTotalCategoryItem c ";
	private final String SELECT_CATE_ITEM = SELECT_CATE_NO_WHERE + "WHERE c.kscmtHoriTotalCategoryPK.companyId = :companyId ";
	// total eval order
	private final String SELECT_ORDER_NO_WHERE = "SELECT c FROM KscmtTotalEvalOrderItem c ";
	private final String SELECT_ORDER_ITEM = SELECT_ORDER_NO_WHERE + "WHERE c.kscmtTotalEvalOrderPK.companyId = :companyId ";
	private final String SELECT_ORDER_CD_ITEM = SELECT_ORDER_ITEM + " AND c.kscmtTotalEvalOrderPK.categoryCode = :categoryCode AND c.kscmtTotalEvalOrderPK.totalItemNo = :totalItemNo";
	// total eval item
	private final String SELECT_ITEM_NO_WHERE = "SELECT c FROM KscmtTotalEvalItem c ";
	private final String SELECT_ITEM = SELECT_ITEM_NO_WHERE + "WHERE c.kscmtTotalEvalItemPK.companyId = :companyId";
	private final String SELECT_ITEM_CD = SELECT_ITEM + "AND c.kscmtTotalEvalItemPK.totalItemNo = :totalItemNo";
	// hori cal days set
	private final String SELECT_SET_NO_WHERE = "SELECT c FROM KscstHoriCalDaysSetItem c ";
	private final String SELECT_SET_ITEM = SELECT_SET_NO_WHERE + "WHERE c.kscstHoriCalDaysSetPK.companyId = :companyId";
	
	/**
	 * change total eval order entity to total eval order domain
	 * @param entity
	 * @return
	 * author: Hoang Yen
	 */
	private static TotalEvalOrder toDomainOrder(KscmtTotalEvalOrderItem entity){
		TotalEvalOrder domain = TotalEvalOrder.createFromJavaType(entity.kscmtTotalEvalOrderPK.companyId, 
																	entity.kscmtTotalEvalOrderPK.categoryCode, 
																	entity.kscmtTotalEvalOrderPK.totalItemNo, 
																	entity.dispOrder);
		return domain;
	}
	
	/**
	 * change HoriCalDaysSet entity to HoriCalDaysSet domain
	 * @param entity
	 * @return
	 * author: Hoang Yen
	 */
	private static HoriCalDaysSet toDomainSet(KscstHoriCalDaysSetItem entity){
		HoriCalDaysSet domain = HoriCalDaysSet.createFromJavaType(entity.kscstHoriCalDaysSetPK.companyId, 
																entity.kscstHoriCalDaysSetPK.categoryCode, 
																entity.halfDay, 
																entity.yearHd, 
																entity.specialHoliday, 
																entity.heavyHd);
		return domain;
	}
	
	/**
	 * change total eval order domain to total eval order entity
	 * @param domain
	 * @return
	 * author: Hoang Yen
	 */
	private static KscmtTotalEvalOrderItem toEntityOrder(TotalEvalOrder domain){
		val entity = new KscmtTotalEvalOrderItem();
		entity.kscmtTotalEvalOrderPK = new KscmtTotalEvalOrderPK(domain.getCompanyId(), 
																domain.getCategoryCode().v(), 
																domain.getTotalItemNo().v());
		entity.dispOrder = domain.getDispOrder();
		return entity;
	}
	
	/**
	 * change hori total category entity to hori total category domain
	 * @param entity
	 * @return
	 * author: Hoang Yen
	 */
	private static HoriTotalCategory toDomainCate(KscmtHoriTotalCategoryItem entity){
		List<KscmtTotalEvalOrderItem> ls = entity.listTotalEvalOrder;
		List<TotalEvalOrder> domls = new ArrayList<>();
		for(KscmtTotalEvalOrderItem item : ls){
			domls.add(toDomainOrder(item));
		}
		HoriTotalCategory domain = HoriTotalCategory.createFromJavaType(entity.kscmtHoriTotalCategoryPK.companyId, 
																		entity.kscmtHoriTotalCategoryPK.categoryCode, 
																		entity.categoryName, 
																		entity.memo, 
																		domls);
		return domain;
	}
	
	/**
	 * change  hori total category domain to hori total category entity
	 * @param domain
	 * @return
	 * author: Hoang Yen
	 */
	public static KscmtHoriTotalCategoryItem toEntityCate(HoriTotalCategory domain){
		val entity = new KscmtHoriTotalCategoryItem();
		entity.kscmtHoriTotalCategoryPK = new KscmtHoriTotalCategoryPK(domain.getCompanyId(), domain.getCategoryCode().v());
		entity.categoryName = domain.getCategoryName().v();
		entity.memo = domain.getMemo().v();
		if(domain.getTotalEvalOrders() != null){
			entity.listTotalEvalOrder = domain.getTotalEvalOrders().stream()
										.map(x -> toEntityOrder(x))
										.collect(Collectors.toList());
		}
		return entity;
	}
	
	/**
	 * change total eval item entity to total eval item domain
	 * @param entity
	 * @return
	 * author: Hoang Yen
	 */
	private static TotalEvalItem toDomainItem(KscmtTotalEvalItem entity){
		TotalEvalItem domain = TotalEvalItem.createFromJavaType(entity.kscmtTotalEvalItemPK.companyId, 
				entity.kscmtTotalEvalItemPK.totalItemNo,
				entity.totalItemName);
		return domain;
	}
	
	/**
	 * find all hori total category
	 * author: Hoang Yen
	 */
	@Override
	public List<HoriTotalCategory> findAllCate(String companyId) {
		return this.queryProxy().query(SELECT_CATE_ITEM, KscmtHoriTotalCategoryItem.class)
								.setParameter("companyId", companyId)
								.getList(c -> toDomainCate(c));
	}
	
	/**
	 * update hori total category
	 * author: Hoang Yen
	 */
	@Override
	public void updateCate(HoriTotalCategory horiTotalCategory) {
		KscmtHoriTotalCategoryItem entity = toEntityCate(horiTotalCategory);
		KscmtHoriTotalCategoryItem oldEntity = this.queryProxy().find(entity.kscmtHoriTotalCategoryPK, KscmtHoriTotalCategoryItem.class).get();
		oldEntity.categoryName = entity.categoryName;
		oldEntity.memo = entity.memo;
		if(horiTotalCategory.getTotalEvalOrders() != null){
			oldEntity.listTotalEvalOrder = horiTotalCategory.getTotalEvalOrders().stream()
											.map(x -> toEntityOrder(x))
											.collect(Collectors.toList());
		}
		this.commandProxy().update(oldEntity);
	}
	
	/**
	 * insert hori total category
	 * author: Hoang Yen
	 */
	@Override
	public void insertCate(HoriTotalCategory horiTotalCategory) {
		KscmtHoriTotalCategoryItem entity = toEntityCate(horiTotalCategory);
		this.commandProxy().insert(entity);
	}
	
	/**
	 * delete hori total category
	 * author: Hoang Yen
	 */
	@Override
	public void deleteCate(String companyId, String categoryCode) {
		KscmtHoriTotalCategoryPK kscstHoriTotalCategoryPK = new KscmtHoriTotalCategoryPK(companyId, categoryCode);
		this.commandProxy().remove(KscmtHoriTotalCategoryItem.class, kscstHoriTotalCategoryPK);
	}
	
	/**
	 * find hori total category by code
	 * author: Hoang Yen
	 */
	@Override
	public Optional<HoriTotalCategory> findCateByCode(String companyId, String categoryCode) {
		return this.queryProxy().find(new KscmtHoriTotalCategoryPK(companyId, categoryCode), KscmtHoriTotalCategoryItem.class)
								.map(c -> toDomainCate(c));
	}
	
	/**
	 * find all total eval order
	 * author: Hoang Yen
	 */
	@Override
	public List<TotalEvalOrder> findAllOrder(String companyId) {
		return this.queryProxy().query(SELECT_ORDER_ITEM, KscmtTotalEvalOrderItem.class)
										.setParameter("companyId", companyId)
										.getList(c -> toDomainOrder(c));
	}
	
	/**
	 * update list total eval order
	 * author: Hoang Yen
	 */
	@Override
	public void updateOrder(List<TotalEvalOrder> totalEvalOrders) {
		for(TotalEvalOrder item : totalEvalOrders){
			KscmtTotalEvalOrderItem entity = toEntityOrder(item);
			KscmtTotalEvalOrderItem oldEntity = this.queryProxy().find(entity.kscmtTotalEvalOrderPK, KscmtTotalEvalOrderItem.class).get();
			oldEntity.dispOrder = entity.dispOrder;
			this.commandProxy().update(oldEntity);
		}
	}

	/**
	 * insert list total eval order
	 * author: Hoang Yen
	 */
	@Override
	public void insertOrder(List<TotalEvalOrder> totalEvalOrders) {
		for(TotalEvalOrder item : totalEvalOrders){
			KscmtTotalEvalOrderItem entity = toEntityOrder(item);
			this.commandProxy().insert(entity);
		}
	}

	/**
	 * find a total eval order by code
	 * author: Hoang Yen
	 */
	@Override
	public List<TotalEvalOrder> findOrder(String companyId, String categoryCode, Integer totalItemNo) {
		return this.queryProxy().query(SELECT_ORDER_CD_ITEM, KscmtTotalEvalOrderItem.class)
				.setParameter("companyId", companyId)
				.setParameter("categoryCode", categoryCode)
				.setParameter("totalItemNo", totalItemNo)
				.getList(c -> toDomainOrder(c));
	}

	/**
	 * find all eval item
	 * author: Hoang Yen
	 */
	@Override
	public List<TotalEvalItem> findAllItem(String companyId) {
		return this.queryProxy().query(SELECT_ITEM, KscmtTotalEvalItem.class)
				.setParameter("companyId", companyId)
				.getList(c -> toDomainItem(c));
	}
	
	/**
	 * find eval item by totalItemNo
	 * author: Hoang Yen
	 */
	@Override
	public List<TotalEvalItem> findEvalItem(String companyId, int totalItemNo) {
		return this.queryProxy().query(SELECT_ITEM_CD, KscmtTotalEvalItem.class)
				.setParameter("companyId", companyId)
				.setParameter("totalItemNo", totalItemNo)
				.getList(c -> toDomainItem(c));
	}

	/**
	 * find all hori cal day set
	 * author: Hoang Yen
	 */
	@Override
	public List<HoriCalDaysSet> findAllCal(String companyId) {
		return this.queryProxy().query(SELECT_SET_ITEM, KscstHoriCalDaysSetItem.class)
				.setParameter("companyId", companyId)
				.getList(c -> toDomainSet(c));
	}
}
