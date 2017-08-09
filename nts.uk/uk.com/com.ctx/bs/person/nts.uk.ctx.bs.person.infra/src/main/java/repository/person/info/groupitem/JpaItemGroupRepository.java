/**
 * 
 */
package repository.person.info.groupitem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import entity.person.info.groupitem.PpemtPInfoItemGroup;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.groupitem.IPersonInfoItemGroupRepository;
import nts.uk.ctx.bs.person.dom.person.groupitem.PersonInfoItemGroup;

@Stateless
public class JpaItemGroupRepository extends JpaRepository implements IPersonInfoItemGroupRepository {

	private final static String SELECT_ALL;

	private final static String SELECT_BY_KEY;
	
	private static final String GET_ALL_ITEM_DIFINATION;
	
	static {
		StringBuilder builderString = new StringBuilder();
		
		builderString = new StringBuilder();
		builderString.append("SELECT c FROM  PpemtPInfoItemGroup c");
		SELECT_ALL = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT c FROM  PpemtPInfoItemGroup c");
		builderString.append(" WHERE c.ppemtPinfoItemGroupPk.groupItemId = :groupId");
		SELECT_BY_KEY = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT e.ppemtPInfoItemGroupDfPk.itemDefId");
		builderString.append(" FROM PpemtPInfoItemGroup d");
		builderString.append(" INNER JOIN PpemtPInfoItemGroupDf e");
		builderString.append(" ON d.ppemtPinfoItemGroupPk.groupItemId = e.ppemtPInfoItemGroupDfPk.groupItemId");
		builderString.append(" AND d.ppemtPinfoItemGroupPk.groupItemId = :groupItemId");
		GET_ALL_ITEM_DIFINATION = builderString.toString();

	}


	@Override
	public List<PersonInfoItemGroup> getAll() {
		return this.queryProxy().query(SELECT_ALL, PpemtPInfoItemGroup.class).getList(c -> toDomain(c));
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

	private static PersonInfoItemGroup toDomain(PpemtPInfoItemGroup entity) {
		return PersonInfoItemGroup.createFromJavaType(entity.ppemtPinfoItemGroupPk.groupItemId, entity.companyId,
				entity.groupName, Integer.parseInt(entity.dispOrder));
	}

	@Override
	public List<String> getListItemIdByGrId(String groupItemId) {
		List<Object> resultList = this.queryProxy().query(GET_ALL_ITEM_DIFINATION, Object.class)
				.setParameter("groupItemId", groupItemId).getList();

		return !resultList.isEmpty() ? resultList.stream().map(item -> item.toString()).collect(Collectors.toList())
				: new ArrayList<>();
	}

	/*
	 * private static PpemtPInfoItemGroup toEntity(PersonInfoItemGroup domain) {
	 * PpemtPInfoItemGroup entity = new PpemtPInfoItemGroup();
	 * entity.ppemtPinfoItemGroupPk = new
	 * PpemtPInfoItemGroupPk(domain.getPersonInfoItemGroupID()); entity.companyId =
	 * domain.getCompanyId(); entity.groupName = domain.getFieldGroupName().v();
	 * entity.dispOrder = domain.getDispOrder().v().toString(); return entity; 
	 * }
	 */
}
