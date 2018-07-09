package nts.uk.ctx.exio.app.find.exo.charoutputsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.exo.dataformat.ItemType;

@Getter
@AllArgsConstructor
public class OutputTypeSettingDTO {
	
	String Cid;
	
	String classification;
	
	ItemType itemType;
}
