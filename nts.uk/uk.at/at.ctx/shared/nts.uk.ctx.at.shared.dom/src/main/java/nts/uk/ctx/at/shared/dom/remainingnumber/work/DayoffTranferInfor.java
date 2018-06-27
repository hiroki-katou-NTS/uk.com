package nts.uk.ctx.at.shared.dom.remainingnumber.work;


import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 代休振替情報
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DayoffTranferInfor {
	/**	就業時間帯コード */
	private Optional<String> workTimeCode;
	/**	振替休出時間 */
	private Optional<TranferTimeInfor> tranferBreakTime;
	/**	振替残業時間 */
	private Optional<TranferTimeInfor> tranferOverTime;
	//TODO 作成元区分を取得する
	//public CreateAtr createAtr;
	//TODO 代休振替発生時間を取得する
	//TODO 代休発生日数を取得する
}
