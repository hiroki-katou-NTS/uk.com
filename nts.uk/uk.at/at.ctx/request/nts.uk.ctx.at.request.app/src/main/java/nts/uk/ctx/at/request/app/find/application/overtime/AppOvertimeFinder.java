package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayInfoOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkContent;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;

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
		DisplayInfoOverTime output = null;
		String companyId = param.companyId;
		Optional<GeneralDate> dateOp = Optional.empty();
		if (StringUtils.isNotBlank(param.dateOp)) {
			dateOp = Optional.of(GeneralDate.fromString(param.dateOp, PATTERN_DATE));	
		}
		
		OvertimeAppAtr overtimeAppAtr = EnumAdaptor.valueOf(param.overtimeAppAtr, OvertimeAppAtr.class);
		AppDispInfoStartupOutput appDispInfoStartupOutput = param.appDispInfoStartupDto.toDomain();
		Optional<Integer> startTimeSPR = Optional.empty();
		if (param.startTimeSPR != null) {
			startTimeSPR = Optional.of(param.startTimeSPR);
		}
		
		Optional<Integer> endTimeSPR = Optional.empty();
		
		if (param.endTimeSPR != null) {
			endTimeSPR = Optional.of(param.endTimeSPR);
		}
		Boolean isProxy = param.isProxy;
		String employeeId = param.employeeId;
		
		PrePostAtr prePostInitAtr = EnumAdaptor.valueOf(param.prePostInitAtr, PrePostAtr.class);
		
		OvertimeLeaveAppCommonSet overtimeLeaveAppCommonSet = param.overtimeLeaveAppCommonSetCommand.toDomain();
		ApplicationTime advanceApplicationTime = param.advanceApplicationTimeCommand.toDomain();
		ApplicationTime achieveApplicationTime = param.achieveApplicationTimeCommand.toDomain();
		WorkContent workContent = param.workContentCommand.toDomain();
		
		output = overtimeService.startA(
				companyId,
				dateOp,
				overtimeAppAtr,
				appDispInfoStartupOutput,
				startTimeSPR,
				endTimeSPR,
				isProxy,
				employeeId,
				prePostInitAtr,
				overtimeLeaveAppCommonSet,
				advanceApplicationTime,
				achieveApplicationTime,
				workContent);
		return DisplayInfoOverTimeDto.fromDomain(output);
	}
}
