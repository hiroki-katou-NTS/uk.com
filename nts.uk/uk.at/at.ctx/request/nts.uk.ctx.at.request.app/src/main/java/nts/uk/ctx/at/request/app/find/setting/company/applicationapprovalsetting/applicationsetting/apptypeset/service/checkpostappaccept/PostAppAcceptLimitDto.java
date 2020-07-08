package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.apptypeset.service.checkpostappaccept;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service.checkpostappaccept.PostAppAcceptLimit;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class PostAppAcceptLimitDto {
	/**
	 * 受付制限利用する
	 */
	private boolean useReceptionRestriction;
	
	/**
	 * 受付可能年月日
	 */
	private String opAcceptableDate;
	
	public static PostAppAcceptLimitDto fromDomain(PostAppAcceptLimit postAppAcceptLimit) {
		return new PostAppAcceptLimitDto(
				postAppAcceptLimit.isUseReceptionRestriction(), 
				postAppAcceptLimit.getOpAcceptableDate().map(x -> x.toString()).orElse(null));
	}
}
