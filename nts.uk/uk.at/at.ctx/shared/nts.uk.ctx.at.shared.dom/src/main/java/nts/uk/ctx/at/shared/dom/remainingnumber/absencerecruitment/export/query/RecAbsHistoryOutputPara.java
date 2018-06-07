package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
/**
 * 振出振休履歴対照情報
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecAbsHistoryOutputPara {
	/**
	 * 年月日
	 */
	private GeneralDate ymd;
	/**
	 * 使用日数
	 */
	private Optional<Double> useDays;
	/**
	 * 振休履歴
	 */
	private Optional<AbsenceHistoryOutputPara> absHisData;
	/**
	 * 振出履歴
	 */
	private Optional<RecruitmentHistoryOutPara> recHisData;
	
}
