package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.flex;

import lombok.AllArgsConstructor;
import nts.uk.shr.com.i18n.TextResource;

/**
 * フレックス勤務の所定時間参照
 * @author shuichu_ishida
 */
@AllArgsConstructor
public enum ReferencePredTimeOfFlex {
	/** マスタから参照 */
	FROM_MASTER(0, TextResource.localize("KMK004_288")),
	/** 実績から参照 */
	FROM_RECORD(1, TextResource.localize("KMK004_289"));
	
	public int value;
	public String nameId;
	
	public static ReferencePredTimeOfFlex valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ReferencePredTimeOfFlex val : ReferencePredTimeOfFlex.values()) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
