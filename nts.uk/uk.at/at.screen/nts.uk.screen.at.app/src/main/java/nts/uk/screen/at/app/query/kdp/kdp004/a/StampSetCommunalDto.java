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
	private List<StampPageLayoutDto> pageLayouts;

	// 氏名選択利用する
	private boolean nameSelectArt;

	// パスワード入力が必須か
	private boolean passwordRequiredArt;

	// 社員コード認証利用するか
	private boolean employeeAuthcUseArt;

	// 指認証失敗回数
	private Integer authcFailCnt;
	
	private boolean buttonEmphasisArt;
	private Integer correctionInterval;
	private String textColor;
	private String backGroundColor;
	private Integer resultDisplayTime;

	public static StampSetCommunalDto fromDomain(StampSetCommunal domain) {
		List<StampPageLayoutDto> lstStampPageLayout = domain
															.getLstStampPageLayout()
															.stream()
															.map(pageLayout -> StampPageLayoutDto.fromDomain(pageLayout))
															.collect(Collectors.toList());
		DisplaySettingsStampScreenDto displaySetStampScreen =	DisplaySettingsStampScreenDto.fromDomain(domain.getDisplaySetStampScreen());
	return	StampSetCommunalDto.builder()
				.cid(domain.getCid())
				.displaySetStampScreen(displaySetStampScreen)
				.pageLayouts(lstStampPageLayout)
				.nameSelectArt(domain.isNameSelectArt())
				.passwordRequiredArt(domain.isPasswordRequiredArt())
				.employeeAuthcUseArt(domain.isEmployeeAuthcUseArt())
				.authcFailCnt(domain.getAuthcFailCnt().isPresent() ? domain.getAuthcFailCnt().get().v() : null)
				.correctionInterval(displaySetStampScreen.getServerCorrectionInterval())
				.textColor(displaySetStampScreen.getSettingDateTimeColor().getTextColor())
				.backGroundColor(displaySetStampScreen.getSettingDateTimeColor().getBackgroundColor())
				.resultDisplayTime(displaySetStampScreen.getResultDisplayTime())
				.build();
	}
}
