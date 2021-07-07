/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * AR: ポータルの打刻設定
 * path : UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の前準備.ポータルの打刻設定
 * @author laitv
 *
 */

@Getter
@AllArgsConstructor
public class PortalStampSettings implements DomainAggregate{

	// 会社ID
	private final String cid;
	
	// 打刻画面の表示設定
	private DisplaySettingsStampScreen displaySettingsStampScreen;
	
	// 打刻ボタン設定
	private List<ButtonSettings> buttonSettings;
	
	// 打刻ボタンを抑制する
	private boolean buttonEmphasisArt;
	
	// トップメニューリンク利用する
	private boolean toppageLinkArt;
	
	//外出打刻を利用する
	private boolean goOutUseAtr;
	
	//打刻一覧を表示する
	private boolean displayStampList;
	
	// [C-0] ポータルの打刻設定(会社ID, 打刻画面の表示設定, 打刻ボタン設定, 打刻ボタンを抑制する,トップメニューリンク利用する)																							
	
	// [1] ボタン詳細設定を取得する
	public Optional<ButtonSettings> getDetailButtonSettings(ButtonPositionNo buttonPositionNo) {
		return this.buttonSettings.stream().filter(it -> it.getButtonPositionNo().equals(buttonPositionNo)).findFirst();
	}
	
}
