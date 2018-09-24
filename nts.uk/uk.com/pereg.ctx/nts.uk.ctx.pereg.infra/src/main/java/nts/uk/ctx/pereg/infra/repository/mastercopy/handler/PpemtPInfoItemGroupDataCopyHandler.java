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
public class PpemtPInfoItemGroupDataCopyHandler extends DataCopyHandler {

	public PpemtPInfoItemGroupDataCopyHandler(int copyMethod, String companyId, EntityManager em) {
		super();
		this.copyMethod = copyMethod;
		this.companyId = companyId;
		this.entityManager = em;
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
		List<PpemtPInfoItemGroup> entityComZero = this.entityManager
				.createQuery(QUERY_DATA_BY_COMPANYID, PpemtPInfoItemGroup.class)
				.setParameter("companyId", companyZeroId).getResultList();

		List<PpemtPInfoItemGroup> entityCurrentCom = this.entityManager
				.createQuery(QUERY_DATA_BY_COMPANYID, PpemtPInfoItemGroup.class).setParameter("companyId", companyId)
				.getResultList();

		if (entityComZero.isEmpty())
			return;
		switch (copyMethod) {
		case REPLACE_ALL:

			// Delete all old data
			entityCurrentCom.forEach(item -> {
				// layoutID of current company
				String groupItemId = item.ppemtPinfoItemGroupPk.groupItemId;

				// get data layout item cls of current company
				List<PpemtPInfoItemGroupDf> itemList = this.entityManager
						.createQuery(GET_ITEM_GROUP, PpemtPInfoItemGroupDf.class)
						.setParameter("groupItemId", groupItemId).getResultList();

				// remove all data layout item cls of current company
				if (!itemList.isEmpty()) {
					this.entityManager.createQuery(DELETE_ITEM_GROUP, PpemtPInfoItemGroupDf.class)
							.setParameter("groupItemId", groupItemId).executeUpdate();
				}

				// remove data layout of current company
				this.entityManager.createQuery(DELETE_DATA, PpemtLayoutItemCls.class)
						.setParameter("companyId", companyId).executeUpdate();

				this.entityManager.flush();
			});

			entityComZero.forEach(entity -> {

				// get group item ID
				String groupItemId = UUID.randomUUID().toString();
				PpemtPInfoItemGroupPk Pk = new PpemtPInfoItemGroupPk(groupItemId);
				PpemtPInfoItemGroup newEntity = new PpemtPInfoItemGroup(Pk, companyId, entity.groupName,
						entity.dispOrder);

				// get get data layout item cls of company Zero
				List<PpemtPInfoItemGroupDf> itemList = this.entityManager
						.createQuery(GET_ITEM_GROUP, PpemtPInfoItemGroupDf.class)
						.setParameter("groupItemId", entity.ppemtPinfoItemGroupPk.groupItemId).getResultList();

				// Insert new data
				this.entityManager.persist(newEntity);

				// set layoutID and insert
				itemList.forEach(item -> {
					// item.ppemtPInfoItemGroupDfPk.groupItemId = groupItemId;
					// item.companyId = companyId;

					PpemtPInfoItemGroupDfPk itemPk = new PpemtPInfoItemGroupDfPk(groupItemId,
							item.ppemtPInfoItemGroupDfPk.itemDefId);
					PpemtPInfoItemGroupDf itemEntity = new PpemtPInfoItemGroupDf(itemPk, companyId);
					this.entityManager.persist(itemEntity);

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
					List<PpemtPInfoItemGroupDf> itemList = this.entityManager
							.createQuery(GET_ITEM_GROUP, PpemtPInfoItemGroupDf.class)
							.setParameter("groupItemId", entity.ppemtPinfoItemGroupPk.groupItemId).getResultList();

					// Insert new data
					this.entityManager.persist(newEntity);

					// set layoutID and insert
					itemList.forEach(item -> {
						PpemtPInfoItemGroupDfPk itemPk = new PpemtPInfoItemGroupDfPk(groupItemId,
								item.ppemtPInfoItemGroupDfPk.itemDefId);
						PpemtPInfoItemGroupDf itemEntity = new PpemtPInfoItemGroupDf(itemPk, companyId);
						this.entityManager.persist(itemEntity);
					});
				});

			} else {

				entityComZero.forEach(e -> {
					PpemtPInfoItemGroup desData = this.checkContainGroupName(entityCurrentCom, e);
					if (desData == null) {
						PpemtPInfoItemGroup entity = e;
						// get group item ID
						String ItemId = UUID.randomUUID().toString();

						PpemtPInfoItemGroupPk Pk = new PpemtPInfoItemGroupPk(ItemId);
						PpemtPInfoItemGroup newEntity = new PpemtPInfoItemGroup(Pk, companyId, entity.groupName,
								entity.dispOrder);

						// get get data layout item cls of company Zero
						List<PpemtPInfoItemGroupDf> itemList = this.entityManager
								.createQuery(GET_ITEM_GROUP, PpemtPInfoItemGroupDf.class)
								.setParameter("groupItemId", entity.ppemtPinfoItemGroupPk.groupItemId).getResultList();

						// Insert new data
						this.entityManager.persist(newEntity);

						// set layoutID and insert
						itemList.forEach(item -> {
							PpemtPInfoItemGroupDfPk itemPk = new PpemtPInfoItemGroupDfPk(ItemId,
									item.ppemtPInfoItemGroupDfPk.itemDefId);
							PpemtPInfoItemGroupDf itemEntity = new PpemtPInfoItemGroupDf(itemPk, companyId);
							this.entityManager.persist(itemEntity);
						});
					} else {
						PpemtPInfoItemGroup zeroData = e;
						PpemtPInfoItemGroup desDataItem = desData;
						// get get data layout item cls of company Zero
						List<PpemtPInfoItemGroupDf> itemZeroList = this.entityManager
								.createQuery(GET_ITEM_GROUP, PpemtPInfoItemGroupDf.class)
								.setParameter("groupItemId", zeroData.ppemtPinfoItemGroupPk.groupItemId)
								.getResultList();

						// get get data layout item cls of destinatio company
						List<PpemtPInfoItemGroupDf> itemDesList = this.entityManager
								.createQuery(GET_ITEM_GROUP, PpemtPInfoItemGroupDf.class)
								.setParameter("groupItemId", desDataItem.ppemtPinfoItemGroupPk.groupItemId)
								.getResultList();

						List<String> defineIdDesList = itemDesList.stream()
								.map(item -> item.ppemtPInfoItemGroupDfPk.itemDefId.trim())
								.collect(Collectors.toList());
						
						itemZeroList.forEach(item -> {
							if (!defineIdDesList.contains(item.ppemtPInfoItemGroupDfPk.itemDefId.trim())) {
								PpemtPInfoItemGroupDfPk itemPk = new PpemtPInfoItemGroupDfPk(
										desDataItem.ppemtPinfoItemGroupPk.groupItemId,
										item.ppemtPInfoItemGroupDfPk.itemDefId);
								PpemtPInfoItemGroupDf itemEntity = new PpemtPInfoItemGroupDf(itemPk, companyId);
								this.entityManager.persist(itemEntity);
							}

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

	private PpemtPInfoItemGroup checkContainGroupName(List<PpemtPInfoItemGroup> groupList, PpemtPInfoItemGroup item) {
		List<String> groupNameList = groupList.stream().map(e -> e.groupName.trim()).collect(Collectors.toList());
		for (PpemtPInfoItemGroup info : groupList) {
			if (info.groupName.trim().equals(item.groupName.trim()))
				return info;
		}

		return null;
	}

}
