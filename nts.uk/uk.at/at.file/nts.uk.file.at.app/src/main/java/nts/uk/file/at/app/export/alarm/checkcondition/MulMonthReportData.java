package nts.uk.file.at.app.export.alarm.checkcondition;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MulMonthReportData {
	//1: コード
	private String code;
	//2: 名称
	private String name;
	//4: チェック対象範囲 雇用
	private String filterEmp;
	//5: チェック対象範囲 雇用対象
	private Optional<String> employees;
	//6: チェック対象範囲 分類
	private String filterClas;
	//7: チェック対象範囲 分類
	private Optional<String> classifications;
	//8: チェック対象範囲 職位
	private String filterJobTitles;
	//9: チェック対象範囲 職位対象
	private Optional<String> jobtitles;
	//10: チェック対象範囲 勤務種別
	private String filterWorkType;
	//11: チェック対象範囲 勤務種別対象
	private Optional<String> worktypeselections;
	//13: チェック条件 名称
	private Optional<String> nameCond;
	//14: チェック条件 チェック項目
	private Optional<String> checkItem;
	//15: チェック条件 チェック条件
	private Optional<String> targetAttendances;
	
	//condition
	private Optional<Integer> useAtrCond;
	private Optional<Integer> conditionAtrCond;
	private Optional<Integer> conditionTypeCond;
	private Optional<String> compareAtrCond;
	private Optional<Integer> startValueCond;
	private Optional<Integer> endValueCond;
	
	//condition AVG
	private Optional<Integer> useAtrAvg;
	private Optional<Integer> conditionAtrAvg;
	private Optional<Integer> conditionTypeAvg;
	private Optional<String> compareAtrAvg;
	private Optional<Integer> startValueAvg;
	private Optional<Integer> endValueAvg;
	
	//condition CONTINOUES
	private Optional<Integer> useAtrCont;
	//21: チェック条件 連続期間
	private Optional<Integer> continueMonth;
	private Optional<Integer> conditionAtrCont;
	private Optional<Integer> conditionTypeCont;
	private Optional<String> compareAtrCont;
	private Optional<Integer> startValueCont;
	private Optional<Integer> endValueCont;
	
	//condition COSP
	private Optional<Integer> useAtrCosp;
	//19: チェック条件 複合条件 グループ１
	private Optional<String> conditionCalculateCosp;
	//20 : チェック条件 計算式 回数
	private Optional<Integer> times;
	private Optional<Integer> conditionAtrCosp;
	private Optional<Integer> conditionTypeCosp;
	private Optional<String> compareAtrCosp;
	private Optional<Integer> startValueCosp;
	private Optional<Integer> endValueCosp;
	
	private Optional<String> insDate;
	//22: チェック条件 表示するメッセージ
	private Optional<String> message;
}
