package nts.uk.ctx.at.request.infra.repository.application.overtime;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTimeRepository;

public class JpaAppOverTimeRepository extends JpaRepository implements AppOverTimeRepository{

	@Override
	public AppOverTime find(String companyId, String appId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub
		
	}

}
