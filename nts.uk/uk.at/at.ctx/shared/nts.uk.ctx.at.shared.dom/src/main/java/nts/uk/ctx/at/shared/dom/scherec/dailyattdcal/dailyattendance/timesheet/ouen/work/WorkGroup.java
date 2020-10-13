package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;

@Getter
/** 作業グループ */
public class WorkGroup implements DomainObject {

	/** 作業CD1: int */
	private WorkCode workCD1;
	
	/** 作業CD2: int */
	private Optional<WorkCode> workCD2;
	
	/** 作業CD3: int */
	private Optional<WorkCode> workCD3;
	
	/** 作業CD4: int */
	private Optional<WorkCode> workCD4;
	
	/** 作業CD5: int */
	private Optional<WorkCode> workCD5;

	private WorkGroup(WorkCode workCD1, Optional<WorkCode> workCD2, 
			Optional<WorkCode> workCD3, Optional<WorkCode> workCD4, Optional<WorkCode> workCD5) {
		super();
		this.workCD1 = workCD1;
		this.workCD2 = workCD2;
		this.workCD3 = workCD3;
		this.workCD4 = workCD4;
		this.workCD5 = workCD5;
	}
	
	public static WorkGroup create(WorkCode workCD1, Optional<WorkCode> workCD2, 
			Optional<WorkCode> workCD3, Optional<WorkCode> workCD4, Optional<WorkCode> workCD5){
		
		return new WorkGroup(workCD1, workCD2, workCD3, workCD4, workCD5);
	}
	
	public static WorkGroup create(String workCd1, String workCd2, String workCd3, String workCd4, String workCd5){
		
		return new WorkGroup(
				new WorkCode(workCd1), 
				Optional.of(workCode(workCd2)), 
				Optional.of(workCode(workCd3)),
				Optional.of(workCode(workCd4)),
				Optional.of(workCode(workCd5)));
	}

	private static WorkCode workCode(String workCd2) {
		return workCd2 == null ? null : new WorkCode(workCd2);
	}
	
	public Integer getWorkCount() {
		int count = 1;
		if(this.workCD2.isPresent())
			count++;
		
		if(this.workCD3.isPresent())
			count++;
		
		if(this.workCD4.isPresent())
			count++;
		
		if(this.workCD5.isPresent())
			count++;
		
		return count;
	}
	
	/**
	 * 指定された作業枠までの作業グループに作り直す
	 * @param workFrame
	 * @return
	 */
	public WorkGroup reCreateUpToWorkFrame(Integer workFrame) {
		Map<Integer, Optional<WorkCode>> newGroup = new HashMap<>();
		for(int i = 0; i < workFrame; i++) {
			newGroup.put(i, getWorkCode(i));
		}
		return WorkGroup.create(this.workCD1, newGroup.get(2), newGroup.get(3), newGroup.get(4), newGroup.get(5));
	}
	
	/**
	 * 指定された作業枠の作業CDを取得する
	 * @param workFrame
	 * @return
	 */
	public Optional<WorkCode> getWorkCode(Integer workFrame) {
		if(workFrame == 1)
			return Optional.of(this.workCD1);
		
		if(workFrame == 2)
			return this.workCD2;
		
		if(workFrame == 3)
			return this.workCD3;
		
		if(workFrame == 4)
			return this.workCD4;
		
		if(workFrame == 5)
			return this.workCD5;
		
		return Optional.empty();
	}
}
