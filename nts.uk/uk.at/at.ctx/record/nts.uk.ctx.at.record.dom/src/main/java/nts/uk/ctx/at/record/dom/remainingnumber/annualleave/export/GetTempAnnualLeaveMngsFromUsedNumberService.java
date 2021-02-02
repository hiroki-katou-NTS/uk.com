package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;

/**
 * 使用数を暫定年休管理データに変換する
 */
@Getter
@Setter
public class GetTempAnnualLeaveMngsFromUsedNumberService {

	/**
	 * 使用数を暫定年休管理データに変換する
	 * @param employeeId 社員ID
	 * @param usedNumber 使用数
	 * @return 暫定年休管理データ(List)
	 */
	public static List<TempAnnualLeaveMngs> tempAnnualLeaveMngs(
			String employeeId,
			LeaveUsedNumber usedNumber) {

		// 暫定年休管理データ
		List<TempAnnualLeaveMngs> interimRemain= new ArrayList<>();

		// 暫定年休管理データ追加（日数用）
		AddTempAnnualLeaveMngs addTempMngs = new AddTempAnnualLeaveMngs();
		// 暫定年休管理データ追加（時間用）
		AddTimeTempAnnualLeaveMngs addTimeTempMngs = new AddTimeTempAnnualLeaveMngs();

		// 使用日数を暫定年休管理データに変換する
		interimRemain = addTempMngs.addTempAnnualLeaveMngs(
				employeeId,
				new DatePeriod(GeneralDate.ymd(2000, 1, 1), GeneralDate.ymd(9999, 12, 31)),
				interimRemain,
				usedNumber);

		// 使用時間を暫定年休管理データに変換する
		interimRemain = addTimeTempMngs.addTimeTempAnnualLeaveMngs(
				employeeId,
				new DatePeriod(GeneralDate.ymd(2000, 1, 1), GeneralDate.ymd(9999, 12, 31)),
				interimRemain,
				usedNumber);

		return interimRemain;
	}
}
