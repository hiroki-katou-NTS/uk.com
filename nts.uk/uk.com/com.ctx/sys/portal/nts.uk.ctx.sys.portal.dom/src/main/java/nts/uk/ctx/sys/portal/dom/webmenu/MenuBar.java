package nts.uk.ctx.sys.portal.dom.webmenu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
/**
 * 
 * @author sonnh
 *
 */

@Value
public class MenuBar {
	/**
	 * 
	 */
	
	private MenuBarName menuBarName;
	
	/**
	 * 
	 */
	
	private ActivationClassification activationClassification;
	
	/**
	 * 
	 */
	
	private ColorCode backgroundColor;
	
	/**
	 * 
	 */
	
	private ColorCode letterColor;

}
