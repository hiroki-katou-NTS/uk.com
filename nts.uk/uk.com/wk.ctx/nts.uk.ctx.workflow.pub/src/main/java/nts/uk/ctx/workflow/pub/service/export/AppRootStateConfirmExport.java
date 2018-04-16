package nts.uk.ctx.workflow.pub.service.export;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 承認ルートインスタンスを生成する
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppRootStateConfirmExport {
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
