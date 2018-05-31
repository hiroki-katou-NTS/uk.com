package nts.uk.ctx.pereg.app.find.roles.auth.item;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import nts.uk.ctx.pereg.app.find.person.info.item.ItemRequiredBackGroud;
@Value
public class ItemAuth {
	public ItemAuth(List<PersonInfoItemDetailDto> itemDto, List<PersonInfoItemDetailDto> item, List<ItemReadOnly> itemReadDto, List<ItemRequiredBackGroud> itemRequired) {
		this.itemLst.addAll(itemDto);
		this.itemAuthLst.addAll(item);
		this.itemReadLst.addAll(itemReadDto);
		this.itemRequired.addAll(itemRequired);
	}
	private List<PersonInfoItemDetailDto>  itemLst = new ArrayList<>();
	
	private List<PersonInfoItemDetailDto>  itemAuthLst = new ArrayList<>();
	
	private List<ItemReadOnly>  itemReadLst = new ArrayList<>();
	
	private List<ItemRequiredBackGroud>    itemRequired = new ArrayList<>();
}
