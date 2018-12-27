package nts.uk.ctx.exio.app.find.exo.outputitembatchsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;

@Getter
@Setter
@AllArgsConstructor
public class StandarOutputItemDTO {
	
	String outputItemCode;
	
	String outputItemName;
	
	public static StandarOutputItemDTO fromdomain(StandardOutputItem domain){
		return new StandarOutputItemDTO(domain.getOutputItemCode().v(), domain.getOutputItemName().v());
	}

}
