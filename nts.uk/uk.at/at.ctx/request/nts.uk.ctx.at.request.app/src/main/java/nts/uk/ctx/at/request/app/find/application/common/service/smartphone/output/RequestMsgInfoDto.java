package nts.uk.ctx.at.request.app.find.application.common.service.smartphone.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.apptypeset.service.checkpostappaccept.PostAppAcceptLimitDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.apptypeset.service.checkpreappaccept.PreAppAcceptLimitDto;
import nts.uk.ctx.at.request.app.find.setting.workplace.appuseset.ApplicationUseSetDto;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.output.RequestMsgInfoOutput;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class RequestMsgInfoDto {
	/**
	 * 申請利用設定
	 */
	private ApplicationUseSetDto applicationUseSetting;
	
	/**
	 * 当月の締切制限
	 */
	private DeadlineLimitCurrentMonthDto deadlineLimitCurrentMonth;
	
	/**
	 * 事前申請の受付制限
	 */
	private PreAppAcceptLimitDto preAppAcceptLimit;
	
	/**
	 * 事後申請の受付制限
	 */
	private PostAppAcceptLimitDto postAppAcceptLimit;
	
	public static RequestMsgInfoDto fromDomain(RequestMsgInfoOutput requestMsgInfoOutput) {
		return new RequestMsgInfoDto(
				ApplicationUseSetDto.fromDomain(requestMsgInfoOutput.getApplicationUseSetting()), 
				DeadlineLimitCurrentMonthDto.fromDomain(requestMsgInfoOutput.getDeadlineLimitCurrentMonth()), 
				PreAppAcceptLimitDto.fromDomain(requestMsgInfoOutput.getPreAppAcceptLimit()), 
				PostAppAcceptLimitDto.fromDomain(requestMsgInfoOutput.getPostAppAcceptLimit()));
	} 
}
