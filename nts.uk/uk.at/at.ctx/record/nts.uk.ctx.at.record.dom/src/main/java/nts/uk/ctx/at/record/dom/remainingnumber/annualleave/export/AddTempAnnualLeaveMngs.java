package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpDailyLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 暫定年休管理データ追加（日数用）
 * @author yuri_tamakoshi
 */
public class AddTempAnnualLeaveMngs {

	/**
	 * 暫定年休管理データ追加（日数用）
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param interimRemain 暫定年休管理データ
	 * @param usedNumber 月別実績の取得数
	 * @return TempAnnualLeaveMngs 暫定年休管理データ
	 */
	public List<TempAnnualLeaveMngs> addTempAnnualLeaveMngs(
			String employeeId,
			DatePeriod period,
			List<TempAnnualLeaveMngs> interimRemain,
			LeaveUsedNumber usedNumber) {

		// 取得数の差がなくなるまでループ
		while(true){

			// 計算用使用数
			double calcUsedNumber;

			// 暫定年休管理データと月別実績の取得数の差を求める
			double diff = usedNumber.getDays().v()
					- interimRemain.stream().mapToDouble(c  -> c.getUsedNumber().getUsedDayNumber().map(mapper->mapper.v()).orElse(0.0)).sum(); //要確認

			// 取得数の差>＝1の場合・・・使用数を1とする
			// 取得数の差 = 0.5の場合・・・使用数を0.5とする
			if(diff >= 1) {
				calcUsedNumber = 1.0;
			} else if(diff == 0.5) {
				calcUsedNumber = 0.5;
			}else {
				break;
			}

			// 期間．開始日から暫定年休管理データの取得日がかぶっていない日を求める
			val remainDates = interimRemain.stream().map(c -> c.getYmd()).collect(Collectors.toList());
			val nextDate = period.stream().filter(c -> !remainDates.contains(c)).findFirst();

			// 暫定年休管理データに追加
			//	※残数処理では勤務種類は使用しないためダミーデータ
			nextDate.ifPresent(c -> {
				interimRemain.add(TempAnnualLeaveMngs.of(
						"",									// 	残数管理データID
						employeeId, 						//		社員ID ←パラメータ「社員ID」
						c, 										//		対象日　←求めた対象日
						CreateAtr.RECORD, 			//		作成元区分　←実績
						RemainType.ANNUAL, 		//		残数種類　←年休
						RemainAtr.SINGLE,			//		残数分類　←単一
						new WorkTypeCode("1"), 	//		勤務種類 = 1
						new TempAnnualLeaveUsedNumber(
								Optional.of(new TmpDailyLeaveUsedDayNumber(calcUsedNumber)), Optional.empty()), 	//		年休使用数．日数　←求めた使用数
						Optional.empty()));	//		時間休暇種類．時間消化区分 = 1
				});
		}
		return interimRemain;
	}
}
