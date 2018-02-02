package nts.uk.ctx.at.auth.dom.employmentrole;

public enum ScheduleEmployeeRef {
	/**
	 * 全員(all)
	 */
	ALL(0,"全社員"),
	/**
	 * 所属部門全員（配下含む）(allIncludeSubor)
	 */
	ALL_INCLUDE_SUBOR(1,"所属部門全員（配下含む）"),
	/**
	 * 所属部門全員（配下含まず）(allExcludeSubor)
	 */
	ALL_EXCLUDE_SUBOR(2,"所属部門全員（配下含まず）"),
	/**
	 * 所属チーム全員(allTeams)
	 */
	ALL_TEAMS(3,"所属チーム全員"),
	/**
	 * 社員参照範囲と同じ(sameEmployeeRefRange)
	 */
	SAME_EMPLOYEE_REF_RANGE(4,"社員参照範囲と同じ");
	
	public int value;
	
	public String nameId;
	
	ScheduleEmployeeRef(int type,String nameId){
		this.value = type;
		this.nameId = nameId;
	}
	
}
