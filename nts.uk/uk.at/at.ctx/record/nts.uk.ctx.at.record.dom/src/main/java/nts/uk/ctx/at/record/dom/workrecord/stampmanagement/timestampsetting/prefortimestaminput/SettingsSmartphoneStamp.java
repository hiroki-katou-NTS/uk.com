/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * AR: スマホ打刻の打刻設定
 * path : UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の前準備.スマホ打刻の打刻設定
 * @author laitv
 *
 */
public class SettingsSmartphoneStamp implements DomainAggregate{
	
	// 会社ID
	@Getter
	private final String cid;
	
	// 打刻画面の表示設定
	@Getter
	private DisplaySettingsStampScreen displaySettingsStamScreen;
	
	// ページレイアウト設定
	@Getter
	private List<StampPageLayout> pageLayoutSettings;
	
	// 打刻ボタンを抑制する
	@Getter
	private Boolean suppressStampBtn;
	
	// [C-0] スマホ打刻の打刻設定(会社ID, 打刻画面の表示設定, ページレイアウト設定, 打刻ボタンを抑制する)																							
	public SettingsSmartphoneStamp(String cid, DisplaySettingsStampScreen displaySettingsStamScreen,
			List<StampPageLayout> pageLayoutSettings, Boolean suppressStampBtn) {
		this.cid = cid;
		this.displaySettingsStamScreen = displaySettingsStamScreen;
		this.pageLayoutSettings = pageLayoutSettings;
		this.suppressStampBtn = suppressStampBtn;
	}
	
	
	// [1] ボタン詳細設定を取得する																							
	public Optional<ButtonSettings> getDetailButtonSettings(StampButton stamButton) {
		
		// $打刻ページレイアウト = @ページレイアウト設定 :	filter $.ページNO = 打刻ボタン.ページNO
		Optional<StampPageLayout> stampPageLayout = this.pageLayoutSettings.stream().filter(it -> it.getPageNo().equals(stamButton.getPageNo())).findFirst();

		// if $打刻ページレイアウト.isEmpty return Optional.empty					
		if (!stampPageLayout.isPresent()) {
			return Optional.empty();
		}
		
		// return $打刻ページレイアウト.ボタン詳細設定リスト : filter $.ボタン位置NO = 打刻ボタン.ボタン位置NO
		return stampPageLayout.get().getLstButtonSet().stream().filter(it -> it.getButtonPositionNo().equals(stamButton.getButtonPositionNo())).findFirst();
	}
	
	// [2] ページを追加する
	public void addPage(StampPageLayout pageLayoutSetting) {
		
		this.pageLayoutSettings.add(pageLayoutSetting);
	}
	
	// [3] ページを更新する
	public void updatePage(StampPageLayout pageLayoutSetting) {
		
		// $打刻ページリスト = @ページレイアウト設定 :filter not $.ページNO == ページNO							
		List<StampPageLayout> pageList = this.pageLayoutSettings.stream().filter(it -> !it.getPageNo().equals(pageLayoutSetting.getPageNo())).collect(Collectors.toList());
		
		// $打刻ページリスト.add(打刻ページ)							
		pageList.add(pageLayoutSetting);
		
		// @ページレイアウト設定 = $打刻ページリストsort $.ページNO 			
		pageList.sort((p1,p2) -> p1.getPageNo().v() - p2.getPageNo().v());
		
		this.pageLayoutSettings = pageList;
	}
	
	// [4] ページを削除する
	public void deletePage() {
		
		this.pageLayoutSettings.clear();
	}
	
	
	
}
