package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 代休残数
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RemainUnDigestedDayTimes {
	/**	残時間        ||  代休発生日数 */
	private int remainTimes;
	/**	残日数        ||  代休発生時間(Optional) */
	private double remainDays;
	/**	未消化時間 ||  代休使用日数*/
	private int unDigestedTimes;
	/**	未消化日数 ||  代休使用時間(Optional)*/
	private double unDigestedDays;
	
	private boolean errors;
}
