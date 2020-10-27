/**
 * 
 */
package nts.uk.ctx.pereg.infra.repository.person.info.groupitem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.person.groupitem.IPersonInfoItemGroupRepository;
import nts.uk.ctx.pereg.dom.person.groupitem.PersonInfoItemGroup;
import nts.uk.ctx.pereg.infra.entity.person.info.groupitem.PpemtPInfoItemGroup;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaItemGroupRepository extends JpaRepository implements IPersonInfoItemGroupRepository {

	private final static String SELECT_ALL = "SELECT DISTINCT g FROM PpemtPInfoItemGroup g";

	private final static String SELECT_BY_KEY =  String.join(" ", SELECT_ALL,
			"WHERE g.companyId = :companyId",
			"AND g.ppemtGroupItemPk.groupItemId = :groupId");

	private final static String SELECT_ALL_ORDER_BY_ASC = String.join(" ", SELECT_ALL,
			"INNER JOIN PpemtPInfoItemGroupDf d",
			"ON g.ppemtGroupItemPk.groupItemId = d.ppemtPInfoItemGroupDfPk.groupItemId",
			"WHERE g.companyId = :companyId",
			"ORDER BY g.dispOrder ASC");

	private static final String GET_ALL_ITEM_DIFINATION = String.join(" ",
			"SELECT e.ppemtPInfoItemGroupDfPk.itemDefId FROM PpemtPInfoItemGroup d",
			"INNER JOIN PpemtPInfoItemGroupDf e",
			"ON d.ppemtGroupItemPk.groupItemId = e.ppemtPInfoItemGroupDfPk.groupItemId",
			"AND d.ppemtGroupItemPk.groupItemId = :groupItemId",
			"WHERE d.companyId = :companyId");

	@Override
	public List<PersonInfoItemGroup> getAll() {
		String cId = AppContexts.user().companyId();
		return this.queryProxy().query(SELECT_ALL_ORDER_BY_ASC, PpemtPInfoItemGroup.class)
				.setParameter("companyId", cId)
				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<PersonInfoItemGroup> getById(String groupId) {
		String cId = AppContexts.user().companyId();
		PpemtPInfoItemGroup entity = this.queryProxy().query(SELECT_BY_KEY, PpemtPInfoItemGroup.class)
				.setParameter("companyId", cId)
				.setParameter("groupId", groupId)
				.getSingleOrNull();

		if (entity == null) {
			return Optional.empty();
		} else {
			return Optional.of(toDomain(entity));
		}
	}

	@Override
	public List<String> getListItemIdByGrId(String groupItemId) {
		String cId = AppContexts.user().companyId();
		List<Object> resultList = this.queryProxy().query(GET_ALL_ITEM_DIFINATION, Object.class)
				.setParameter("companyId", cId)
				.setParameter("groupItemId", groupItemId)
				.getList();

		return !resultList.isEmpty() ? resultList.stream().map(item -> item.toString()).collect(Collectors.toList())
				: new ArrayList<>();
	}

	private static PersonInfoItemGroup toDomain(PpemtPInfoItemGroup entity) {
		return PersonInfoItemGroup.createFromJavaType(entity.ppemtGroupItemPk.groupItemId, entity.companyId,
				entity.groupName, entity.dispOrder);
	}
}
