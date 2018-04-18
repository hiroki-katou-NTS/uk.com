package nts.uk.ctx.at.shared.dom.worktime.algorithm.difftimecorrection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.StatutoryAtr;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
// 補正用休憩時間帯
public class RestTimeForFix {
	// 時間帯
	TimeSpanForCalc timezone;
	// 法定内区分
	StatutoryAtr statutoryAtr;
}
