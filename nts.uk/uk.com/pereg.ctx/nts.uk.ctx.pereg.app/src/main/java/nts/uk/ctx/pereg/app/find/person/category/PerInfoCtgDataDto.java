package nts.uk.ctx.pereg.app.find.person.category;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerInfoCtgDataDto {

	private String ctgId;
	private List<String> itemList = new ArrayList<>();
}
