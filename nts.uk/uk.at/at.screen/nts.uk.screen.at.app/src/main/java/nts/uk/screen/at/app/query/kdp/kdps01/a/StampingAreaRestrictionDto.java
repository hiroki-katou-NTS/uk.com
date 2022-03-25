package nts.uk.screen.at.app.query.kdp.kdps01.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction;

/**
 * 
 * @author sonnlb
 */
@AllArgsConstructor
@Data
public class StampingAreaRestrictionDto {

	private final Integer useLocationInformation;

	/** 制限方法 */
	private final Integer stampingAreaLimit;

	public static StampingAreaRestrictionDto fromDomain(StampingAreaRestriction domain) {

		if (domain == null) {
			return null;
		}

		return new StampingAreaRestrictionDto(domain.getUseLocationInformation().value,
				domain.getStampingAreaLimit().value);
	}
}
