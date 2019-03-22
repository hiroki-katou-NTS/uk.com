package nts.uk.ctx.at.shared.dom.vacation.obligannleause;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;

/**
 * 年休使用義務
 * @author shuichi_ishida
 */
@Getter
public class ObligedAnnualLeaveUse {

	/** 社員ID */
	private String employeeId;
	/** 義務日数 */
	private AnnualLeaveUsedDayNumber obligDays;	
	/** 付与残数 */
	private List<AnnualLeaveGrantRemainingData> grantRemainList;
	
	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 */
	private ObligedAnnualLeaveUse(String employeeId) {
		this.employeeId = employeeId;
		this.obligDays = new AnnualLeaveUsedDayNumber(0.0);
		this.grantRemainList = new ArrayList<>();
	}
	
	/**
	 * 年休使用義務を作成
	 * @param employeeId 社員ID
	 * @param obligDays 年休使用義務日数
	 * @param grantRemainList 年休付与残数データリスト
	 * @return 年休使用義務
	 */
	public static ObligedAnnualLeaveUse create(
			String employeeId,
			AnnualLeaveUsedDayNumber obligDays,
			List<AnnualLeaveGrantRemainingData> grantRemainList){
		
		ObligedAnnualLeaveUse domain = new ObligedAnnualLeaveUse(employeeId);
		if (obligDays != null) domain.obligDays = obligDays;
		domain.grantRemainList.addAll(grantRemainList);
		return domain;
	}
}
