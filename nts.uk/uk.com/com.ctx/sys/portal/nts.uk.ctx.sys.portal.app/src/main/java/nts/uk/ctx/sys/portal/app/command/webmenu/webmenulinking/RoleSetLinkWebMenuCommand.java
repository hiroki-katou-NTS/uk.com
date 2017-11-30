package nts.uk.ctx.sys.portal.app.command.webmenu.webmenulinking;

import java.util.List;

import lombok.Value;

@Value
public class RoleSetLinkWebMenuCommand {
	/** ロールセットコード. */
	private String roleSetCd;

	/** 会社ID */
	private String companyId;

	/** List of web menu code **/
	private List<String> webMenuCds;
	
}
