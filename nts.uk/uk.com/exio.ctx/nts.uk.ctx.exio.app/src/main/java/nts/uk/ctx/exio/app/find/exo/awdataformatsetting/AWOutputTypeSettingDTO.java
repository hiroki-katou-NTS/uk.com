package nts.uk.ctx.exio.app.find.exo.awdataformatsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.exo.dataformat.ItemType;

@Getter
@AllArgsConstructor
public class AWOutputTypeSettingDTO {
	
	String Cid;
	
	String classification;
	
	ItemType itemType;
}
