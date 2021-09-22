package nts.uk.ctx.at.record.infra.repository.jobmanagement.favoritetask.favoritetaskitem;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteTaskDisplayOrder;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteTaskDisplayOrderRepository;

/**
 * 
 * @author tutt
 *
 */
@Stateless
public class JpaFavoriteTaskDisplayOrderRepository implements FavoriteTaskDisplayOrderRepository {

	@Override
	public void insert(FavoriteTaskDisplayOrder order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(FavoriteTaskDisplayOrder order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String employeeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<FavoriteTaskDisplayOrder> get(String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

}
