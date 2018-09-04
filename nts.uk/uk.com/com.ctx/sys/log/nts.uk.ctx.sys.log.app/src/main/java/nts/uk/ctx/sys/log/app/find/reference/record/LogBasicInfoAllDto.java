package nts.uk.ctx.sys.log.app.find.reference.record;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;

/*
 * author: huannv
 */
@Getter
@Setter
@AllArgsConstructor
public class LogBasicInfoAllDto {
	/** operationId */
	private String operationId;
	/** userName login */
	private String userNameLogin; // no 2
	/** employeeCode Login */
	private String employeeCodeLogin; // no 3
	/** userId */
	private String userIdTaget;
	/** userName */
	private String userNameTaget; // no 20
	/** employeeId */
	private String employeeIdTaget;
	/** employeeCode */
	private String employeeCodeTaget;// no 21
	/** ipAdress */
	private String ipAddress;
	/** modifyDateTime */
	private String modifyDateTime;
	/** processAttr */
	private String processAttr;

	/** List data log */
	/*
	 * private List<LogDataCorrectRecordRefeDto> lstLogDataCorrectRecordRefeDto;
	 */

	/** List persion corect log */
	/*
	 * private List<LogPerCateCorrectRecordDto> lstLogPerCateCorrectRecordDto;
	 */

	/** List sub header */
	/* private List<LogOutputItemDto> lstLogOutputItemDto; */
	// log startPage
	private String menuName;
	private String note;
	private String menuNameReSource;
	// log login
	private String loginMethod;
	private String loginStatus;
	private String userIdLogin;
	private String pcName;
	private String account;
	private String employmentAuthorityName;
	private String salarytAuthorityName;
	private String personelAuthorityName;
	private String officeHelperAuthorityName;
	private String accountAuthorityName;
	private String myNumberAuthorityName;
	private String groupCompanyAddminAuthorityName;
	private String companyAddminAuthorityName;
	private String systemAdminAuthorityName;
	private String personalInfoAuthorityName;
	private String accessResourceUrl;
	//datacorect
	//userIdTaget,userNameTaget,employeeCodeTaget
	private String tarGetYmd;
	private String tarGetYm;
	private String tarGetY;
	private String keyString;
	private String catagoryCorection;
	private String itemName;
	private String itemvalueBefor;
	private String itemvalueAppter;
	private String itemContentValueBefor;
	private String itemContentValueAppter;
	// data personupdate
	private String categoryProcess;//22
	private String categoryName;//23
	private String methodCorrection;// 24
	//25 tarGetYmd,26 tarGetYm,27 tarGetY 28 keyString
	// 29 itemName,30 itemvalueBefor,31 itemContentValueBefor
	//32 itemvalueAppter 33 itemContentValueAppter
	private String itemEditAddition;//34
	private String tarGetYmdEditAddition; //35 36 note

	public static LogBasicInfoAllDto fromDomain(LogBasicInformation domain) {
		return new LogBasicInfoAllDto(domain.getOperationId(), domain.getUserInfo().getUserName(), null, null, null,
				null, null, null, domain.getModifiedDateTime().toString(), null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,null,
				null,null,null,null,null,null,null,null,null,null,null,null,null,null);
	}
}
