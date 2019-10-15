package nts.uk.ctx.pereg.app.find.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.stamp.card.stampcardedit.StampCardEditFinder;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoValueDto;
import nts.uk.ctx.pereg.app.find.person.info.item.StringItemDto;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;

@Stateless
public class StampCardLength {

	@Inject
	private StampCardEditFinder stampCardEditFinder;
	
	public int getDigitOfStamp() {
		return  stampCardEditFinder.findDto().getDigitsNumber();
	}

	public void updateLength(PersonInfoCategory perInfoCategory, List<LayoutPersonInfoClsDto> classItemList) {
		if (!perInfoCategory.getCategoryCode().v().equals("CS00069")) {
			return;
		}

		List<LayoutPersonInfoValueDto> itemIS00779List = new ArrayList<>();
		
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			List<LayoutPersonInfoValueDto> itemList = classItem.getItems().stream()
					.filter(itemDefDto -> itemDefDto.getItemCode().equals("IS00779"))
					.collect(Collectors.toList());
			
			itemIS00779List.addAll(itemList);
		}

		int digitsNumber = this.getDigitOfStamp();

		itemIS00779List.forEach(itemIS00779 -> {
			StringItemDto dataTypeState = (StringItemDto) itemIS00779.getItem();
			
			dataTypeState.updateStringLength(digitsNumber);
		});
	}

	public void updateLength(PersonInfoCategory perInfoCategory, List<LayoutPersonInfoClsDto> classItemList, int digitsNumber) {
		if (!perInfoCategory.getCategoryCode().v().equals("CS00069")) {
			return;
		}

		List<LayoutPersonInfoValueDto> itemIS00779List = new ArrayList<>();
		
		for (LayoutPersonInfoClsDto classItem : classItemList) {
			List<LayoutPersonInfoValueDto> itemList = classItem.getItems().stream()
					.filter(itemDefDto -> itemDefDto.getItemCode().equals("IS00779"))
					.collect(Collectors.toList());
			
			itemIS00779List.addAll(itemList);
		}

		itemIS00779List.forEach(itemIS00779 -> {
			StringItemDto dataTypeState = (StringItemDto) itemIS00779.getItem();
			
			dataTypeState.updateStringLength(digitsNumber);
		});	
	}
}
