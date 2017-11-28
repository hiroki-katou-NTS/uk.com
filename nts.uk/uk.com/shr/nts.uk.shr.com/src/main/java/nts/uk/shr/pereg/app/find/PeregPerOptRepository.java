package nts.uk.shr.pereg.app.find;

import java.util.List;

import nts.uk.shr.pereg.app.find.dto.PersonOptionalDto;

public interface PeregPerOptRepository {
	/**
	 * get optional data of person category type by record id 
	 * 
	 * @param recordId
	 * @return
	 */
	List<PersonOptionalDto> getData(String recordId);
}
