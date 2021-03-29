package nts.uk.screen.at.app.query.kdp.kdp004.a;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampSettingDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;

/**
 * 
 * @author sonnlb
 *
 */
@Data
public class StampSetCommunalDto extends StampSettingDto {
	
	
	public StampSetCommunalDto(StampSetCommunal domain) {
		super(domain);
		this.cid = domain.getCid();
		this.nameSelectArt = domain.isNameSelectArt();
		this.passwordRequiredArt = domain.isPasswordRequiredArt();
		this.employeeAuthcUseArt = domain.isEmployeeAuthcUseArt();
		this.authcFailCnt = domain.getAuthcFailCnt().map(m -> m.v()).orElse(null);
	}

	// 会社ID
	private String cid;

	// 氏名選択利用する
	private boolean nameSelectArt;

	// パスワード入力が必須か
	private boolean passwordRequiredArt;

	// 社員コード認証利用するか
	private boolean employeeAuthcUseArt;

	// 指認証失敗回数
	private Integer authcFailCnt;
}
