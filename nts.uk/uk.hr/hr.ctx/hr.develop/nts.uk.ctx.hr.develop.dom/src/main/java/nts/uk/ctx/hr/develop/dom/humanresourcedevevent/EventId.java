package nts.uk.ctx.hr.develop.dom.humanresourcedevevent;

/**
 * 
 * 人材育成イベント
 */
public enum EventId {
	/* 経営計画立案 */
	BUSINESS_PLANNING (0),
	
	/* 要員計画立案  */
	PERSONNEL_PLANNING(1),
	
	/* 教育計画立案  */
	EDUCATION_PLANNING(2),
	
	/* 教育計画見直し  */
	EDUCATION_PLAN_REVIEW(5),
	
	/* 育成結果分析 */
	TRAINING_RESULT_ANALYSIS(3),
	
	/* 施策検討  */
	EXAMINATION_OF_MEASURES(4),
	
	/* 配置の決定  */
	PLACEMENT_DECISION(6),
	
	/* 採用計画立案  */
	RECRUITMENT_PLANNING(7),
	
	/* 選考・内定  */
	SELECTION_INFORMAL(8),
	
	/* 内定者フォロー  */
	APPOINTED_PERSON_FOLLOW(9),
	
	/* 新入社員教育  */
	NEW_EMPLOYEE_TRAINING(10),
	
	/* 配属の決定  */
	ASSIGNMENT_DECISION(11),
	
	/* 人事発令 */
	PERSONNEL_ORDER(12),
	
	/* キャリア管理  */
	CAREER_MANAGEMENT(13),
	
	/* 教育  */
	EDUCATION(14),
	
	/* 昇格試験  */
	PROMOTION_TEST(15),
	
	/* 専門資格の取得  */
	ACQUISITION_OF_PROFESSIONAL_QUALIFICATIONS(16),
	
	/* 社内アンケート調査  */
	INTERNAL_QUESTIONAIRE_SURVEY(17),
	
	/* 業務状況確認  */
	BUSINESS_STATUS_CHECK(18),
	
	/* フォロー  */
	FOLLOW(19),
	
	/* 人事評価  */
	PERSONNEL_ASSESSMENT(20),
	
	/* 評価の最終調整  */
	FINAL_ADJUSTMENT_OF_EVALUATION(21),
	
	/* 昇進の決定  */
	ADVANCE_DECISION(22),
	
	/* 昇格の決定  */
	PROMOTION_DECISION(23),
	
	/* 届出  */
	NOTIFICATION(100);
	
	public final int value;
	
	private EventId(int type){
		this.value = type;
	}
}
