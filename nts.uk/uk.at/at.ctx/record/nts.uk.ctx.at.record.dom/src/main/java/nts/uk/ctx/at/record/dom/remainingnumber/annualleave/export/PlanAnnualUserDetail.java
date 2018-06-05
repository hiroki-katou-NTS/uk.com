package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PlanAnnualUserDetail {
	/**
	 * 勤務種類
	 */
	private String workTypeCode;
	/**
	 * 使用日
	 */
	List<GeneralDate> lstYmd;

}
