package nts.uk.screen.at.app.query.kdp.kdp004.a;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.stamp.management.StampPageLayoutDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetCommunal;
import nts.uk.screen.at.app.query.kdp.kdp001.a.DisplaySettingsStampScreenDto;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StampSetCommunalDto {
	// 会社ID
	private String cid;

	// 打刻画面の表示設定r
	private DisplaySettingsStampScreenDto displaySetStampScreen;

	// ページレイアウト設定
	private List<StampPageLayoutDto> lstStampPageLayout;

	// 氏名選択利用する
	private boolean nameSelectArt;

	// パスワード入力が必須か
	private boolean passwordRequiredArt;

	// 社員コード認証利用するか
	private boolean employeeAuthcUseArt;

	// 指認証失敗回数
	private Integer authcFailCnt;

	public static StampSetCommunalDto fromDomain(StampSetCommunal domain) {
		List<StampPageLayoutDto> lstStampPageLayout = domain
															.getLstStampPageLayout()
															.stream()
															.map(pageLayout -> StampPageLayoutDto.fromDomain(pageLayout))
															.collect(Collectors.toList());
		
	return	StampSetCommunalDto.builder()
				.cid(domain.getCid())
				.displaySetStampScreen(DisplaySettingsStampScreenDto.fromDomain(domain.getDisplaySetStampScreen()))
				.lstStampPageLayout(lstStampPageLayout)
				.nameSelectArt(domain.isNameSelectArt())
				.passwordRequiredArt(domain.isPasswordRequiredArt())
				.employeeAuthcUseArt(domain.isEmployeeAuthcUseArt())
				.authcFailCnt(domain.getAuthcFailCnt().isPresent() ? domain.getAuthcFailCnt().get().v() : null)
				.build();
	}
}
