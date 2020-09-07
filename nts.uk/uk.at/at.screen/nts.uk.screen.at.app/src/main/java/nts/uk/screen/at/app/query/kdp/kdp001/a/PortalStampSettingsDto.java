package nts.uk.screen.at.app.query.kdp.kdp001.a;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.app.find.stamp.management.ButtonSettingsDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@Data
public class PortalStampSettingsDto {
	// 会社ID
	private String cid;

	// 打刻画面の表示設定
	private DisplaySettingsStampScreenDto displaySettingsStampScreen;

	// 打刻ボタン設定
	private List<ButtonSettingsDto> buttonSettings;

	// 打刻ボタンを抑制する
	private Integer suppressStampBtn;

	// トップメニューリンク利用する
	private Integer useTopMenuLink;

	public static PortalStampSettingsDto fromDomain(PortalStampSettings portalStampSettings) {

		List<ButtonSettingsDto> buttonSettings = portalStampSettings.getButtonSettings().stream()
				.map(x -> ButtonSettingsDto.fromDomain(x)).collect(Collectors.toList());

		return new PortalStampSettingsDto(portalStampSettings.getCid(),
				DisplaySettingsStampScreenDto.fromDomain(portalStampSettings.getDisplaySettingsStampScreen()),
				buttonSettings, portalStampSettings.isButtonEmphasisArt() ? 1 : 0, portalStampSettings.isToppageLinkArt() ? 1 : 0);
	}
}
