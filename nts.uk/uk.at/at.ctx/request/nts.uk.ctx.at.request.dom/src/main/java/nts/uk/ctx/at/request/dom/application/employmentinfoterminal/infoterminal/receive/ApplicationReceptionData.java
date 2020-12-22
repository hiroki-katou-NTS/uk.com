package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.ReflectionStatus;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.NRHelper;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;

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

	public Application createAplication(String companyId, String employeeId, Integer typeBeforeAfter,
			ApplicationType typeCategory, Optional<GeneralDate> startDate, Optional<GeneralDate> endDate,
			String reason) {
		return createAplication(companyId, employeeId, typeBeforeAfter, typeCategory, startDate, endDate, reason,
				false);
	}
	
	public Application createAplication(String companyId, String employeeId, Integer typeBeforeAfter,
			ApplicationType typeCategory, Optional<GeneralDate> startDate, Optional<GeneralDate> endDate, String reason,
			boolean dakoku) {

		List<ReflectionStatusOfDay> listReflectionStatusOfDay = new ArrayList<>();
		if (startDate.isPresent() && endDate.isPresent()) {
			GeneralDate startDateT = startDate.get();
			GeneralDate endDateT = endDate.get();
			for (GeneralDate loopDate = startDateT; loopDate.beforeOrEquals(endDateT); loopDate = loopDate.addDays(1)) {
				listReflectionStatusOfDay.add(ReflectionStatusOfDay.createNew(ReflectedState.NOTREFLECTED,
						ReflectedState.NOTREFLECTED, loopDate));
			}
		} else {
			listReflectionStatusOfDay.add(ReflectionStatusOfDay.createNew(ReflectedState.NOTREFLECTED,
					ReflectedState.NOTREFLECTED, startDate.isPresent() ? startDate.get() : GeneralDate.today()));
		}

		Integer reasonConvert = Integer.parseInt(reason) - (Integer.parseInt(reason) / 10) * 10;

		Application app = new Application(0, IdentifierUtil.randomUniqueId(),
				EnumAdaptor.valueOf(typeBeforeAfter, PrePostAtr.class), employeeId, typeCategory,
				startDate.map(x -> new ApplicationDate(x)).orElse(new ApplicationDate(GeneralDate.today())), employeeId,
				NRHelper.getDateTime(ymd, time), new ReflectionStatus(listReflectionStatusOfDay));

		app.setOpStampRequestMode(Optional.ofNullable(dakoku ? StampRequestMode.STAMP_ONLINE_RECORD : null));
		app.setOpReversionReason(Optional.empty());
		app.setOpAppStartDate(startDate.map(x -> new ApplicationDate(x)));
		app.setOpAppEndDate(endDate.map(x -> new ApplicationDate(x)));
		app.setOpAppReason(Optional.empty());
		app.setOpAppStandardReasonCD(Optional.of(new AppStandardReasonCode(reasonConvert)));
		
		return app;
	}

}
