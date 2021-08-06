package nts.uk.ctx.at.record.app.command.knr.knr001.a;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class NRConvertInfoCommand {

	private OutPlaceConvertCommand outPlaceConvert;
	
	private int entranceExit;
}
