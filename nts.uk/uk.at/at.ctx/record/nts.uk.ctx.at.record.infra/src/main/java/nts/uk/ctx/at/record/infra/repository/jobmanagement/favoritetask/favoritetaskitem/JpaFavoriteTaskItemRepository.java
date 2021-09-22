package nts.uk.ctx.at.record.infra.repository.jobmanagement.favoritetask.favoritetaskitem;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteTaskItem;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteTaskItemRepository;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.TaskContent;

/**
 * 
 * 
 * @author tutt
 *
 */
public class JpaFavoriteTaskItemRepository implements FavoriteTaskItemRepository  {

	@Override
	public void insert(FavoriteTaskItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(FavoriteTaskItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String employeeID, String favoriteId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FavoriteTaskItem> getAll(String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<FavoriteTaskItem> getByFavoriteId(String favoriteId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FavoriteTaskItem> getBySameSetting(String employeeId, List<TaskContent> contents) {
		// TODO Auto-generated method stub
		return null;
	}

}
