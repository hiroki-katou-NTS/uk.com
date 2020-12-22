package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import java.util.List;

import nts.arc.task.tran.AtomTask;
/**
 * アラームチェック条件を登録する
 * @author lan_lt
 *
 */
public class RegisterAlarmCheckConditionService {
	/**
	 * [1] 登録する
	 * @param require
	 * @param code コード
	 * @param msgLst サブコードのメッセージリスト
	 * @return
	 */
	public static AtomTask register(Require require, AlarmCheckConditionScheduleCode code, List<MessageInfo>  msgLst) {
		AlarmCheckConditionSchedule alarmCheckCon = require.getAlarmCheckCond(code);
		msgLst.stream().forEach(c -> {
			alarmCheckCon.updateMessage(c.getSubCode(), c.getMessage());
		});
		
		return AtomTask.of(() -> {
			require.updateMessage(alarmCheckCon);
		});
	}
	
	public static interface Require {
		/**
		 * [R-1]  コードを指定勤務予定のアラームチェック条件を取得する
		 * @param code
		 * @return
		 */
		AlarmCheckConditionSchedule getAlarmCheckCond(AlarmCheckConditionScheduleCode code);	
		
		/**
		 * [R-2] 条件を変更する
		 * @param alarm
		 */
		void updateMessage(AlarmCheckConditionSchedule alarm);
	}
}
