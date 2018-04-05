package nts.uk.ctx.pereg.app.find.roles.auth.item;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
@Value
public class ItemReadOnly {
	public ItemReadOnly(String personItemDefId2, List<String> asList) {
		this.personItemDefId = personItemDefId2;
		this.option.addAll(asList);
	}

	private String personItemDefId;
	
	private List<String> option = new ArrayList<>();
}
