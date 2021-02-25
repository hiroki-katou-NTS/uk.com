package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.apptypeset.service.checkpreappaccept;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service.checkpreappaccept.PreAppAcceptLimit;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class PreAppAcceptLimitDto {
	/**
	 * 受付制限利用する
	 */
	private boolean useReceptionRestriction;
	
	/**
	 * 受付可能年月日
	 */
	private String opAcceptableDate;
	
	/**
	 * 受付可能時刻
	 */
	private Integer opAvailableTime;
	
	public static PreAppAcceptLimitDto fromDomain(PreAppAcceptLimit preAppAcceptLimit) {
		return new PreAppAcceptLimitDto(
				preAppAcceptLimit.isUseReceptionRestriction(), 
				preAppAcceptLimit.getOpAcceptableDate().map(x -> x.toString()).orElse(null), 
				preAppAcceptLimit.getOpAvailableTime().map(x -> x.v()).orElse(null));
	}
}
