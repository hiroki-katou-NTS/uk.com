package nts.uk.ctx.basic.infra.repository.organization.workplace;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.basic.dom.organization.workPlace.WorkPlace;
import nts.uk.ctx.basic.dom.organization.workPlace.WorkPlaceCode;
import nts.uk.ctx.basic.dom.organization.workPlace.WorkPlaceRepository;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class JpaWorkPlaceRepository extends JpaRepository implements WorkPlaceRepository{

	@Override
	public void add(WorkPlace workPlace) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(WorkPlace workPlace) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String companyCode, WorkPlaceCode workPlaceCode, String historyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerMemo(String companyCode, String historyId, Memo memo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<WorkPlace> findSingle(String companyCode, WorkPlaceCode workPlaceCode, String historyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkPlace> findAll(String companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
