/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.company.find;

import javax.ejb.Stateless;
import javax.transaction.Transactional;


/**
 * The Class Kmk004ComDtoFinder.
 */
@Stateless
@Transactional
public class Kmk004ComDtoFinder {
	// inject:
	
	public Kmk004ComDto findKmk004Dto(){
		Kmk004ComDto kmk004Dto = new Kmk004ComDto();
		
		return kmk004Dto;
	}

}
