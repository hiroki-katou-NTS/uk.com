package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

import java.util.List;
import java.util.Optional;

public interface StandardWidgetRepository {
	
	/**
	 * Find by toppagerPartID
	 * @param toppagePartID
	 * @return StandardWidget
	 */
	List<StandardWidget> getAll();
	
	Optional<StandardWidget> getByID(String ToppagePartID, String companyID);
	
	List<StandardWidget> findByTopPagePartId(List<String> toppagePartIDs, String cID);
}
