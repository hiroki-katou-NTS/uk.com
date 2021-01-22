package nts.uk.query.app.user.information;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.service.OtherContactInfomation;

@Data
@Builder
public class ContactInformationDTO {
	/** 会社の携帯メールアドレス */
	private String companyMobileEmailAddress;
	
	/** 個人の携帯メールアドレス */
	private String personalMobileEmailAddress;
	
	/** 個人のメールアドレス */
	private String personalEmailAddress;
	
	/** 会社のメールアドレス */
	private String companyEmailAddress;
	
	/** 他の連絡先 */
	private List<OtherContactInfomation> otherContactsInfomation;
	
	/** 座席ダイヤルイン */
	private String seatDialIn;
	
	/** 座席内線番号 */
	private String seatExtensionNumber;
	
	/** 緊急連絡先1 */
	private String emergencyNumber1;
	
	/** 緊急連絡先2 */
	private String emergencyNumber2;
	
	/** 個人の携帯電話番号 */
	private String personalMobilePhoneNumber;
	
	/** 会社の携帯電話番号 */
	private String companyMobilePhoneNumber;
}
