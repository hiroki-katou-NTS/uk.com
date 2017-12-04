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

@Stateless
public class JpaItemGroupRepository extends JpaRepository implements IPersonInfoItemGroupRepository {

	private final static String SELECT_ALL = "SELECT DISTINCT g FROM PpemtPInfoItemGroup g";

	private final static String SELECT_BY_KEY = SELECT_ALL + " WHERE g.ppemtPinfoItemGroupPk.groupItemId = :groupId";

	private final static String SELECT_ALL_ORDER_BY_ASC = SELECT_ALL + " INNER JOIN PpemtPInfoItemGroupDf d"
			+ " ON g.ppemtPinfoItemGroupPk.groupItemId = d.ppemtPInfoItemGroupDfPk.groupItemId"
			+ " ORDER BY g.dispOrder ASC";

	private static final String GET_ALL_ITEM_DIFINATION = "SELECT e.ppemtPInfoItemGroupDfPk.itemDefId FROM PpemtPInfoItemGroup d"
			+ " INNER JOIN PpemtPInfoItemGroupDf e"
			+ " ON d.ppemtPinfoItemGroupPk.groupItemId = e.ppemtPInfoItemGroupDfPk.groupItemId"
			+ " AND d.ppemtPinfoItemGroupPk.groupItemId = :groupItemId";

	@Override
	public List<PersonInfoItemGroup> getAll() {
		return this.queryProxy().query(SELECT_ALL_ORDER_BY_ASC, PpemtPInfoItemGroup.class).getList(c -> toDomain(c));
	}

	@Override
	public Optional<PersonInfoItemGroup> getById(String groupId) {
		PpemtPInfoItemGroup entity = this.queryProxy().query(SELECT_BY_KEY, PpemtPInfoItemGroup.class)
				.setParameter("groupId", groupId).getSingleOrNull();

		if (entity == null) {
			return Optional.empty();
		} else {
			return Optional.of(toDomain(entity));
		}
	}

	@Override
	public List<String> getListItemIdByGrId(String groupItemId) {
		List<Object> resultList = this.queryProxy().query(GET_ALL_ITEM_DIFINATION, Object.class)
				.setParameter("groupItemId", groupItemId).getList();

		return !resultList.isEmpty() ? resultList.stream().map(item -> item.toString()).collect(Collectors.toList())
				: new ArrayList<>();
	}

	private static PersonInfoItemGroup toDomain(PpemtPInfoItemGroup entity) {
		return PersonInfoItemGroup.createFromJavaType(entity.ppemtPinfoItemGroupPk.groupItemId, entity.companyId,
				entity.groupName, entity.dispOrder);
	}
}
