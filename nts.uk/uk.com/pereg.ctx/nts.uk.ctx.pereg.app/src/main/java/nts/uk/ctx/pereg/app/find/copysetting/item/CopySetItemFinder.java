package nts.uk.ctx.pereg.app.find.copysetting.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.person.info.category.PerInfoCtgMapDto;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.app.find.initsetting.item.InitValueSetItemFinder;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;
import nts.uk.ctx.pereg.dom.copysetting.item.EmpCopySettingItemRepository;
import nts.uk.ctx.pereg.dom.copysetting.setting.EmpCopySettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CopySetItemFinder {

	@Inject
	private EmpCopySettingItemRepository empCopyItemRepo;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	@Inject
	private InitValueSetItemFinder initItemFinder;

	@Inject
	private EmpCopySettingRepository empCopySettingRepo;

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	public List<SettingItemDto> getAllCopyItemByCtgCode(String categoryCd, String employeeId, GeneralDate baseDate) {

		String companyId = AppContexts.user().companyId();

		// check empployeeId
		boolean isSelf = employeeId == AppContexts.user().employeeId() ? true : false;

		List<SettingItemDto> itemList = this.empCopyItemRepo.getAllItemFromCategoryCd(categoryCd, companyId, isSelf)
				.stream().map(x -> SettingItemDto.createFromJavaType(x.getPerInfoCtgId(), x.getCategoryCode(),
						x.getItemDefId(), x.getItemCode(), x.getItemName(), x.getIsRequired().value, ""))
				.collect(Collectors.toList());

		if (itemList.isEmpty()) {
			boolean isPersonnelRepresentative = true;

			if (isPersonnelRepresentative) {
				throw new BusinessException(new RawErrorMessage("Msg_347"));
			} else {
				throw new BusinessException(new RawErrorMessage("Msg_348"));
			}
		}

		return this.initItemFinder.loadSettingItems(itemList, categoryCd, companyId, employeeId, baseDate);

	}

	public List<PerInfoCtgMapDto> getAllPerInfoCategoryWithCondition(String ctgName) {
		// get all perinforcategory by company id
		String companyId = AppContexts.user().companyId();
		String contractCode = AppContexts.user().contractCode();
		List<PersonInfoCategory> lstPerInfoCtg = null;
		if (ctgName.equals(""))
			lstPerInfoCtg = perInfoCtgRepositoty.getAllPerInfoCategory(companyId, contractCode);
		else {
			lstPerInfoCtg = perInfoCtgRepositoty.getPerInfoCategoryByName(companyId, contractCode, ctgName);
		}
		List<PersonInfoCategory> lstFilter = new ArrayList<PersonInfoCategory>();

		// get all PersonInfoItemDefinition
		for (PersonInfoCategory obj : lstPerInfoCtg) {
			// check whether category has already copied or not
			// filter: category has items
			if (pernfoItemDefRep.countPerInfoItemDefInCategory(obj.getPersonInfoCategoryId(), companyId) > 0) {
				lstFilter.add(obj);
			}
		}
		List<PerInfoCtgMapDto> lstReturn = null;
		if (lstFilter.size() != 0) {
			lstReturn = PersonInfoCategory.getAllPerInfoCategoryWithCondition(lstFilter).stream().map(p -> {
				boolean alreadyCopy = empCopySettingRepo.checkPerInfoCtgAlreadyCopy(p.getPersonInfoCategoryId(),
						companyId);
				// boolean alreadyCopy = true;
				return new PerInfoCtgMapDto(p.getPersonInfoCategoryId(), p.getCategoryCode().v(),
						p.getCategoryName().v(), alreadyCopy);
			}).collect(Collectors.toList());
		}
		if (lstFilter.size() == 0 || lstReturn.size() == 0)
			throw new BusinessException("Msg_352");
		return lstReturn;
	}

}
