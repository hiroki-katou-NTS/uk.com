package nts.uk.ctx.pereg.app.find.initsetting.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.app.find.common.ComboBoxRetrieveFactory;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.shr.pereg.app.ComboBoxObject;

@Stateless
public class SettingItemDtoMapping {

	@Inject
	private ComboBoxRetrieveFactory comboBoxFac;

	public void setTextForSelectionItem(List<SettingItemDto> result) {

		List<SettingItemDto> SelectionItemLst = result.stream()
				.filter(x -> x.getDataType().equals(DataTypeValue.SELECTION)).collect(Collectors.toList());

		if (!CollectionUtil.isEmpty(SelectionItemLst)) {
			SelectionItemLst.forEach(item -> {

				List<ComboBoxObject> comboxList = this.comboBoxFac.getComboBox(item.getSelectionItemRefType(),
						item.getSelectionItemRefCd(), GeneralDate.today());

				comboxList.forEach(cbItem -> {
					if (cbItem.getOptionValue().equals(item.getSaveData().getValue())) {

						item.getSaveData().setValue(cbItem.getOptionText());
					}
				});
			});
		}

	}
}
