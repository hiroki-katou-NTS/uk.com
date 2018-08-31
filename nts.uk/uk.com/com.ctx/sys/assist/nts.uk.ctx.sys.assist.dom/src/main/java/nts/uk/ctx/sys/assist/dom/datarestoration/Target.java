package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 対象者
 */
@Getter
public class Target extends DomainObject {

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
	private Optional<String> scd;

	/**
	 * ビジネスネーム
	 */
	private Optional<String> bussinessName;

	public Target(String dataRecoveryProcessId, String sid, String scd, String bussinessName) {
		this.dataRecoveryProcessId = dataRecoveryProcessId;
		this.sid                   = sid;
		this.scd                   = Optional.ofNullable(scd);
		this.bussinessName         = Optional.ofNullable(bussinessName);
	}
}
