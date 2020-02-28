package nts.uk.ctx.hr.develop.dom.interview;
/**  面談区分 **/
public enum InterviewCategory {
	
	/** 採用管理 **/
	 RECRUITMENT_MANAGEMENT (0),
	/** 退職 **/
	 RETIREMENT (1),
	/** 定年退職 **/
	 RETIREMENT_AGE (2),
	/** 異動 **/
	 CHANGE (3),
	/** 出向 **/
	 SECONDED (4),
	/** 転籍 **/
	 TRANSFER (5),
	/** 契約更新 **/
	CONTRACT_RENEWAL (6),
	/** 教育管理 **/
	EDUCATION_MANAGEMENT (7),
	/** 人事評価 **/
	PERSON_ASSESSMENT (8),
	/** キャリア管理**/
	CAREER_MANAGEMENT(9),
	/** 日常業務 **/
	DAILY_WORK (10),
	/** 休職復帰**/
	RETURN_TO_LEAVE (11),
	/** 出向復帰 **/
	RETURN_TO_WORK (12);
	 
	 public final int value;
	
	private InterviewCategory(int type){
		this.value = type;
	}
	
}





































