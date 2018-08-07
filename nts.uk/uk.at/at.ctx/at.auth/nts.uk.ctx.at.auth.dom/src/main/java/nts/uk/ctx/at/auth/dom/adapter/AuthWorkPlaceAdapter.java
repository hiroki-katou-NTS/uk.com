package nts.uk.ctx.at.auth.dom.adapter;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface AuthWorkPlaceAdapter {
	List<String> getListWorkPlaceID(String employeeID , GeneralDate referenceDate);
	
	WorkplaceInfoImport getWorkplaceListId(GeneralDate referenceDate, String employeeID, boolean referEmployee);
}
