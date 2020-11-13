package nts.uk.ctx.at.request.dom.applicationreflect.algorithm.common;

import java.util.ArrayList;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflectDaily;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.ReflectionStatus;
import nts.uk.ctx.at.request.dom.applicationreflect.object.ReflectStatusResult;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;

public class ReflectApplicationHelper {

	public static Application createApp(ApplicationType appType, PrePostAtr pre) {

		return new Application(1, "1", pre, "1", appType, new ApplicationDate(GeneralDate.today()), "1",
				GeneralDateTime.now(), new ReflectionStatus(new ArrayList<>()));
	}

	public static Application createApp(PrePostAtr pre) {
		return createApp(ApplicationType.STAMP_APPLICATION, pre);
	}

	public static Application createAppWithReason(ApplicationType appType, Integer standCode, String appReason) {
		Application app = new Application(1, "1", PrePostAtr.POSTERIOR, "1", appType,
				new ApplicationDate(GeneralDate.today()), "1", GeneralDateTime.now(),
				new ReflectionStatus(new ArrayList<>()));
		app.setOpAppStandardReasonCD(
				Optional.ofNullable(standCode == null ? null : new AppStandardReasonCode(standCode)));
		app.setOpAppReason(Optional.ofNullable(appReason == null ? null : new AppReason(appReason)));
		return app;
	}

	public static ReflectStatusResult createReflectStatusResult() {
		return new ReflectStatusResult(ReflectedState.NOTREFLECTED, ReasonNotReflectDaily.ACTUAL_CONFIRMED,
				ReasonNotReflect.WORK_CONFIRMED);
	}
}
