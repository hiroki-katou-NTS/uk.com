package repository.person.setting.selection;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selection.IPerInfoSelectionItemRepository;
import nts.uk.ctx.bs.person.dom.person.setting.selection.PerInfoSelectionItem;

public class JpaPerInfoSelectionItemRepository extends JpaRepository implements IPerInfoSelectionItemRepository{

	@Override
	public void add(PerInfoSelectionItem perInfoSelectionItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(PerInfoSelectionItem perInfoSelectionItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(PerInfoSelectionItem perInfoSelectionItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PerInfoSelectionItem> getAllPerInfoSelectionItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PerInfoSelectionItem getPerInfoSelectionItem(String selectionItemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkExist(String selectionItemId) {
		// TODO Auto-generated method stub
		return false;
	}

}
