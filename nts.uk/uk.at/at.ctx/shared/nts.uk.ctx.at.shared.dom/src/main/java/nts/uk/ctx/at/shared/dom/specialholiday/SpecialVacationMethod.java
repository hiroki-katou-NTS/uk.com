package nts.uk.ctx.at.shared.dom.specialholiday;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SpecialVacationMethod {
	/* 無期限 */
	IndefinitePeriod(0),
	/* 次回付与日まで使用可能 */
	AvailableUntilNextGrantDate(1),
	/* 付与日から指定期間まで使用可能 */
	AvailableGrantDateDesignate(2);

	public final int value;
}
