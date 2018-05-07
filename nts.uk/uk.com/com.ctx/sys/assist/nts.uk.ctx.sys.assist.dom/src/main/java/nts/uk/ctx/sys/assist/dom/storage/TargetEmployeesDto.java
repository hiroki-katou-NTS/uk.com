/**
 * 
 */
package nts.uk.ctx.sys.assist.dom.storage;

import lombok.Data;

/**
 * @author nam.lh
 *
 */

@Data
public class TargetEmployeesDto {
	/**
	 * データ保存処理ID
	 */
	private String storeProcessingId;

	/**
	 * 社員ID
	 */
	private String Sid;

	/**
	 * ビジネスネーム
	 */
	private String businessname;

	/**
	 * 社員コード
	 */
	private String scd;
}
