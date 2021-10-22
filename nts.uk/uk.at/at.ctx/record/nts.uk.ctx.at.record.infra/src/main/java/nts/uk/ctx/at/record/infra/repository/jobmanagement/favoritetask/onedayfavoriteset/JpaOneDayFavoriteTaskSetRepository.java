package nts.uk.ctx.at.record.infra.repository.jobmanagement.favoritetask.onedayfavoriteset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.OneDayFavoriteSet;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.OneDayFavoriteTaskSetRepository;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.favoritetask.favoritetaskitem.KrcdtTaskFavFrameSet;
import nts.uk.ctx.at.record.infra.entity.jobmanagement.favoritetask.onedayfavoriteset.KrcdtTaskFavDaySet;

/**
 * 
 * @author tutt
 *
 */
@Stateless
public class JpaOneDayFavoriteTaskSetRepository extends JpaRepository implements OneDayFavoriteTaskSetRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT s FROM KrcdtTaskFavDaySet s";

	private static final String SELECT_BY_SID = SELECT_ALL_QUERY_STRING + " WHERE s.sId = :sId";
	private static final String SELECT_BY_FAID = SELECT_ALL_QUERY_STRING + " WHERE s.favId = :favId";
	private static final String SELECT_BY_FAVID_AND_SID = SELECT_BY_FAID + " AND s.sId = :sId";

	@Override
	public void insert(OneDayFavoriteSet set) {
		this.commandProxy().insert(new KrcdtTaskFavDaySet(set));
	}

	@Override
	public void update(OneDayFavoriteSet set) {
		this.commandProxy().update(new KrcdtTaskFavDaySet(set));

	}

	@Override
	public void delete(String sId, String favId) {
		Optional<KrcdtTaskFavDaySet> setEntity = this.queryProxy()
				.query(SELECT_BY_FAVID_AND_SID, KrcdtTaskFavDaySet.class).setParameter("favId", favId)
				.setParameter("sId", sId).getSingle();

		if (setEntity.isPresent()) {
			this.commandProxy().remove(setEntity.get());
		}
	}

	@Override
	public List<OneDayFavoriteSet> getAll(String sId) {
		List<KrcdtTaskFavDaySet> entities = this.queryProxy().query(SELECT_BY_SID, KrcdtTaskFavDaySet.class).setParameter("sId", sId)
				.getList();
		
		return null;
	}

	@Override
	public Optional<OneDayFavoriteSet> getByFavoriteId(String favoriteId) {
		// TODO Auto-generated method stub
		return null;
	}

}
