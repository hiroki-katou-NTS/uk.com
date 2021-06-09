package nts.uk.screen.at.app.query.kdp.kdps01.a;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.stamp.management.StampPageLayoutDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.settingforsmartphone.SettingsSmartphoneStamp;
import nts.uk.screen.at.app.query.kdp.kdp001.a.DisplaySettingsStampScreenDto;

/**
 * 
 * @author sonnlb
 * 
 *         スマホ打刻の打刻設定
 */
@AllArgsConstructor
@Data
public class SettingsSmartphoneStampDto {
	// 会社ID
	private final String cid;

	// 打刻画面の表示設定
	private DisplaySettingsStampScreenDto displaySettingsStampScreen;

	// ページレイアウト設定
	@Setter
	private List<StampPageLayoutDto> pageLayoutSettings;

	// 打刻ボタンを抑制する
	private boolean buttonEmphasisArt;
	
	// 位置情報を利用する
	private boolean locationInfoUse;

	public static SettingsSmartphoneStampDto fromDomain(SettingsSmartphoneStamp domain) {

		return new SettingsSmartphoneStampDto(
				domain.getCid(),
				DisplaySettingsStampScreenDto.fromDomain(domain.getDisplaySettingsStampScreen()), 
				domain.getPageLayoutSettings().stream().map(setting-> StampPageLayoutDto.fromDomain(setting)).collect(Collectors.toList()),
				domain.isButtonEmphasisArt(), domain.getLocationInfoUse().isUse());
	}
}
