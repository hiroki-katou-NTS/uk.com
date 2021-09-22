package nts.uk.ctx.at.record.infra.repository.jobmanagement.favoritetask.onedayfavoriteset;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.OneDayFavoriteTaskDisplayOrder;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.OneDayFavoriteTaskDisplayOrderRepository;

/**
 * 
 * @author tutt
 *
 */
@Stateless
public class JpaOneDayFavoriteTaskDisplayOrderRepository implements OneDayFavoriteTaskDisplayOrderRepository {

	@Override
	public void insert(OneDayFavoriteTaskDisplayOrder order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(OneDayFavoriteTaskDisplayOrder order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String employeeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<OneDayFavoriteTaskDisplayOrder> get(String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

}
