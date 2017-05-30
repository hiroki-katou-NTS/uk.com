/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.category;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.app.find.company.organization.category.dto.ManagementCategoryFindDto;
import nts.uk.ctx.basic.dom.company.organization.category.ManagementCategory;
import nts.uk.ctx.basic.dom.company.organization.category.ManagementCategoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ManagementCategoryFinder.
 */
@Stateless
public class ManagementCategoryFinder {
	
	/** The repository. */
	@Inject
	private ManagementCategoryRepository repository;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<ManagementCategoryFindDto> findAll(){
		
		// get login info
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();

		// get all management category
		List<ManagementCategory> managementCategories = this.repository
			.getAllManagementCategory(companyId);
		
		// to domain
		return managementCategories.stream().map(category -> {
			ManagementCategoryFindDto dto = new ManagementCategoryFindDto();
			category.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
