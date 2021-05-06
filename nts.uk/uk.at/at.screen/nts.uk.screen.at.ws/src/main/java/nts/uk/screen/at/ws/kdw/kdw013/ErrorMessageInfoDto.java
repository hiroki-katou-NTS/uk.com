package nts.uk.screen.at.ws.kdw.kdw013;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author tutt
 *
 */
public class ErrorMessageInfoDto {
	/**
	 * 会社ID
	 */
	private String companyID;
	/**
	 * 社員ID
	 */
	private String employeeID;
	/**
	 * 処理日
	 */
	private GeneralDate processDate;
	/**
	 * 実施内容
	 */
	private int executionContent;
	/**
	 * リソースID
	 */
	private String resourceID;
	/**
	 * エラーメッセージ
	 */
	private String messageError;
}
