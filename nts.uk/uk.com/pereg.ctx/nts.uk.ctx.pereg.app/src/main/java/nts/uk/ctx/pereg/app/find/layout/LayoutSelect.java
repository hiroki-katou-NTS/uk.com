/**
 * 
 */
package nts.uk.ctx.pereg.app.find.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.JobEntryHistory;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsFinder;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.app.find.processor.LayoutingProcessor;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.layout.classification.LayoutItemType;
import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoPermissionType;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuth;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.find.PeregMaintLayoutQuery;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

@Stateless
public class LayoutSelect {

	@Inject
	private LayoutPersonInfoClsFinder clsFinder;

	@Inject
	private EmployeeRepository employeeRepository;

	@Inject
	private PersonInfoItemAuthRepository perInfoItemAuthRepo;

	@Inject
	private PersonInfoCategoryAuthRepository perInfoCtgAuthRepo;

	@Inject
	private PerInfoCategoryRepositoty perInfoCateRepo;

	@Inject
	private LayoutingProcessor layoutingProcessor;

	public EmpMaintLayoutDto getLayout(PeregMaintLayoutQuery layoutQuery) {
		EmpMaintLayoutDto result = new EmpMaintLayoutDto();
		// query properties
		GeneralDate stardardDate = layoutQuery.getStandardDate();
		String mainteLayoutId = layoutQuery.getLayoutId();
		String browsingEmpId = layoutQuery.getBrowsingEmpId();

		// login information
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		String loginEmployeeId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonnel();
		Employee employee = employeeRepository.findBySid(companyId, browsingEmpId).get();

		if (employee.getHistoryWithReferDate(stardardDate).isPresent()) {
			result.setStardandDate(stardardDate);
		} else {
			Optional<JobEntryHistory> hitoryOption = employee.getHistoryBeforeReferDate(stardardDate);
			if (hitoryOption.isPresent()) {
				stardardDate = hitoryOption.get().getRetirementDate();
			} else {
				hitoryOption = employee.getHistoryAfterReferDate(stardardDate);
				if (hitoryOption.isPresent()) {
					stardardDate = hitoryOption.get().getJoinDate();
				}
			}
		}
		// check authority & get data
		boolean selfBrowsing = browsingEmpId.equals(loginEmployeeId);
		List<LayoutPersonInfoClsDto> itemClassList = this.clsFinder.getListClsDto(mainteLayoutId);
		List<LayoutPersonInfoClsDto> authItemClasList = new ArrayList<>();

		for (LayoutPersonInfoClsDto classItem : itemClassList) {
			// if item is separator line, do not check
			if (classItem.getLayoutItemType() == LayoutItemType.SeparatorLine) {
				authItemClasList.add(classItem);
			} else {
				Optional<PersonInfoCategoryAuth> personCategoryAuthOpt = perInfoCtgAuthRepo
						.getDetailPersonCategoryAuthByPId(roleId, classItem.getPersonInfoCategoryID());

				if (validateAuthClassItem(personCategoryAuthOpt, selfBrowsing)) {
					LayoutPersonInfoClsDto authClassItem = classItem;
					// check author of each definition in class-item
					List<PersonInfoItemAuth> inforAuthItems = perInfoItemAuthRepo.getAllItemAuth(roleId,
							classItem.getPersonInfoCategoryID());
					List<PerInfoItemDefDto> dataInfoItems = validateAuthItem(inforAuthItems, classItem.getListItemDf(),
							selfBrowsing);
					// if definition-items is empty, will NOT show this
					// class-item
					if (dataInfoItems.isEmpty()) {
						continue;
					}

					authClassItem.setListItemDf(dataInfoItems);

					PersonInfoCategory perInfoCategory = perInfoCateRepo
							.getPerInfoCategory(authClassItem.getPersonInfoCategoryID(), contractCode).get();

					PeregQuery query = new PeregQuery(perInfoCategory.getCategoryCode().v(),
							layoutQuery.getBrowsingEmpId(), employee.getPId(), layoutQuery.getStandardDate());

					// get data
					if (authClassItem.getLayoutItemType() == LayoutItemType.ITEM) {

						if (perInfoCategory.getIsFixed() == IsFixed.FIXED) {
							// get domain data
							PeregDto queryResult = layoutingProcessor.findSingle(query);
						} else {

						}
					} else if (authClassItem.getLayoutItemType() == LayoutItemType.LIST) {
						/*
						 * getDataforListItem(perInfoCategory,
						 * personCategoryAuthOpt.get(), inforAuthItems,
						 * authClassItem, stardardDate, employee.getPId(),
						 * employee.getSId(), selfBrowsing);
						 */
					}

					authItemClasList.add(authClassItem);
				}
			}
		}

		List<LayoutPersonInfoClsDto> authItemClasList1 = new ArrayList<>();
		for (int i = 0; i < authItemClasList.size(); i++) {
			if (i == 0) {
				authItemClasList1.add(authItemClasList.get(i));
			} else {
				boolean notAcceptElement = authItemClasList.get(i).getLayoutItemType() == LayoutItemType.SeparatorLine
						&& authItemClasList.get(i - 1).getLayoutItemType() == LayoutItemType.SeparatorLine;
				if (!notAcceptElement) {
					authItemClasList1.add(authItemClasList.get(i));
				}
			}
		}

		result.setClassificationItems(authItemClasList1);
		return result;

	}

	/**
	 * @param roleId
	 * @param item
	 * @param selfBrowsing
	 *            Target: check author of person who login with class-item
	 * @return
	 */
	private boolean validateAuthClassItem(Optional<PersonInfoCategoryAuth> personCategoryAuthOpt,
			boolean selfBrowsing) {
		if (!personCategoryAuthOpt.isPresent()) {
			return false;
		}
		if (selfBrowsing && personCategoryAuthOpt.get().getAllowPersonRef() == PersonInfoPermissionType.YES) {
			return true;
		}
		if (!selfBrowsing && personCategoryAuthOpt.get().getAllowOtherRef() == PersonInfoPermissionType.YES) {
			return true;
		}
		return false;
	}

	/**
	 * @param authItems
	 * @param listItemDef
	 * @param selfBrowsing
	 * @return Target: check author of person who login with each
	 *         definition-items in class-item
	 */
	private List<PerInfoItemDefDto> validateAuthItem(List<PersonInfoItemAuth> authItems,
			List<PerInfoItemDefDto> listItemDef, boolean selfBrowsing) {
		List<PerInfoItemDefDto> dataInfoItems = new ArrayList<>();
		for (PerInfoItemDefDto itemDef : listItemDef) {
			Optional<PersonInfoItemAuth> authItemOpt = authItems.stream()
					.filter(p -> p.getPersonItemDefId().equals(itemDef.getId())).findFirst();
			if (authItemOpt.isPresent()) {
				if (selfBrowsing && authItemOpt.get().getSelfAuth() != PersonInfoAuthType.HIDE) {
					dataInfoItems.add(itemDef);
				} else if (!selfBrowsing && authItemOpt.get().getOtherAuth() != PersonInfoAuthType.HIDE) {
					dataInfoItems.add(itemDef);
				}
			}
		}
		return dataInfoItems;

	}
	
	

}
