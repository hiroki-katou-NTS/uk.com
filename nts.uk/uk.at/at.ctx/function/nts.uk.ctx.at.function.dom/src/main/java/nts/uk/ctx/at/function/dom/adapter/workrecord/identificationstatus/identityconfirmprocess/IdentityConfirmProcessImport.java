package nts.uk.ctx.at.function.dom.adapter.workrecord.identificationstatus.identityconfirmprocess;

import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * 
 * @author thuongtv
 *
 */
@Value
public class IdentityConfirmProcessImport {

	
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
	private Optional<SelfConfirmErrorImport> yourSelfConfirmError;
}
