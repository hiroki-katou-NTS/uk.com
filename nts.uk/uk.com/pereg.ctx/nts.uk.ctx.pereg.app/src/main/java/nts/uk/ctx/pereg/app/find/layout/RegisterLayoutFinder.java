package nts.uk.ctx.pereg.app.find.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.app.command.addemployee.AddEmployeeCommand;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.app.find.copysetting.item.CopySettingItemFinder;
import nts.uk.ctx.pereg.app.find.copysetting.setting.EmpCopySettingFinder;
import nts.uk.ctx.pereg.app.find.initsetting.item.InitValueSetItemFinder;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;
import nts.uk.ctx.pereg.app.find.initsetting.item.findInitItemDto;
import nts.uk.ctx.pereg.app.find.layoutdef.NewLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.ActionRole;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsFinder;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SelectionItemDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SingleItemDto;
import nts.uk.ctx.pereg.app.find.person.setting.init.category.PerInfoInitValueSettingCtgFinder;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pereg.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.pereg.dom.person.layout.NewLayout;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregQuery;

/**
 * @author sonnlb
 *
 */
@Stateless
public class RegisterLayoutFinder {

	// sonnlb start code
	@Inject
	private INewLayoutReposotory repo;

	@Inject
	private LayoutPersonInfoClsFinder clsFinder;

	@Inject
	private PerInfoInitValueSettingCtgFinder initCtgSettingFinder;

	@Inject
	private EmpCopySettingFinder copySettingFinder;

	@Inject

	private CopySettingItemFinder copyItemFinder;

	@Inject

	private InitValueSetItemFinder initItemFinder;

	@Inject
	private ComboBoxRetrieveFactory cbbfact;

	// sonnlb end

	// sonnlb code start

	/**
	 * get Layout Dto by create type
	 * 
	 * @param command
	 *            : command from client push to webservice
	 * @return NewLayoutDto
	 */
	public NewLayoutDto getLayoutByCreateType(AddEmployeeCommand command) {

		Optional<NewLayout> layout = repo.getLayout(false);
		if (!layout.isPresent()) {

			return null;
		}

		NewLayout _layout = layout.get();

		List<LayoutPersonInfoClsDto> itemCls = getItemCls(command, _layout);

		return NewLayoutDto.fromDomain(_layout, itemCls);

	}

	private List<LayoutPersonInfoClsDto> getItemCls(AddEmployeeCommand command, NewLayout _layout) {

		List<LayoutPersonInfoClsDto> itemCls = this.clsFinder.getListClsDtoHasCtgCd(_layout.getLayoutID());
		List<SettingItemDto> items = new ArrayList<SettingItemDto>();

		if (command.getCreateType() != 3) {
			items = this.getSetItems(command);
		}
		setData(items, itemCls, command);

		return itemCls;
	}

	private void setData(List<SettingItemDto> setItems, List<LayoutPersonInfoClsDto> itemCls,
			AddEmployeeCommand command) {

		itemCls.forEach(item -> {
			setValueToItemCls(setItems, item, command);
		});

	}

	private void setValueToItemCls(List<SettingItemDto> dataServer, LayoutPersonInfoClsDto itemCls,
			AddEmployeeCommand command) {
		List<PerInfoItemDefDto> itemDefList = itemCls.getListItemDf();
		List<Object> items = new ArrayList<Object>();
		if (!CollectionUtil.isEmpty(itemDefList)) {

			itemDefList.forEach(itemDef -> {
				Optional<SettingItemDto> setItemOpt = dataServer.stream()
						.filter(item -> item.getItemDefId().equals(itemDef.getId())).findFirst();

				LayoutPersonInfoValueDto item = null;
				if (setItemOpt.isPresent()) {
					SettingItemDto setItem = setItemOpt.get();
					item = createLayoutItemByDef(setItem, itemDef, ActionRole.EDIT.value, itemCls, command);
				} else {

					item = createLayoutItemByDef(null, itemDef, ActionRole.EDIT.value, itemCls, command);

				}

				items.add(item);

			});
			itemCls.getListItemDf().clear();

			boolean isSetItem = items.stream().filter(x -> {
				LayoutPersonInfoValueDto infoValue = (LayoutPersonInfoValueDto) x;
				return infoValue.getType() == ItemType.SET_ITEM.value;
			}).findFirst().isPresent();

			if (isSetItem) {
				if (items.size() < 2) {
					items.clear();
				}
			}

			itemCls.setItems(items);
		}

		if (!CollectionUtil.isEmpty(items)) {

			itemCls.setItems(items);
		}

	}

	private LayoutPersonInfoValueDto createLayoutItemByDef(SettingItemDto setItem, PerInfoItemDefDto itemDef, int state,
			LayoutPersonInfoClsDto itemCls, AddEmployeeCommand command) {

		LayoutPersonInfoValueDto item = new LayoutPersonInfoValueDto();
		item.setCategoryId(itemDef.getPerInfoCtgId());
		item.setCtgType(itemCls.getCtgType());
		item.setItemDefId(itemDef.getId());
		item.setItemName(itemDef.getItemName());
		item.setItemCode(itemDef.getItemCode());
		item.setRow(0);
		item.setRequired(itemDef.getIsRequired() == 1);
		item.setType(itemDef.getItemTypeState().getItemType());
		if (itemDef.getItemTypeState().getItemType() != 1) {
			SingleItemDto sigleItem = (SingleItemDto) itemDef.getItemTypeState();
			item.setItem(sigleItem.getDataTypeState());
			int dataTypeValue = item.getItem().getDataTypeValue();
			if (dataTypeValue >= DataTypeValue.SELECTION.value) {

				SelectionItemDto selectionItemDto = null;

				List<ComboBoxObject> comboValues;

				selectionItemDto = (SelectionItemDto) item.getItem();

				comboValues = cbbfact.getComboBox(selectionItemDto, AppContexts.user().employeeId(),
						command.getHireDate(), item.isRequired());

				item.setLstComboBoxValue(comboValues);

			}
		}
		item.setActionRole(EnumAdaptor.valueOf(state, ActionRole.class));
		if (setItem != null) {
			item.setValue(setItem.getSaveData().getValue());
		}
		item.setCategoryCode(itemCls.getPersonInfoCategoryCD());
		return item;

	}

	/**
	 * load All PeregDto in database by createType
	 * 
	 * @param createType
	 *            : type client need create data
	 * @param initSettingId
	 *            : settingId need find item in
	 * @param baseDate
	 *            : date need find
	 * @param employeeCopyId
	 *            : id of employee copy
	 * @return SettingItemDto List
	 */
	public List<SettingItemDto> getSetItems(AddEmployeeCommand command) {

		// Copy Type
		if (command.getCreateType() == 1) {

			return getCopyItems(command);

		} else {
			// Init Value Type

			return getInitItemsBySetId(command);

		}
	}
	// sonnlb code end

	public List<SettingItemDto> getInitItemsBySetId(AddEmployeeCommand command) {
		List<PeregQuery> querys = new ArrayList<PeregQuery>();
		List<SettingItemDto> result = new ArrayList<SettingItemDto>();

		this.initCtgSettingFinder.getAllCategoryBySetId(command.getInitSettingId()).forEach(x -> {

			querys.add(new PeregQuery(x.getCategoryCd(), command.getEmployeeCopyId(), null, command.getHireDate()));
		});

		querys.forEach(x -> {

			findInitItemDto findInitCommand = new findInitItemDto(command.getInitSettingId(), command.getHireDate(),
					x.getCategoryCode(), command.getEmployeeName(), command.getEmployeeCode(), command.getHireDate());
			result.addAll(this.initItemFinder.getAllInitItemByCtgCode(false, findInitCommand));
		});
		return result;
	}

	public List<SettingItemDto> getCopyItems(AddEmployeeCommand command) {
		List<SettingItemDto> result = new ArrayList<SettingItemDto>();
		List<PeregQuery> querys = new ArrayList<PeregQuery>();
		this.copySettingFinder.getEmpCopySetting().forEach(x -> {
			querys.add(new PeregQuery(x.getCategoryCd(), command.getEmployeeCopyId(), null, command.getHireDate()));
		});

		querys.forEach(x -> {
			result.addAll(this.copyItemFinder.getAllCopyItemByCtgCode(false, x.getCategoryCode(),
					command.getEmployeeCopyId(), command.getHireDate()));
		});
		return result;
	}

}
