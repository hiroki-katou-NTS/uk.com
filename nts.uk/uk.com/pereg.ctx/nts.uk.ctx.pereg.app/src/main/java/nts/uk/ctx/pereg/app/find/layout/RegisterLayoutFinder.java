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

		List<LayoutPersonInfoClsDto> listItemCls = this.clsFinder.getListClsDto(_layout.getLayoutID());

		if (command.getCreateType() != 3) {

			List<SettingItemDto> dataServer = this.getItemListByCreateType(command);

			if (CollectionUtil.isEmpty(dataServer)) {

				return null;
			}

			setData(dataServer, listItemCls);
			if (command.getCreateType() == 1) {

				return listItemCls;

			}

			if (command.getCreateType() == 2) {

				removeNotDefItem(listItemCls);

				return listItemCls.stream().filter(itemCls -> !CollectionUtil.isEmpty(itemCls.getItems()))
						.collect(Collectors.toList());
			}

		}

		if (command.getCreateType() == 3) {

			listItemCls.forEach(itemCls -> {
				if (!CollectionUtil.isEmpty(itemCls.getListItemDf())) {
					itemCls.getListItemDf().forEach(itemDef -> {
						LayoutPersonInfoValueDto newLayoutDto = createLayoutInfoDtoFromDef(null, itemDef,
								ActionRole.EDIT.value);

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

	private void removeNotDefItem(List<LayoutPersonInfoClsDto> listItemCls) {
		listItemCls.forEach(itemCls -> {
			if (!CollectionUtil.isEmpty(itemCls.getItems())) {
				itemCls.setItems(itemCls.getItems().stream().filter(item -> {

					LayoutPersonInfoValueDto subItem = (LayoutPersonInfoValueDto) item;

					return subItem.getValue() != null;
				}).collect(Collectors.toList()));
			}
		});

	}

	private void setData(List<SettingItemDto> dataServer, List<LayoutPersonInfoClsDto> listItemCls) {

		dataServer.forEach(setItem -> {

			createInfoValueDtoByDefId(setItem, listItemCls);
		});

	}

	private void createInfoValueDtoByDefId(SettingItemDto setItem, List<LayoutPersonInfoClsDto> listItemCls) {

		Optional<LayoutPersonInfoClsDto> clsOpt = listItemCls.stream()
				.filter(itemCls -> !CollectionUtil.isEmpty(itemCls.getListItemDf()))
				.filter(itemCls -> itemCls.getListItemDf().stream()
						.filter(itemDf -> itemDf.getId().equals(setItem.getItemDefId())).findFirst().isPresent())
				.findFirst();

		if (clsOpt.isPresent()) {
			LayoutPersonInfoClsDto cls = clsOpt.get();
			Optional<PerInfoItemDefDto> itemDef = cls.getListItemDf().stream()
					.filter(itemDf -> itemDf.getId().equals(setItem.getItemDefId())).findFirst();
			LayoutPersonInfoValueDto newLayoutDto = createLayoutInfoDtoFromDef(setItem, itemDef.get(),
					ActionRole.EDIT.value);
			if (CollectionUtil.isEmpty(cls.getItems())) {
				List<Object> itemList = new ArrayList<Object>();
				itemList.add(newLayoutDto);
				cls.setItems(itemList);
			} else {

				cls.getItems().add(newLayoutDto);
			}

		}
	}

	private LayoutPersonInfoValueDto createLayoutInfoDtoFromDef(SettingItemDto setItem, PerInfoItemDefDto itemDef,
			int actionRole) {

		LayoutPersonInfoValueDto dataObject = new LayoutPersonInfoValueDto();
		dataObject.setCategoryId(itemDef.getPerInfoCtgId());
		dataObject.setItemDefId(itemDef.getId());
		dataObject.setItemName(itemDef.getItemName());
		dataObject.setItemCode(itemDef.getItemCode());
		dataObject.setRow(0);
		dataObject.setRequired(itemDef.getIsRequired() == 1);
		SingleItemDto sigleItem = (SingleItemDto) itemDef.getItemTypeState();
		dataObject.setItem(sigleItem.getDataTypeState());
		dataObject.setActionRole(EnumAdaptor.valueOf(actionRole, ActionRole.class));
		if (setItem != null) {
			dataObject.setValue(setItem.getValueAsString());
			dataObject.setCategoryCode(setItem.getCategoryCode());
		}
		int dataTypeValue = dataObject.getItem().getDataTypeValue();
		if (dataTypeValue == 6) {
			SelectionItemDto selectionItemDto = (SelectionItemDto) dataObject.getItem();
			List<ComboBoxObject> lstComboBox = comboBoxRetrieveFactory.getComboBox(selectionItemDto,
					GeneralDate.today());
			dataObject.setLstComboBoxValue(lstComboBox);
		}

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
