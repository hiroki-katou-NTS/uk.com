package nts.uk.ctx.pereg.app.find.person.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmpInfoCtgData;
import nts.uk.ctx.pereg.dom.person.info.category.HistoryTypes;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoPermissionType;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

@Stateless
public class PerInfoCategoryFinder {

	@Inject
	I18NResourcesForUK internationalization;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	@Inject
	private PersonInfoCategoryAuthRepository personInfoCategoryAuthRepository;

	@Inject
	private EmInfoCtgDataRepository empDataRepo;

	public List<PerInfoCtgFullDto> getAllPerInfoCtg() {
		return perInfoCtgRepositoty
				.getAllPerInfoCategory(PersonInfoCategory.ROOT_COMPANY_ID, PersonInfoItemDefinition.ROOT_CONTRACT_CODE)
				.stream().map(p -> {
					return new PerInfoCtgFullDto(p.getPersonInfoCategoryId(), p.getCategoryCode().v(),
							p.getCategoryName().v(), p.getPersonEmployeeType().value, p.getIsAbolition().value,
							p.getCategoryType().value, p.getIsFixed().value);
				}).collect(Collectors.toList());
	};

	// vinhpx: start

	// get per info ctg list: contains ctg and children
	// isParent, 1 - parent; 0 - is not
	public List<PerInfoCtgWithParentMapDto> getPerInfoCtgWithParent(String parentCd) {
		List<PerInfoCtgWithParentMapDto> lstResult = new ArrayList<>();
		lstResult = perInfoCtgRepositoty
				.getPerInfoCtgByParentCode(parentCd, PersonInfoItemDefinition.ROOT_CONTRACT_CODE).stream().map(p -> {
					return new PerInfoCtgWithParentMapDto(p.getPersonInfoCategoryId(), p.getCategoryCode().v(),
							p.getCategoryName().v(), p.getPersonEmployeeType().value, p.getIsAbolition().value,
							p.getCategoryType().value, p.getIsFixed().value, 0);
				}).collect(Collectors.toList());
		lstResult.add(perInfoCtgRepositoty.getPerInfoCategory(parentCd, PersonInfoItemDefinition.ROOT_CONTRACT_CODE)
				.map(p -> {
					return new PerInfoCtgWithParentMapDto(p.getPersonInfoCategoryId(), p.getCategoryCode().v(),
							p.getCategoryName().v(), p.getPersonEmployeeType().value, p.getIsAbolition().value,
							p.getCategoryType().value, p.getIsFixed().value, 1);
				}).orElse(null));
		return lstResult;
	}

	/**
	 * check exist auth of ctg by empId and ctgId
	 * 
	 * @param empId
	 * @param ctgId
	 * @return
	 */
	public boolean checkPerInfoCtgAuth(String empId, String ctgId, String roleId) {
		String loginEmpId = AppContexts.user().employeeId();
		boolean isSelfAuth = empId.equals(loginEmpId);
		// get perInfoCtgAuth
		Optional<PersonInfoCategoryAuth> perInfoCtgAuth = personInfoCategoryAuthRepository
				.getDetailPersonCategoryAuthByPId(roleId, ctgId);
		if (!perInfoCtgAuth.isPresent())
			return false;
		PersonInfoCategoryAuth personInfoCategoryAuth = perInfoCtgAuth.get();
		if (isSelfAuth) {
			return personInfoCategoryAuth.getAllowPersonRef() == PersonInfoPermissionType.YES;
		} else
			return personInfoCategoryAuth.getAllowOtherRef() == PersonInfoPermissionType.YES;
	}

	// vinhpx: end

	public PerInfoCtgFullDto getPerInfoCtg(String perInfoCtgId) {
		return perInfoCtgRepositoty.getPerInfoCategory(perInfoCtgId, PersonInfoItemDefinition.ROOT_CONTRACT_CODE)
				.map(p -> {
					return new PerInfoCtgFullDto(p.getPersonInfoCategoryId(), p.getCategoryCode().v(),
							p.getCategoryName().v(), p.getPersonEmployeeType().value, p.getIsAbolition().value,
							p.getCategoryType().value, p.getIsFixed().value);
				}).orElse(null);
	};

	public PerInfoCtgDataEnumDto getAllPerInfoCtgByCompany() {
		String companyId = AppContexts.user().companyId();
		String contractCode = AppContexts.user().contractCode();

		List<PerInfoCtgShowDto> categoryList = perInfoCtgRepositoty.getAllPerInfoCategory(companyId, contractCode)
				.stream().map(p -> {
					if (pernfoItemDefRep.countPerInfoItemDefInCategory(p.getPersonInfoCategoryId(), companyId) > 0) {
						return new PerInfoCtgShowDto(p.getPersonInfoCategoryId(), p.getCategoryName().v(),
								p.getCategoryType().value, p.getIsAbolition().value, p.getCategoryParentCode().v());
					}
					return null;
				}).filter(m -> m != null).collect(Collectors.toList());

		List<EnumConstant> historyTypes = EnumAdaptor.convertToValueNameList(HistoryTypes.class, internationalization);
		return new PerInfoCtgDataEnumDto(historyTypes, categoryList);
	};

	public PerInfoCtgDataEnumDto getAllPerInfoCtgByCompanyRoot() {
		List<PerInfoCtgShowDto> categoryList = perInfoCtgRepositoty
				.getAllPerInfoCategory(PersonInfoCategory.ROOT_COMPANY_ID, PersonInfoItemDefinition.ROOT_CONTRACT_CODE)
				.stream().map(p -> {
					return new PerInfoCtgShowDto(p.getPersonInfoCategoryId(), p.getCategoryName().v(),
							p.getCategoryType().value, p.getIsAbolition().value, p.getCategoryParentCode().v());
				}).collect(Collectors.toList());

		List<EnumConstant> historyTypes = EnumAdaptor.convertToValueNameList(HistoryTypes.class, internationalization);
		return new PerInfoCtgDataEnumDto(historyTypes, categoryList);
	};

	public PerInfoCtgWithItemsNameDto getPerInfoCtgWithItemsName(String perInfoCtgId) {
		List<String> itemNameList = pernfoItemDefRep.getPerInfoItemsName(perInfoCtgId,
				PersonInfoItemDefinition.ROOT_CONTRACT_CODE);
		PerInfoCtgWithItemsNameDto resultCtg = perInfoCtgRepositoty
				.getPerInfoCategory(perInfoCtgId, PersonInfoItemDefinition.ROOT_CONTRACT_CODE).map(p -> {
					return new PerInfoCtgWithItemsNameDto(p.getPersonInfoCategoryId(), p.getCategoryName().v(),
							p.getCategoryType().value, p.getIsFixed().value, p.getPersonEmployeeType().value,
							itemNameList, p.getIsFixed().equals(IsFixed.NOT_FIXED)
									? isChangeAbleCtgType(p.getPersonInfoCategoryId()) : false);
				}).orElse(null);

		return resultCtg;

	};
	
	public int getDispOrder(String perInfoCtgId) {
		return perInfoCtgRepositoty.getDispOrder(perInfoCtgId);
	}

	private boolean isChangeAbleCtgType(String perInfoCtgId) {

		String contractCd = AppContexts.user().contractCode();
		// check not change Ctg Type
		PersonInfoCategory ctg = this.perInfoCtgRepositoty.getPerInfoCategory(perInfoCtgId, contractCd).get();

		List<String> ctgIds = this.perInfoCtgRepositoty.getAllCategoryByCtgCD(ctg.getCategoryCode().toString());

		if (ctgIds.size() > 0) {

			List<EmpInfoCtgData> empDataLst = this.empDataRepo.getByEmpIdAndCtgId(ctgIds);

			if (empDataLst.size() > 0) {
				return false;
			}
		}

		return true;
	}
}
