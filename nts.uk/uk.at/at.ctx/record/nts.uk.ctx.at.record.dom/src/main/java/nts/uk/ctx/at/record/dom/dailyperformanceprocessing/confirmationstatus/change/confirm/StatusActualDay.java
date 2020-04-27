package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * @author ThanhNX 日の実績の状況を取得する
 */
@AllArgsConstructor
@Getter
public class StatusActualDay {

	/**
	 * 社員ID
	 */
	private String employeeId;

	/**
	 * 年月日
	 */
	private GeneralDate date;

	/**
	 * 所属職場ID
	 */
	private String wplId;

	/**
	 * 締めID
	 */
	private int closureId;
}
