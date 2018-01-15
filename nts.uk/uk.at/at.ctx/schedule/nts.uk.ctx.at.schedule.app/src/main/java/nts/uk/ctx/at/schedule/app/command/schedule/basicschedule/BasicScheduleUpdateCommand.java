package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
public class BasicScheduleUpdateCommand {
	/**
	 * 画面項目「社員リスト」で選択した社員
	 */
	List<String> employeeIds;
	/**
	 * 画面項目「カレンダー」でチェックを付けた日
	 */
	List<GeneralDate> dates;
	/**
	 * 画面項目「確定解除設定」（確定 or 解除）
	 */
	int confirmedAtr;
	/**
	 *手修正解除区分　=　画面項目「手修正の色設定」　
	 */
	boolean checkedHandler;

}
