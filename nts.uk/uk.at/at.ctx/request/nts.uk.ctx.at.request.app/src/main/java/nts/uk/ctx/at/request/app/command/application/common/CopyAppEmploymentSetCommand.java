package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CopyAppEmploymentSetCommand {
	/**
	 * List target employment code 
	 */
	private List<String> targetEmploymentCodes;
	/**
	 * Overide flag
	 */
	private boolean overide;
	/**
	 * Selected employment code
	 */
	private String employmentCode;
}
