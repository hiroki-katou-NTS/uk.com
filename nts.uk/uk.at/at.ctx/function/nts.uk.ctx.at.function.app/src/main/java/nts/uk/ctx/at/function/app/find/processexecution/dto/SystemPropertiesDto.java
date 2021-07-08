package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class SystemPropertiesDto {
	/** 大塚 */
	private final boolean otsukaOption;
	
	/**	クラウド環境で動作させるか */
	private final boolean isCloud;
}
