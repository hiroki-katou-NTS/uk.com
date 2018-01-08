package nts.uk.ctx.at.request.dom.application;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
@Builder
public class Application_New extends DomainObject {
	@Setter
	private Long version;
	
	private String companyID; 
	
	// 申請ID
	private String appID;
	
	// 事前事後区分
	private PrePostAtr prePostAtr;
	
	// 入力日
	private GeneralDateTime inputDate;
	
	// 入力者
	@Setter
	private String enteredPersonID;
	
	// 差戻し理由
	@Setter
	private AppReason reversionReason;
	
	// 申請日
	@Setter
	private GeneralDate appDate;
	
	// 申請理由
	@Setter
	private AppReason appReason;
	
	// 申請種類
	private ApplicationType appType;
	
	// 申請者
	@Setter
	private String employeeID;
	
	// 申請終了日
	@Setter
	private Optional<GeneralDate> startDate;
	
	// 申請開始日
	@Setter
	private Optional<GeneralDate> endDate;
	
	// 反映情報
	private ReflectionInformation_New reflectionInformation;
	
	public static Application_New firstCreate(String companyID, PrePostAtr prePostAtr, GeneralDate appDate, ApplicationType appType, String employeeID, AppReason appReason){
		return Application_New.builder()
				.version(0L)
				.companyID(companyID)
				.appID(IdentifierUtil.randomUniqueId())
				.prePostAtr(prePostAtr)
				.inputDate(GeneralDateTime.now())
				.enteredPersonID(employeeID)
				.reversionReason(new AppReason(""))
				.appDate(appDate)
				.appReason(appReason)
				.appType(appType)
				.employeeID(employeeID)
				.startDate(Optional.of(appDate))
				.endDate(Optional.of(appDate))
				.reflectionInformation(ReflectionInformation_New.firstCreate())
				.build();
	}
}
