/**
 * 
 */
package repository.person.info.groupitem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.maintenencelayout.PpemtMaintenanceLayout;
import entity.maintenencelayout.PpemtMaintenanceLayoutPk;
import entity.person.info.groupitem.PpemtPinfoItemGroup;
import entity.person.info.groupitem.PpemtPinfoItemGroupPk;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.groupitem.PersonInfoItemGroup;
import nts.uk.ctx.bs.person.dom.person.groupitem.PersonInfoItemGroupRepository;
import nts.uk.ctx.bs.person.dom.person.maintenancelayout.MaintenanceLayout;
import nts.uk.ctx.bs.person.dom.person.maintenancelayout.MaintenanceLayoutRepository;

@Stateless
public class JpaGroupItemtRepository extends JpaRepository implements PersonInfoItemGroupRepository {

	private String getAllGroupItem = "select c FROM  PpemtPinfoItemGroup c";

	private static PersonInfoItemGroup toDomain(PpemtPinfoItemGroup entity) {
		val domain = PersonInfoItemGroup.createFromJavaType(entity.ppemtPinfoItemGroupPk.groupItemId, entity.companyId,
				entity.groupName, Integer.parseInt(entity.dispOrder));
		return domain;
	}

	private static PpemtPinfoItemGroup toEntity(PersonInfoItemGroup domain) {
		PpemtPinfoItemGroup entity = new PpemtPinfoItemGroup();
		entity.ppemtPinfoItemGroupPk = new PpemtPinfoItemGroupPk(domain.getPersonInfoItemGroupID());
		entity.companyId = domain.getCompanyId();
		entity.groupName = domain.getFieldGroupName().v();
		entity.dispOrder = domain.getDisPOrder().v().toString();
		return entity;
	}

	@Override
	public List<PersonInfoItemGroup> getAllPersonInfoItemGroup() {
		return this.queryProxy().query(getAllGroupItem, PpemtPinfoItemGroup.class).getList(c -> toDomain(c));
	}
}
