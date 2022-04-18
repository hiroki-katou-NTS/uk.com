package nts.uk.file.at.infra.employmentinfoterminal.infoterminal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeclareSetExportDto {
	/** 会社ID */
	private String companyId;
	/** 申告利用区分 */
	private String usageAtr;
	/** 枠設定 */
	private String frameSet;
	/** 深夜時間自動計算 */
	private String midnightAutoCalc;
	/** 早出残業 */
	private String earlyOvertime;
	/** 早出残業深夜 */
	private String earlyOvertimeMn;
	/** 普通残業 */
	private String overtime;
	/** 普通残業深夜 */
	private String overtimeMn;
	/** 法定内 */
	private String statutory;
	/** 法定外 */
	private String notStatutory;
	/** 法定外祝日 */
	private String notStatHoliday;
	/** 法定内 */
	private String statutoryMn;
	/** 法定外 */
	private String notStatutoryMn;
	/** 法定外祝日 */
	private String notStatHolidayMn;
}
