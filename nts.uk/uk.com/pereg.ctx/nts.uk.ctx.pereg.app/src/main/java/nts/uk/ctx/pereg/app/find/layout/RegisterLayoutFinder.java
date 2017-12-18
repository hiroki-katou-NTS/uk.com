package nts.uk.ctx.pereg.app.find.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
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
import nts.uk.ctx.pereg.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.pereg.dom.person.layout.NewLayout;
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
	private ComboBoxRetrieveFactory comboBoxRetrieveFactory;

	// sonnlb end

	// sonnlb code start

	/**
	 * get Layout Dto by create type
	 * 
	 * @param command
	 *            : command from client push to webservice
	 * @return NewLayoutDto
	 */
	public NewLayoutDto getByCreateType(AddEmployeeCommand command) {

		Optional<NewLayout> layout = repo.getLayout(false);
		if (!layout.isPresent()) {

			return null;
		}

		NewLayout _layout = layout.get();

		List<LayoutPersonInfoClsDto> listItemCls = getlistItemCls(command, _layout);

		return NewLayoutDto.fromDomain(_layout, listItemCls);

	}

	private List<LayoutPersonInfoClsDto> getlistItemCls(AddEmployeeCommand command, NewLayout _layout) {

		List<LayoutPersonInfoClsDto> listItemCls = this.clsFinder.getListClsDtoHasCtgCd(_layout.getLayoutID());

		if (command.getCreateType() != 3) {

			List<SettingItemDto> dataServer = this.getItemListByCreateType(command);

			if (CollectionUtil.isEmpty(dataServer)) {

				return null;
			}

			setData(dataServer, listItemCls, command.getCreateType());
			if (command.getCreateType() == 1) {

				return listItemCls;

			}

			if (command.getCreateType() == 2) {

				return listItemCls.stream().filter(itemCls -> !CollectionUtil.isEmpty(itemCls.getItems()))
						.collect(Collectors.toList());
			}

		}

		if (command.getCreateType() == 3) {

			listItemCls.forEach(itemCls -> {
				if (!CollectionUtil.isEmpty(itemCls.getListItemDf())) {
					itemCls.getListItemDf().forEach(itemDef -> {
						LayoutPersonInfoValueDto newLayoutDto = createPersonInfoValueDtoFromDef(null, itemDef,
								ActionRole.EDIT.value, itemCls.getPersonInfoCategoryCD());

						if (CollectionUtil.isEmpty(itemCls.getItems())) {
							List<Object> itemList = new ArrayList<Object>();
							itemList.add(newLayoutDto);
							itemCls.setItems(itemList);
						} else {

							itemCls.getItems().add(newLayoutDto);
						}
					});
				}

			});
		}

		return listItemCls;
	}

	private void setData(List<SettingItemDto> dataServer, List<LayoutPersonInfoClsDto> listItemCls, int createType) {

		listItemCls.forEach(itemCls -> {
			createInfoValueDtoByItemCls(dataServer, itemCls, createType);
		});

	}

	private void createInfoValueDtoByItemCls(List<SettingItemDto> dataServer, LayoutPersonInfoClsDto itemCls,
			int createType) {
		List<PerInfoItemDefDto> itemDefList = itemCls.getListItemDf();
		if (!CollectionUtil.isEmpty(itemDefList)) {
			List<Object> itemDataList = new ArrayList<Object>();
			itemDefList.forEach(itemDef -> {
				Optional<SettingItemDto> setItemOpt = dataServer.stream()
						.filter(item -> item.getItemDefId().equals(itemDef.getId())).findFirst();

				LayoutPersonInfoValueDto infoValue = null;
				if (setItemOpt.isPresent()) {
					SettingItemDto setItem = setItemOpt.get();
					infoValue = createPersonInfoValueDtoFromDef(setItem, itemDef, ActionRole.EDIT.value,
							itemCls.getPersonInfoCategoryCD());
				} else {
					if (itemDef.getItemTypeState().getItemType() == 1 || createType == 1) {
						infoValue = createPersonInfoValueDtoFromDef(null, itemDef, ActionRole.EDIT.value,
								itemCls.getPersonInfoCategoryCD());
					}

				}

				if (infoValue != null) {
					itemDataList.add(infoValue);
				}

			});

			itemCls.setItems(itemDataList);
		}

	}

	private LayoutPersonInfoValueDto createPersonInfoValueDtoFromDef(SettingItemDto setItem, PerInfoItemDefDto itemDef,
			int actionRole, String ctgCd) {

		LayoutPersonInfoValueDto dataObject = new LayoutPersonInfoValueDto();
		dataObject.setCategoryId(itemDef.getPerInfoCtgId());
		dataObject.setItemDefId(itemDef.getId());
		dataObject.setItemName(itemDef.getItemName());
		dataObject.setItemCode(itemDef.getItemCode());
		dataObject.setRow(0);
		dataObject.setRequired(itemDef.getIsRequired() == 1);
		dataObject.setType(itemDef.getItemTypeState().getItemType());
		if (itemDef.getItemTypeState().getItemType() != 1) {
			SingleItemDto sigleItem = (SingleItemDto) itemDef.getItemTypeState();
			dataObject.setItem(sigleItem.getDataTypeState());
			int dataTypeValue = dataObject.getItem().getDataTypeValue();
			if (dataTypeValue == 6) {
				SelectionItemDto selectionItemDto = (SelectionItemDto) dataObject.getItem();
				List<ComboBoxObject> lstComboBox = comboBoxRetrieveFactory.getComboBox(selectionItemDto,
						GeneralDate.today());
				dataObject.setLstComboBoxValue(lstComboBox);
			}
		}
		dataObject.setActionRole(EnumAdaptor.valueOf(actionRole, ActionRole.class));
		if (setItem != null) {
			dataObject.setValue(setItem.getValueAsString());
		}
		dataObject.setCategoryCode(ctgCd);
		return dataObject;

	}

	// private PerInfoItemDefForLayoutDto
	// createLayoutInfoDtoFromDef(SettingItemDto setItem, PerInfoItemDefDto
	// itemDef,
	// int actionRole) {
	//
	// PerInfoItemDefForLayoutDto dataObject = new PerInfoItemDefForLayoutDto();
	//
	// dataObject.setPerInfoCtgId(itemDef.getPerInfoCtgId());
	// dataObject.setId(itemDef.getId());
	// dataObject.setItemName(itemDef.getItemName());
	// dataObject.setItemCode(itemDef.getItemCode());
	// dataObject.setRow(0);
	// dataObject.setIsRequired(itemDef.getIsRequired());
	// dataObject.setItemTypeState(itemDef.getItemTypeState());
	// dataObject.setActionRole(EnumAdaptor.valueOf(actionRole,
	// ActionRole.class));
	//
	// dataObject.setSelectionItemRefType(itemDef.getSelectionItemRefType());
	// List<EnumConstant> selectionItemRefTypes =
	// EnumAdaptor.convertToValueNameList(ReferenceTypes.class, ukResouce);
	// dataObject.setSelectionItemRefTypes(selectionItemRefTypes);
	// if (setItem != null) {
	// dataObject.setPerInfoCtgCd(setItem.getCategoryCode());
	// }
	// if (itemDef.getItemTypeState().getItemType() == 2) {
	//
	// SingleItemDto singleItem = (SingleItemDto) itemDef.getItemTypeState();
	//
	// int dataTypeValue = singleItem.getDataTypeState().getDataTypeValue();
	// if (dataTypeValue == 6) {
	// SelectionItemDto selectionItemDto = (SelectionItemDto)
	// singleItem.getDataTypeState();
	// List<ComboBoxObject> lstComboBox =
	// comboBoxRetrieveFactory.getComboBox(selectionItemDto,
	// GeneralDate.today());
	// dataObject.setLstComboxBoxValue(lstComboBox);
	// }
	// }
	//
	// return dataObject;
	//
	// }

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
	public List<SettingItemDto> getItemListByCreateType(AddEmployeeCommand command) {

		List<SettingItemDto> result = new ArrayList<SettingItemDto>();
		List<PeregQuery> listQuery = new ArrayList<PeregQuery>();
		// Copy Type
		if (command.getCreateType() == 1) {

			this.copySettingFinder.getEmpCopySetting().forEach(x -> {
				listQuery.add(
						new PeregQuery(x.getCategoryCd(), command.getEmployeeCopyId(), null, command.getHireDate()));
			});

			listQuery.forEach(x -> {
				result.addAll(this.copyItemFinder.getAllCopyItemByCtgCode(x.getCategoryCode(),
						command.getEmployeeCopyId(), command.getHireDate()));
			});

		} else {
			// Init Value Type

			this.initCtgSettingFinder.getAllCategoryBySetId(command.getInitSettingId()).forEach(x -> {

				listQuery.add(
						new PeregQuery(x.getCategoryCd(), command.getEmployeeCopyId(), null, command.getHireDate()));
			});

			listQuery.forEach(x -> {

				findInitItemDto findInitCommand = new findInitItemDto(command.getInitSettingId(), command.getHireDate(),
						x.getCategoryCode(), command.getEmployeeName(), command.getEmployeeCode(),
						command.getHireDate());
				result.addAll(this.initItemFinder.getAllInitItemByCtgCode(findInitCommand));
			});

		}
		return result;
	}
	// sonnlb code end

}
