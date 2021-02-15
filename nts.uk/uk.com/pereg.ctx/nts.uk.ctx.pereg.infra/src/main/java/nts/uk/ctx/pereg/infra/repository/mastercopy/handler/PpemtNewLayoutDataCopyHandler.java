/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pereg.infra.repository.mastercopy.handler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.mastercopy.DataCopyHandler;
import nts.uk.ctx.pereg.infra.entity.layout.PpemtNewLayout;
import nts.uk.ctx.pereg.infra.entity.layout.PpemtNewLayoutPk;
import nts.uk.ctx.pereg.infra.entity.layout.cls.PpemtLayoutItemCls;
import nts.uk.ctx.pereg.infra.entity.layout.cls.PpemtLayoutItemClsPk;
import nts.uk.ctx.pereg.infra.entity.layout.cls.definition.PpemtLayoutItemClsDf;
import nts.uk.ctx.pereg.infra.entity.layout.cls.definition.PpemtLayoutItemClsDfPk;
import nts.uk.ctx.pereg.infra.entity.person.info.ctg.PpemtPerInfoCtg;
import nts.uk.ctx.pereg.infra.entity.person.info.item.PpemtPerInfoItem;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PpemtNewLayoutDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class PpemtNewLayoutDataCopyHandler extends DataCopyHandler {

	private static final String QUERY_DATA_BY_COMPANYID = "SELECT l FROM PpemtNewLayout l WHERE l.companyId = :companyId";
	private static final String GET_LAYOUT_ITEM = "SELECT l FROM PpemtLayoutItemCls l WHERE l.ppemtLayoutItemClsPk.layoutId = :layoutId";
	private static final String GET_LAYOUT_ITEM_DF = "SELECT l FROM PpemtLayoutItemClsDf l WHERE l.ppemtLayoutItemClsDfPk.layoutId = :layoutId";

	private static final String GET_PER_INFO_CTG = "SELECT p FROM PpemtPerInfoCtg p WHERE p.cid = :companyId";
	private static final String GET_PER_INFO_ITEM = "Select p FROM PpemtPerInfoItem p WHERE p.perInfoCtgId= :perInfoCtgId";

	private static final String DELETE_DATA = "DELETE FROM PpemtNewLayout l WHERE l.companyId = :companyId";
	private static final String DELETE_LAYOUT_ITEM = "DELETE FROM PpemtLayoutItemCls l WHERE l.ppemtLayoutItemClsPk.layoutId = :layoutId";
	private static final String DELETE_LAYOUT_ITEM_DF = "DELETE FROM PpemtLayoutItemClsDf l WHERE l.ppemtLayoutItemClsDfPk.layoutId = :layoutId";

	public PpemtNewLayoutDataCopyHandler(int copyMethod, String companyId, EntityManager em) {
		super();
		this.copyMethod = copyMethod;
		this.companyId = companyId;
		this.entityManager = em;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pereg.dom.mastercopy.DataCopyHandler#doCopy()
	 */
	@Override
	public Map<String, String> doCopy() {

		// Get company zero id
		String companyZeroId = AppContexts.user().zeroCompanyIdInContract();
		// Get company zero data
		List<PpemtNewLayout> entityComZero = this.entityManager
				.createQuery(QUERY_DATA_BY_COMPANYID, PpemtNewLayout.class)
				.setParameter("companyId", companyZeroId).getResultList();

		List<PpemtNewLayout> entityCurrentCom = this.entityManager
				.createQuery(QUERY_DATA_BY_COMPANYID, PpemtNewLayout.class)
				.setParameter("companyId", companyId).getResultList();

		if (entityComZero.isEmpty()) {
			return Collections.emptyMap();
		}

		switch (copyMethod) {
		case REPLACE_ALL:
			// Delete all old data
			entityCurrentCom.forEach(item -> {
				// layoutID of current company
				String CurrentComlayoutId = item.ppemtNewLayoutPk.layoutId;

				// get data layout item cls of current company
				List<PpemtLayoutItemCls> itemList = this.entityManager
						.createQuery(GET_LAYOUT_ITEM, PpemtLayoutItemCls.class)
						.setParameter("layoutId", CurrentComlayoutId).getResultList();

				// remove all data layout item cls of current company
				if (!itemList.isEmpty()) {
					this.entityManager.createQuery(DELETE_LAYOUT_ITEM, PpemtLayoutItemCls.class)
							.setParameter("layoutId", CurrentComlayoutId).executeUpdate();

					this.entityManager
							.createQuery(DELETE_LAYOUT_ITEM_DF, PpemtLayoutItemClsDf.class)
							.setParameter("layoutId", item.ppemtNewLayoutPk.layoutId)
							.executeUpdate();
				}

				// remove data layout of current company
				this.entityManager.createQuery(DELETE_DATA, PpemtLayoutItemCls.class)
						.setParameter("companyId", companyId).executeUpdate();

				this.entityManager.flush();
			});
			entityComZero.forEach(entity -> {

				// get layoutID
				String layoutId = IdentifierUtil.randomUniqueId();
				PpemtNewLayoutPk newPk = new PpemtNewLayoutPk(layoutId);
				PpemtNewLayout newEntity = new PpemtNewLayout(newPk, companyId, entity.layoutCode,
						entity.layoutName);

				// get get data layout item cls of company Zero
				List<PpemtLayoutItemCls> itemList = this.entityManager
						.createQuery(GET_LAYOUT_ITEM, PpemtLayoutItemCls.class)
						.setParameter("layoutId", entity.ppemtNewLayoutPk.layoutId).getResultList();

				// get data PPEMT_PER_INFO_CTG CurrentCom
				List<PpemtPerInfoCtg> perInfoCtgCurrentCom = this.entityManager
						.createQuery(GET_PER_INFO_CTG, PpemtPerInfoCtg.class)
						.setParameter("companyId", companyId).getResultList();

				// get data PPEMT_PER_INFO_CTG ZeroCom
				List<PpemtPerInfoCtg> perInfoCtgCurrentZero = this.entityManager
						.createQuery(GET_PER_INFO_CTG, PpemtPerInfoCtg.class)
						.setParameter("companyId", companyZeroId).getResultList();

				// get data layout item cls df
				List<PpemtLayoutItemClsDf> itemListDf = this.entityManager
						.createQuery(GET_LAYOUT_ITEM_DF, PpemtLayoutItemClsDf.class)
						.setParameter("layoutId", entity.ppemtNewLayoutPk.layoutId).getResultList();

				// Insert new data

				this.entityManager.persist(newEntity);

				// insert data layout item cls
				for (PpemtLayoutItemCls i : itemList) {
					PpemtPerInfoCtg infoCtgComZero = perInfoCtgCurrentZero.stream()
							.filter(e -> e.ppemtPerInfoCtgPK.perInfoCtgId.equals(i.categoryId))
							.findFirst().orElse(null);

					String categoryID = infoCtgComZero != null
							? this.checkCategoryCd(perInfoCtgCurrentCom, infoCtgComZero) : null;

					PpemtLayoutItemClsPk PK = new PpemtLayoutItemClsPk(layoutId,
							i.ppemtLayoutItemClsPk.dispOrder);
					PpemtLayoutItemCls item = new PpemtLayoutItemCls(PK, categoryID, i.itemType);
					this.entityManager.persist(item);

					List<PpemtLayoutItemClsDf> itemListDfFilter = itemListDf.stream()
							.filter(e -> e.ppemtLayoutItemClsDfPk.layoutDispOrder == i.ppemtLayoutItemClsPk.dispOrder)
							.collect(Collectors.toList());

					// get data PPEMT_PER_INFO_CTG currentCom
					List<PpemtPerInfoItem> perInfoItemCurrentCom = this.entityManager
							.createQuery(GET_PER_INFO_ITEM, PpemtPerInfoItem.class)
							.setParameter("perInfoCtgId", categoryID).getResultList();

					// get data PPEMT_PER_INFO_CTG ZeroCom
					List<PpemtPerInfoItem> perInfoItemzeroCom = this.entityManager
							.createQuery(GET_PER_INFO_ITEM, PpemtPerInfoItem.class)
							.setParameter("perInfoCtgId", i.categoryId).getResultList();

					for (PpemtLayoutItemClsDf layoutItem : itemListDfFilter) {
						PpemtPerInfoItem infoItemZero = perInfoItemzeroCom.stream()
								.filter(e -> e.ppemtPerInfoItemPK.perInfoItemDefId
										.equals(layoutItem.itemDfID))
								.findFirst().orElse(null);

						if (infoItemZero != null) {
							String defineID = this.checkItemDfId(perInfoItemCurrentCom,
									infoItemZero);

							if (defineID != null) {
								PpemtLayoutItemClsDfPk layoutItemPK = new PpemtLayoutItemClsDfPk(
										layoutId, layoutItem.ppemtLayoutItemClsDfPk.layoutDispOrder,
										layoutItem.ppemtLayoutItemClsDfPk.dispOrder);
								PpemtLayoutItemClsDf itemDf = new PpemtLayoutItemClsDf(layoutItemPK,
										defineID);
								this.entityManager.persist(itemDf);
							}
						}
					}
				}
			});
			break;
		case ADD_NEW:

			// Insert Data
			if (!entityComZero.isEmpty() && entityCurrentCom.isEmpty()) {

				entityComZero.forEach(entity -> {

					// get layoutID
					String layoutId = IdentifierUtil.randomUniqueId();
					PpemtNewLayoutPk newPk = new PpemtNewLayoutPk(layoutId);
					PpemtNewLayout newEntity = new PpemtNewLayout(newPk, companyId,
							entity.layoutCode, entity.layoutName);

					// get get data layout item cls of company Zero
					List<PpemtLayoutItemCls> itemList = this.entityManager
							.createQuery(GET_LAYOUT_ITEM, PpemtLayoutItemCls.class)
							.setParameter("layoutId", entity.ppemtNewLayoutPk.layoutId)
							.getResultList();

					// get data PPEMT_PER_INFO_CTG CurrentCom
					List<PpemtPerInfoCtg> perInfoCtgCurrentCom = this.entityManager
							.createQuery(GET_PER_INFO_CTG, PpemtPerInfoCtg.class)
							.setParameter("companyId", companyId).getResultList();

					// get data PPEMT_PER_INFO_CTG ZeroCom
					List<PpemtPerInfoCtg> perInfoCtgCurrentZero = this.entityManager
							.createQuery(GET_PER_INFO_CTG, PpemtPerInfoCtg.class)
							.setParameter("companyId", companyZeroId).getResultList();

					// get data layout item cls df
					List<PpemtLayoutItemClsDf> itemListDf = this.entityManager
							.createQuery(GET_LAYOUT_ITEM_DF, PpemtLayoutItemClsDf.class)
							.setParameter("layoutId", entity.ppemtNewLayoutPk.layoutId)
							.getResultList();

					// Insert new data

					this.entityManager.persist(newEntity);

					// insert data layout item cls
					for (PpemtLayoutItemCls i : itemList) {
						PpemtPerInfoCtg infoCtgComZero = perInfoCtgCurrentZero.stream()
								.filter(e -> e.ppemtPerInfoCtgPK.perInfoCtgId.equals(i.categoryId))
								.findFirst().orElse(null);

						String categoryID = infoCtgComZero != null
								? this.checkCategoryCd(perInfoCtgCurrentCom, infoCtgComZero) : null;

						PpemtLayoutItemClsPk PK = new PpemtLayoutItemClsPk(layoutId,
								i.ppemtLayoutItemClsPk.dispOrder);
						PpemtLayoutItemCls item = new PpemtLayoutItemCls(PK, categoryID,
								i.itemType);
						this.entityManager.persist(item);

						List<PpemtLayoutItemClsDf> itemListDfFilter = itemListDf.stream()
								.filter(e -> e.ppemtLayoutItemClsDfPk.layoutDispOrder == i.ppemtLayoutItemClsPk.dispOrder)
								.collect(Collectors.toList());

						// get data PPEMT_PER_INFO_CTG currentCom
						List<PpemtPerInfoItem> perInfoItemCurrentCom = this.entityManager
								.createQuery(GET_PER_INFO_ITEM, PpemtPerInfoItem.class)
								.setParameter("perInfoCtgId", categoryID).getResultList();

						// get data PPEMT_PER_INFO_CTG ZeroCom
						List<PpemtPerInfoItem> perInfoItemzeroCom = this.entityManager
								.createQuery(GET_PER_INFO_ITEM, PpemtPerInfoItem.class)
								.setParameter("perInfoCtgId", i.categoryId).getResultList();

						for (PpemtLayoutItemClsDf layoutItem : itemListDfFilter) {
							PpemtPerInfoItem infoItemZero = perInfoItemzeroCom.stream()
									.filter(e -> e.ppemtPerInfoItemPK.perInfoItemDefId
											.equals(layoutItem.itemDfID))
									.findFirst().orElse(null);

							if (infoItemZero != null) {
								String defineID = this.checkItemDfId(perInfoItemCurrentCom,
										infoItemZero);

								if (defineID != null) {
									PpemtLayoutItemClsDfPk layoutItemPK = new PpemtLayoutItemClsDfPk(
											layoutId,
											layoutItem.ppemtLayoutItemClsDfPk.layoutDispOrder,
											layoutItem.ppemtLayoutItemClsDfPk.dispOrder);
									PpemtLayoutItemClsDf itemDf = new PpemtLayoutItemClsDf(
											layoutItemPK, defineID);
									this.entityManager.persist(itemDf);
								}
							}
						}
					}
				});
			}
			break;
		case DO_NOTHING:
			// Do nothing
		default:
			break;
		}
		
		return Collections.emptyMap();
	}

	private String checkCategoryCd(List<PpemtPerInfoCtg> list, PpemtPerInfoCtg item) {
		for (PpemtPerInfoCtg value : list) {
			if (value.categoryCd.equals(item.categoryCd))
				return value.ppemtPerInfoCtgPK.perInfoCtgId;
		}
		return null;
	}

	private String checkItemDfId(List<PpemtPerInfoItem> list, PpemtPerInfoItem item) {
		for (PpemtPerInfoItem value : list) {
			if (value.itemCd.equals(item.itemCd))
				return value.ppemtPerInfoItemPK.perInfoItemDefId;
		}
		return null;
	}
}
