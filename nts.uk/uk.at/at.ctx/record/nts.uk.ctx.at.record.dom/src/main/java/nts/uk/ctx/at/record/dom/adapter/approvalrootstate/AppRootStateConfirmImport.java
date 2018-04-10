package nts.uk.ctx.at.record.dom.adapter.approvalrootstate;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppRootStateConfirmImport {

	/**
	 * エラーフラグ
	 */
	private Boolean isError;
	
	/**
	 * ルートインスタンスID
	 */
	private Optional<String> rootStateID;
	
	/**
	 * エラーメッセージID
	 */
	private Optional<String> errorMsg;
}
