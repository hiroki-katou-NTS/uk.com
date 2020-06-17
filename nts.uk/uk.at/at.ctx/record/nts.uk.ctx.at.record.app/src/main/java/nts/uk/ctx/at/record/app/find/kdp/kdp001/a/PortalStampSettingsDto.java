package nts.uk.ctx.at.record.app.find.kdp.kdp001.a;

import java.util.List;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.record.app.find.stamp.management.ButtonSettingsDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
public class PortalStampSettingsDto {
	// 会社ID
	private String cid;

	// 打刻画面の表示設定
	private DisplaySettingsStampScreenDto displaySettingsStampScreen;

	// 打刻ボタン設定
	private List<ButtonSettingsDto> buttonSettings;

	// 打刻ボタンを抑制する
	private Boolean suppressStampBtn;

	// トップメニューリンク利用する
	private Boolean useTopMenuLink;

	public static PortalStampSettingsDto fromDomain(PortalStampSettings portalStampSettings) {
		// TODO Auto-generated method stub
		return null;
	}
}
