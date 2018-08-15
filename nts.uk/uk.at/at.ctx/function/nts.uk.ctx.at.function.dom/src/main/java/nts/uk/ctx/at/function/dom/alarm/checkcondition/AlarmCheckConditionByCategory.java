package nts.uk.ctx.at.function.dom.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;

import nts.arc.enums.EnumAdaptor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AlarmChkCondAgree36;

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
	
	// 36協定のアラームチェック条件
	private AlarmChkCondAgree36 alarmChkCondAgree36;

	public AlarmCheckConditionByCategory(String companyId, int category, String code, String name,
			AlarmCheckTargetCondition extractTargetCondition, List<String> listRoleId,
			ExtractionCondition extractionCondition, AlarmChkCondAgree36 alarmChkCondAgree36) {
		super();
		this.companyId = companyId;
		this.category = EnumAdaptor.valueOf(category, AlarmCategory.class);
		this.code = new AlarmCheckConditionCode(code);
		this.name = new AlarmCheckConditionName(name);
		this.extractTargetCondition = extractTargetCondition;
		this.listRoleId = listRoleId;
		this.extractionCondition = extractionCondition;
		this.alarmChkCondAgree36 = alarmChkCondAgree36;
	}

	public void changeState(String name, List<String> lstRoleId, AlarmCheckTargetCondition targetCondition,
			ExtractionCondition extractCondition) {
		this.name = new AlarmCheckConditionName(name);
		this.listRoleId = lstRoleId;
		this.extractTargetCondition.changeState(targetCondition);
		if (this.extractionCondition != null && extractCondition != null) {
			this.extractionCondition.changeState(extractCondition);
		}
	}

	public boolean isDaily() {
		return this.category == AlarmCategory.DAILY;
	}
	
	public boolean isMonthly() {
		return this.category == AlarmCategory.MONTHLY;
	}
	public boolean isMultipleMonth() {
		return this.category == AlarmCategory.MULTIPLE_MONTH;
	}
	
	public boolean is4Week4Day() {
		return this.category == AlarmCategory.SCHEDULE_4WEEK;
	}
	
}
