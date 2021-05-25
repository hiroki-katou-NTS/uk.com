package nts.uk.ctx.at.record.pub.remainnumber.specialleave.export;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveGrantRemainingDataExport;

/**
 * 特別休暇付与残数データ
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class SpecialLeaveGrantRemainingDataExport extends LeaveGrantRemainingDataExport{

	/**
	 * 特別休暇コード
	 */
	protected int specialLeaveCode;

	/**
	 * コンストラクタ
	 * @param c
	 * @param specialLeaveCodeIn
	 */
	public SpecialLeaveGrantRemainingDataExport(LeaveGrantRemainingDataExport c, int specialLeaveCodeIn) {
		super(c);
		specialLeaveCode = specialLeaveCodeIn;
	}

}
