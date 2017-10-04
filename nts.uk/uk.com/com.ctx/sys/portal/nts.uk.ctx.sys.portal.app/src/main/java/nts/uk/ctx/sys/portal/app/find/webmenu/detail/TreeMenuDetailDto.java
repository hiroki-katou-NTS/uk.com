package nts.uk.ctx.sys.portal.app.find.webmenu.detail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TreeMenuDetailDto {
	private String code;

	private String defaultName;
	
	private String displayName;
	
	private String url;
	
	private int displayOrder;

	private int system;
	
	// Separator line (0) or menu (1)
	private int menuAttr;

	private int classification;
	
	private int afterLoginDisplay;
	
}
