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
		List<SettingItemDto> dataServer = new ArrayList<SettingItemDto>();

		if (command.getCreateType() != 3) {
			dataServer = this.getAllSettingItemList(command);
		}
		setData(dataServer, listItemCls, command);

		return listItemCls;
	}

	private void setData(List<SettingItemDto> dataServer, List<LayoutPersonInfoClsDto> listItemCls,
			AddEmployeeCommand command) {

		listItemCls.forEach(itemCls -> {
			createInfoValueDtoByItemCls(dataServer, itemCls, command);
		});

	}

	private void createInfoValueDtoByItemCls(List<SettingItemDto> dataServer, LayoutPersonInfoClsDto itemCls,
			AddEmployeeCommand command) {
		List<PerInfoItemDefDto> itemDefList = itemCls.getListItemDf();
		List<Object> itemDataList = new ArrayList<Object>();
		if (!CollectionUtil.isEmpty(itemDefList)) {

			itemDefList.forEach(itemDef -> {
				Optional<SettingItemDto> setItemOpt = dataServer.stream()
						.filter(item -> item.getItemDefId().equals(itemDef.getId())).findFirst();

				LayoutPersonInfoValueDto infoValue = null;
				if (setItemOpt.isPresent()) {
					SettingItemDto setItem = setItemOpt.get();
					infoValue = createPersonInfoValueDtoFromDef(setItem, itemDef, ActionRole.EDIT.value, itemCls,
							command);
				} else {

					infoValue = createPersonInfoValueDtoFromDef(null, itemDef, ActionRole.EDIT.value, itemCls, command);

				}

				itemDataList.add(infoValue);

			});
			itemCls.getListItemDf().clear();

			boolean isSetItem = itemDataList.stream().filter(x -> {
				LayoutPersonInfoValueDto infoValue = (LayoutPersonInfoValueDto) x;
				return infoValue.getType() == ItemType.SET_ITEM.value;
			}).findFirst().isPresent();

			if (isSetItem) {
				if (itemDataList.size() < 2) {
					itemDataList.clear();
				}
			}

			itemCls.setItems(itemDataList);
		}

		if (!CollectionUtil.isEmpty(itemDataList)) {

			itemCls.setItems(itemDataList);
		}

	}

	private LayoutPersonInfoValueDto createPersonInfoValueDtoFromDef(SettingItemDto setItem, PerInfoItemDefDto itemDef,
			int actionRole, LayoutPersonInfoClsDto itemCls, AddEmployeeCommand command) {

		LayoutPersonInfoValueDto dataObject = new LayoutPersonInfoValueDto();
		dataObject.setCategoryId(itemDef.getPerInfoCtgId());
		dataObject.setCtgType(itemCls.getCtgType());
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
						AppContexts.user().employeeId(), command.getHireDate(), true);
				dataObject.setLstComboBoxValue(lstComboBox);
			}
		}
		dataObject.setActionRole(EnumAdaptor.valueOf(actionRole, ActionRole.class));
		if (setItem != null) {
			dataObject.setValue(setItem.getSaveData().getValue());
		}
		dataObject.setCategoryCode(itemCls.getPersonInfoCategoryCD());
		return dataObject;

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
	public List<SettingItemDto> getAllSettingItemList(AddEmployeeCommand command) {

		// Copy Type
		if (command.getCreateType() == 1) {

			return getAllCopyItem(command);

		} else {
			// Init Value Type

			return getAllInitItemBySetId(command);

		}
	}
	// sonnlb code end

	public List<SettingItemDto> getAllInitItemBySetId(AddEmployeeCommand command) {
		List<PeregQuery> listQuery = new ArrayList<PeregQuery>();
		List<SettingItemDto> result = new ArrayList<SettingItemDto>();

		this.initCtgSettingFinder.getAllCategoryBySetId(command.getInitSettingId()).forEach(x -> {

			listQuery.add(new PeregQuery(x.getCategoryCd(), command.getEmployeeCopyId(), null, command.getHireDate()));
		});

		listQuery.forEach(x -> {

			findInitItemDto findInitCommand = new findInitItemDto(command.getInitSettingId(), command.getHireDate(),
					x.getCategoryCode(), command.getEmployeeName(), command.getEmployeeCode(), command.getHireDate());
			result.addAll(this.initItemFinder.getAllInitItemByCtgCode(false, findInitCommand));
		});
		return result;
	}

	public List<SettingItemDto> getAllCopyItem(AddEmployeeCommand command) {
		List<SettingItemDto> result = new ArrayList<SettingItemDto>();
		List<PeregQuery> listQuery = new ArrayList<PeregQuery>();
		this.copySettingFinder.getEmpCopySetting().forEach(x -> {
			listQuery.add(new PeregQuery(x.getCategoryCd(), command.getEmployeeCopyId(), null, command.getHireDate()));
		});

		listQuery.forEach(x -> {
			result.addAll(this.copyItemFinder.getAllCopyItemByCtgCode(false, x.getCategoryCode(),
					command.getEmployeeCopyId(), command.getHireDate()));
		});
		return result;
	}

}
