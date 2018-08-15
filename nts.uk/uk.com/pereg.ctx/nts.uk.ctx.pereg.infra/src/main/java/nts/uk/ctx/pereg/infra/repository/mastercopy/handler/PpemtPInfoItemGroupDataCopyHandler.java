package nts.uk.ctx.pereg.infra.repository.mastercopy.handler;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.mastercopy.*;
import nts.uk.ctx.pereg.infra.entity.layout.cls.PpemtLayoutItemCls;
import nts.uk.ctx.pereg.infra.entity.person.info.groupitem.PpemtPInfoItemGroup;
import nts.uk.ctx.pereg.infra.entity.person.info.groupitem.PpemtPInfoItemGroupPk;
import nts.uk.ctx.pereg.infra.entity.person.info.groupitem.definition.PpemtPInfoItemGroupDf;
import nts.uk.ctx.pereg.infra.entity.person.info.groupitem.definition.PpemtPInfoItemGroupDfPk;
import nts.uk.shr.com.context.AppContexts;

@Getter
@Setter
@NoArgsConstructor
public class PpemtPInfoItemGroupDataCopyHandler extends JpaRepository implements DataCopyHandler {

	/** The copy method. */
	private CopyMethod copyMethod;

	/** The company id. */
	private String companyId;

	/** The em. */
	EntityManager em;

	public PpemtPInfoItemGroupDataCopyHandler(CopyMethod copyMethod, String companyId, EntityManager em) {
		super();
		this.copyMethod = copyMethod;
		this.companyId = companyId;
		this.em = em;
	}

	private static final String QUERY_DATA_BY_COMPANYID = "SELECT p FROM PpemtPInfoItemGroup p WHERE p.companyId = :companyId";
	private static final String GET_ITEM_GROUP = "SELECT p FROM PpemtPInfoItemGroupDf p WHERE p.ppemtPInfoItemGroupDfPk.groupItemId = :groupItemId";
	private static final String DELETE_DATA = "DELETE FROM PpemtPInfoItemGroup p WHERE p.companyId = :companyId";
	private static final String DELETE_ITEM_GROUP = "DELETE FROM PpemtPInfoItemGroupDf p WHERE p.ppemtPInfoItemGroupDfPk.groupItemId= :groupItemId";

	@Override
	public void doCopy() {

		// Get company zero id
		String companyZeroId = AppContexts.user().zeroCompanyIdInContract();
		
		// Get company zero data
		List<PpemtPInfoItemGroup> entityComZero = this.em
				.createQuery(QUERY_DATA_BY_COMPANYID, PpemtPInfoItemGroup.class)
				.setParameter("companyId", companyZeroId).getResultList();

		List<PpemtPInfoItemGroup> entityCurrentCom = this.em
				.createQuery(QUERY_DATA_BY_COMPANYID, PpemtPInfoItemGroup.class).setParameter("companyId", companyId)
				.getResultList();

		switch (copyMethod) {
		case REPLACE_ALL:

			// Delete all old data
			entityCurrentCom.forEach(item -> {
				// layoutID of current company
				String groupItemId = item.ppemtPinfoItemGroupPk.groupItemId;

				// get data layout item cls of current company
				List<PpemtPInfoItemGroupDf> itemList = this.em.createQuery(GET_ITEM_GROUP, PpemtPInfoItemGroupDf.class)
						.setParameter("groupItemId", groupItemId).getResultList();

				// remove all data layout item cls of current company
				if (!itemList.isEmpty()) {
					this.em.createQuery(DELETE_ITEM_GROUP, PpemtPInfoItemGroupDf.class)
							.setParameter("groupItemId", groupItemId).executeUpdate();
				}

				// remove data layout of current company
				this.em.createQuery(DELETE_DATA, PpemtLayoutItemCls.class).setParameter("companyId", companyId)
						.executeUpdate();

				this.em.flush();
			});

			entityComZero.forEach(entity -> {

				// get group item ID
				String groupItemId = UUID.randomUUID().toString();
				PpemtPInfoItemGroupPk Pk = new PpemtPInfoItemGroupPk(groupItemId);
				PpemtPInfoItemGroup newEntity = new PpemtPInfoItemGroup(Pk, companyId, entity.groupName,
						entity.dispOrder);

				// get get data layout item cls of company Zero
				List<PpemtPInfoItemGroupDf> itemList = this.em.createQuery(GET_ITEM_GROUP, PpemtPInfoItemGroupDf.class)
						.setParameter("groupItemId", entity.ppemtPinfoItemGroupPk.groupItemId).getResultList();

				// Insert new data
				this.em.persist(newEntity);

				// set layoutID and insert
				itemList.forEach(item -> {
					// item.ppemtPInfoItemGroupDfPk.groupItemId = groupItemId;
					// item.companyId = companyId;

					PpemtPInfoItemGroupDfPk itemPk = new PpemtPInfoItemGroupDfPk(groupItemId,
							item.ppemtPInfoItemGroupDfPk.itemDefId);
					PpemtPInfoItemGroupDf itemEntity = new PpemtPInfoItemGroupDf(itemPk, companyId);
					this.em.persist(itemEntity);

				});

			});
			break;

		case ADD_NEW:

			if (entityCurrentCom.isEmpty()) {

				entityComZero.forEach(entity -> {

					// get group item ID
					String groupItemId = UUID.randomUUID().toString();

					PpemtPInfoItemGroupPk Pk = new PpemtPInfoItemGroupPk(groupItemId);
					PpemtPInfoItemGroup newEntity = new PpemtPInfoItemGroup(Pk, companyId, entity.groupName,
							entity.dispOrder);

					// get get data layout item cls of company Zero
					List<PpemtPInfoItemGroupDf> itemList = this.em
							.createQuery(GET_ITEM_GROUP, PpemtPInfoItemGroupDf.class)
							.setParameter("groupItemId", entity.ppemtPinfoItemGroupPk.groupItemId).getResultList();

					// Insert new data
					this.em.persist(newEntity);

					// set layoutID and insert
					itemList.forEach(item -> {
						PpemtPInfoItemGroupDfPk itemPk = new PpemtPInfoItemGroupDfPk(groupItemId,
								item.ppemtPInfoItemGroupDfPk.itemDefId);
						PpemtPInfoItemGroupDf itemEntity = new PpemtPInfoItemGroupDf(itemPk, companyId);
						this.em.persist(itemEntity);
					});
				});

			} else {
				
				entityComZero.forEach(e -> {
					if (!this.checkContainGroupName(entityCurrentCom, e)) {
						PpemtPInfoItemGroup entity = e;
						// get group item ID
						String ItemId = UUID.randomUUID().toString();

						PpemtPInfoItemGroupPk Pk = new PpemtPInfoItemGroupPk(ItemId);
						PpemtPInfoItemGroup newEntity = new PpemtPInfoItemGroup(Pk, companyId, entity.groupName,
								entity.dispOrder);

						// get get data layout item cls of company Zero
						List<PpemtPInfoItemGroupDf> itemList = this.em
								.createQuery(GET_ITEM_GROUP, PpemtPInfoItemGroupDf.class)
								.setParameter("groupItemId", entity.ppemtPinfoItemGroupPk.groupItemId).getResultList();

						// Insert new data
						this.em.persist(newEntity);

						// set layoutID and insert
						itemList.forEach(item -> {
							PpemtPInfoItemGroupDfPk itemPk = new PpemtPInfoItemGroupDfPk(ItemId,
									item.ppemtPInfoItemGroupDfPk.itemDefId);
							PpemtPInfoItemGroupDf itemEntity = new PpemtPInfoItemGroupDf(itemPk, companyId);
							this.em.persist(itemEntity);
						});
					}
				});

			}
			break;
		case DO_NOTHING:
			// Do nothing
		default:
			break;

		}

	}

	private Boolean checkContainGroupName(List<PpemtPInfoItemGroup> groupList, PpemtPInfoItemGroup item) {
		List<String> groupNameList = groupList.stream().map(e -> e.groupName.trim()).collect(Collectors.toList());
		return groupNameList.contains(item.groupName.trim());
	}

}
