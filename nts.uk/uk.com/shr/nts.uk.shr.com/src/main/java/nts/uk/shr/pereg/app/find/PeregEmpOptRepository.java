package nts.uk.shr.pereg.app.find;

import java.util.List;

import nts.uk.shr.pereg.app.find.dto.EmpOptionalDto;

public interface PeregEmpOptRepository {
	/**
	 * get optional data of employee category type by record id 
	 * 
	 * @param recordId
	 * @return
	 */
	List<EmpOptionalDto> getData(String recordId);
	
}
