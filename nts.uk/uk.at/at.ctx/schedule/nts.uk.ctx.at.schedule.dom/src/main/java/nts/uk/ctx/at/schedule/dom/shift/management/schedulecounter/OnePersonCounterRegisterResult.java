package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import java.util.List;

import lombok.Value;
import nts.arc.task.tran.AtomTask;

@Value
public class OnePersonCounterRegisterResult {
	
	private AtomTask atomTask;
	
	private List<OnePersonCounterCategory> notDetailSettingList;

}
