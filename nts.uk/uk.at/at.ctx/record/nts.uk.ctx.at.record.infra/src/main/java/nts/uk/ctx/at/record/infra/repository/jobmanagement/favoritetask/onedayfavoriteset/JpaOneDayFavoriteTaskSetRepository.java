package nts.uk.ctx.at.record.infra.repository.jobmanagement.favoritetask.onedayfavoriteset;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.OneDayFavoriteSet;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.OneDayFavoriteTaskSetRepository;

/**
 * 
 * @author tutt
 *
 */
public class JpaOneDayFavoriteTaskSetRepository extends JpaRepository implements OneDayFavoriteTaskSetRepository {

	@Override
	public void insert(OneDayFavoriteSet set) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(OneDayFavoriteSet set) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String employee, String favoriteId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<OneDayFavoriteSet> getAll(String employee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<OneDayFavoriteSet> getByFavoriteId(String favoriteId) {
		// TODO Auto-generated method stub
		return null;
	}

}
