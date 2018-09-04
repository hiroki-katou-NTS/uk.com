package nts.uk.ctx.pereg.infra.repository.mastercopy.handler;

import java.util.List;
import javax.persistence.EntityManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.mastercopy.*;
import nts.uk.ctx.pereg.infra.entity.layout.PpemtNewLayout;
import nts.uk.ctx.pereg.infra.entity.layout.PpemtNewLayoutPk;
import nts.uk.ctx.pereg.infra.entity.layout.cls.PpemtLayoutItemCls;
import nts.uk.ctx.pereg.infra.entity.layout.cls.PpemtLayoutItemClsPk;
import nts.uk.ctx.pereg.infra.entity.layout.cls.definition.PpemtLayoutItemClsDf;
import nts.uk.ctx.pereg.infra.entity.layout.cls.definition.PpemtLayoutItemClsDfPk;
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
	private static final String DELETE_DATA = "DELETE FROM PpemtNewLayout l WHERE l.companyId = :companyId";
	private static final String DELETE_LAYOUT_ITEM = "DELETE FROM PpemtLayoutItemCls l WHERE l.ppemtLayoutItemClsPk.layoutId = :layoutId";
	private static final String DELETE_LAYOUT_ITEM_DF = "DELETE FROM PpemtLayoutItemClsDf l WHERE l.ppemtLayoutItemClsDfPk.layoutId = :layoutId";

	public PpemtNewLayoutDataCopyHandler(int copyMethod, String companyId, EntityManager em) {
		super();
		this.copyMethod = copyMethod;
		this.companyId = companyId;
		this.entityManager = em;
	}

	@Override
	public void doCopy() {

		// Get company zero id
		String companyZeroId = AppContexts.user().zeroCompanyIdInContract();
		// Get company zero data
		List<PpemtNewLayout> entityComZero = this.entityManager
				.createQuery(QUERY_DATA_BY_COMPANYID, PpemtNewLayout.class).setParameter("companyId", companyZeroId)
				.getResultList();

		List<PpemtNewLayout> entityCurrentCom = this.entityManager
				.createQuery(QUERY_DATA_BY_COMPANYID, PpemtNewLayout.class).setParameter("companyId", companyId)
				.getResultList();

		if (entityComZero.isEmpty())
			return;
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

					this.entityManager.createQuery(DELETE_LAYOUT_ITEM_DF, PpemtLayoutItemClsDf.class)
							.setParameter("layoutId", item.ppemtNewLayoutPk.layoutId).executeUpdate();
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
				PpemtNewLayout newEntity = new PpemtNewLayout(newPk, companyId, entity.layoutCode, entity.layoutName);

				// get get data layout item cls of company Zero
				List<PpemtLayoutItemCls> itemList = this.entityManager
						.createQuery(GET_LAYOUT_ITEM, PpemtLayoutItemCls.class)
						.setParameter("layoutId", entity.ppemtNewLayoutPk.layoutId).getResultList();

				// get data layout item cls df
				List<PpemtLayoutItemClsDf> itemListDf = this.entityManager
						.createQuery(GET_LAYOUT_ITEM_DF, PpemtLayoutItemClsDf.class)
						.setParameter("layoutId", entity.ppemtNewLayoutPk.layoutId).getResultList();

				// Insert new data

				this.entityManager.persist(newEntity);

				// insert data layout item cls
				itemList.forEach(i -> {
					PpemtLayoutItemClsPk PK = new PpemtLayoutItemClsPk(layoutId, i.ppemtLayoutItemClsPk.dispOrder);
					PpemtLayoutItemCls item = new PpemtLayoutItemCls(PK, i.categoryId, i.itemType);
					this.entityManager.persist(item);

				});

				// insert data layout item cls Df

				itemListDf.forEach(i -> {
					PpemtLayoutItemClsDfPk PK = new PpemtLayoutItemClsDfPk(layoutId,
							i.ppemtLayoutItemClsDfPk.layoutDispOrder, i.ppemtLayoutItemClsDfPk.dispOrder);
					PpemtLayoutItemClsDf itemDf = new PpemtLayoutItemClsDf(PK, i.itemDfID);
					this.entityManager.persist(itemDf);
				});

			});
			break;
		case ADD_NEW:

			// Insert Data
			if (!entityComZero.isEmpty() && entityCurrentCom.isEmpty()) {

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

					// get data layout item cls df
					List<PpemtLayoutItemClsDf> itemListDf = this.entityManager
							.createQuery(GET_LAYOUT_ITEM_DF, PpemtLayoutItemClsDf.class)
							.setParameter("layoutId", entity.ppemtNewLayoutPk.layoutId).getResultList();
					
					// Insert new data
					this.entityManager.persist(newEntity);

					// set layout item cls and insert
					itemList.forEach(i -> {
						PpemtLayoutItemClsPk PK = new PpemtLayoutItemClsPk(layoutId, i.ppemtLayoutItemClsPk.dispOrder);
						PpemtLayoutItemCls item = new PpemtLayoutItemCls(PK, i.categoryId, i.itemType);
						this.entityManager.persist(item);
					});
					
					// set data layout item cls Df and insert 
					itemListDf.forEach(i -> {
						PpemtLayoutItemClsDfPk PK = new PpemtLayoutItemClsDfPk(layoutId,
								i.ppemtLayoutItemClsDfPk.layoutDispOrder, i.ppemtLayoutItemClsDfPk.dispOrder);
						PpemtLayoutItemClsDf itemDf = new PpemtLayoutItemClsDf(PK, i.itemDfID);
						this.entityManager.persist(itemDf);
					});

				});

			}
			break;
		case DO_NOTHING:
			// Do nothing
		default:
			break;

		}
	}

}
