package nts.uk.screen.at.app.query.kmp.kmp001;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * カードNOから抽出した社員情報を取得する
 * 
 * 【output】
 * カードNOの設定されている社員
　* ・List<打刻カード>
　* ・List<社員情報>
 * @author chungnt
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class EmployeeInformationFromCardNoDto {
	
	private String stampNumber;
	private String employeeCode;
	private String businessName;
	private String employeeId;
}
