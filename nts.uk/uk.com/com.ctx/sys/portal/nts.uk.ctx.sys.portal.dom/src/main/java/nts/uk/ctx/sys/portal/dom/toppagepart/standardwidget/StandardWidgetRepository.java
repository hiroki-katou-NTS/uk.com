package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

import java.util.List;
import java.util.Optional;

public interface StandardWidgetRepository {
	
	/**
	 * Find by toppagerPartID
	 * @param toppagePartID
	 * @return StandardWidget
	 */
	Optional<StandardWidget> getByID (String toppagePartID);
	
	//List<StandardWidget> getByListID (String listToppagePartID);
}
