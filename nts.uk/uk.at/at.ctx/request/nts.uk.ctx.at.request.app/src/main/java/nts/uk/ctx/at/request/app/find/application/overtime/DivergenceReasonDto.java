package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.setting.company.appreasonstandard.ReasonTypeItemDto;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;

@AllArgsConstructor
@NoArgsConstructor
public class DivergenceReasonDto {
	/**
	 * 会社ID
	 * companyID
	 */
	public String companyID;
	
	/**
	 * 申請種類
	 */
	public Integer appType;
	
	/**
	 * 定型理由項目
	 */
	public ReasonTypeItemDto reasonTypeItem;
	
	public static DivergenceReasonDto fromDomain(DivergenceReason divergenceReason) {
		return null;
	}
}
