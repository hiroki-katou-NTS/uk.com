package nts.uk.ctx.pereg.infra.repository.newlayout.mastercopy.handler;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.person.layout.mastercopy.CopyMethod;
import nts.uk.ctx.pereg.dom.person.layout.mastercopy.DataCopyHandler;
import nts.uk.ctx.pereg.infra.entity.layout.PpemtNewLayout;
import nts.uk.ctx.pereg.infra.entity.layout.cls.PpemtLayoutItemCls;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PpemtNewLayoutDataCopyHandler.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PpemtNewLayoutDataCopyHandler extends JpaRepository implements DataCopyHandler {

	/** The copy method. */
	private CopyMethod copyMethod;

	/** The company id. */
	private String companyId;

	private static final String QUERY_DATA_BY_COMPANYID = "SELECT l FROM PpemtNewLayout l WHERE l.companyId = :companyId";
	private static final String GET_LAYOUT_ITEM = "SELECT l FROM PpemtLayoutItemCls l WHERE l.ppemtNewLayoutPk.layoutId = :layoutId";

	@Override
	public void doCopy() {

		// Get company zero id
		String userContractCode = AppContexts.user().companyId();
		String companyZeroId = userContractCode + "-0000";
		// Get company zero data
		Optional<PpemtNewLayout> entityComZero = this.queryProxy().query(QUERY_DATA_BY_COMPANYID, PpemtNewLayout.class)
				.setParameter("companyId", companyZeroId).getSingle();

		Optional<PpemtNewLayout> entityCurrentCom = this.queryProxy()
				.query(QUERY_DATA_BY_COMPANYID, PpemtNewLayout.class).setParameter("companyId", companyId).getSingle();

		switch (copyMethod) {
		case REPLACE_ALL:
			entityComZero.ifPresent(entity -> {

				// Delete all old data
				entityCurrentCom.ifPresent(item -> {
					// layoutID of current company
					String CurrentComlayoutId = item.ppemtNewLayoutPk.layoutId;

					// get data layout item cls of current company
					List<PpemtLayoutItemCls> itemList = this.queryProxy()
							.query(GET_LAYOUT_ITEM, PpemtLayoutItemCls.class)
							.setParameter("layoutId", CurrentComlayoutId).getList();

					// remove all data layout item cls of current company
					if (!itemList.isEmpty()) {
						this.commandProxy().removeAll(itemList);
					}

					// remove data layout of current company
					this.commandProxy().remove(item);

					this.getEntityManager().flush();
				});

				// set company ID
				entity.companyId = companyId;

				// get layoutID
				String layoutId = IdentifierUtil.randomUniqueId();

				// get get data layout item cls of company Zero
				List<PpemtLayoutItemCls> itemList = this.queryProxy().query(GET_LAYOUT_ITEM, PpemtLayoutItemCls.class)
						.setParameter("layoutId", entity.ppemtNewLayoutPk.layoutId).getList();

				// set layoutID
				itemList.forEach(item -> {
					item.ppemtLayoutItemClsPk.layoutId = layoutId;

				});

				// Insert new data
				this.commandProxy().insert(entity);
				this.commandProxy().insertAll(itemList);

			});

		case ADD_NEW:

			// Insert Data
			entityComZero.ifPresent(entity -> {
				if (entityCurrentCom == null) {
					// set company ID
					entity.companyId = companyId;

					// get layoutID
					String layoutId = IdentifierUtil.randomUniqueId();

					// get get data layout item cls of company Zero
					List<PpemtLayoutItemCls> itemList = this.queryProxy()
							.query(GET_LAYOUT_ITEM, PpemtLayoutItemCls.class)
							.setParameter("layoutId", entity.ppemtNewLayoutPk.layoutId).getList();

					// set layoutID
					itemList.forEach(item -> {
						item.ppemtLayoutItemClsPk.layoutId = layoutId;

					});

					// Insert new data
					this.commandProxy().insert(entity);
					this.commandProxy().insertAll(itemList);
				}
			});
		case DO_NOTHING:
			// Do nothing
		default:
			break;

		}
	}

}
