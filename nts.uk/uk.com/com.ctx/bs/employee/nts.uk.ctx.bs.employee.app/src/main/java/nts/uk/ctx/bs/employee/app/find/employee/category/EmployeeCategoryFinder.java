package nts.uk.ctx.bs.employee.app.find.employee.category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.service.EmpCtgDomainServices;
import nts.uk.ctx.bs.employee.dom.employeeinfo.service.ParamForGetItemDef;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddressRepository;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyContact;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyCtRepository;
import nts.uk.ctx.bs.person.dom.person.family.Family;
import nts.uk.ctx.bs.person.dom.person.family.FamilyRepository;
import nts.uk.ctx.bs.person.dom.person.info.category.CategoryType;
import nts.uk.ctx.bs.person.dom.person.info.category.IsFixed;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeCategoryFinder {

	/** The employee repository. */
	@Inject
	private EmployeeRepository employeeRepository;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	@Inject
	private EmpCtgDomainServices empCtgDomainServices;

	@Inject
	private CurrentAddressRepository currentAddressRepo;
	
	@Inject
	private FamilyRepository familyRepo;
	
	@Inject
	private PersonEmergencyCtRepository personEmergencyCtRepo;

	public List<PersonInfoCtgFullDto> getAllPerInfoCtg(String companyId, String employeeIdSelected) {
		String empIdCurrentLogin = AppContexts.user().employeeId();
		// get roleIdOfLogin from app context;
		// String roleIdOfLogin = AppContexts.user().roles().forPersonalInfo();
		String roleIdOfLogin = "99900000-0000-0000-0000-000000000001";

		// get list ctg
		List<PersonInfoCtgFullDto> listCategory = employeeRepository.getAllPerInfoCtg(companyId).stream().map(p -> {
			return new PersonInfoCtgFullDto(p.getPersonInfoCategoryId(), p.getCategoryCode().v(),
					p.getCategoryName().v(), p.getPersonEmployeeType().value, p.getIsAbolition().value,
					p.getCategoryType().value, p.getIsFixed().value, null, null, null);
		}).collect(Collectors.toList());

		if (employeeIdSelected == empIdCurrentLogin) {
			// get List Ctg theo quyền

		} else {
			// get List Ctg theo quyền

		}

		for (int i = 0; i < listCategory.size(); i++) {

		}
		return listCategory;
	};

	public PersonInfoCtgFullDto getCtgAndItemByCtgId(String empSelected, String ctgId) {
		String ctgIDTest = "2e164ec3-0002-4a4d-9266-498a46dd86d4";
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		// String loginEmpId = AppContexts.user().employeeId();
		String loginEmpId = "99900000-0000-0000-0000-000000000001";
		String roleId = AppContexts.user().roles().forPersonnel();
		String roleIdOfLogin = "99900000-0000-0000-0000-000000000001";
		val perCtgInfo = perInfoCtgRepositoty.getPerInfoCategory(ctgIDTest, AppContexts.user().contractCode()).get();

		// check Type của Category
		if (perCtgInfo.getCategoryType() == CategoryType.SINGLEINFO) {
			return PersonInfoCtgFullDto.createObjectFromDomain(perCtgInfo);
		} else {
			List<PersonInfoItemDefinition> lstPerInfoItemDef = empCtgDomainServices
					.getPerItemDef(new ParamForGetItemDef(perCtgInfo, roleIdOfLogin == null ? "" : roleIdOfLogin,
							companyId, contractCode, loginEmpId.equals(empSelected)));

			if (perCtgInfo.getPersonEmployeeType() == PersonEmployeeType.PERSON) {// person

				// Get persinId
				String personId = employeeRepository.findBySid(companyId, empSelected).get().getPId();

				if (perCtgInfo.getIsFixed() == IsFixed.FIXED) {// fixed
					List<CategoryItemFixDto> listCtgItemFixDto = getListPersonCtgItemFix(personId, perCtgInfo,
							lstPerInfoItemDef);

				} else {// optional

				}
			} else {// employee
				if (perCtgInfo.getIsFixed() == IsFixed.FIXED) {// fixed

				} else {// optional

				}
			}
		}

		val lstPerItemDef = pernfoItemDefRep.getPerInfoItemByCtgId(ctgId, AppContexts.user().companyId(),
				AppContexts.user().contractCode());
		return PersonInfoCtgFullDto.createObjectFromDomain(perCtgInfo, lstPerItemDef);

	}

	/**
	 * get category item fixed
	 * 
	 * @param perInfoCtg
	 * @param parentInfoId
	 * @return CtgItemFixDto
	 */
	private List<CategoryItemFixDto> getListPersonCtgItemFix(String personId, PersonInfoCategory perInfoCtg,
			List<PersonInfoItemDefinition> lstPerInfoItemDef) {

		List<CategoryItemFixDto> listCtgItemFixDto = new ArrayList<CategoryItemFixDto>();

		if (perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE) {
			switch (perInfoCtg.getCategoryCode().v()) {
			case "CS00003":
				// get list currentaddress
				List<CurrentAddress> listCurrentAdd = currentAddressRepo.getListByPid(personId);
				if (listCurrentAdd.size() > 0) {
					listCurrentAdd.forEach(x -> {
						CategoryItemFixDto dto = CategoryItemFixDto.createCurrentAddress(x.getCurrentAddressId(),
								x.getPid(), x.getCountryId(), x.getPostalCode().v(), x.getPhoneNumber().v(),
								x.getPrefectures().v(), x.getHouseRent().v(), x.getPeriod().start(),
								x.getPeriod().end(), x.getAddress1().getAddress1().v(),
								x.getAddress1().getAddressKana1().v(), x.getAddress2().getAddress2().v(),
								x.getAddress2().getAddressKana2().v(), x.getHomeSituationType().v(),
								x.getPersonMailAddress().v(), x.getHouseType().v(), x.getNearestStation().v());
						listCtgItemFixDto.add(dto);
					});
				}

				break;
			case "CS00004":
				List<Family> lstFamily = familyRepo.getListByPid(personId);
				

				break;
			case "CS00014":
				// get list widowHistory 
				//List<WidowHistory> listWidowHistory = widowHistoryRepo.getListByPid(personId);
				// Domain WidowHistory chuwa xong ( chua co pid)

				break;
			case "CS00015":
				List<PersonEmergencyContact> lstEmergencyCt = personEmergencyCtRepo.getListbyPid(personId);
				break;

			}

		}
		return listCtgItemFixDto;

	}

	/**
	 * get category item optional
	 * 
	 * @param perInfoCtg
	 * @param parentInfoId
	 * @return CtgItemFixDto
	 */
	private Object getListPersonCtgItemOptional(PersonInfoCategory perInfoCtg,
			List<PersonInfoItemDefinition> lstPerInfoItemDef) {
		return null;

	}
}
