package nts.uk.ctx.at.shared.dom.vacation.obligannleause;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;

/**
 * 年休付与情報Output
 * @author shuichi_ishida
 */
@Getter
public class AnnLeaGrantInfoOutput {

	/** 社員ID */
	private String employeeId;
	/** 付与残数 */
	private List<AnnualLeaveGrantRemainingData> grantRemainList;
	
	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 */
	public AnnLeaGrantInfoOutput(String employeeId) {
		this.employeeId = employeeId;
		this.grantRemainList = new ArrayList<>();
	}
}
