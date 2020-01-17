package nts.uk.ctx.hr.shared.dom.referEvaluationItem;

/**
 * @author thanhpv
 * 評価項目
 */
public enum EvaluationItem {

	PERSONNEL_ASSESSMENT (0,"人事評価"),
	
	HEALTH_CONDITION (1,"健康状態"),
	
	STRESS_CHECK (2, "ストレスチェック");
	
	public int value;
	
	public String nameId;

	EvaluationItem(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
