package nts.uk.ctx.bs.person.dom.person.setting.selectionitem;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author tuannv
 *
 */
public interface IPerInfoSelectionItemRepository {

	void add(PerInfoSelectionItem perInfoSelectionItem);

	void update(PerInfoSelectionItem perInfoSelectionItem);

	void remove(String selectionItemId);

	List<PerInfoSelectionItem> getAllSelectionItemByContractCd(String contractCd);

	Optional<PerInfoSelectionItem> getSelectionItemBySelectionItemId(String selectionItemId);

	Optional<PerInfoSelectionItem> getSelectionItemByName(String selectionItemName);
	
	/**
	 * getAllSelection
	 * @return List<PerInfoSelectionItem>
	 */
	List<PerInfoSelectionItem> getAllSelection(int selectionItemClsAtr);
	
	//Lanlt
	
}
