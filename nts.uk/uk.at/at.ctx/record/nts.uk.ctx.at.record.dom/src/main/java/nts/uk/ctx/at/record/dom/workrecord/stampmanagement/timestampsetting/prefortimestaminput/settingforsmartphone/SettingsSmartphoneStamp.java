/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.settingforsmartphone;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.DisplaySettingsStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampButton;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageLayout;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * AR: スマホ打刻の打刻設定
 * path : UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の前準備.スマホ打刻の打刻設定
 * @author laitv
 *
 */
@AllArgsConstructor
@Getter
public class SettingsSmartphoneStamp implements DomainAggregate{
	
	// 会社ID
	private final String cid;
	
	// 打刻画面の表示設定
	private DisplaySettingsStampScreen displaySettingsStampScreen;
	
	// ページレイアウト設定
	@Setter
	private List<StampPageLayout> pageLayoutSettings;
	
	// 打刻ボタンを抑制する
	private boolean buttonEmphasisArt;
	
	// 	位置情報を利用する
	private NotUseAtr locationInfoUse;
	
	// 	打刻エリア制限する
	private NotUseAtr areaLimitAtr;
	
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
