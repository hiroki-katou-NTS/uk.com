package nts.uk.ctx.at.shared.dom.remainingnumber.work;


import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
/**
 * 代休振替情報
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DayoffTranferInfor {
	/**	就業時間帯コード */
	private Optional<String> workTimeCode;
	/**	振替休出時間 */
	private Optional<TranferTimeInfor> tranferBreakTime;
	/**	振替残業時間 */
	private Optional<TranferTimeInfor> tranferOverTime;
	/**
	 * 振替時間情報を取得する
	 * @return
	 */
	public TranferTimeInfor getTranferTimeInfor() {
		TranferTimeInfor outputData = new TranferTimeInfor(CreateAtr.SCHEDULE, 0, Optional.of((double) 0));		
		//振替休出時間をチェックする
		if(this.getTranferBreakTime().isPresent()) {
			//アルゴリズム「振替時間情報を更新する」を実行する
			outputData = this.changeTranferBreakTime(this.getTranferBreakTime().get(), outputData);
		}
		
		if(this.getTranferOverTime().isPresent()) {
			outputData = this.changeTranferBreakTime(this.getTranferOverTime().get(), outputData);
		}
		return outputData;
	}
	/**
	 * 振替時間情報を更新する
	 * @param beforeData 更新元：振替休出情報
	 * @param afterData 更新先：振替時間情報
	 * @return
	 */
	private TranferTimeInfor changeTranferBreakTime(TranferTimeInfor beforeData, TranferTimeInfor afterData) {
		//作成元区分をチェックする
		if(beforeData.getCreateAtr().value < afterData.getCreateAtr().value) {
			afterData.setCreateAtr(beforeData.getCreateAtr());
		}
		//振替時間を設定する
		afterData.setTranferTime(afterData.getTranferTime() + (beforeData.getTranferTime() == null ? 0 : beforeData.getTranferTime()));
		//日数をチェックする
		if(beforeData.getDays().isPresent()) {
			double tmp = afterData.getDays().get() + (beforeData.getDays().isPresent() ? beforeData.getDays().get() : 0);
			afterData.setDays(Optional.of(tmp));
		}
		return afterData;
	}

}
