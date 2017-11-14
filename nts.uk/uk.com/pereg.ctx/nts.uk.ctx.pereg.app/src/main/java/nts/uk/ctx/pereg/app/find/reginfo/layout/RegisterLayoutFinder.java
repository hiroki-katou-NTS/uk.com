package nts.uk.ctx.pereg.app.find.reginfo.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.layout.NewLayoutDto;
import find.layout.classification.LayoutPersonInfoClsDto;
import find.layout.classification.LayoutPersonInfoClsFinder;
import find.layout.classification.LayoutPersonInfoValueDto;
import find.person.info.item.PerInfoItemDefDto;
import find.person.setting.init.category.PerInfoInitValueSettingCtgFinder;
import find.person.setting.init.category.SettingCtgDto;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.layout.GetLayoutByCeateTypeDto;
import nts.uk.ctx.bs.person.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.bs.person.dom.person.layout.NewLayout;
import nts.uk.ctx.bs.person.dom.person.layout.classification.LayoutItemType;
import nts.uk.ctx.pereg.app.find.reginfo.copysetting.item.CopySetItemFinder;
import nts.uk.ctx.pereg.app.find.reginfo.copysetting.setting.EmpCopySettingFinder;
import nts.uk.ctx.pereg.app.find.reginfo.initsetting.item.InitValueSetItemFinder;
import nts.uk.ctx.pereg.app.find.reginfo.initsetting.item.SettingItemDto;

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
	private InitValueSetItemFinder initItemSettingFinder;

	@Inject
	private EmpCopySettingFinder copySettingFinder;

	private CopySetItemFinder copySetItemFinder;
	// sonnlb end

	// sonnlb code start

	/**
	 * get Layout Dto by create type
	 * 
	 * @param command
	 *            : command from client push to webservice
	 * @return NewLayoutDto
	 */
	public NewLayoutDto getByCreateType(GetLayoutByCeateTypeDto command) {

		Optional<NewLayout> layout = repo.getLayout();
		if (!layout.isPresent()) {

			return null;
		}

		NewLayout _layout = layout.get();

		// Get list Classification Item by layoutID
		List<LayoutPersonInfoClsDto> listItemCls = this.clsFinder.getListClsDto(_layout.getLayoutID());

		if (command.getCreateType() != 3) {

			List<SettingItemDto> layoutItemList = loadAllItemByCreateType(command.getCreateType(),
					command.getInitSettingId(), command.getBaseDate(), command.getEmployeeId());

			if (layoutItemList.isEmpty()) {

				return null;

			}

			for (LayoutPersonInfoClsDto itemCls : listItemCls) {
				LayoutItemType layoutType = itemCls.getLayoutItemType();
				switch (layoutType) {
				case ITEM: // item

					List<Object> itemValues = createItemValueList(itemCls.getListItemDf(), layoutItemList);

					itemCls.setItems(itemValues);

					break;
				case LIST: // list

					break;

				case SeparatorLine: // spa

					break;
				}
				itemCls.setItems(null);
			}

		}

		// remove all category no item;
		listItemCls = listItemCls.stream().filter(x -> x.getListItemDf() != null ? x.getListItemDf().size() > 0 : false)
				.collect(Collectors.toList());

		return NewLayoutDto.fromDomain(_layout, listItemCls);

	}

	/**
	 * create item list from each item layout list and value from dataSourceList
	 * 
	 * @param dataSourceList
	 *            : datasource List
	 * @param layoutItemList
	 *            : itemList need set value
	 * @return itemList as List<Object>
	 */
	private List<Object> createItemValueList(List<PerInfoItemDefDto> dataSourceList,
			List<SettingItemDto> layoutItemList) {
		List<Object> itemValueList = new ArrayList<Object>();
		for (PerInfoItemDefDto itemDf : dataSourceList) {

			SettingItemDto item = findItemFromList(layoutItemList, itemDf);

			if (item != null) {
				// because is single item
				int rowIndex = 0;
				LayoutPersonInfoValueDto value = new LayoutPersonInfoValueDto(itemDf.getPerInfoCtgId(),
						item.getCategoryCode(), itemDf.getId(), itemDf.getItemName(), itemDf.getItemCode(), rowIndex,
						item.getValueAsString());
				itemValueList.add(value);
			} else {
				// remove itemDf not found
				layoutItemList.remove(itemDf);

			}

		}
		return itemValueList;
	}

	/**
	 * get item from list when same itemcode and categoryId
	 * 
	 * @param itemDataList
	 *            list source
	 * @param item
	 *            condiction
	 * @return SettingItemDto
	 */
	private SettingItemDto findItemFromList(List<SettingItemDto> itemDataList, PerInfoItemDefDto item) {

		return itemDataList.stream().filter(
				i -> i.getItemCode().equals(item.getItemCode()) && i.getPerInfoCtgId().equals(item.getPerInfoCtgId()))
				.findFirst().orElse(null);
	}

	/**
	 * load All SettingItemDto in database by createType
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
	public List<SettingItemDto> loadAllItemByCreateType(int createType, String initSettingId, GeneralDate baseDate,
			String employeeCopyId) {
		// get all Data
		List<SettingItemDto> returnList = new ArrayList<SettingItemDto>();

		// Copy Type
		if (createType == 1) {
			List<SettingCtgDto> ctgList = new ArrayList<SettingCtgDto>();

			ctgList = this.copySettingFinder.getEmpCopySetting();

			for (SettingCtgDto settingCtg : ctgList) {

				List<SettingItemDto> itemList = this.copySetItemFinder
						.getAllCopyItemByCtgCode(settingCtg.getCategoryCd(), employeeCopyId, baseDate);
				returnList.addAll(itemList);
			}

		} else {
			// Init Value Type

			List<SettingCtgDto> ctgList = new ArrayList<SettingCtgDto>();

			ctgList = this.initCtgSettingFinder.getAllCategoryBySetId(initSettingId);

			for (SettingCtgDto settingCtg : ctgList) {

				List<SettingItemDto> itemList = this.initItemSettingFinder.getAllInitItemByCtgCode(initSettingId,
						settingCtg.getCategoryCd(), baseDate);
				returnList.addAll(itemList);

			}

		}
		// fill all item in list to save resources when searching by itemcode
		return returnList;
	}
	// sonnlb code end

}
