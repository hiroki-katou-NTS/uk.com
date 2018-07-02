package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.sysfixedcheckcondition;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.fixedcheckitem.ValueExtractAlarmWRPubExport;

public interface SysFixedCheckConMonPub {
		//1:月次未確認
		public Optional<ValueExtractAlarmWRPubExport>  checkAgreement(String employeeID,int yearMonth);
		//5:36協定のチェック
		public Optional<ValueExtractAlarmWRPubExport>  checkMonthlyUnconfirmed(String employeeID,int yearMonth);
}
