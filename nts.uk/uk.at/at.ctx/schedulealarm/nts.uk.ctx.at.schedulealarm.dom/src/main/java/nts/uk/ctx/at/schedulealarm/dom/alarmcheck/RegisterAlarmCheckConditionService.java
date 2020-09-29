package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import java.util.List;

import nts.arc.task.tran.AtomTask;

public class RegisterAlarmCheckConditionService {
	public static AtomTask register(Require require, AlarmCheckConditionCode code, List<MessageInfo>  msgLst) {
		AlarmCheckConditionSchedule alarmCheckCon = require.getAlarmCheckCond(code);
		msgLst.stream().forEach(c -> {
			alarmCheckCon.updateMessage(c.getSubCode(), c.getMessage());
		});
		
		return AtomTask.of(() -> {
			require.updateMessage(alarmCheckCon);
		});
	}
	
	public static interface Require {

		AlarmCheckConditionSchedule getAlarmCheckCond(AlarmCheckConditionCode code);	
		
		void updateMessage(AlarmCheckConditionSchedule alarm);
	}
}
