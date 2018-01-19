/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.employmentfunction;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.employmentfunction.LinkPlanTimeItem;
import nts.uk.ctx.at.function.dom.employmentfunction.LinkPlanTimeItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class LinkPlanTimeItemFinder.
 */
@Stateless
public class LinkPlanTimeItemFinder {
	
	/** The link plan time item repository. */
	@Inject LinkPlanTimeItemRepository linkPlanTimeItemRepository;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<Integer> findAll() {
		
		// Get Login User Info
		LoginUserContext loginUserContext = AppContexts.user();

		// Get Company Id
		String companyId = loginUserContext.companyId();

		// Get All Employment
		List<LinkPlanTimeItem> empList = this.linkPlanTimeItemRepository.findAll(companyId);
		
		List<Integer> lstAtdID = empList.stream()
									.map(obj -> obj.getAtdId())
									.collect(Collectors.toList());
		return lstAtdID;
	}
}
