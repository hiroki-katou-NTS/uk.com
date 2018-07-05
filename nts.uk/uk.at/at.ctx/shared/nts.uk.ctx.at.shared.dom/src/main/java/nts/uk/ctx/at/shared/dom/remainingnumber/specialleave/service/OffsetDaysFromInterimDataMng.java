package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OffsetDaysFromInterimDataMng {
	/**
	 * 使用数付与前
	 */
	private double beforeUseDays;
	/**
	 * 使用数付与後
	 */
	private double afterUseDays;
	/**
	 * 未消化数
	 */
	private double undigested;
}
