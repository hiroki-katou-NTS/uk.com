package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 暫定年休管理データ時間を追加（時間用）
 * @author yuri_tamakoshi
 */
public class AddTimeTempAnnualLeaveMngs {
	/**
	 * 暫定年休管理データ追加（時間用）
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param interimRemain 暫定年休管理データ
	 * @param usedNumber 月別実績の取得時間数
	 * @return 暫定年休管理データ
	 */
	public List<TempAnnualLeaveMngs> addTimeTempAnnualLeaveMngs(
			String employeeId,
			DatePeriod period,
			List<TempAnnualLeaveMngs> interimRemain, //AnnualLeaveGrantRemainingData
			LeaveUsedNumber usedNumber) {

		// 暫定年休管理データの使用数と月別実績の取得時間数の差を求める
		val diff = usedNumber.getMinutes().map(c -> c.v()).orElse(0)
				- interimRemain.stream().mapToInt(c -> c.getUsedNumber().getMinutes().map(x -> x.v()).orElse(0)).sum();

		// 期間．開始日から暫定年休管理データの取得日がかぶっていない日を求める
		val remainDates = interimRemain.stream().map(c -> c.getYmd()).collect(Collectors.toList());
		val nextDate = period.stream().filter(c -> !remainDates.contains(c)).findFirst();

		// 暫定年休管理データを追加する
		// ※残数処理では勤務種類、時間休暇種類は使用しないためダミーデータ
		nextDate.ifPresent(c -> {
			interimRemain.add(TempAnnualLeaveMngs.of(
						"",										// 	残数管理データID // 要確認
						employeeId, 						//		社員ID ←パラメータ「社員ID」
						c, 										//		対象日　←求めた対象日
						CreateAtr.RECORD, 			//		作成元区分　←実績
						RemainType.ANNUAL, 		//		残数種類　←年休
						RemainAtr.SINGLE,			//		残数分類　←単一
						new WorkTypeCode("1"), 	//		勤務種類 = 1
						new LeaveUsedNumber(0d, diff, null, null, null), 		//		年休使用数．時間　←求めた使用数
						Optional.ofNullable(DigestionHourlyTimeType.of(true, Optional.of(AppTimeType.OFFWORK)))
					));	//		時間休暇種類．時間消化区分 = 1
			});
		return interimRemain;
	}
}
