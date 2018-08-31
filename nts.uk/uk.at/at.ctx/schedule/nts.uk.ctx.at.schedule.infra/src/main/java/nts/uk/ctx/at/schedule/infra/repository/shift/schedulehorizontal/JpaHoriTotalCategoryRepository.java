package nts.uk.ctx.at.schedule.infra.repository.shift.schedulehorizontal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal.HoriTotalCNTSetDto;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriCalDaysSet;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriTotalCNTSet;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriTotalCategory;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.TotalEvalItem;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.TotalEvalOrder;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.repository.HoriTotalCategoryRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscstHoriCalDaysSetItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscstHoriCalDaysSetPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscstHoriTotalCntSetItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscstHoriTotalCntSetPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscmtHoriTotalCategoryItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscmtHoriTotalCategoryPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscmtTotalEvalItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscmtTotalEvalOrderItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.schedulehorizontal.KscmtTotalEvalOrderPK;
@Stateless
public class JpaHoriTotalCategoryRepository extends JpaRepository implements HoriTotalCategoryRepository{
	// hori total category
	private static final String SELECT_CATE_NO_WHERE = "SELECT c FROM KscmtHoriTotalCategoryItem c ";
	private static final String SELECT_CATE_ITEM = SELECT_CATE_NO_WHERE + "WHERE c.kscmtHoriTotalCategoryPK.companyId = :companyId ";
	// total eval order
	private static final String SELECT_ORDER_NO_WHERE = "SELECT c FROM KscmtTotalEvalOrderItem c ";
	private static final String SELECT_ORDER_ITEM = SELECT_ORDER_NO_WHERE + "WHERE c.kscmtTotalEvalOrderPK.companyId = :companyId ";
	private static final String SELECT_ORDER_CD_ITEM = SELECT_ORDER_ITEM + " AND c.kscmtTotalEvalOrderPK.categoryCode = :categoryCode AND c.kscmtTotalEvalOrderPK.totalItemNo = :totalItemNo";
	// total eval item
	private static final String SELECT_ITEM_NO_WHERE = "SELECT c FROM KscmtTotalEvalItem c ";
	private static final String SELECT_ITEM = SELECT_ITEM_NO_WHERE + "WHERE c.kscmtTotalEvalItemPK.companyId = :companyId";
	private static final String SELECT_ITEM_CD = SELECT_ITEM + "AND c.kscmtTotalEvalItemPK.totalItemNo = :totalItemNo";
	// hori cal days set
	private static final String SELECT_SET_NO_WHERE = "SELECT c FROM KscstHoriCalDaysSetItem c ";
	private static final String SELECT_SET_ITEM = SELECT_SET_NO_WHERE + "WHERE c.kscstHoriCalDaysSetPK.companyId = :companyId";
	private static final String SELECT_SET_ITEM_CD = SELECT_SET_ITEM + "AND c.kscstHoriCalDaysSetPK.categoryCode = :categoryCode";
	// hori total cnt set
	private static final String SELECT_CNT_NO_WHERE = "SELECT c FROM KscstHoriTotalCntSetItem c ";
	private static final String SELECT_CNT_ITEM = SELECT_CNT_NO_WHERE + "WHERE c.kscstHoriTotalCntSetPK.companyId = :companyId ";
	private static final String SELECT_CNT_ITEM_CD = SELECT_CNT_ITEM + "AND c.kscstHoriTotalCntSetPK.categoryCode = :categoryCode AND c.kscstHoriTotalCntSetPK.totalItemNo = :totalItemNo ";
	
	/**
	 * change total eval order entity to total eval order domain
	 * @param entity
	 * @return
	 * author: Hoang Yen
	 */
	private static TotalEvalOrder toDomainOrder(KscmtTotalEvalOrderItem entity){
		List<HoriTotalCNTSet> totalCntSetls = new ArrayList<>();
		KscstHoriCalDaysSetItem object = entity.horiCalDaysSet;
		HoriCalDaysSet horiCalDaysSet = object == null ? null : toDomainSet(object);
		List<KscstHoriTotalCntSetItem> lsCntSetEntity = entity.listHoriCNTSet;
		// get hori total cnt set dom list
		for(KscstHoriTotalCntSetItem obj : lsCntSetEntity){
			totalCntSetls.add(toDomainCNT(obj));
		}
		TotalEvalOrder domain = TotalEvalOrder.createFromJavaType(entity.kscmtTotalEvalOrderPK.companyId, 
																	entity.kscmtTotalEvalOrderPK.categoryCode, 
																	entity.kscmtTotalEvalOrderPK.totalItemNo, 
																	entity.dispOrder,
																	horiCalDaysSet,
																	totalCntSetls);
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
																entity.kscstHoriCalDaysSetPK.totalItemNo,
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
		List<KscstHoriTotalCntSetItem> lst = new ArrayList<>();
		if(domain.getHoriCalDaysSet() != null){
			entity.horiCalDaysSet = toEntitySet(domain.getHoriCalDaysSet());
		}
		if(domain.getCntSetls() != null){
			for(HoriTotalCNTSet item : domain.getCntSetls()){
				lst.add(toEntityCNT(item));
			}
			entity.listHoriCNTSet = lst;
		}
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
		List<KscmtTotalEvalOrderItem> lsEvalOrderEntity = entity.listTotalEvalOrder;
		List<TotalEvalOrder> domlsEvalOrderEntity = new ArrayList<>();

		// get eval order entity dom list
		for(KscmtTotalEvalOrderItem item : lsEvalOrderEntity){
			domlsEvalOrderEntity.add(toDomainOrder(item));
		}
		HoriTotalCategory domain = HoriTotalCategory.createFromJavaType(entity.kscmtHoriTotalCategoryPK.companyId, 
																		entity.kscmtHoriTotalCategoryPK.categoryCode, 
																		entity.categoryName, 
																		entity.memo,
																		domlsEvalOrderEntity);
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
	 * change  hori total CNT domain to hori total CNT entity
	 * @param domain
	 * @return
	 * author: Hoang Yen
	 */
	public static KscstHoriTotalCntSetItem toEntityCNT(HoriTotalCNTSet domain){
		val entity = new KscstHoriTotalCntSetItem();
		entity.kscstHoriTotalCntSetPK = new KscstHoriTotalCntSetPK(domain.getCompanyId(), 
																	domain.getCategoryCode(), 
																	domain.getTotalItemNo(), 
																	domain.getTotalTimeNo());
		return entity;
	}
	
	/**
	 * change hori cal day set domain to hori cal day set entity
	 * @param domain
	 * @return
	 * author: Hoang Yen
	 */
	public static KscstHoriCalDaysSetItem toEntitySet(HoriCalDaysSet domain){
		val entity = new KscstHoriCalDaysSetItem();
		entity.kscstHoriCalDaysSetPK = new KscstHoriCalDaysSetPK(domain.getCompanyId(), domain.getCategoryCode().v(), domain.getTotalItemNo().v());
		entity.halfDay = domain.getHalfDay().value;
		entity.yearHd = domain.getYearHd().value;
		entity.specialHoliday = domain.getSpecialHoliday().value;
		entity.heavyHd = domain.getHeavyHd().value;
		return entity;
	}
	
	/**
	 * change hori total CNT entity to hori total CNT domain
	 * @param entity
	 * @return
	 * author: Hoang Yen
	 */
	private static HoriTotalCNTSet toDomainCNT(KscstHoriTotalCntSetItem entity){
		HoriTotalCNTSet domain = HoriTotalCNTSet.createFromJavaType(entity.kscstHoriTotalCntSetPK.companyId, 
																	entity.kscstHoriTotalCntSetPK.categoryCode, 
																	entity.kscstHoriTotalCntSetPK.totalItemNo, 
																	entity.kscstHoriTotalCntSetPK.totalTimeNo);
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
			oldEntity.horiCalDaysSet = toEntitySet(item.getHoriCalDaysSet());
			if(item.getCntSetls() != null){
				oldEntity.listHoriCNTSet = item.getCntSetls().stream()
																.map(c -> toEntityCNT(c))
																.collect(Collectors.toList());
			}
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
	
	/**
	 * find all hori total cnt set
	 * author: Hoang Yen
	 */
	@Override
	public List<HoriTotalCNTSet> findAllCNT(String companyId) {
		return this.queryProxy().query(SELECT_CNT_ITEM, KscstHoriTotalCntSetItem.class)
				.setParameter("companyId", companyId)
				.getList(c -> toDomainCNT(c));
	}

	/**
	 * update a hori cal day set item
	 * author: Hoang Yen
	 */
	@Override
	public void updateCalDaySet(HoriCalDaysSet horiCalDaysSet) {
		KscstHoriCalDaysSetItem entity = toEntitySet(horiCalDaysSet);
		KscstHoriCalDaysSetItem oldEntity = this.queryProxy().find(entity.kscstHoriCalDaysSetPK, KscstHoriCalDaysSetItem.class).get();
		oldEntity.halfDay = entity.halfDay;
		oldEntity.yearHd = entity.yearHd;
		oldEntity.specialHoliday = entity.specialHoliday;
		oldEntity.heavyHd = entity.heavyHd;
		this.commandProxy().update(oldEntity);
	}

	/**
	 * insert a hori cal day set item
	 * author: Hoang Yen
	 */
	@Override
	public void insertCalDaySet(HoriCalDaysSet horiCalDaysSet) {
		KscstHoriCalDaysSetItem entity = toEntitySet(horiCalDaysSet);
		this.commandProxy().insert(entity);
	}
	
	/**
	 * find hori cal day set by categoryCode 
	 * author: Hoang Yen
	 */
	@Override
	public List<HoriCalDaysSet> findCalSet(String companyId, String categoryCode) {
		return this.queryProxy().query(SELECT_SET_ITEM_CD, KscstHoriCalDaysSetItem.class)
								.setParameter("companyId", companyId)
								.setParameter("categoryCode", categoryCode)
								.getList(c -> toDomainSet(c));
	}
	
	/**
	 * update cnt set list
	 * author: Hoang Yen
	 */
	@Override
	public void updateCNTSet(List<HoriTotalCNTSet> cntSets) {
		for(HoriTotalCNTSet item : cntSets){
			KscstHoriTotalCntSetItem entity = toEntityCNT(item);
			KscstHoriTotalCntSetItem oldEntity = this.queryProxy().find(entity.kscstHoriTotalCntSetPK, KscstHoriTotalCntSetItem.class).get();
			this.commandProxy().update(oldEntity);
		}
	}

	/**
	 * insert cnt set list
	 * author: Hoang Yen
	 */
	@Override
	public void insertCNTSet(List<HoriTotalCNTSet> cntSets) {
		for(HoriTotalCNTSet item : cntSets){
			KscstHoriTotalCntSetItem entity = toEntityCNT(item);
			this.commandProxy().insert(entity);
		}
	}

	/**
	 * find hori total cnt set
	 * author: Hoang Yen
	 */
	@Override
	public List<HoriTotalCNTSet> findCNTSet(String companyId, String categoryCode, int totalItemNo) { 
		return this.queryProxy().query(SELECT_CNT_ITEM_CD, KscstHoriTotalCntSetItem.class)
				.setParameter("companyId", companyId)
				.setParameter("categoryCode", categoryCode)
				.setParameter("totalItemNo", totalItemNo)
				.getList(c -> toDomainCNT(c));
	}
}
