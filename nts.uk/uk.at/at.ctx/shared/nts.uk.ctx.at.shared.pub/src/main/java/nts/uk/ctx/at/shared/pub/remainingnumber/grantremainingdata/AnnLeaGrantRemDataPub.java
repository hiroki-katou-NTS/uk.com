package nts.uk.ctx.at.shared.pub.remainingnumber.grantremainingdata;

import java.util.List;

public interface AnnLeaGrantRemDataPub {
	/**
	 * RequestList281
	 * 期限が残っている年休付与残数データを取得する
	 * @param employeeID
	 * @return
	 */
	public List<AnnualLeaveGrantRemainDataExport> getAnnualLeaveGrantRemainingData(String employeeID);
}
