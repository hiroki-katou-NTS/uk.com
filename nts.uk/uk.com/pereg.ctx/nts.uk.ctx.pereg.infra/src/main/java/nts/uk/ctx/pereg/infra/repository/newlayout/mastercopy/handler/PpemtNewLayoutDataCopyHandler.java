package nts.uk.ctx.pereg.infra.repository.newlayout.mastercopy.handler;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.person.layout.mastercopy.CopyMethod;
import nts.uk.ctx.pereg.dom.person.layout.mastercopy.DataCopyHandler;
import nts.uk.ctx.pereg.infra.entity.layout.PpemtNewLayout;
import nts.uk.ctx.pereg.infra.entity.layout.PpemtNewLayoutPk;
import nts.uk.ctx.pereg.infra.entity.layout.cls.PpemtLayoutItemCls;
import nts.uk.ctx.pereg.infra.entity.layout.cls.PpemtLayoutItemClsPk;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PpemtNewLayoutDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
public class PpemtNewLayoutDataCopyHandler extends JpaRepository implements DataCopyHandler {

	/** The copy method. */
	private CopyMethod copyMethod;

	/** The company id. */
	private String companyId;

	EntityManager em;

	private static final String QUERY_DATA_BY_COMPANYID = "SELECT l FROM PpemtNewLayout l WHERE l.companyId = :companyId";
	private static final String GET_LAYOUT_ITEM = "SELECT l FROM PpemtLayoutItemCls l WHERE l.ppemtLayoutItemClsPk.layoutId = :layoutId";
	private static final String DELETE_DATA = "DELETE PpemtNewLayout l WHERE l.companyId = :companyId";
	private static final String DELETE_LAYOUT_ITEM = "DELETE PpemtLayoutItemCls l WHERE l.ppemtLayoutItemClsPk.layoutId = :layoutId";
	private static final String INSERT_DATA = "INSERT INTO PpemtNewLayout p VALUES p.ppemtNewLayoutPk = :ppemtNewLayoutPk, p.companyId = :companyId, p.layoutCode= :layoutCode, p.layoutName= :layoutName";
	private static final String INSERT_LAYOUT_ITEM = "INSERT INTO PpemtLayoutItemCls p VALUES p.ppemtLayoutItemClsPk = :ppemtLayoutItemClsPk, p.categoryId = :categoryId, p.itemType = :itemType";

	public PpemtNewLayoutDataCopyHandler(CopyMethod copyMethod, String companyId, EntityManager em) {
		super();
		this.copyMethod = copyMethod;
		this.companyId = companyId;
		this.em = em;
	}

	@Override
	public void doCopy() {

		// Get company zero id
		String companyZeroId = AppContexts.user().zeroCompanyIdInContract();
		// Get company zero data
		List<PpemtNewLayout> entityComZero = this.em.createQuery(QUERY_DATA_BY_COMPANYID, PpemtNewLayout.class)
				.setParameter("companyId", companyZeroId).getResultList();

		List<PpemtNewLayout> entityCurrentCom = this.em.createQuery(QUERY_DATA_BY_COMPANYID, PpemtNewLayout.class)
				.setParameter("companyId", companyId).getResultList();

		switch (copyMethod) {
		case REPLACE_ALL:
			// Delete all old data
			entityCurrentCom.forEach(item -> {
				// layoutID of current company
				String CurrentComlayoutId = item.ppemtNewLayoutPk.layoutId;

				// get data layout item cls of current company
				List<PpemtLayoutItemCls> itemList = this.em.createQuery(GET_LAYOUT_ITEM, PpemtLayoutItemCls.class)
						.setParameter("layoutId", CurrentComlayoutId).getResultList();

				// remove all data layout item cls of current company
				if (!itemList.isEmpty()) {
					this.em.createQuery(DELETE_LAYOUT_ITEM, PpemtLayoutItemCls.class)
							.setParameter("layoutId", CurrentComlayoutId).executeUpdate();
				}

				// remove data layout of current company
				this.em.createQuery(DELETE_DATA, PpemtLayoutItemCls.class).setParameter("companyId", companyId)
						.executeUpdate();

				this.em.flush();
			});
			entityComZero.forEach(entity -> {

				// get layoutID
				String layoutId = IdentifierUtil.randomUniqueId();
				PpemtNewLayoutPk newPk = new PpemtNewLayoutPk(layoutId);
				PpemtNewLayout newEntity = new PpemtNewLayout(newPk, companyId, entity.layoutCode, entity.layoutName);

				// get get data layout item cls of company Zero
				List<PpemtLayoutItemCls> itemList = this.em.createQuery(GET_LAYOUT_ITEM, PpemtLayoutItemCls.class)
						.setParameter("layoutId", entity.ppemtNewLayoutPk.layoutId).getResultList();

				// Insert new data

				this.em.persist(newEntity);

				itemList.forEach(i -> {
					PpemtLayoutItemClsPk PK = new PpemtLayoutItemClsPk(layoutId, i.ppemtLayoutItemClsPk.dispOrder);
					PpemtLayoutItemCls item = new PpemtLayoutItemCls(PK, i.categoryId, i.itemType);
					this.em.persist(item);
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
					List<PpemtLayoutItemCls> itemList = this.em.createQuery(GET_LAYOUT_ITEM, PpemtLayoutItemCls.class)
							.setParameter("layoutId", entity.ppemtNewLayoutPk.layoutId).getResultList();

					// Insert new data
					this.em.persist(newEntity);

					// set layoutID and insert
					itemList.forEach(i -> {
						PpemtLayoutItemClsPk PK = new PpemtLayoutItemClsPk(layoutId, i.ppemtLayoutItemClsPk.dispOrder);
						PpemtLayoutItemCls item = new PpemtLayoutItemCls(PK, i.categoryId, i.itemType);
						this.em.persist(item);
					});

				});

			}
			break;
		case DO_NOTHING:
			// Do nothing
			break;
		default:
			break;

		}
	}

}
