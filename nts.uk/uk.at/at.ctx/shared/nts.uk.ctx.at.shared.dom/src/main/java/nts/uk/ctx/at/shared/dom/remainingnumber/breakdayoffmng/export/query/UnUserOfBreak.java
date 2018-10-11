package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;

/**
 * 休出の未使用
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UnUserOfBreak {
	/**	１日相当時間 */
	private String breakId;
	/**	休出データID */
	private Integer onedayTime;
	/**	使用期限日 */
	private GeneralDate expirationDate;
	/**	発生時間数 */
	private Integer occurrenceTimes;
	/**	発生日数 */
	private double occurrenceDays;
	/**	半日相当時間 */
	private Integer haftDayTime;
	/**	未使用時間 */
	private Integer unUsedTimes;
	/**	未使用日数 */
	private double unUsedDays;
	/**	代休消化区分 */
	private DigestionAtr digestionAtr;
	/**	消滅日 */
	private Optional<GeneralDate> disappearanceDate;
	/**
	 * 使用開始日
	 */
	private Optional<GeneralDate> useStartDate;
}
