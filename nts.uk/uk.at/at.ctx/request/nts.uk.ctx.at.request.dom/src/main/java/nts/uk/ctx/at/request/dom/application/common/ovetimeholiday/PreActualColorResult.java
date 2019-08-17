package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreActualColorResult {
	/**
	 * 事前申請状態
	 */
	public boolean beforeAppStatus;
	
	/**
	 * 実績状態
	 */
	public Integer actualStatus;
	
	/**
	 * List<勤怠種類, 枠NO, 計算入力差異, 事前申請超過, 実績超過>
	 */
	public List<OvertimeColorCheck> resultLst;
	
}
