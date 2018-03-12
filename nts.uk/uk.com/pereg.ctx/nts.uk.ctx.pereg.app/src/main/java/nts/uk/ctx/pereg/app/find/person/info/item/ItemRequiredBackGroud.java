package nts.uk.ctx.pereg.app.find.person.info.item;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ItemRequiredBackGroud {

	private String rowId;
	
	private String columnKey;
	
	private List<String> state = new ArrayList<>(); 

}
