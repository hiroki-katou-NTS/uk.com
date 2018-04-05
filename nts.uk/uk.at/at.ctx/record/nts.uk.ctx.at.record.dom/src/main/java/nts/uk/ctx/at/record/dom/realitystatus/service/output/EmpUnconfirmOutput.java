package nts.uk.ctx.at.record.dom.realitystatus.service.output;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@NoArgsConstructor
public class EmpUnconfirmOutput {
	/**
	 * 社員ID
	 */
	private String sId;
	/**
	 * 社員名
	 */
	private String sName;
	/**
	 * メールアドレス
	 */
	private String email;
	/**
	 * 期間
	 */
	private GeneralDate startDate;
	/**
	 * 期間
	 */
	private GeneralDate endDate;
}
