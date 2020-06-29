package nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class MailServerSetImport {
	/**
	 * メールサーバ設定済区分
	 */
	private boolean mailServerSet;
	
	/**
	 * ドメインモデル「メールサーバ」
	 */
	private Optional<MailServerImport> opMailServerImport; 
}
