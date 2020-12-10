package nts.uk.screen.at.app.kmk004.h;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.FlexTimeHandle;

/**
 * 
 * @author sonnlb
 *
 *         フレックス時間の扱い
 */
@AllArgsConstructor
@Data
public class FlexTimeHandleDto {
	/** 残業時間をフレックス時間に含める */
	private boolean includeOverTime;

	/** 法定外休出時間をフレックス時間に含める */
	private boolean includeIllegalHdwk;

	public static FlexTimeHandleDto fromDomain(FlexTimeHandle domain) {

		return new FlexTimeHandleDto(domain.isIncludeOverTime(), domain.isIncludeIllegalHdwk());
	}
}
