package nts.uk.ctx.sys.assist.app.find.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.sys.assist.dom.datarestoration.Target;

@AllArgsConstructor
@Value
public class TargetItemDto {
	/**
	 * データ復旧処理ID
	 */
	private String dataRecoveryProcessId;

	/**
	 * 社員ID
	 */
	private String sid;

	/**
	 * 社員コード
	 */
	private String scd;

	/**
	 * ビジネスネーム
	 */
	private String bussinessName;
	
	public static TargetItemDto convertToDto(Target target){
		return new TargetItemDto(target.getDataRecoveryProcessId(), target.getSid(), target.getScd().orElse(""), target.getBussinessName().orElse(""));
	}
}
