package nts.uk.ctx.sys.portal.app.command.toppagesetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSetting;

/**
 * The Class TopPagePersonSettingCommandBase.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopPagePersonSettingCommandBase implements TopPagePersonSetting.MementoGetter {
	
	/** The employee id. */
	private String employeeId;
	
	/** The role set code. */
	private String roleSetCode;
	
	/** The switch date. */
	private Integer switchingDate;
	
	/** The login menu code. */
	private String loginMenuCode;
	
	/** The top menu code. */
	private String topMenuCode;
	
	/** The menu classification. */
	private int menuClassification; 
	
	/** The system. */
	private int system;

}
