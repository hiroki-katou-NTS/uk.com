/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pereg.infra.repository.mastercopy.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationUtils;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.command.CommandProxy;
import nts.arc.layer.infra.data.query.QueryProxy;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtDateRangeItem;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtg;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtgOrder;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtPerInfoItem;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtPerInfoItemOrder;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PersonalInfoDefCopyHandler.
 */
public class PersonalInfoDefCopyHandler extends DataCopyHandler {

	/** The Constant FIND_ALL_PERSONAL_INFO_CATEGORY. */
	private static final String FIND_ALL_PERSONAL_INFO_CATEGORY = "SELECT p FROM PpemtPerInfoCtg p WHERE p.cid =:cid";

	/** The Constant FIND_ALL_PERSONAL_INFO_CATEGORY_ORDER. */
	private static final String FIND_ALL_PERSONAL_INFO_CATEGORY_ORDER = "SELECT p FROM PpemtPerInfoCtgOrder p "
			+ "WHERE p.cid =:cid and p.ppemtPerInfoCtgPK.perInfoCtgId IN :perInfoCtgIdList";

	/** The Constant FIND_ALL_PERSONAL_INFO_ITEM. */
	private static final String FIND_ALL_PERSONAL_INFO_ITEM = "SELECT p FROM PpemtPerInfoItem p "
			+ "WHERE p.perInfoCtgId IN :perInfoCtgIdList";

	/** The Constant FIND_ALL_PERSONAL_INFO_ITEM_ORDER. */
	private static final String FIND_ALL_PERSONAL_INFO_ITEM_ORDER = "SELECT p FROM PpemtPerInfoItemOrder p "
			+ "WHERE p.perInfoCtgId IN :perInfoCtgIdList";

	/** The Constant FIND_ALL_DATE_RANGE_ITEM. */
	private static final String FIND_ALL_DATE_RANGE_ITEM = "SELECT d FROM PpemtDateRangeItem d "
			+ "WHERE d.ppemtPerInfoCtgPK.perInfoCtgId IN :perInfoCtgIdList";

	/** The Constant FIND_ALL_PERSONAL_INFO_ITEM_ON_PER_INFO_CTG_ID. */
	private static final String FIND_ALL_PERSONAL_INFO_ITEM_ON_PER_INFO_CTG_ID = "SELECT i FROM PpemtPerInfoItem i "
			+ "WHERE i.perInfoCtgId = :perInfoCtgId";

	/** The copy method. */
	private int copyMethod;

	/** The company id. */
	private String companyId;

	/** The query proxy. */
	QueryProxy queryProxy;

	/** The command proxy. */
	CommandProxy commandProxy;

	/**
	 * Instantiates a new personal info def copy handler.
	 *
	 * @param repository
	 *            the repository
	 * @param copyMethod
	 *            the copy method
	 * @param companyId
	 *            the company id
	 */
	public PersonalInfoDefCopyHandler(JpaRepository repository, int copyMethod, String companyId) {
		this.copyMethod = copyMethod;
		this.companyId = companyId;
		this.queryProxy = repository.queryProxy();
		this.commandProxy = repository.commandProxy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.handler.handler.DataCopyHandler#doCopy()
	 */
	@Override
	public Map<String, String> doCopy() {
		String sourceCid = AppContexts.user().zeroCompanyIdInContract();
		String targetCid = companyId;

		switch (copyMethod) {
		case REPLACE_ALL:
			// Delete all old data
			return copyMasterData(sourceCid, targetCid);
		case ADD_NEW:
			// Insert Data
		case DO_NOTHING:
			// Do nothing
		default:
			return Collections.emptyMap();
		}
	}

	/**
	 * Find all per info ctg by cid.
	 *
	 * @param cid
	 *            the cid
	 * @return the list
	 */
	private List<PpemtPerInfoCtg> findAllPerInfoCtgByCid(String cid) {
		return this.queryProxy.query(FIND_ALL_PERSONAL_INFO_CATEGORY, PpemtPerInfoCtg.class)
				.setParameter("cid", cid).getList();
	}

	/**
	 * Find all per info ctg order by cid.
	 *
	 * @param cid
	 *            the cid
	 * @param personalInfoCatId
	 *            the personal info cat id
	 * @return the list
	 */
	private List<PpemtPerInfoCtgOrder> findAllPerInfoCtgOrderByCid(String cid,
			Set<String> personalInfoCatId) {
		return this.queryProxy
				.query(FIND_ALL_PERSONAL_INFO_CATEGORY_ORDER, PpemtPerInfoCtgOrder.class)
				.setParameter("cid", cid).setParameter("perInfoCtgIdList", personalInfoCatId)
				.getList();
	}

	/**
	 * Find all ppemt per info item by cat id.
	 *
	 * @param personalInfoCatId
	 *            the personal info cat id
	 * @return the list
	 */
	private List<PpemtPerInfoItem> findAllPpemtPerInfoItemByCatId(Set<String> personalInfoCatId) {
		return this.queryProxy.query(FIND_ALL_PERSONAL_INFO_ITEM, PpemtPerInfoItem.class)
				.setParameter("perInfoCtgIdList", personalInfoCatId).getList();
	}

	/**
	 * Find all ppemt per info item by per info ctg id.
	 *
	 * @param perInfoCtgId
	 *            the per info ctg id
	 * @return the list
	 */
	private List<PpemtPerInfoItem> findAllPpemtPerInfoItemByPerInfoCtgId(String perInfoCtgId) {
		return this.queryProxy
				.query(FIND_ALL_PERSONAL_INFO_ITEM_ON_PER_INFO_CTG_ID, PpemtPerInfoItem.class)
				.setParameter("perInfoCtgId", perInfoCtgId).getList();
	}

	/**
	 * Find all per info item order by cat id.
	 *
	 * @param personalInfoCatId
	 *            the personal info cat id
	 * @return the list
	 */
	private List<PpemtPerInfoItemOrder> findAllPerInfoItemOrderByCatId(
			Set<String> personalInfoCatId) {
		return this.queryProxy.query(FIND_ALL_PERSONAL_INFO_ITEM_ORDER, PpemtPerInfoItemOrder.class)
				.setParameter("perInfoCtgIdList", personalInfoCatId).getList();
	}

	/**
	 * Find alldate range item by cat id.
	 *
	 * @param personalInfoCatId
	 *            the personal info cat id
	 * @return the list
	 */
	private List<PpemtDateRangeItem> findAlldateRangeItemByCatId(Set<String> personalInfoCatId) {
		return this.queryProxy.query(FIND_ALL_DATE_RANGE_ITEM, PpemtDateRangeItem.class)
				.setParameter("perInfoCtgIdList", personalInfoCatId).getList();
	}

	/**
	 * Copy master data.
	 *
	 * @param sourceCid
	 *            the source cid
	 * @param targetCid
	 *            the target cid
	 * @return the map
	 */
	public Map<String, String> copyMasterData(String sourceCid, String targetCid) {
		Map<String, String> transIdMap = new HashMap<>();
		// Get data company zero
		List<PpemtPerInfoCtg> sPerInfoCtgEntities = findAllPerInfoCtgByCid(sourceCid);
		List<PpemtPerInfoCtgOrder> sPerInfoCtgOrderEntities = new ArrayList<>();
		List<PpemtPerInfoItem> sPerInfoItemEntities = new ArrayList<>();
		List<PpemtPerInfoItemOrder> sPerInfoItemOrderEntities = new ArrayList<>();
		List<PpemtDateRangeItem> sDateRangeItemEntities = new ArrayList<>();
		Set<String> sourcePersonalInfoCatId = new TreeSet<String>();
		if (!sPerInfoCtgEntities.isEmpty()) {
			sourcePersonalInfoCatId = sPerInfoCtgEntities.stream()
					.map(ppemtPerInfoCtg -> ppemtPerInfoCtg.ppemtPerInfoCtgPK.perInfoCtgId)
					.collect(Collectors.toSet());
			sPerInfoCtgOrderEntities = findAllPerInfoCtgOrderByCid(sourceCid,
					sourcePersonalInfoCatId);
			sPerInfoItemEntities = findAllPpemtPerInfoItemByCatId(sourcePersonalInfoCatId);
			sPerInfoItemOrderEntities = findAllPerInfoItemOrderByCatId(sourcePersonalInfoCatId);
			sDateRangeItemEntities = findAlldateRangeItemByCatId(sourcePersonalInfoCatId);
		}

		// Get data company target
		List<PpemtPerInfoCtg> tPerInfoCtgEntities = findAllPerInfoCtgByCid(targetCid);
		List<PpemtPerInfoCtgOrder> tPerInfoCtgOrderEntities = new ArrayList<>();
		List<PpemtPerInfoItem> tPerInfoItemEntities = new ArrayList<>();
		List<PpemtPerInfoItemOrder> tPerInfoItemOrderEntities = new ArrayList<>();
		List<PpemtDateRangeItem> tPateRangeItemEntities = new ArrayList<>();
		if (!tPerInfoCtgEntities.isEmpty()) {
			Set<String> targetPersonalInfoCatId = tPerInfoCtgEntities.stream()
					.map(ppemtPerInfoCtg -> ppemtPerInfoCtg.ppemtPerInfoCtgPK.perInfoCtgId)
					.collect(Collectors.toSet());
			tPerInfoCtgOrderEntities = findAllPerInfoCtgOrderByCid(targetCid,
					targetPersonalInfoCatId);
			tPerInfoItemEntities = findAllPpemtPerInfoItemByCatId(targetPersonalInfoCatId);
			tPerInfoItemOrderEntities = findAllPerInfoItemOrderByCatId(targetPersonalInfoCatId);
			tPateRangeItemEntities = findAlldateRangeItemByCatId(targetPersonalInfoCatId);
		}

		// group by personal info item def Id
		final List<PpemtPerInfoCtg> s1 = new ArrayList<>();
		final List<PpemtPerInfoCtgOrder> s2 = new ArrayList<>();
		final List<PpemtPerInfoItem> s3 = new ArrayList<>();
		final List<PpemtPerInfoItemOrder> s4 = new ArrayList<>();
		final List<PpemtDateRangeItem> s5 = new ArrayList<>();

		// 取得できなかった場合（会社ID ＝ Input．会社IDの個人情報定義）//ko lấy được
		if (CollectionUtil.isEmpty(tPerInfoCtgEntities)
				&& CollectionUtil.isEmpty(tPerInfoCtgOrderEntities)
				&& CollectionUtil.isEmpty(tPerInfoItemEntities)
				&& CollectionUtil.isEmpty(tPerInfoItemOrderEntities)
				&& CollectionUtil.isEmpty(tPateRangeItemEntities)) {
			// group by personal info category Id
			Map<String, List<PpemtPerInfoCtg>> groupPersonalInfoCatByCatId = new HashMap<>();
			Map<String, List<PpemtPerInfoCtgOrder>> groupPersonalInfoCatOrderByCatId = new HashMap<>();
			Map<String, List<PpemtPerInfoItem>> groupPersonalInfoItemByCatId = new HashMap<>();
			Map<String, List<PpemtPerInfoItemOrder>> groupPersonalInfoItemOrderByCatId = new HashMap<>();
			Map<String, List<PpemtDateRangeItem>> groupDateRangeByCatId = new HashMap<>();
			if (!CollectionUtil.isEmpty(sPerInfoCtgEntities)) {
				groupPersonalInfoCatByCatId = sPerInfoCtgEntities.stream()
						.collect(Collectors.groupingBy(o -> o.ppemtPerInfoCtgPK.perInfoCtgId));
			}
			if (!CollectionUtil.isEmpty(sPerInfoCtgOrderEntities)) {
				groupPersonalInfoCatOrderByCatId = sPerInfoCtgOrderEntities.stream()
						.collect(Collectors.groupingBy(o -> o.ppemtPerInfoCtgPK.perInfoCtgId));
			}
			if (!CollectionUtil.isEmpty(sPerInfoItemEntities)) {
				groupPersonalInfoItemByCatId = sPerInfoItemEntities.stream()
						.collect(Collectors.groupingBy(o -> o.perInfoCtgId));
			}
			if (!CollectionUtil.isEmpty(sPerInfoItemOrderEntities)) {
				groupPersonalInfoItemOrderByCatId = sPerInfoItemOrderEntities.stream()
						.collect(Collectors.groupingBy(o -> o.perInfoCtgId));
			}
			if (!CollectionUtil.isEmpty(sDateRangeItemEntities)) {
				groupDateRangeByCatId = sDateRangeItemEntities.stream()
						.collect(Collectors.groupingBy(o -> o.ppemtPerInfoCtgPK.perInfoCtgId));
			}

			// create
			for (String catId : sourcePersonalInfoCatId) {
				String newCatId = UUID.randomUUID().toString();
				for (PpemtPerInfoCtg perInfoCtgEntity : groupPersonalInfoCatByCatId.get(catId)) {
					PpemtPerInfoCtg cloneObject = SerializationUtils.clone(perInfoCtgEntity);
					cloneObject.ppemtPerInfoCtgPK.perInfoCtgId = newCatId;
					cloneObject.cid = targetCid;
					s1.add(cloneObject);
				}

				if (!CollectionUtil.isEmpty(groupPersonalInfoCatOrderByCatId.get(catId))) {
					for (PpemtPerInfoCtgOrder perInfoCtgOrderEntity : groupPersonalInfoCatOrderByCatId
							.get(catId)) {
						PpemtPerInfoCtgOrder cloneObject = SerializationUtils
								.clone(perInfoCtgOrderEntity);
						cloneObject.cid = targetCid;
						cloneObject.ppemtPerInfoCtgPK.perInfoCtgId = newCatId;
						s2.add(cloneObject);
					}
				}

				if (!CollectionUtil.isEmpty(groupPersonalInfoItemByCatId.get(catId))) {
					for (PpemtPerInfoItem perInfoItemEntity : groupPersonalInfoItemByCatId
							.get(catId)) {
						PpemtPerInfoItem cloneObject = SerializationUtils.clone(perInfoItemEntity);
						cloneObject.perInfoCtgId = newCatId;
						s3.add(cloneObject);
					}
				}

				if (!CollectionUtil.isEmpty(groupPersonalInfoItemOrderByCatId.get(catId))) {
					for (PpemtPerInfoItemOrder perInfoItemOrderEntity : groupPersonalInfoItemOrderByCatId
							.get(catId)) {
						PpemtPerInfoItemOrder cloneObject = SerializationUtils
								.clone(perInfoItemOrderEntity);
						cloneObject.perInfoCtgId = newCatId;
						s4.add(cloneObject);
					}
				}

				if (!CollectionUtil.isEmpty(groupDateRangeByCatId.get(catId))) {
					for (PpemtDateRangeItem dateRangeItem : groupDateRangeByCatId.get(catId)) {
						PpemtDateRangeItem cloneObject = SerializationUtils.clone(dateRangeItem);
						cloneObject.ppemtPerInfoCtgPK.perInfoCtgId = newCatId;
						s5.add(cloneObject);
					}
				}
			}

			Map<String, PpemtPerInfoItem> groupPersonalInfoItemByDefId = new HashMap<>();
			if (!CollectionUtil.isEmpty(s3))
				groupPersonalInfoItemByDefId = s3.stream().collect(Collectors.toMap(
						o -> o.ppemtPerInfoItemPK.perInfoItemDefId, perInfoItem -> perInfoItem));
			Map<String, PpemtPerInfoItemOrder> groupPersonalInfoItemOrderByDefId = new HashMap<>();
			if (!CollectionUtil.isEmpty(s4))
				groupPersonalInfoItemOrderByDefId = s4.stream()
						.collect(Collectors.toMap(o -> o.ppemtPerInfoItemPK.perInfoItemDefId,
								perInfoItemOrder -> perInfoItemOrder));
			Map<String, PpemtDateRangeItem> groupDateRangeItemByDefId = new HashMap<>();
			if (!CollectionUtil.isEmpty(s5))
				groupDateRangeItemByDefId = s5.stream().collect(
						Collectors.toMap(o -> o.startDateItemId, dateRangeItem -> dateRangeItem));

			final List<PpemtPerInfoItem> s33 = new ArrayList<>();
			final List<PpemtPerInfoItemOrder> s44 = new ArrayList<>();
			final List<PpemtDateRangeItem> s55 = new ArrayList<>();

			Set<String> sourcePersonalInfoItemDefId = s3.stream()
					.map(ppemtPerInfoItem -> ppemtPerInfoItem.ppemtPerInfoItemPK.perInfoItemDefId)
					.collect(Collectors.toSet());

			Map<String, String> perInfoItemMapId = new HashMap<String, String>();

			for (String defId : sourcePersonalInfoItemDefId) {
				String newDefId = UUID.randomUUID().toString();
				transIdMap.put(defId, newDefId);

				PpemtPerInfoItem perInfoItemEntity = groupPersonalInfoItemByDefId.get(defId);
				PpemtPerInfoItem cloneObject1 = SerializationUtils.clone(perInfoItemEntity);
				cloneObject1.ppemtPerInfoItemPK.perInfoItemDefId = newDefId;
				s33.add(cloneObject1);

				PpemtPerInfoItemOrder perInfoItemOrderEntity = groupPersonalInfoItemOrderByDefId
						.get(defId);
				if (perInfoItemOrderEntity == null)
					continue;
				PpemtPerInfoItemOrder cloneObject2 = SerializationUtils
						.clone(perInfoItemOrderEntity);
				cloneObject2.ppemtPerInfoItemPK.perInfoItemDefId = newDefId;
				s44.add(cloneObject2);

				perInfoItemMapId.put(defId, newDefId);
			}

			for (String defId : sourcePersonalInfoItemDefId) {
				PpemtDateRangeItem dateRangeItemEntity = groupDateRangeItemByDefId.get(defId);

				if (dateRangeItemEntity == null)
					continue;

				PpemtDateRangeItem cloneObject3 = SerializationUtils.clone(dateRangeItemEntity);
				cloneObject3.startDateItemId = perInfoItemMapId.get(defId);
				cloneObject3.endDateItemId = perInfoItemMapId.get(cloneObject3.endDateItemId);
				cloneObject3.dateRangeItemId = perInfoItemMapId.get(cloneObject3.dateRangeItemId);
				s55.add(cloneObject3);
			}

			// insert new
			this.commandProxy.insertAll(s1);
			this.commandProxy.insertAll(s2);
			this.commandProxy.insertAll(s33);
			this.commandProxy.insertAll(s44);
			this.commandProxy.insertAll(s55);
		} else {// 取得できた場合（会社ID ＝ Input．会社IDの個人情報定義）//Lấy được
			// group by personal info category Id
			Map<String, PpemtPerInfoCtg> sgroupPersonalInfoCatByCatCd = new HashMap<>();
			if (!CollectionUtil.isEmpty(sPerInfoCtgEntities)) {
				sgroupPersonalInfoCatByCatCd = sPerInfoCtgEntities.stream().collect(Collectors
						.toMap(perInfoCtg -> perInfoCtg.categoryCd, perInfoCtg -> perInfoCtg));
			}
			Map<String, PpemtPerInfoCtg> tgroupPersonalInfoCatByCatCd = new HashMap<>();
			if (!CollectionUtil.isEmpty(sPerInfoCtgEntities)) {
				tgroupPersonalInfoCatByCatCd = tPerInfoCtgEntities.stream().collect(Collectors
						.toMap(perInfoCtg -> perInfoCtg.categoryCd, perInfoCtg -> perInfoCtg));
			}

			Set<String> sourcePersonalInfoCatCd = sPerInfoCtgEntities.stream()
					.map(ppemtPerInfoCtg -> ppemtPerInfoCtg.categoryCd).collect(Collectors.toSet());
			// overwrite
			for (String catCd : sourcePersonalInfoCatCd) {
				// 1 update overwrite for PpemtPerInfoCtg
				PpemtPerInfoCtg src = sgroupPersonalInfoCatByCatCd.get(catCd);
				PpemtPerInfoCtg des = tgroupPersonalInfoCatByCatCd.get(catCd);
				if (src == null || des == null)
					continue;
				des.categoryName = src.categoryName;
				des.abolitionAtr = src.abolitionAtr;
				this.commandProxy.update(des);

				// 2 update overwrite for PpemtPerInfoItem
				// LOGGER.info("Test Event CMM001: " + sourceCid + "-" + catCd);
				Map<String, PpemtPerInfoItem> sourcePerInfoItems = findAllPpemtPerInfoItemByPerInfoCtgId(
						src.ppemtPerInfoCtgPK.perInfoCtgId).stream()
								.collect(Collectors.toMap(o -> o.itemCd, o -> o));
				Map<String, PpemtPerInfoItem> destPerInfoItems = findAllPpemtPerInfoItemByPerInfoCtgId(
						des.ppemtPerInfoCtgPK.perInfoCtgId).stream()
								.collect(Collectors.toMap(o -> o.itemCd, o -> o));

				if (!CollectionUtil.isEmpty(sourcePerInfoItems.keySet())) {
					for (String itemCd : sourcePerInfoItems.keySet()) {
						PpemtPerInfoItem srcPerInfoItem = sourcePerInfoItems.get(itemCd);
						PpemtPerInfoItem desPerInfoItem = destPerInfoItems.get(itemCd);
						if (srcPerInfoItem == null || desPerInfoItem == null)
							continue;
						desPerInfoItem.itemCd = itemCd;
						desPerInfoItem.itemName = srcPerInfoItem.itemName;
						desPerInfoItem.abolitionAtr = srcPerInfoItem.abolitionAtr;
						desPerInfoItem.requiredAtr = srcPerInfoItem.requiredAtr;
						this.commandProxy.update(desPerInfoItem);
					}
				}
			}
		}

		return transIdMap;
	}
}
