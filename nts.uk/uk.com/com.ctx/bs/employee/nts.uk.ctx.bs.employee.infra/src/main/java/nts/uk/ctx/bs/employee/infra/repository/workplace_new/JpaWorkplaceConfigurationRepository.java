package nts.uk.ctx.bs.employee.infra.repository.workplace_new;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.workplace_new.WorkplaceConfiguration;
import nts.uk.ctx.bs.employee.dom.workplace_new.WorkplaceConfigurationRepository;
import nts.uk.ctx.bs.employee.infra.entity.workplace_new.BsymtWorkplaceConfig;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaWorkplaceConfigurationRepository extends JpaRepository implements WorkplaceConfigurationRepository {

	@Override
	public Optional<WorkplaceConfiguration> getWkpConfig(String companyId) {
		String query = "SELECT c FROM BsymtWorkplaceConfig c WHERE c.pk.companyId = :companyId";
		List<BsymtWorkplaceConfig> listEntities = this.queryProxy().query(query, BsymtWorkplaceConfig.class)
				.setParameter("companyId", companyId).getList();
		return Optional.ofNullable(BsymtWorkplaceConfig.toDomain(listEntities));
	}

	@Override
	public void deleteConfiguration(String departmentHistoryId) {
		// TODO Auto-generated method stub

	}

}
