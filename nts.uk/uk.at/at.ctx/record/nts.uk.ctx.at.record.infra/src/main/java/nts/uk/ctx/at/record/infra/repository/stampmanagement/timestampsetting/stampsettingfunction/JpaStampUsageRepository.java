package nts.uk.ctx.at.record.infra.repository.stampmanagement.timestampsetting.stampsettingfunction;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampsettingfunction.StampUsageRepository;

@Stateless
public class JpaStampUsageRepository extends JpaRepository implements StampUsageRepository {

	@Override
	public void insert(Object domain) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Object domain) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Object> get() {
		// TODO Auto-generated method stub
		return null;
	}

}
