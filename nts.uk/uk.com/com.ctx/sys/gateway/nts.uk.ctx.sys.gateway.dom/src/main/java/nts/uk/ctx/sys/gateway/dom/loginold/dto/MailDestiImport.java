package nts.uk.ctx.sys.gateway.dom.loginold.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailDestiImport {

	/**
	 * メールアドレス
	 */
	private Optional<AvailableMailAddressImport> mailAddress;
	
	/**
	 * メール送信先機能管理
	 */
	private Optional<MailDestinationFunctionManageImport> mailDestinationFunctionManage;
	
}
