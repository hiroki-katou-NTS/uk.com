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
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayInfoOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;

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
		return DisplayInfoOverTimeDto.fromDomain(output);
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
				param.advanceApplicationTime.toDomain(),
				param.achieveApplicationTime.toDomain(),
				param.workContent.toDomain());
		
		
		return DisplayInfoOverTimeDto.fromDomain(output);
	}
	
}
