package nts.uk.ctx.exio.app.find.exo.charoutputsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.exo.datafomat.ItemType;

@Getter
@AllArgsConstructor
public class OutputTypeSettingCommand {
	
	String Cid;
	
	String classification;
	
	ItemType itemType;
}
