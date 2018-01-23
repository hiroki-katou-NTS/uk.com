package nts.uk.ctx.pereg.app.find.person.category;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDataDto;

@Getter
@Setter
public class PerInfoCtgDataDto {

	private String ctgId;
	private int isAbolition;
	private List<PerInfoItemDataDto> itemList = new ArrayList<>();
}
