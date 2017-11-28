package nts.uk.shr.pereg.app.find;

import java.util.List;

import nts.uk.shr.pereg.app.find.dto.EmpOptionalDto;
import nts.uk.shr.pereg.app.find.dto.PersonOptionalDto;

public interface PeregEmpUserDefFinderRepository {
	/**
	 * get optional data of employee category type by record id 
	 * 
	 * @param recordId
	 * @return
	 */
	List<EmpOptionalDto> getEmpOptionalDto(String recordId);
	
}
