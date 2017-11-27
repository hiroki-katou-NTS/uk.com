package nts.uk.ctx.at.record.infra.repository.calculationsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.record.dom.calculationsetting.repository.StampReflectionManagementRepository;

@Stateless
public class JpaStampReflectionManagementRepository extends JpaRepository implements StampReflectionManagementRepository{

	@Override
	public Optional<StampReflectionManagement> findByCid(String companyId) {

		return null;
	}

}
