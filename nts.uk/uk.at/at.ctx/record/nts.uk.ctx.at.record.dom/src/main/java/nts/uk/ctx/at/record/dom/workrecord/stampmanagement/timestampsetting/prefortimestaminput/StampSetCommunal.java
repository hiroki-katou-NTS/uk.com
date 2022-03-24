/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * AR: 共有打刻の打刻設定 
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の前準備.共有打刻の打刻設定
 * @author laitv
 *
 */
@Getter
@AllArgsConstructor
public class StampSetCommunal implements DomainAggregate {

	// 会社ID
	private final String cid;
	
	// 打刻画面の表示設定
	private DisplaySettingsStampScreen displaySetStampScreen;
	
	// ページレイアウト設定 
	@Setter
	private List<StampPageLayout> lstStampPageLayout;
	
	// 氏名選択利用する
	private boolean nameSelectArt;
	
	// パスワード入力が必須か
	private boolean passwordRequiredArt;
	
	// 社員コード認証利用するか
	
	private boolean employeeAuthcUseArt;
	// 指認証失敗回数
	private Optional<NumberAuthenfailures> authcFailCnt;
	
	// 認証方法設定
	private AuthenticationMethod authcMethod;
	
	// [1] ボタン詳細設定を取得する
	public Optional<ButtonSettings> getDetailButtonSettings(StampButton stamButton) {

		// $打刻ページレイアウト = @ページレイアウト設定 : filter $.ページNO = 打刻ボタン.ページNO 
		Optional<StampPageLayout> stampPageLayout = this.lstStampPageLayout.stream()
				.filter(it -> it.getPageNo().equals(stamButton.getPageNo())).findFirst();

		// if $打刻ページレイアウト.isEmpty return Optional.empty
		if (!stampPageLayout.isPresent()) {
			return Optional.empty();
		}

		// return $打刻ページレイアウト.ボタン詳細設定リスト : filter $.ボタン位置NO = 打刻ボタン.ボタン位置NO
		return stampPageLayout.get().getLstButtonSet().stream()
				.filter(it -> it.getButtonPositionNo().equals(stamButton.getButtonPositionNo())).findFirst();
	}
	
	// [2] ページを追加する  // Thêm page
	public void addPage(StampPageLayout pageLayoutSetting) {

		this.lstStampPageLayout.add(pageLayoutSetting);
	}
	
	// [3] ページを更新する
	public void updatePage(StampPageLayout pageLayoutSetting) {

		// $打刻ページリスト = @ページレイアウト設定 :filter not $.ページNO == ページNO
		List<StampPageLayout> pageList = this.lstStampPageLayout.stream()
				.filter(it -> !it.getPageNo().equals(pageLayoutSetting.getPageNo())).collect(Collectors.toList());

		// $打刻ページリスト.add(打刻ページ)
		pageList.add(pageLayoutSetting);

		// @ページレイアウト設定 = $打刻ページリストsort $.ページNO
		pageList.sort((p1, p2) -> p1.getPageNo().v() - p2.getPageNo().v());

		this.lstStampPageLayout = pageList;
	}

	// [4] ページを削除する
	public void deletePage(PageNo pageNo) {
		// $打刻ページリスト = @ページレイアウト設定 : filter not $.ページNO == ページNO						
		List<StampPageLayout> pageList = this.lstStampPageLayout.stream()
				.filter(it -> !it.getPageNo().equals(pageNo)).collect(Collectors.toList());
		
		// @ページレイアウト設定 = $打刻ページリスト
		this.lstStampPageLayout = pageList;
	}
}
