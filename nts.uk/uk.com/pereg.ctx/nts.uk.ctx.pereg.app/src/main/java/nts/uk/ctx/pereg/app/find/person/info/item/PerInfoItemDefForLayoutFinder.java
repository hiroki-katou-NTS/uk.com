package nts.uk.ctx.pereg.app.find.person.info.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.ActionRole;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

@Stateless
public class PerInfoItemDefForLayoutFinder {

	@Inject
	I18NResourcesForUK ukResouce;

	@Inject
	AffCompanyHistRepository achFinder;

	/**
	 * Hàm Đệ Quy
	 * @param fullItemDefinitionList
	 * @param category
	 * @param parentItem
	 * @param dispOrder
	 * @param role
	 * @return
	 */
	public List<PerInfoItemDefForLayoutDto> getChildrenItems(List<PersonInfoItemDefinition> fullItemDefinitionList,
			PersonInfoCategory category, PersonInfoItemDefinition parentItem, int dispOrder, ActionRole role) {

		List<PerInfoItemDefForLayoutDto> childLayoutitems = new ArrayList<>();

		// get children by itemId list
		ItemType itemType = parentItem.getItemTypeState().getItemType();
		if (itemType == ItemType.SET_ITEM || itemType == ItemType.TABLE_ITEM) {

			List<PersonInfoItemDefinition> childItemDefinitionList = getChildItems(fullItemDefinitionList,
					parentItem.getItemCode().v());

			for (int i = 0; i < childItemDefinitionList.size(); i++) {

				PersonInfoItemDefinition childItemDefinition = childItemDefinitionList.get(i);
				PerInfoItemDefForLayoutDto itemForLayout = createItemLayoutDto(category, childItemDefinition,
						dispOrder, role);
				childLayoutitems.add(itemForLayout);

				List<PerInfoItemDefForLayoutDto> grandChildItemForLayouts = getChildrenItems(fullItemDefinitionList,
						category, childItemDefinition, i, role);

				childLayoutitems.addAll(grandChildItemForLayouts);
			}
		}
		return childLayoutitems;
	}

	private List<PersonInfoItemDefinition> getChildItems(List<PersonInfoItemDefinition> fullItemDefinitionList,
			String itemCode) {
		return fullItemDefinitionList.stream()
				.filter(itemDef -> itemDef.getItemParentCode() != null && itemDef.getItemParentCode().equals(itemCode))
				.collect(Collectors.toList());
	}

	public PerInfoItemDefForLayoutDto createItemLayoutDto(PersonInfoCategory category,
			PersonInfoItemDefinition itemDefinition, int dispOrder, ActionRole role) {
		PerInfoItemDefForLayoutDto itemForLayout = new PerInfoItemDefForLayoutDto(itemDefinition);

		itemForLayout.setDispOrder(dispOrder);
		List<EnumConstant> selectionItemRefTypes = EnumAdaptor.convertToValueNameList(ReferenceTypes.class, ukResouce);
		itemForLayout.setSelectionItemRefTypes(selectionItemRefTypes);

		itemForLayout.setPerInfoCtgCd(category.getCategoryCode().v());
		itemForLayout.setCtgType(category.getCategoryType().value);
		itemForLayout.setActionRole(role);
		return itemForLayout;
	}
	
}