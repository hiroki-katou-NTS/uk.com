package nts.uk.ctx.pereg.app.find.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.app.command.addemployee.AddEmployeeCommand;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.app.find.common.InitDefaultValue;
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
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefForLayoutDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SelectionItemDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SingleItemDto;
import nts.uk.ctx.pereg.app.find.person.setting.init.category.PerInfoInitValueSettingCtgFinder;
import nts.uk.ctx.pereg.app.find.processor.PeregProcessor;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pereg.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.pereg.dom.person.layout.NewLayout;
import nts.uk.ctx.pereg.dom.person.layout.classification.LayoutItemType;
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
	@Inject

	private InitDefaultValue initDefaultValue;
	
	@Inject
	private PeregProcessor processor;
	
	@Inject 
	private PerInfoCategoryRepositoty perInfoCategoryRepositoty;
	
	private final String END_DATE_NAME = "終了日";

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

		if (itemCls.stream().filter(c -> c.getPersonInfoCategoryCD().equals("CS00020")).findFirst().isPresent()) {
			initDefaultValue.setDefaultValueRadio(itemCls);
		}
		return NewLayoutDto.fromDomain(_layout, itemCls);

	}

	private List<LayoutPersonInfoClsDto> getItemCls(AddEmployeeCommand command, NewLayout _layout) {

		List<LayoutPersonInfoClsDto> itemCls = this.clsFinder.getListClsDtoHasCtgCd(_layout.getLayoutID());
		List<SettingItemDto> items = new ArrayList<SettingItemDto>();

		if (command.getCreateType() != 3) {
			items = this.getSetItems(command);
		}
		setData(items, itemCls, command);
		
		// check and set 9999/12/31 to endDate
		itemCls.forEach(classItem -> {
			if (classItem.getCtgType() == CategoryType.CONTINUOUSHISTORY.value && classItem.getItems().size() == 3) {
				LayoutPersonInfoValueDto theThirdItem = (LayoutPersonInfoValueDto) classItem.getItems().get(2);
				if (theThirdItem.getItemName().equals(END_DATE_NAME) && theThirdItem.getValue() == null ) {
					theThirdItem.setValue(GeneralDate.max());
				}
			}
		});

		// check and set employeeName to businessName
		Optional<LayoutPersonInfoClsDto> businessNameOpt = itemCls.stream()
				.filter(classItem -> classItem.getLayoutItemType() != LayoutItemType.SeparatorLine)
				.filter(classItem -> {
					LayoutPersonInfoValueDto item = (LayoutPersonInfoValueDto) classItem.getItems().get(0);
					return item.getItemCode().equals("IS00009");
				}).findFirst();
		if ( businessNameOpt.isPresent()) {
			LayoutPersonInfoClsDto businessName = businessNameOpt.get();
			LayoutPersonInfoValueDto item = (LayoutPersonInfoValueDto) businessName.getItems().get(0);
			if ( item.getValue() == null ) {
				item.setValue(command.getEmployeeName());
			}
		}

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
		item.setItemParentCode(itemDef.getItemParentCode());
		
		// Get contactCD
		String contactCD = AppContexts.user().contractCode();
		// Get Command
		Optional<PersonInfoCategory> perInfoCategory = perInfoCategoryRepositoty.getPerInfoCategory(itemDef.getPerInfoCtgId(),contactCD);
		
		if (!perInfoCategory.isPresent()){
			throw new RuntimeException("invalid PersonInfoCategory");
		}
				
		if (itemDef.getItemTypeState().getItemType() == ItemType.SINGLE_ITEM.value) {
			SingleItemDto sigleItem = (SingleItemDto) itemDef.getItemTypeState();
			item.setItem(sigleItem.getDataTypeState());
			int dataTypeValue = item.getItem().getDataTypeValue();
			
			if (dataTypeValue == DataTypeValue.SELECTION.value || dataTypeValue == DataTypeValue.SELECTION_BUTTON.value
					|| dataTypeValue == DataTypeValue.SELECTION_RADIO.value) {

				SelectionItemDto selectionItemDto = null;

				

				selectionItemDto = (SelectionItemDto) item.getItem();
				boolean isDataType6 = dataTypeValue == DataTypeValue.SELECTION.value;
				
				List<ComboBoxObject> comboValues = cbbfact.getComboBox(selectionItemDto, AppContexts.user().employeeId(),
						command.getHireDate(), item.isRequired(), perInfoCategory.get().getPersonEmployeeType(),
						isDataType6, perInfoCategory.get().getCategoryCode().v());

				item.setLstComboBoxValue(comboValues);
				PerInfoItemDefForLayoutDto dto = new PerInfoItemDefForLayoutDto();
				dto.setItemTypeState(itemDef.getItemTypeState());
				dto.setLstComboxBoxValue(comboValues);
				if(setItem == null) {
					Object value =  processor.getValue(dto);
					item.setValue(value);
				}
			}
		}
		item.setActionRole(EnumAdaptor.valueOf(state, ActionRole.class));
		if (setItem != null) {
			item.setValue(setItem.getSaveData().getValue());
		}
		item.setCategoryCode(itemCls.getPersonInfoCategoryCD());
		item.setResourceId(itemDef.getResourceId());
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

		querys.forEach(query -> {
			result.addAll(this.copyItemFinder.getAllCopyItemByCtgCode(false, query.getCategoryCode(),
					command.getEmployeeCopyId(), command.getHireDate()));
		});
		return result;
	}

}
