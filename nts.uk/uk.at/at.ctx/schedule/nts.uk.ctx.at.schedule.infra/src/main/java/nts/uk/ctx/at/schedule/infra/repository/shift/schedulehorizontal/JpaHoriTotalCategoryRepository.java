package nts.uk.ctx.at.schedule.infra.repository.shift.schedulehorizontal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriTotalCategory;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.TotalEvalItem;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.TotalEvalOrder;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.repository.HoriTotalCategoryRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscstHoriTotalCategoryItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscstHoriTotalCategoryPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscstTotalEvalItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscstTotalEvalOrderItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscstTotalEvalOrderPK;
@Stateless
public class JpaHoriTotalCategoryRepository extends JpaRepository implements HoriTotalCategoryRepository{
	// hori total category
	private final String SELECT_CATE_NO_WHERE = "SELECT c FROM KscstHoriTotalCategoryItem c ";
	private final String SELECT_CATE_ITEM = SELECT_CATE_NO_WHERE + "WHERE c.kscstHoriTotalCategoryPK.companyId = :companyId ";
	// total eval order
	private final String SELECT_ORDER_NO_WHERE = "SELECT c FROM KscstTotalEvalOrderItem c ";
	private final String SELECT_ORDER_ITEM = SELECT_ORDER_NO_WHERE + "WHERE c.kscstTotalEvalOrderPK.companyId = :companyId ";
	private final String SELECT_ORDER_CD_ITEM = SELECT_ORDER_ITEM + " AND c.kscstTotalEvalOrderPK.categoryCode = :categoryCode AND c.kscstTotalEvalOrderPK.totalItemNo = :totalItemNo";
	// total eval item
	private final String SELECT_ITEM_NO_WHERE = "SELECT c FROM KscstTotalEvalItem c ";
	private final String SELECT_ITEM = SELECT_ITEM_NO_WHERE + "WHERE c.kscstTotalEvalItemPK.companyId = :companyId";
	private final String SELECT_ITEM_CD = SELECT_ITEM + "AND c.kscstTotalEvalItemPK.totalItemNo = :totalItemNo";
	/**
	 * change total eval order entity to total eval order domain
	 * @param entity
	 * @return
	 * author: Hoang Yen
	 */
	private static TotalEvalOrder toDomainOrder(KscstTotalEvalOrderItem entity){
		TotalEvalOrder domain = TotalEvalOrder.createFromJavaType(entity.kscstTotalEvalOrderPK.companyId, 
																	entity.kscstTotalEvalOrderPK.categoryCode, 
																	entity.kscstTotalEvalOrderPK.totalItemNo, 
																	entity.dispOrder);
		return domain;
	}
	
	/**
	 * change total eval order domain to total eval order entity
	 * @param domain
	 * @return
	 * author: Hoang Yen
	 */
	private static KscstTotalEvalOrderItem toEntityOrder(TotalEvalOrder domain){
		val entity = new KscstTotalEvalOrderItem();
		entity.kscstTotalEvalOrderPK = new KscstTotalEvalOrderPK(domain.getCompanyId(), 
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
	private static HoriTotalCategory toDomainCate(KscstHoriTotalCategoryItem entity){
		List<KscstTotalEvalOrderItem> ls = entity.listTotalEvalOrder;
		List<TotalEvalOrder> domls = new ArrayList<>();
		for(KscstTotalEvalOrderItem item : ls){
			domls.add(toDomainOrder(item));
		}
		HoriTotalCategory domain = HoriTotalCategory.createFromJavaType(entity.kscstHoriTotalCategoryPK.companyId, 
																		entity.kscstHoriTotalCategoryPK.categoryCode, 
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
	public static KscstHoriTotalCategoryItem toEntityCate(HoriTotalCategory domain){
		val entity = new KscstHoriTotalCategoryItem();
		entity.kscstHoriTotalCategoryPK = new KscstHoriTotalCategoryPK(domain.getCompanyId(), domain.getCategoryCode().v());
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
	private static TotalEvalItem toDomainItem(KscstTotalEvalItem entity){
		TotalEvalItem domain = TotalEvalItem.createFromJavaType(entity.kscstTotalEvalItemPK.companyId, 
				entity.kscstTotalEvalItemPK.totalItemNo,
				entity.totalItemName);
		return domain;
	}
	
	/**
	 * find all hori total category
	 * author: Hoang Yen
	 */
	@Override
	public List<HoriTotalCategory> findAllCate(String companyId) {
		return this.queryProxy().query(SELECT_CATE_ITEM, KscstHoriTotalCategoryItem.class)
								.setParameter("companyId", companyId)
								.getList(c -> toDomainCate(c));
	}
	
	/**
	 * update hori total category
	 * author: Hoang Yen
	 */
	@Override
	public void updateCate(HoriTotalCategory horiTotalCategory) {
		KscstHoriTotalCategoryItem entity = toEntityCate(horiTotalCategory);
		KscstHoriTotalCategoryItem oldEntity = this.queryProxy().find(entity.kscstHoriTotalCategoryPK, KscstHoriTotalCategoryItem.class).get();
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
		KscstHoriTotalCategoryItem entity = toEntityCate(horiTotalCategory);
		this.commandProxy().insert(entity);
	}
	
	/**
	 * delete hori total category
	 * author: Hoang Yen
	 */
	@Override
	public void deleteCate(String companyId, String categoryCode) {
		KscstHoriTotalCategoryPK kscstHoriTotalCategoryPK = new KscstHoriTotalCategoryPK(companyId, categoryCode);
		this.commandProxy().remove(KscstHoriTotalCategoryItem.class, kscstHoriTotalCategoryPK);
	}
	
	/**
	 * find hori total category by code
	 * author: Hoang Yen
	 */
	@Override
	public Optional<HoriTotalCategory> findCateByCode(String companyId, String categoryCode) {
		return this.queryProxy().find(new KscstHoriTotalCategoryPK(companyId, categoryCode), KscstHoriTotalCategoryItem.class)
								.map(c -> toDomainCate(c));
	}
	
	/**
	 * find all total eval order
	 * author: Hoang Yen
	 */
	@Override
	public List<TotalEvalOrder> findAllOrder(String companyId) {
		return this.queryProxy().query(SELECT_ORDER_ITEM, KscstTotalEvalOrderItem.class)
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
			KscstTotalEvalOrderItem entity = toEntityOrder(item);
			KscstTotalEvalOrderItem oldEntity = this.queryProxy().find(entity.kscstTotalEvalOrderPK, KscstTotalEvalOrderItem.class).get();
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
			KscstTotalEvalOrderItem entity = toEntityOrder(item);
			this.commandProxy().insert(entity);
		}
	}

	/**
	 * find a total eval order by code
	 * author: Hoang Yen
	 */
	@Override
	public List<TotalEvalOrder> findOrder(String companyId, String categoryCode, Integer totalItemNo) {
		return this.queryProxy().query(SELECT_ORDER_CD_ITEM, KscstTotalEvalOrderItem.class)
				.setParameter("companyId", companyId)
				.setParameter("categoryCode", categoryCode)
				.setParameter("totalItemNo", totalItemNo)
				.getList(c -> toDomainOrder(c));
	}

	@Override
	public List<TotalEvalItem> findAllItem(String companyId) {
		return this.queryProxy().query(SELECT_ITEM, KscstTotalEvalItem.class)
				.setParameter("companyId", companyId)
				.getList(c -> toDomainItem(c));
	}

	@Override
	public List<TotalEvalItem> findEvalItem(String companyId, int totalItemNo) {
		return this.queryProxy().query(SELECT_ITEM_CD, KscstTotalEvalItem.class)
				.setParameter("companyId", companyId)
				.setParameter("totalItemNo", totalItemNo)
				.getList(c -> toDomainItem(c));
	}
}
