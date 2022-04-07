package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.service;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;

import java.util.List;
import java.util.Optional;

/**
 * 年休社員基本情報-年休付与テーブルが登録されている
 */
public class CheckNotExistAnnualLeaveTable {

	public static boolean check(Require require, String employeeId){
		return require.getTable(require.getBasicInfo(employeeId).get().getGrantRule().getGrantTableCode().toString()).isPresent();
	}

	public interface Require{
		Optional<AnnualLeaveEmpBasicInfo> getBasicInfo(String employeeId);

		Optional<GrantHdTblSet> getTable(String tableCode);
	}
}
