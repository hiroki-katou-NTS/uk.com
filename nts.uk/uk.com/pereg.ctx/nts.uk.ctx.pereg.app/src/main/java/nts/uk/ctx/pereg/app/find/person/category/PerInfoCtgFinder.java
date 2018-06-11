package nts.uk.ctx.pereg.app.find.person.category;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.app.find.person.info.item.PersonInfoItemDefDto;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCtgByCompanyRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PerInfoCtgFinder {
	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	@Inject
	private PerInfoCtgByCompanyRepositoty perInfoByCompanyRepo;

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	public List<PerInfoCtgFullDto> getAllPerInfoCtg(String companyId) {
		String contractCd = companyId.substring(0, 12);
		 int salary = AppContexts.user().roles().forPayroll() != null? 1: 0;
		 int personnel = AppContexts.user().roles().forPersonnel() != null? 1: 0;
		 int employee = AppContexts.user().roles().forAttendance() != null? 1: 0;
		return perInfoCtgRepositoty.getAllPerInfoCategory(companyId, contractCd, salary, personnel, employee).stream().map(p -> {
			return new PerInfoCtgFullDto(p.getPersonInfoCategoryId(), p.getCategoryCode().v(), p.getCategoryName().v(),
					p.getPersonEmployeeType().value, p.getIsAbolition().value, p.getCategoryType().value,
					p.getIsFixed().value);
		}).collect(Collectors.toList());
	};

	public PerCtgInfoDto getDetailCtgInfo(String categoryId) {
		String companyIdRoot = "000000000000-0000";
		String companyId = AppContexts.user().companyId();
		String contractCd = companyId.substring(0, 12);
		PerCtgInfoDto detailCtgInfo = new PerCtgInfoDto();
		PersonInfoCategory perInfoCtg = this.perInfoByCompanyRepo
				.getDetailCategoryInfo(companyId, categoryId, contractCd).orElse(null);
		if (perInfoCtg != null) {
			String categoryNameDefault = this.perInfoByCompanyRepo.getNameCategoryInfo(companyIdRoot,
					perInfoCtg.getCategoryCode().toString());
			detailCtgInfo.setCategoryName(perInfoCtg.getCategoryName().toString());
			detailCtgInfo.setCategoryType(perInfoCtg.getCategoryType().value);
			detailCtgInfo.setAbolition(perInfoCtg.getIsAbolition().value == 1 ? true : false);
			detailCtgInfo.setPersonEmployeeType(perInfoCtg.getPersonEmployeeType().value);
			if (!categoryNameDefault.equals("null")) {
				detailCtgInfo.setCategoryNameDefault(categoryNameDefault);
			}
			List<PersonInfoItemDefDto> itemLst = this.pernfoItemDefRep
					.getAllPerInfoItemDefByCategoryIdWithoutSetItem(categoryId, contractCd).stream()
					.map(item -> PersonInfoItemDefDto.fromDomain(item)).collect(Collectors.toList());
			if (itemLst.size() > 0) {
				detailCtgInfo.setIsExistedItemLst(1);

			} else {

				detailCtgInfo.setIsExistedItemLst(0);
			}
			detailCtgInfo.setItemLst(itemLst);
			detailCtgInfo.setSystemRequired(perInfoCtg.getIsFixed().value);

		}
		return detailCtgInfo;
	}

	public List<PerInfoCtgDataDto> getAllCtgUsedByCompanyId(){
		String companyId = AppContexts.user().companyId();
		
		List<String> ctgLst = this.perInfoCtgRepositoty.getAllPerInfoCtgUsed(companyId).stream().map(p -> {
			return p.getPersonInfoCategoryId();
		}).collect(Collectors.toList());;
		
		List<PersonInfoItemDefinition> itemList = this.pernfoItemDefRep.getAllItemUsedByCtgId(ctgLst);
		
		List<PerInfoCtgDataDto> ctg =  ctgLst.stream().map(c -> {
			PerInfoCtgDataDto ctgDto = new PerInfoCtgDataDto();
			ctgDto.setCtgId(c);
			List<PersonInfoItemDefinition> item = itemList.stream()
					.filter(p -> p.getPerInfoCategoryId().equals(c))
					.collect(Collectors.toList());
			ctgDto.setItemList(item.stream().map(p -> { return p.getPerInfoItemDefId();}).collect(Collectors.toList()));
			itemList.removeAll(item);
			return ctgDto;
		}).collect(Collectors.toList());
		return ctg;
	}

}
