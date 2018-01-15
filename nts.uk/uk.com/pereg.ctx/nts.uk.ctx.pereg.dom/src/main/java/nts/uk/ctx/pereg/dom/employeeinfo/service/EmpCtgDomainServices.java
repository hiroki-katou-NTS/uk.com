package nts.uk.ctx.pereg.dom.employeeinfo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.setitem.SetItem;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoAuthType;
import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;

@Stateless
public class EmpCtgDomainServices {

	/**
	 * LaiTv
	 */

	@Inject
	private PerInfoCategoryRepositoty perInfoCategoryRepositoty;

	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;

	@Inject
	private PersonInfoItemAuthRepository personInfoItemAuthRepository;

	/**
	 * get person information item definition
	 * 
	 * @param paramObject
	 * @return List<PersonInfoItemDefinition>
	 */

	public List<PersonInfoItemDefinition> getPerItemDef(ParamForGetItemDef paramObject) {

		List<PersonInfoItemDefinition> lstResult = new ArrayList<>();
		PersonInfoItemDefinition perInfoDef = new PersonInfoItemDefinition();
		if (paramObject.getPersonInfoCategory().getCategoryType() == CategoryType.MULTIINFO) {
			lstResult = getPerInfoItemDefWithAuth(paramObject);
			perInfoDef = getPerInfoItemDefWithAuth(paramObject).get(0);
		} else {
			perInfoDef = getPerInfoItemDefWithHis(paramObject);
		}

		if (perInfoDef.getItemTypeState().getItemType() == ItemType.SET_ITEM) {
			// get itemId list of children
			SetItem setItem = (SetItem) perInfoDef.getItemTypeState();
			// get children by itemId list
			lstResult = perInfoItemDefRepositoty.getPerInfoItemDefByListId(setItem.getItems(),
					paramObject.getContractCode());
		}
		lstResult.add(perInfoDef);
		return lstResult;
	}

	/**
	 * get list person information item definition and filter by auth
	 * 
	 * @param paramObject
	 * @return List<PersonInfoItemDefinition>
	 */

	public List<PersonInfoItemDefinition> getPerInfoItemDefWithAuth(ParamForGetItemDef paramObject) {
		// get per info item def with order
		List<PersonInfoItemDefinition> lstPerInfoDef = perInfoItemDefRepositoty.getAllPerInfoItemDefByCategoryId(
				paramObject.getPersonInfoCategory().getPersonInfoCategoryId(), paramObject.getContractCode());

		// filter by auth return
		lstPerInfoDef.stream().filter(x -> {
			return paramObject.isSelfAuth()
					? personInfoItemAuthRepository
							.getItemDetai(paramObject.getRoleId(), x.getPerInfoCategoryId(), x.getPerInfoItemDefId())
							.get().getSelfAuth() != PersonInfoAuthType.HIDE
					: personInfoItemAuthRepository
							.getItemDetai(paramObject.getRoleId(), x.getPerInfoCategoryId(), x.getPerInfoItemDefId())
							.get().getOtherAuth() != PersonInfoAuthType.HIDE;
		}).collect(Collectors.toList());

		return lstPerInfoDef;
	}

	/**
	 * get list person information item definition and filter by date range
	 * 
	 * @param paramObject
	 * @return List<PersonInfoItemDefinition>
	 */
	private PersonInfoItemDefinition getPerInfoItemDefWithHis(ParamForGetItemDef paramObject) {

		DateRangeItem dateRangeItem = this.perInfoCategoryRepositoty
				.getDateRangeItemByCategoryId(paramObject.getPersonInfoCategory().getPersonInfoCategoryId());
		return perInfoItemDefRepositoty
				.getPerInfoItemDefById(dateRangeItem.getDateRangeItemId(), paramObject.getContractCode()).get();
	}
}
