package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.test.repository;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.HalfdayAnnualLeaveMax;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.TimeAnnualLeaveMax;

/**
 * 年休上限データ
 * @author masaaki_jinno
 *
 */
public class TestAnnualLeaveMaxData {
	
	/**
	 * 「AnnualLeaveMaxData」クラスのオブジェクトを生成して返す。
	 * @param employeeId
	 * @param companyId
	 * @param halfdayAnnualLeaveMax
	 * @param timeAnnualLeaveMax
	 * @return
	 */
	static public AnnualLeaveMaxData createAnnualLeaveMaxData(
			String employeeId, // 社員ID
			String companyId,  // 会社ID
			Optional<HalfdayAnnualLeaveMax> halfdayAnnualLeaveMax, // 半日年休上限
			Optional<TimeAnnualLeaveMax> timeAnnualLeaveMax // 時間年休上限
		){
		
		AnnualLeaveMaxData a = new AnnualLeaveMaxData(
				employeeId, companyId, halfdayAnnualLeaveMax, timeAnnualLeaveMax);
	
		return a;
	}
}
