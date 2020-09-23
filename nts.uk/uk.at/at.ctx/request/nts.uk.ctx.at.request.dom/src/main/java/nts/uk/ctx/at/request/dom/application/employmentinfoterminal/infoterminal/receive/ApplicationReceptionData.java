package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectionInformation_New;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.NRHelper;

/**
 * @author ThanhNX
 *
 *         申請受信データ
 */
@EqualsAndHashCode
public class ApplicationReceptionData {

	/**
	 * ID番号
	 */
	@Getter
	private String idNumber;

	/**
	 * 申請区分
	 */
	@Getter
	private String applicationCategory;

	/**
	 * 年月日
	 */
	@Getter
	private String ymd;

	/**
	 * 時分秒
	 */
	@Getter
	private String time;

	public ApplicationReceptionData(String idNumber, String applicationCategory, String ymd, String time) {
		this.idNumber = idNumber;
		this.applicationCategory = applicationCategory;
		this.ymd = ymd;
		this.time = time;
	}

	public Application_New createAplication(String companyId, String employeeId, Integer typeBeforeAfter, ApplicationType typeCategory,
			Optional<GeneralDate> startDate, Optional<GeneralDate> endDate, String reason) {
		//TODO: domain dang thieu truong so voi EA
		return Application_New.builder()
									.version(0L)
									.companyID(companyId)
									.appID(IdentifierUtil.randomUniqueId())
									.prePostAtr(EnumAdaptor.valueOf(typeBeforeAfter, PrePostAtr.class))
									.inputDate(NRHelper.getDateTime(ymd, time))
									.enteredPersonID(employeeId)
									.reversionReason(new AppReason(""))
									.appDate(startDate.isPresent() ? startDate.get() : GeneralDate.today())
									.appReason(new AppReason(""))//TODO: optional
									.appType(typeCategory)
									.employeeID(employeeId)
									.startDate(startDate)
									.endDate(endDate)
									.reflectionInformation(ReflectionInformation_New.firstCreate())
									.build();
	}

}
