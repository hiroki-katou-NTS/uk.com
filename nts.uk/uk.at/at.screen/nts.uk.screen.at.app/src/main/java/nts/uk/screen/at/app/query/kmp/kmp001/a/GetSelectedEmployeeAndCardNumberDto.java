package nts.uk.screen.at.app.query.kmp.kmp001.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;

/**
 * ・選択した社員(社員ID)の社員情報：個人社員基本情報
 * ・選択した社員(社員ID)の登録カードNOリスト：List<打刻カード番号>
 * @author chungnt
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSelectedEmployeeAndCardNumberDto {
	
	// 個人社員基本情報
	private IPersonInfoPub iPersonInfoPub;
	
	// List<打刻カード番号>
	private List<String> cardNumbers;
	
}
