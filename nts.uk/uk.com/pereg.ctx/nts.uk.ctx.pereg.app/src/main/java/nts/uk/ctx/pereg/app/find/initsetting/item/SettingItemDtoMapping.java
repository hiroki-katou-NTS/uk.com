package nts.uk.ctx.pereg.app.find.initsetting.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.shr.pereg.app.ComboBoxObject;

@Stateless
public class SettingItemDtoMapping {

	@Inject
	private ComboBoxRetrieveFactory comboBoxFac;

	public void setTextForItem(List<SettingItemDto> result, String employeeId, GeneralDate baseDate) {
		setTextForSelectionItem(result, employeeId, baseDate);
		setTextForSetItem(result);
	}

	public void setTextForSelectionItem(List<SettingItemDto> result, String employeeId, GeneralDate baseDate) {

		List<SettingItemDto> SelectionItemLst = result.stream()
				.filter(x -> x.getDataType().equals(DataTypeValue.SELECTION)).collect(Collectors.toList());

		if (!CollectionUtil.isEmpty(SelectionItemLst)) {
			SelectionItemLst.forEach(item -> {

				List<ComboBoxObject> comboxList = this.comboBoxFac.getComboBox(item.getSelectionItemRefType(),
						item.getSelectionItemRefCd(), baseDate, employeeId, null, true);

				comboxList.forEach(cbItem -> {
					if (cbItem.getOptionValue().equals(item.getSaveData().getValue())) {

						item.getSaveData().setValue(cbItem.getOptionText());
					}
				});
			});
		}

	}

	private void setTextForSetItem(List<SettingItemDto> result) {
		List<SettingItemDto> childList = result.stream().filter(x -> !StringUtils.isEmpty(x.getItemParentCd()))
				.collect(Collectors.toList());

		if (!CollectionUtil.isEmpty(childList)) {
			List<String> itemSetCdLst = new ArrayList<String>();
			childList.forEach(child -> {

				if (!itemSetCdLst.contains(child.getItemParentCd())) {
					itemSetCdLst.add(child.getItemParentCd());
				}

			});

			itemSetCdLst.forEach(itemCd -> {

				Optional<SettingItemDto> itemSetOpt = result.stream().filter(item -> item.getItemCode().equals(itemCd))
						.findFirst();
				if (itemSetOpt.isPresent()) {

					SettingItemDto itemSet = itemSetOpt.get();
					String itemValue = genItemvalue(result, itemCd);
					itemSet.setData(itemValue);
				}

			});
		}
	}

	private String genItemvalue(List<SettingItemDto> result, String itemCd) {

		String itemValue = "";
		List<SettingItemDto> childItems = result.stream()
				.filter(item -> String.valueOf(item.getItemParentCd()).equals(itemCd)).collect(Collectors.toList());

		for (SettingItemDto childItem : childItems) {

			if (!StringUtils.isEmpty(childItem.getSaveData().getValue().toString())) {
				if (itemValue.equals("")) {

					itemValue = childItem.getSaveData().getValue().toString();
				} else {
					itemValue = String.join(getBetweenChar(childItem.getDataType()), itemValue,
							childItem.getSaveData().getValue().toString());
				}
			}
		}

		return itemValue;
	}

	private String getBetweenChar(DataTypeValue dataType) {

		switch (dataType) {
		case DATE:
		case TIME:
		case TIMEPOINT:
			return "~";

		default:
			return " ";
		}

	}
}
