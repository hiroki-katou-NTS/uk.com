package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.CheckBeforeOutputDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.DetailOutputDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.CheckBeforeOutput;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayInfoOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;

@Stateless
public class AppOvertimeFinder {
	public static final String PATTERN_DATE = "yyyy/MM/dd";
	
	@Inject
	private OvertimeService overtimeService;
	
	/**
	 * Refactor5
	 * start at A
	 * @param paramOverTimeStart
	 * @return
	 */
	public DisplayInfoOverTimeDto start(ParamOverTimeStart param) {
		DisplayInfoOverTime output;
		String companyId = param.companyId;
		Optional<GeneralDate> dateOp = Optional.empty();
		if (StringUtils.isNotBlank(param.dateOp)) {
			dateOp = Optional.of(GeneralDate.fromString(param.dateOp, PATTERN_DATE));	
		}
		
		OvertimeAppAtr overtimeAppAtr = EnumAdaptor.valueOf(param.overtimeAppAtr, OvertimeAppAtr.class);
		AppDispInfoStartupOutput appDispInfoStartupOutput = param.appDispInfoStartupDto.toDomain();
		Optional<Integer> startTimeSPR = Optional.ofNullable(param.startTimeSPR);
		
		Optional<Integer> endTimeSPR = Optional.ofNullable(param.endTimeSPR);
		Boolean isProxy = param.isProxy;

		output = overtimeService.startA(
				companyId,
				dateOp,
				overtimeAppAtr,
				appDispInfoStartupOutput,
				startTimeSPR,
				endTimeSPR,
				isProxy
				);
		return DisplayInfoOverTimeDto.fromDomain(output);
	}
	public DisplayInfoOverTimeDto changeDate(ParamOverTimeChangeDate param) {
		DisplayInfoOverTime output = new DisplayInfoOverTime();
		String companyId = param.companyId;
		Optional<GeneralDate> dateOp = Optional.empty();
		if (StringUtils.isNotBlank(param.dateOp)) {
			dateOp = Optional.of(GeneralDate.fromString(param.dateOp, PATTERN_DATE));	
		}
		
		AppDispInfoStartupOutput appDispInfoStartupOutput = param.appDispInfoStartupDto.toDomain();
		Optional<Integer> startTimeSPR = Optional.ofNullable(param.startTimeSPR);
		
		Optional<Integer> endTimeSPR = Optional.ofNullable(param.endTimeSPR);

		output = overtimeService.changeDate(
				companyId,
				param.employeeId,
				dateOp,
				EnumAdaptor.valueOf(param.overtimeAppAtr, OvertimeAppAtr.class),
				appDispInfoStartupOutput,
				startTimeSPR,
				endTimeSPR,
				param.overTimeAppSet.toDomain(companyId),
				CollectionUtil.isEmpty(param.worktypes) ? Collections.emptyList() : param.worktypes
						.stream()
						.map(x -> x.toDomain(param.companyId))
						.collect(Collectors.toList())
				);
		return DisplayInfoOverTimeDto.fromDomainChangeDate(output);
	}
	
	public DisplayInfoOverTimeDto calculate(ParamCalculation param) {
		String companyId = param.companyId;
		Optional<GeneralDate> dateOp = Optional.empty();
		if (StringUtils.isNotBlank(param.dateOp)) {
			dateOp = Optional.of(GeneralDate.fromString(param.dateOp, PATTERN_DATE));	
		}
		
		DisplayInfoOverTime output = overtimeService.calculate(
				companyId,
				param.employeeId,
				dateOp,
				EnumAdaptor.valueOf(param.prePostInitAtr, PrePostAtr.class),
				param.overtimeLeaveAppCommonSet.toDomain(),
				param.advanceApplicationTime == null ? null : param.advanceApplicationTime.toDomain(),
				param.achieveApplicationTime == null ? null : param.achieveApplicationTime.toDomain(),
				param.workContent.toDomain());
		
		
		return DisplayInfoOverTimeDto.fromDomainCalculation(output);
	}
	
	public CheckBeforeOutputDto checkBeforeRegister(ParamCheckBeforeRegister param) {
		CheckBeforeOutput output = null;
		DisplayInfoOverTime displayInfoOverTime = param.displayInfoOverTime.toDomain();
		Application application = this.createApplication(param.appOverTime.application);
		AppOverTime appOverTime = param.appOverTime.toDomain();
		appOverTime.setApplication(application);
		output = overtimeService.checkErrorRegister(
				param.require,
				param.companyId,
				displayInfoOverTime,
				appOverTime);
		return CheckBeforeOutputDto.fromDomain(output);
	}
	
	public CheckBeforeOutputDto checkBeforeUpdate(ParamCheckBeforeRegister param) {
		CheckBeforeOutput output = null;
		DisplayInfoOverTime displayInfoOverTime = param.displayInfoOverTime.toDomain();
		Application application = param.appOverTime.application.toDomain();
		AppOverTime appOverTime = param.appOverTime.toDomain();
		appOverTime.setApplication(application);
		output = overtimeService.checkBeforeUpdate(
				param.require,
				param.companyId,
				appOverTime,
				displayInfoOverTime);
		return CheckBeforeOutputDto.fromDomain(output);
	}
	
	public DetailOutputDto getDetail(ParamDetail param) {
		
		return DetailOutputDto.fromDomain(overtimeService.getDetailData(
				param.companyId,
				param.appId,
				param.appDispInfoStartup.toDomain()));
	}
	
	public Application createApplication(ApplicationDto application) {
		
		return Application.createFromNew(
				EnumAdaptor.valueOf(application.getPrePostAtr(), PrePostAtr.class),
				application.getEmployeeID(),
				ApplicationType.OVER_TIME_APPLICATION,
				new ApplicationDate(GeneralDate.fromString(application.getAppDate(), PATTERN_DATE)),
				application.getEnteredPerson(),
				application.getOpStampRequestMode() == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(application.getOpStampRequestMode(), StampRequestMode.class)),
				application.getOpReversionReason() == null ? Optional.empty() : Optional.of(new ReasonForReversion(application.getOpReversionReason())),
				application.getOpAppStartDate() == null ? Optional.empty() : Optional.of(new ApplicationDate(GeneralDate.fromString(application.getOpAppStartDate(), PATTERN_DATE))),
				application.getOpAppEndDate() == null ? Optional.empty() : Optional.of(new ApplicationDate(GeneralDate.fromString(application.getOpAppEndDate(), PATTERN_DATE))),
				application.getOpAppReason() == null ? Optional.empty() : Optional.of(new AppReason(application.getOpAppReason())),
				application.getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(application.getOpAppStandardReasonCD())));
	}
	
}
