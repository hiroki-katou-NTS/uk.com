package nts.uk.ctx.pereg.app.find.person.setting.init.category;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pereg.app.find.person.info.item.ItemRequiredBackGroud;
import nts.uk.ctx.pereg.app.find.person.setting.init.item.ItemDto;
@Getter
@Setter
public class CategoryStateDto {
	private List<ItemRequiredBackGroud> itemRequired = new ArrayList<>();
	
	private List<ItemDto> itemLst = new ArrayList<>();

}
