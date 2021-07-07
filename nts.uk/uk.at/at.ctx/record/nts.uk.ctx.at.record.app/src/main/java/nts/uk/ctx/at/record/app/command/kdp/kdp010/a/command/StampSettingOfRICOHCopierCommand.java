/**
 * 
 */
package nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command;

import java.util.List;
import java.util.stream.Collectors;

import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.app.command.stamp.management.StampPageLayoutCommand;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.PasswordForRICOH;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.StampSettingOfRICOHCopier;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhPV
 */
@NoArgsConstructor
@Setter
public class StampSettingOfRICOHCopierCommand{
	
	// ICカード登録パスワード
	private String icCardPassword;
	
	// ページレイアウト設定
	private List<StampPageLayoutCommand> pageLayoutSettings;
	
	// 打刻画面の表示設定
	private DisplaySettingsStampScreenCommand displaySettingsStampScreen;

	public StampSettingOfRICOHCopier toDomain() {
		return new StampSettingOfRICOHCopier(
				AppContexts.user().companyId(),
				new PasswordForRICOH(this.icCardPassword),
				this.pageLayoutSettings.stream().map(c->c.toDomain()).collect(Collectors.toList()), 
				this.displaySettingsStampScreen.toDomain()
		);
	}
	
}
