package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationlatearrival;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyRequest;

@AllArgsConstructor
public class LateEarlyRequestDto {
	/** * 会社ID */
	public String companyId;
	
	/** * 実績を表示する */
	public int showResult;
	
	/**
	 * From Domain
	 * @param lateEarlyRequest
	 * @return
	 */
	public static LateEarlyRequestDto fromDomain(LateEarlyRequest lateEarlyRequest){
		return new LateEarlyRequestDto(
				lateEarlyRequest.getCompanyId(), 
				lateEarlyRequest.getShowResult().value);
	}
}
