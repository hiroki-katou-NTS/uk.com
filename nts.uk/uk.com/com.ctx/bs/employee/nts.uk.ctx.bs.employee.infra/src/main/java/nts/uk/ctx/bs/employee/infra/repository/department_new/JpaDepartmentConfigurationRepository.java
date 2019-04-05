package nts.uk.ctx.bs.employee.infra.repository.department_new;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.department_new.DepartmentConfiguration;
import nts.uk.ctx.bs.employee.dom.department_new.DepartmentConfigurationRepository;
import nts.uk.ctx.bs.employee.infra.entity.department_new.BsymtDepartmentConfig;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaDepartmentConfigurationRepository extends JpaRepository implements DepartmentConfigurationRepository {

	@Override
	public Optional<DepartmentConfiguration> getDepConfig(String companyId) {
		String query = "SELECT c FROM BsymtDepartmentConfig c WHERE c.pk.companyId = :companyId";
		List<BsymtDepartmentConfig> listEntities = this.queryProxy().query(query, BsymtDepartmentConfig.class)
				.setParameter("companyId", companyId).getList();
		return Optional.ofNullable(BsymtDepartmentConfig.toDomain(listEntities));
	}

	@Override
	public void deleteConfiguration(String departmentHistoryId) {
		// TODO Auto-generated method stub

	}

}
