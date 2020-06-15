/**
 * 
 */
package nts.uk.ctx.at.record.app.find.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author laitv 
 * 打刻会社一覧
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StampCompany {

	private String companyCode;      // 会社コード
	private String companyName;      // 会社名
	private String companyId;        // 会社ID
	private String contractCd;       // 契約コード
	private Boolean selectUseOfName; // 氏名選択利用
	private Boolean fingerAuthStamp; // 指認証打刻
	private Boolean icCardStamp;     // ICカード打刻 
}
