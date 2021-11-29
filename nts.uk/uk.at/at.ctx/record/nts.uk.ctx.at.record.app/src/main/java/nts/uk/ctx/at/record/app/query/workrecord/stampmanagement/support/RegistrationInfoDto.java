package nts.uk.ctx.at.record.app.query.workrecord.stampmanagement.support;

import lombok.Getter;
import lombok.Setter;

/**
*
* @author : NWS_namnv
*/
@Setter
@Getter
public class RegistrationInfoDto {
	
	// 会社情報.会社名
	private String companyName;
	
	// 職場情報一覧.職場表示名称
	private String workplaceName;

}
