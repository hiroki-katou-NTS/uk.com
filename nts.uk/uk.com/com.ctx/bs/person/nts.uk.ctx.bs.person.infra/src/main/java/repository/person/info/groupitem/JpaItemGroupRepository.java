/**
 * 
 */
package repository.person.info.groupitem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.person.info.groupitem.PpemtPInfoItemGroup;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.groupitem.IPersonInfoItemGroupRepository;
import nts.uk.ctx.bs.person.dom.person.groupitem.PersonInfoItemGroup;

@Stateless
public class JpaItemGroupRepository extends JpaRepository implements IPersonInfoItemGroupRepository {

	private final static String SELECT_ALL = "SELECT c FROM  PpemtPInfoItemGroup c";

	private final static String SELECT_BY_KEY = SELECT_ALL + " WHERE c.ppemtPinfoItemGroupPk.groupItemId = :groupId";

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

	/*
	 * private static PpemtPInfoItemGroup toEntity(PersonInfoItemGroup domain) {
	 * PpemtPInfoItemGroup entity = new PpemtPInfoItemGroup();
	 * entity.ppemtPinfoItemGroupPk = new
	 * PpemtPInfoItemGroupPk(domain.getPersonInfoItemGroupID()); entity.companyId =
	 * domain.getCompanyId(); entity.groupName = domain.getFieldGroupName().v();
	 * entity.dispOrder = domain.getDispOrder().v().toString(); return entity; }
	 */
}
