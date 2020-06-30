package nts.uk.ctx.at.request.infra.repository.application.workchange;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeRepository;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaAppWorkChangeRepository extends JpaRepository implements AppWorkChangeRepository {

	@Override
	public Optional<AppWorkChange> findbyID(String appID) {
		return null;
	}

	@Override
	public void add(AppWorkChange appWorkChange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(AppWorkChange appWorkChange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(AppWorkChange appWorkChange) {
		// TODO Auto-generated method stub
		
	}

}
