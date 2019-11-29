package nts.uk.ctx.hr.shared.dom.referEvaluationItem;

/**
 * @author thanhpv
 * 評価項目
 */
public enum EvaluationItem {

	PERSONNEL_ASSESSMENT (0,"人事評価"),
	
	HEALTH_CONDITION (0,"健康状態"),
	
	STRESS_CHECK (1, "ストレスチェック");
	
	public int value;
	
	public String name;

	EvaluationItem(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
}
