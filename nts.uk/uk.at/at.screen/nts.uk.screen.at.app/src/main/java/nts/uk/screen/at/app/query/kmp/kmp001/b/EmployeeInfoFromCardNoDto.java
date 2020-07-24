package nts.uk.screen.at.app.query.kmp.kmp001.b;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class EmployeeInfoFromCardNoDto {
	
	private List<StampCardDto> stampCards;
	private List<EmployeeInfoDto> employeeInfo;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class StampCardDto {
	private String employeeId;
	private String stampNumber;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class EmployeeInfoDto {
	private String employeeId;
	private String employeeCode;
	private String businessName;
}
