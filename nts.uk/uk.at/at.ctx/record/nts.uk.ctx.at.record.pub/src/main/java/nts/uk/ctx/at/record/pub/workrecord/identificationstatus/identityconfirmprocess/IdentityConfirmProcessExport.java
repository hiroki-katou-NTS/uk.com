package nts.uk.ctx.at.record.pub.workrecord.identificationstatus.identityconfirmprocess;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.enums.SelfConfirmError;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

@AllArgsConstructor
@Getter
public class IdentityConfirmProcessExport {

	/**
	 * 会社ID
	 */
	private CompanyId companyId;
	
	/**
	 * 日の本人確認を利用する
	 */
	private boolean useConfirmByYourself;
	
	/**
	 * 月の本人確認を利用する
	 */
	private boolean useIdentityOfMonth;
	
	/**
	 * エラーがある場合の日の本人確認
	 */
	private Optional<SelfConfirmError> yourSelfConfirmError;

	public static IdentityConfirmProcessExport fromDomain(IdentityProcessUseSet domain) {
		return new IdentityConfirmProcessExport(domain.getCompanyId(), domain.isUseConfirmByYourself(), domain.isUseIdentityOfMonth(), 
				domain.getYourSelfConfirmError());
	}

}
