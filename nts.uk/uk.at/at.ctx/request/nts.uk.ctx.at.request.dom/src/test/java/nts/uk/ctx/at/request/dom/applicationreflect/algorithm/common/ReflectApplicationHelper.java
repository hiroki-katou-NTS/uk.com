package nts.uk.ctx.at.request.dom.applicationreflect.algorithm.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflectDaily;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.ReflectionStatus;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;
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

	public static ReflectStatusResult createReflectStatusResult(ReflectedState state) {
		return createReflectStatusResult(state, null);
	}
	public static ReflectStatusResult createReflectStatusResult(ReflectedState state, ReasonNotReflect schedule) {
		return new ReflectStatusResult(state, null, schedule);
	}
	
	public static ReflectStatusResult createRCReflectStatusResult(ReflectedState state, ReasonNotReflectDaily record) {
		return new ReflectStatusResult(state, record, null);
	}

	public static Application createAppWithPeriod(ApplicationType appType, DatePeriod period, ReflectedState state) {
		Application app = new Application(1, "1", PrePostAtr.PREDICT, "1", appType, null, "1", GeneralDateTime.now(),
				new ReflectionStatus(new ArrayList<>()));
		app.setOpAppStartDate(Optional.of(new ApplicationDate(period.start())));
		app.setOpAppEndDate(Optional.of(new ApplicationDate(period.end())));
		List<ReflectionStatusOfDay> listReflectionStatusOfDay = new ArrayList<>();
		listReflectionStatusOfDay.addAll(period.datesBetween().stream().map(x -> createReflectionStatusOfDay(x, state))
				.collect(Collectors.toList()));
		app.setReflectionStatus(new ReflectionStatus(listReflectionStatusOfDay));
		return app;
	}

	public static Application createAppWithDate(ApplicationType appType, GeneralDate date, ReflectedState state) {
		return createAppWithDate(appType, PrePostAtr.PREDICT, date, state);
	}
	
	public static Application createAppWithDate(ApplicationType appType, PrePostAtr atr, GeneralDate date, ReflectedState state) {
		Application app = new Application(1, "1", atr, "1", appType, new ApplicationDate(date), "1",
				GeneralDateTime.now(), new ReflectionStatus(new ArrayList<>()));
		List<ReflectionStatusOfDay> listReflectionStatusOfDay = new ArrayList<>();
		listReflectionStatusOfDay.add(createReflectionStatusOfDay(date, state));
		app.setReflectionStatus(new ReflectionStatus(listReflectionStatusOfDay));
		return app;
	}

	private static ReflectionStatusOfDay createReflectionStatusOfDay(GeneralDate targetDate, ReflectedState state) {
		return new ReflectionStatusOfDay(state, state, targetDate, Optional.empty(), Optional.empty());
	}
}
