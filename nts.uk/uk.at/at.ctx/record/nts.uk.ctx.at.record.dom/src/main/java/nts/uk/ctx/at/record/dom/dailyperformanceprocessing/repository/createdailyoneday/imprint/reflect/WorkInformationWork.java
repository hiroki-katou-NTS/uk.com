package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflect;
/**
 * 勤務先情報Work
 * @author phongtq
 *
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WorkInformationWork {
	/** 勤務場所 - 場所コード*/
	private WorkLocationCD locationCD;
	/** 職場ID*/
	private WorkplaceId workplaceId;
	/** 開始区分*/
	private StartAtr startAtr; 
}
