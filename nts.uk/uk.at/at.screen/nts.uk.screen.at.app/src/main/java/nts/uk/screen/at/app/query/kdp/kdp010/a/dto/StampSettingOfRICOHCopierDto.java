/**
 * 
 */
package nts.uk.screen.at.app.query.kdp.kdp010.a.dto;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.stamp.management.StampPageLayoutDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.StampSettingOfRICOHCopier;
import nts.uk.screen.at.app.query.kdp.kdp001.a.DisplaySettingsStampScreenDto;

/**
 * @author ThanhPV
 */
@NoArgsConstructor
@Getter
public class StampSettingOfRICOHCopierDto{
	
	// 会社ID
	private String cid;
	
	// ICカード登録パスワード
	private String icCardPassword;
	
	// ページレイアウト設定
	private List<StampPageLayoutDto> pageLayoutSettings;
	
	// 打刻画面の表示設定
	private DisplaySettingsStampScreenDto displaySettingsStampScreen;

	public StampSettingOfRICOHCopierDto(StampSettingOfRICOHCopier domain) {
		super();
		this.cid = domain.getCid();
		this.icCardPassword = domain.getIcCardPassword().v();
		this.pageLayoutSettings = domain.getPageLayoutSettings().stream().map(c -> StampPageLayoutDto.fromDomain(c)).collect(Collectors.toList());
		this.displaySettingsStampScreen = DisplaySettingsStampScreenDto.fromDomain(domain.getDisplaySettingsStampScreen());
	}
	
}
