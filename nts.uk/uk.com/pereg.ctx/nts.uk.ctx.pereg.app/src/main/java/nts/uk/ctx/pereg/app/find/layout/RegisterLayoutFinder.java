package nts.uk.ctx.pereg.app.find.layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.pereg.app.find.copysetting.item.CopySettingItemFinder;
import nts.uk.ctx.pereg.app.find.copysetting.setting.EmpCopySettingFinder;
import nts.uk.ctx.pereg.app.find.initsetting.item.InitValueSetItemFinder;
import nts.uk.ctx.pereg.app.find.initsetting.item.SettingItemDto;
import nts.uk.ctx.pereg.app.find.layoutdef.NewLayoutDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsFinder;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SingleItemDto;
import nts.uk.ctx.pereg.app.find.person.info.item.StringItemDto;
import nts.uk.ctx.pereg.app.find.person.setting.init.category.PerInfoInitValueSettingCtgFinder;
import nts.uk.ctx.pereg.app.find.person.setting.init.category.SettingCtgDto;
import nts.uk.ctx.pereg.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.pereg.dom.person.layout.NewLayout;
import nts.uk.ctx.pereg.dom.person.layout.classification.LayoutItemType;

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

	private CopySettingItemFinder copySetItemFinder;
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

			List<SettingItemDto> dataSourceList = loadAllItemByCreateType(command.getCreateType(),
					command.getInitSettingId(), command.getBaseDate(), command.getEmployeeId());

			if (dataSourceList.isEmpty()) {

				return null;

			}

			for (LayoutPersonInfoClsDto itemCls : listItemCls) {
				if (!CollectionUtil.isEmpty(itemCls.getListItemDf())) {
					if (command.getCreateType() == 2) {

						itemCls.setListItemDf(itemCls.getListItemDf().stream()
								.filter(x -> findItemFromList(dataSourceList, x) != null).collect(Collectors.toList()));

					}

					LayoutItemType layoutType = itemCls.getLayoutItemType();

					switch (layoutType) {
					case ITEM: // item

						List<Object> itemValues = createItemValueList(itemCls.getListItemDf(), dataSourceList);

						itemCls.setItems(itemValues.isEmpty() ? null : itemValues);

						break;
					case LIST: // list
						itemCls.setItems(null);
						break;

					default:
						// spa
						itemCls.setItems(null);
						break;
					}
				}
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
	 * @param <E>
	 * 
	 * @param dataSourceList
	 *            : datasource List
	 * @param layoutItemList
	 *            : itemList need set value
	 * @return itemList as List<Object>
	 */
	private <E> List<Object> createItemValueList(List<PerInfoItemDefDto> layoutItemList,
			List<SettingItemDto> dataSourceList) {
		List<Object> itemValueList = new ArrayList<Object>();
		for (PerInfoItemDefDto itemDf : layoutItemList) {

			SettingItemDto item = findItemFromList(dataSourceList, itemDf);

			LayoutPersonInfoValueDto value = new LayoutPersonInfoValueDto(itemDf.getPerInfoCtgId(),
					item.getCategoryCode(), itemDf.getId(), itemDf.getItemName(), itemDf.getItemCode(), 0,
					item.getValueAsString());

			val itemDto = (SingleItemDto) itemDf.getItemTypeState();
			value.setItem(itemDto.getDataTypeState());

			itemValueList.add(value);

		}
		return itemValueList;
	}

	/**
	 * get item from list when same itemId
	 * 
	 * @param itemDataList
	 *            list source
	 * @param item
	 *            condiction
	 * @return SettingItemDto
	 */
	private SettingItemDto findItemFromList(List<SettingItemDto> itemDataList, PerInfoItemDefDto item) {

		return itemDataList.stream().filter(i -> i.getItemDefId().equals(item.getId())).findFirst().orElse(null);
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
