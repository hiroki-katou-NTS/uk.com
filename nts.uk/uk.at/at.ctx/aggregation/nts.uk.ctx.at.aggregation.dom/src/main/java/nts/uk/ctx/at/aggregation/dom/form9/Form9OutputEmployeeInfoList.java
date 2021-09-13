package nts.uk.ctx.at.aggregation.dom.form9;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 様式９の出力社員情報リスト
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.様式９の出力社員情報を取得する.様式９の出力社員情報リスト
 * @author lan_lt
 *
 */
@Setter
@Getter
@AllArgsConstructor
public class Form9OutputEmployeeInfoList {
	
	/** 社員情報リスト **/
	private List<Form9OutputEmployeeInfo> employeeInfoList;
	
	/**
	 * 社員IDリストを取得する
	 * 
	 */
	public List<String> getEmployeeIdList() {
		return this.employeeInfoList.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
	}
}
