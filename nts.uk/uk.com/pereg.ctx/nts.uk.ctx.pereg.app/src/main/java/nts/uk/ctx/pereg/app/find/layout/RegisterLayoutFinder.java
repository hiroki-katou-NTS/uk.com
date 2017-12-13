package nts.uk.ctx.pereg.app.find.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.app.command.addemployee.AddEmployeeCommand;
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
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCategoryFinder;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFullDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.app.find.person.setting.init.category.PerInfoInitValueSettingCtgFinder;
import nts.uk.ctx.pereg.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.pereg.dom.person.layout.NewLayout;
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

			if (command.getCreateType() == 2) {

				removeNotDefItem(listItemCls);

				return listItemCls.stream().filter(itemCls -> !CollectionUtil.isEmpty(itemCls.getItems()))
						.collect(Collectors.toList());
			}

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
			LayoutPersonInfoValueDto newLayoutDto = LayoutPersonInfoValueDto.fromItemDef(setItem, itemDef.get(),
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
