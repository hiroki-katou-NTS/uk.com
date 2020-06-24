package nts.uk.screen.at.app.query.kmp.kmp001.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * output of 初期起動を行う:
 * 打刻カード桁数：打刻カード桁数
 * 打刻カード編集方法：打刻カード編集方法
 * @author chungnt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PerformInitialStartupDto {
	
	//打刻カード桁数
	private int stampCardDigitNumber;
	
	//打刻カード編集方法
	private int  stampCardEditMethod;
	
}
