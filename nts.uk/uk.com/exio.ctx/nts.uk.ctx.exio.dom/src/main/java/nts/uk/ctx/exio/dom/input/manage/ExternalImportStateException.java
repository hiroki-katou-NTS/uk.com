package nts.uk.ctx.exio.dom.input.manage;

/**
 * 外部受入の現在の実行状態では実行不能であることを通知する
 */
@SuppressWarnings("serial")
public class ExternalImportStateException extends Exception {

	public ExternalImportStateException(String errorMessage) {
		super(errorMessage);
	}
}
