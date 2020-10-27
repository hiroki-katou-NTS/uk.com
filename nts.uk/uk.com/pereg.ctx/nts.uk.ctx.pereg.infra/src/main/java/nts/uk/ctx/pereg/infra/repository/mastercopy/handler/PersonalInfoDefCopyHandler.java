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
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtItemDateRange;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtCtg;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtCtgSort;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtItem;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtItemSort;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PersonalInfoDefCopyHandler.
 */
public class PersonalInfoDefCopyHandler extends DataCopyHandler {

	/** The Constant FIND_ALL_PERSONAL_INFO_CATEGORY. */
	private static final String FIND_ALL_PERSONAL_INFO_CATEGORY = "SELECT p FROM PpemtCtg p WHERE p.cid =:cid";

	/** The Constant FIND_ALL_PERSONAL_INFO_CATEGORY_ORDER. */
	private static final String FIND_ALL_PERSONAL_INFO_CATEGORY_ORDER = "SELECT p FROM PpemtCtgSort p "
			+ "WHERE p.cid =:cid and p.ppemtCtgPK.perInfoCtgId IN :perInfoCtgIdList";

	/** The Constant FIND_ALL_PERSONAL_INFO_ITEM. */
	private static final String FIND_ALL_PERSONAL_INFO_ITEM = "SELECT p FROM PpemtItem p "
			+ "WHERE p.perInfoCtgId IN :perInfoCtgIdList";

	/** The Constant FIND_ALL_PERSONAL_INFO_ITEM_ORDER. */
	private static final String FIND_ALL_PERSONAL_INFO_ITEM_ORDER = "SELECT p FROM PpemtItemSort p "
			+ "WHERE p.perInfoCtgId IN :perInfoCtgIdList";

	/** The Constant FIND_ALL_DATE_RANGE_ITEM. */
	private static final String FIND_ALL_DATE_RANGE_ITEM = "SELECT d FROM PpemtItemDateRange d "
			+ "WHERE d.ppemtCtgPK.perInfoCtgId IN :perInfoCtgIdList";

	/** The Constant FIND_ALL_PERSONAL_INFO_ITEM_ON_PER_INFO_CTG_ID. */
	private static final String FIND_ALL_PERSONAL_INFO_ITEM_ON_PER_INFO_CTG_ID = "SELECT i FROM PpemtItem i "
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
	private List<PpemtCtg> findAllPerInfoCtgByCid(String cid) {
		return this.queryProxy.query(FIND_ALL_PERSONAL_INFO_CATEGORY, PpemtCtg.class)
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
	private List<PpemtCtgSort> findAllPerInfoCtgOrderByCid(String cid,
			Set<String> personalInfoCatId) {
		return this.queryProxy
				.query(FIND_ALL_PERSONAL_INFO_CATEGORY_ORDER, PpemtCtgSort.class)
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
	private List<PpemtItem> findAllPpemtItemByCatId(Set<String> personalInfoCatId) {
		return this.queryProxy.query(FIND_ALL_PERSONAL_INFO_ITEM, PpemtItem.class)
				.setParameter("perInfoCtgIdList", personalInfoCatId).getList();
	}

	/**
	 * Find all ppemt per info item by per info ctg id.
	 *
	 * @param perInfoCtgId
	 *            the per info ctg id
	 * @return the list
	 */
	private List<PpemtItem> findAllPpemtItemByPerInfoCtgId(String perInfoCtgId) {
		return this.queryProxy
				.query(FIND_ALL_PERSONAL_INFO_ITEM_ON_PER_INFO_CTG_ID, PpemtItem.class)
				.setParameter("perInfoCtgId", perInfoCtgId).getList();
	}

	/**
	 * Find all per info item order by cat id.
	 *
	 * @param personalInfoCatId
	 *            the personal info cat id
	 * @return the list
	 */
	private List<PpemtItemSort> findAllPerInfoItemOrderByCatId(
			Set<String> personalInfoCatId) {
		return this.queryProxy.query(FIND_ALL_PERSONAL_INFO_ITEM_ORDER, PpemtItemSort.class)
				.setParameter("perInfoCtgIdList", personalInfoCatId).getList();
	}

	/**
	 * Find alldate range item by cat id.
	 *
	 * @param personalInfoCatId
	 *            the personal info cat id
	 * @return the list
	 */
	private List<PpemtItemDateRange> findAlldateRangeItemByCatId(Set<String> personalInfoCatId) {
		return this.queryProxy.query(FIND_ALL_DATE_RANGE_ITEM, PpemtItemDateRange.class)
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
		List<PpemtCtg> sPerInfoCtgEntities = findAllPerInfoCtgByCid(sourceCid);
		List<PpemtCtgSort> sPerInfoCtgOrderEntities = new ArrayList<>();
		List<PpemtItem> sPerInfoItemEntities = new ArrayList<>();
		List<PpemtItemSort> sPerInfoItemOrderEntities = new ArrayList<>();
		List<PpemtItemDateRange> sDateRangeItemEntities = new ArrayList<>();
		Set<String> sourcePersonalInfoCatId = new TreeSet<String>();
		if (!sPerInfoCtgEntities.isEmpty()) {
			sourcePersonalInfoCatId = sPerInfoCtgEntities.stream()
					.map(ppemtCtg -> ppemtCtg.ppemtCtgPK.perInfoCtgId)
					.collect(Collectors.toSet());
			sPerInfoCtgOrderEntities = findAllPerInfoCtgOrderByCid(sourceCid,
					sourcePersonalInfoCatId);
			sPerInfoItemEntities = findAllPpemtItemByCatId(sourcePersonalInfoCatId);
			sPerInfoItemOrderEntities = findAllPerInfoItemOrderByCatId(sourcePersonalInfoCatId);
			sDateRangeItemEntities = findAlldateRangeItemByCatId(sourcePersonalInfoCatId);
		}

		// Get data company target
		List<PpemtCtg> tPerInfoCtgEntities = findAllPerInfoCtgByCid(targetCid);
		List<PpemtCtgSort> tPerInfoCtgOrderEntities = new ArrayList<>();
		List<PpemtItem> tPerInfoItemEntities = new ArrayList<>();
		List<PpemtItemSort> tPerInfoItemOrderEntities = new ArrayList<>();
		List<PpemtItemDateRange> tPateRangeItemEntities = new ArrayList<>();
		if (!tPerInfoCtgEntities.isEmpty()) {
			Set<String> targetPersonalInfoCatId = tPerInfoCtgEntities.stream()
					.map(ppemtCtg -> ppemtCtg.ppemtCtgPK.perInfoCtgId)
					.collect(Collectors.toSet());
			tPerInfoCtgOrderEntities = findAllPerInfoCtgOrderByCid(targetCid,
					targetPersonalInfoCatId);
			tPerInfoItemEntities = findAllPpemtItemByCatId(targetPersonalInfoCatId);
			tPerInfoItemOrderEntities = findAllPerInfoItemOrderByCatId(targetPersonalInfoCatId);
			tPateRangeItemEntities = findAlldateRangeItemByCatId(targetPersonalInfoCatId);
		}

		// group by personal info item def Id
		final List<PpemtCtg> s1 = new ArrayList<>();
		final List<PpemtCtgSort> s2 = new ArrayList<>();
		final List<PpemtItem> s3 = new ArrayList<>();
		final List<PpemtItemSort> s4 = new ArrayList<>();
		final List<PpemtItemDateRange> s5 = new ArrayList<>();

		// 取得できなかった場合（会社ID ＝ Input．会社IDの個人情報定義）//ko lấy được
		if (CollectionUtil.isEmpty(tPerInfoCtgEntities)
				&& CollectionUtil.isEmpty(tPerInfoCtgOrderEntities)
				&& CollectionUtil.isEmpty(tPerInfoItemEntities)
				&& CollectionUtil.isEmpty(tPerInfoItemOrderEntities)
				&& CollectionUtil.isEmpty(tPateRangeItemEntities)) {
			// group by personal info category Id
			Map<String, List<PpemtCtg>> groupPersonalInfoCatByCatId = new HashMap<>();
			Map<String, List<PpemtCtgSort>> groupPersonalInfoCatOrderByCatId = new HashMap<>();
			Map<String, List<PpemtItem>> groupPersonalInfoItemByCatId = new HashMap<>();
			Map<String, List<PpemtItemSort>> groupPersonalInfoItemOrderByCatId = new HashMap<>();
			Map<String, List<PpemtItemDateRange>> groupDateRangeByCatId = new HashMap<>();
			if (!CollectionUtil.isEmpty(sPerInfoCtgEntities)) {
				groupPersonalInfoCatByCatId = sPerInfoCtgEntities.stream()
						.collect(Collectors.groupingBy(o -> o.ppemtCtgPK.perInfoCtgId));
			}
			if (!CollectionUtil.isEmpty(sPerInfoCtgOrderEntities)) {
				groupPersonalInfoCatOrderByCatId = sPerInfoCtgOrderEntities.stream()
						.collect(Collectors.groupingBy(o -> o.ppemtCtgPK.perInfoCtgId));
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
						.collect(Collectors.groupingBy(o -> o.ppemtCtgPK.perInfoCtgId));
			}

			// create
			for (String catId : sourcePersonalInfoCatId) {
				String newCatId = UUID.randomUUID().toString();
				for (PpemtCtg perInfoCtgEntity : groupPersonalInfoCatByCatId.get(catId)) {
					PpemtCtg cloneObject = SerializationUtils.clone(perInfoCtgEntity);
					cloneObject.ppemtCtgPK.perInfoCtgId = newCatId;
					cloneObject.cid = targetCid;
					s1.add(cloneObject);
				}

				if (!CollectionUtil.isEmpty(groupPersonalInfoCatOrderByCatId.get(catId))) {
					for (PpemtCtgSort perInfoCtgOrderEntity : groupPersonalInfoCatOrderByCatId
							.get(catId)) {
						PpemtCtgSort cloneObject = SerializationUtils
								.clone(perInfoCtgOrderEntity);
						cloneObject.cid = targetCid;
						cloneObject.ppemtCtgPK.perInfoCtgId = newCatId;
						s2.add(cloneObject);
					}
				}

				if (!CollectionUtil.isEmpty(groupPersonalInfoItemByCatId.get(catId))) {
					for (PpemtItem perInfoItemEntity : groupPersonalInfoItemByCatId
							.get(catId)) {
						PpemtItem cloneObject = SerializationUtils.clone(perInfoItemEntity);
						cloneObject.perInfoCtgId = newCatId;
						s3.add(cloneObject);
					}
				}

				if (!CollectionUtil.isEmpty(groupPersonalInfoItemOrderByCatId.get(catId))) {
					for (PpemtItemSort perInfoItemOrderEntity : groupPersonalInfoItemOrderByCatId
							.get(catId)) {
						PpemtItemSort cloneObject = SerializationUtils
								.clone(perInfoItemOrderEntity);
						cloneObject.perInfoCtgId = newCatId;
						s4.add(cloneObject);
					}
				}

				if (!CollectionUtil.isEmpty(groupDateRangeByCatId.get(catId))) {
					for (PpemtItemDateRange dateRangeItem : groupDateRangeByCatId.get(catId)) {
						PpemtItemDateRange cloneObject = SerializationUtils.clone(dateRangeItem);
						cloneObject.ppemtCtgPK.perInfoCtgId = newCatId;
						s5.add(cloneObject);
					}
				}
			}

			Map<String, PpemtItem> groupPersonalInfoItemByDefId = new HashMap<>();
			if (!CollectionUtil.isEmpty(s3))
				groupPersonalInfoItemByDefId = s3.stream().collect(Collectors.toMap(
						o -> o.ppemtItemPK.perInfoItemDefId, perInfoItem -> perInfoItem));
			Map<String, PpemtItemSort> groupPersonalInfoItemOrderByDefId = new HashMap<>();
			if (!CollectionUtil.isEmpty(s4))
				groupPersonalInfoItemOrderByDefId = s4.stream()
						.collect(Collectors.toMap(o -> o.ppemtItemPK.perInfoItemDefId,
								perInfoItemOrder -> perInfoItemOrder));
			Map<String, PpemtItemDateRange> groupDateRangeItemByDefId = new HashMap<>();
			if (!CollectionUtil.isEmpty(s5))
				groupDateRangeItemByDefId = s5.stream().collect(
						Collectors.toMap(o -> o.startDateItemId, dateRangeItem -> dateRangeItem));

			final List<PpemtItem> s33 = new ArrayList<>();
			final List<PpemtItemSort> s44 = new ArrayList<>();
			final List<PpemtItemDateRange> s55 = new ArrayList<>();

			Set<String> sourcePersonalInfoItemDefId = s3.stream()
					.map(ppemtItem -> ppemtItem.ppemtItemPK.perInfoItemDefId)
					.collect(Collectors.toSet());

			Map<String, String> perInfoItemMapId = new HashMap<String, String>();

			for (String defId : sourcePersonalInfoItemDefId) {
				String newDefId = UUID.randomUUID().toString();
				transIdMap.put(defId, newDefId);

				PpemtItem perInfoItemEntity = groupPersonalInfoItemByDefId.get(defId);
				PpemtItem cloneObject1 = SerializationUtils.clone(perInfoItemEntity);
				cloneObject1.ppemtItemPK.perInfoItemDefId = newDefId;
				s33.add(cloneObject1);

				PpemtItemSort perInfoItemOrderEntity = groupPersonalInfoItemOrderByDefId
						.get(defId);
				if (perInfoItemOrderEntity == null)
					continue;
				PpemtItemSort cloneObject2 = SerializationUtils
						.clone(perInfoItemOrderEntity);
				cloneObject2.ppemtItemPK.perInfoItemDefId = newDefId;
				s44.add(cloneObject2);

				perInfoItemMapId.put(defId, newDefId);
			}

			for (String defId : sourcePersonalInfoItemDefId) {
				PpemtItemDateRange dateRangeItemEntity = groupDateRangeItemByDefId.get(defId);

				if (dateRangeItemEntity == null)
					continue;

				PpemtItemDateRange cloneObject3 = SerializationUtils.clone(dateRangeItemEntity);
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
			Map<String, PpemtCtg> sgroupPersonalInfoCatByCatCd = new HashMap<>();
			if (!CollectionUtil.isEmpty(sPerInfoCtgEntities)) {
				sgroupPersonalInfoCatByCatCd = sPerInfoCtgEntities.stream().collect(Collectors
						.toMap(perInfoCtg -> perInfoCtg.categoryCd, perInfoCtg -> perInfoCtg));
			}
			Map<String, PpemtCtg> tgroupPersonalInfoCatByCatCd = new HashMap<>();
			if (!CollectionUtil.isEmpty(sPerInfoCtgEntities)) {
				tgroupPersonalInfoCatByCatCd = tPerInfoCtgEntities.stream().collect(Collectors
						.toMap(perInfoCtg -> perInfoCtg.categoryCd, perInfoCtg -> perInfoCtg));
			}

			Set<String> sourcePersonalInfoCatCd = sPerInfoCtgEntities.stream()
					.map(ppemtCtg -> ppemtCtg.categoryCd).collect(Collectors.toSet());
			// overwrite
			for (String catCd : sourcePersonalInfoCatCd) {
				// 1 update overwrite for PpemtCtg
				PpemtCtg src = sgroupPersonalInfoCatByCatCd.get(catCd);
				PpemtCtg des = tgroupPersonalInfoCatByCatCd.get(catCd);
				if (src == null || des == null)
					continue;
				des.categoryName = src.categoryName;
				des.abolitionAtr = src.abolitionAtr;
				this.commandProxy.update(des);

				// 2 update overwrite for PpemtItem
				// LOGGER.info("Test Event CMM001: " + sourceCid + "-" + catCd);
				Map<String, PpemtItem> sourcePerInfoItems = findAllPpemtItemByPerInfoCtgId(
						src.ppemtCtgPK.perInfoCtgId).stream()
								.collect(Collectors.toMap(o -> o.itemCd, o -> o));
				Map<String, PpemtItem> destPerInfoItems = findAllPpemtItemByPerInfoCtgId(
						des.ppemtCtgPK.perInfoCtgId).stream()
								.collect(Collectors.toMap(o -> o.itemCd, o -> o));

				if (!CollectionUtil.isEmpty(sourcePerInfoItems.keySet())) {
					for (String itemCd : sourcePerInfoItems.keySet()) {
						PpemtItem srcPerInfoItem = sourcePerInfoItems.get(itemCd);
						PpemtItem desPerInfoItem = destPerInfoItems.get(itemCd);
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
