/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.DisplaySettingsStampScreen;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampButton;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageLayout;

/**
 * AR: RICOH複写機の打刻設定
 * @path : UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.打刻設定.打刻入力の前準備.RICOH複写機の打刻設定.RICOH複写機の打刻設定
 * @author ThanhPV
 */
@AllArgsConstructor
@Getter
public class StampSettingOfRICOHCopier implements DomainAggregate{
	
	// 会社ID
	private final String cid;
	
	// ICカード登録パスワード
	private PasswordForRICOH icCardPassword;
	
	// ページレイアウト設定
	@Setter
	private List<StampPageLayout> pageLayoutSettings;
	
	// 打刻画面の表示設定
	private DisplaySettingsStampScreen displaySettingsStampScreen;
	
	// [1] ボタン詳細設定を取得する																							
	public Optional<ButtonSettings> getDetailButtonSettings(StampButton stampButton) {
		
		// $打刻ページレイアウト = @ページレイアウト設定 :	filter $.ページNO = 打刻ボタン.ページNO
		Optional<StampPageLayout> stampPageLayout = this.pageLayoutSettings.stream().filter(it -> it.getPageNo().equals(stampButton.getPageNo())).findFirst();

		// if $打刻ページレイアウト.isEmpty return Optional.empty					
		if (!stampPageLayout.isPresent()) {
			return Optional.empty();
		}
		
		// return $打刻ページレイアウト.ボタン詳細設定リスト : filter $.ボタン位置NO = 打刻ボタン.ボタン位置NO
		return stampPageLayout.get().getLstButtonSet().stream().filter(it -> it.getButtonPositionNo().equals(stampButton.getButtonPositionNo())).findFirst();
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
	public void deletePage(PageNo pageNo) {
		// $打刻ページリスト = @ページレイアウト設定 : filter not $.ページNO == ページNO						
		List<StampPageLayout> pageList = this.pageLayoutSettings.stream()
				.filter(it -> !it.getPageNo().equals(pageNo)).collect(Collectors.toList());
		
		// @ページレイアウト設定 = $打刻ページリスト
		this.pageLayoutSettings = pageList;
	}
	
}
