package nts.uk.ctx.at.function.dom.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;

import nts.arc.enums.EnumAdaptor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;

/**
 * 
 * @author HungTT - カテゴリ別アラームチェック条件
 *
 */

@Getter
public class AlarmCheckConditionByCategory extends AggregateRoot {

	// アラームリストの結果が閲覧できるロール一覧
	private List<String> listRoleId = new ArrayList<>();

	// カテゴリ
	private AlarmCategory category;

	// コード
	private AlarmCheckConditionCode code;

	// 会社ID
	private String companyId;

	// 名称
	private AlarmCheckConditionName name;

	// 抽出対象者の条件
	private AlarmCheckTargetCondition extractTargetCondition;

	// 抽出条件
	private ExtractionCondition extractionCondition;

	public AlarmCheckConditionByCategory(String companyId, int category, String code, String name,
			AlarmCheckTargetCondition extractTargetCondition, List<String> listRoleId,
			ExtractionCondition extractionCondition) {
		super();
		this.companyId = companyId;
		this.category = EnumAdaptor.valueOf(category, AlarmCategory.class);
		this.code = new AlarmCheckConditionCode(code);
		this.name = new AlarmCheckConditionName(name);
		this.extractTargetCondition = extractTargetCondition;
		this.listRoleId = listRoleId;
		this.extractionCondition = extractionCondition;
	}

	public void changeState(String name, List<String> lstRoleId, AlarmCheckTargetCondition targetCondition,
			ExtractionCondition extractCondition) {
		this.name = new AlarmCheckConditionName(name);
		this.listRoleId = lstRoleId;
		this.extractTargetCondition.changeState(targetCondition);
		this.extractionCondition.changeState(extractCondition);
	}

}
