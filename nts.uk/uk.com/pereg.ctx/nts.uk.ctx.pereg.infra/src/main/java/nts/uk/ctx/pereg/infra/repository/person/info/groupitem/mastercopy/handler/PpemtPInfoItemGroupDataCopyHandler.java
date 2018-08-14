package nts.uk.ctx.pereg.infra.repository.person.info.groupitem.mastercopy.handler;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.person.layout.mastercopy.CopyMethod;
import nts.uk.ctx.pereg.dom.person.layout.mastercopy.DataCopyHandler;
import nts.uk.ctx.pereg.infra.entity.person.info.groupitem.PpemtPInfoItemGroup;
import nts.uk.ctx.pereg.infra.entity.person.info.groupitem.definition.PpemtPInfoItemGroupDf;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PpemtPInfoItemGroupDataCopyHandler extends JpaRepository implements DataCopyHandler {

	/** The copy method. */
	private CopyMethod copyMethod;

	/** The company id. */
	private String companyId;

	private static final String QUERY_DATA_BY_COMPANYID = "SELECT p FROM PpemtPInfoItemGroup p WHERE p.companyId = :companyId";
	private static final String GET_ITEM_GROUP = "SELECT p FROM PpemtPInfoItemGroupDf p WHERE p.PpemtPInfoItemGroupDfPk.groupItemId = :groupItemId";

	@Override
	public void doCopy() {

		// Get company zero id
		String userContractCode = AppContexts.user().companyId();
		String companyZeroId = userContractCode + "-0000";
		// Get company zero data
		Optional<PpemtPInfoItemGroup> entityComZero = this.queryProxy()
				.query(QUERY_DATA_BY_COMPANYID, PpemtPInfoItemGroup.class).setParameter("companyId", companyZeroId)
				.getSingle();

		Optional<PpemtPInfoItemGroup> entityCurrentCom = this.queryProxy()
				.query(QUERY_DATA_BY_COMPANYID, PpemtPInfoItemGroup.class).setParameter("companyId", companyId)
				.getSingle();

		switch (copyMethod) {
		case REPLACE_ALL:
			entityComZero.ifPresent(entity -> {

				// Delete all old data
				entityCurrentCom.ifPresent(item -> {
					// layoutID of current company
					String groupItemId = item.ppemtPinfoItemGroupPk.groupItemId;

					// get data layout item cls of current company
					List<PpemtPInfoItemGroupDf> itemList = this.queryProxy()
							.query(GET_ITEM_GROUP, PpemtPInfoItemGroupDf.class).setParameter("groupItemId", groupItemId)
							.getList();

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

				// get group item ID
				String groupItemId = UUID.randomUUID().toString();

				// get get data layout item cls of company Zero
				List<PpemtPInfoItemGroupDf> itemList = this.queryProxy()
						.query(GET_ITEM_GROUP, PpemtPInfoItemGroupDf.class)
						.setParameter("groupItemId", entity.ppemtPinfoItemGroupPk.groupItemId).getList();

				// set layoutID
				itemList.forEach(item -> {
					item.ppemtPInfoItemGroupDfPk.groupItemId = groupItemId;
					item.companyId = companyId;

				});

				// Insert new data
				this.commandProxy().insert(entity);
				this.commandProxy().insertAll(itemList);

			});
			break;

		case ADD_NEW:

			// Insert Data
			entityComZero.ifPresent(entity -> {

				if (!entityCurrentCom.isPresent()) {

					// set company ID
					entity.companyId = companyId;

					// get group item ID
					String groupItemId = UUID.randomUUID().toString();

					// get get data layout item cls of company Zero
					List<PpemtPInfoItemGroupDf> itemList = this.queryProxy()
							.query(GET_ITEM_GROUP, PpemtPInfoItemGroupDf.class)
							.setParameter("groupItemId", entity.ppemtPinfoItemGroupPk.groupItemId).getList();

					// set layoutID
					itemList.forEach(item -> {
						item.ppemtPInfoItemGroupDfPk.groupItemId = groupItemId;
						item.companyId = companyId;

					});

					// Insert new data
					this.commandProxy().insert(entity);
					this.commandProxy().insertAll(itemList);
				} else {
					String groupItemId = entityCurrentCom.get().ppemtPinfoItemGroupPk.groupItemId;
					// get data info item cls of current company
					List<PpemtPInfoItemGroupDf> curentComItemList = this.queryProxy()
							.query(GET_ITEM_GROUP, PpemtPInfoItemGroupDf.class).setParameter("groupItemId", groupItemId)
							.getList();

					if (curentComItemList.isEmpty()) {
						// copy data if data item list is empty
						List<PpemtPInfoItemGroupDf> itemList = this.queryProxy()
								.query(GET_ITEM_GROUP, PpemtPInfoItemGroupDf.class)
								.setParameter("groupItemId", entity.ppemtPinfoItemGroupPk.groupItemId).getList();

						itemList.forEach(item -> {
							item.ppemtPInfoItemGroupDfPk.groupItemId = groupItemId;
							item.companyId = companyId;
						});

						this.commandProxy().insertAll(itemList);
					}

				}
			});
			break;
		case DO_NOTHING:
			// Do nothing
			break;
		default:
			break;

		}

	}

}
