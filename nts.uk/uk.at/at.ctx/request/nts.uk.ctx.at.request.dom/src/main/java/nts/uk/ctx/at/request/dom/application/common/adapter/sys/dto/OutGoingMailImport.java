package nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dat.lh
 */
@AllArgsConstructor
@Data
public class OutGoingMailImport {
	/**
	 * メールアドレス
	 */
	private String emailAddress;
}
