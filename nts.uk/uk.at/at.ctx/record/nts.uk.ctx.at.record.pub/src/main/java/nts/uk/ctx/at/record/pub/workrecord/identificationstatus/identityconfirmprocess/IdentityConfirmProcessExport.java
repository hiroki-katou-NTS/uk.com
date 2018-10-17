package nts.uk.ctx.at.record.pub.workrecord.identificationstatus.identityconfirmprocess;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcess;

@AllArgsConstructor
@Getter
public class IdentityConfirmProcessExport {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 日の本人確認を利用する
	 */
	private int useDailySelfCk;

	/**
	 * 月の本人確認を利用する
	 */
	private int useMonthSelfCK;

	/**
	 * エラーがある場合の本人確認
	 */
	private Integer yourselfConfirmError;

	public static IdentityConfirmProcessExport fromDomain(IdentityProcess domain) {
		return new IdentityConfirmProcessExport(domain.getCid(), domain.getUseDailySelfCk(), domain.getUseMonthSelfCK(),
				domain.getYourselfConfirmError() == null ? null : domain.getYourselfConfirmError().value);
	}

}
