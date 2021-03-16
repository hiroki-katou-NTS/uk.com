package nts.uk.screen.at.app.kmk004.g;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex.GetFlexPredWorkTime;

/**
 * 
 * @author sonnlb
 *
 *         会社別フレックス勤務集計方法
 */
@Data
@AllArgsConstructor
public class GetFlexPredWorkTimeDto {

	/** 会社ID */
	private String companyId;
	/** 参照先 */
	private int reference;

	public static GetFlexPredWorkTimeDto fromDomain(GetFlexPredWorkTime domain) {
		return new GetFlexPredWorkTimeDto(domain.getCompanyId(), domain.getReference().value);
	}

}
