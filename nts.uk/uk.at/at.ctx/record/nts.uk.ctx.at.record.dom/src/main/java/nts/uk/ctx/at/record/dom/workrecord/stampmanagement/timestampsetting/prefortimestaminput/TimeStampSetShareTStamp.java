/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * AR: 共有打刻の打刻設定 
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の前準備.共有打刻の打刻設定
 * @author laitv
 *
 */
public class TimeStampSetShareTStamp implements DomainAggregate {

	// 会社ID
	@Getter
	private final String cid;
	
	// 打刻画面の表示設定
	@Getter
	private DisplaySettingsStampScreen displaySetStampScreen;
	
	// ページレイアウト設定 
	@Getter
	private List<StampPageLayout> lstStampPageLayout;
	
	// 氏名選択利用する
	@Getter
	private Boolean  useSelectName;
	
	// パスワード入力が必須か
	@Getter
	private Boolean passwordInputReq;
	
	// 社員コード認証利用するか
	private NotUseAtr useEmpCodeToAuthen;
	
	// 指認証失敗回数
	private Optional<NumberAuthenfailures> numberAuthenfailures;

	//[C-0] 共有打刻の打刻設定(会社ID, 打刻画面の表示設定, ページレイアウト設定,氏名選択利用する,パスワード入力が必須か,社員コード認証利用するか,指認証失敗回数)
	public TimeStampSetShareTStamp(String cid, DisplaySettingsStampScreen displaySetStampScreen,
			List<StampPageLayout> lstStampPageLayout, Boolean useSelectName, Boolean passwordInputReq,
			NotUseAtr useEmpCodeToAuthen, Optional<NumberAuthenfailures> numberAuthenfailures) {
		this.cid = cid;
		this.displaySetStampScreen = displaySetStampScreen;
		this.lstStampPageLayout = lstStampPageLayout;
		this.useSelectName = useSelectName;
		this.passwordInputReq = passwordInputReq;
		this.useEmpCodeToAuthen = useEmpCodeToAuthen;
		this.numberAuthenfailures = numberAuthenfailures;
	}
	
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
