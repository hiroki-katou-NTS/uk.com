package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 個人利用の打刻設定
 * @author phongtq
 *
 */
@AllArgsConstructor
@Getter
public class StampSettingPerson implements DomainAggregate {
	
	/** 会社ID */
	private final String companyId;
	
	/** 打刻ボタンを抑制する */
	private boolean buttonEmphasisArt;
	
	/** 打刻画面の表示設定 */
	private DisplaySettingsStampScreen stampingScreenSet;
	
	/** ページレイアウト設定 */
	private List<StampPageLayout> lstStampPageLayout;
	
	/**	打刻履歴表示方法 */
	private HistoryDisplayMethod historyDisplayMethod;
	
	/**
	 * ボタン詳細設定を取得する
	 * @param StampButton (打刻ボタン)
	 * @return Optional<ボタン詳細設定>
	 */
	public Optional<ButtonSettings> getButtonSet(StampButton stambutton){
		
		// $打刻ページレイアウト = @ページレイアウト設定 : filter $.ページNO = 打刻ボタン.ページNO
		Optional<StampPageLayout> pageLayout = lstStampPageLayout.stream().filter(x -> x.getPageNo().v() == stambutton.getPageNo().v()).findFirst();
		
		// if $打刻ページレイアウト.isEmpty
		if(!pageLayout.isPresent()){
			// return Optional.empty
			return Optional.empty();
		}
		
		// $打刻ページレイアウト.ボタン詳細設定リスト : filter $ボタン位置NO = 打刻ボタン.ボタン位置NO
		Optional<ButtonSettings> buttonSettings = pageLayout.get().getLstButtonSet().stream().filter(x -> x.getButtonPositionNo().v() == stambutton.getButtonPositionNo().v()).findFirst();
		
		// return Optional<ボタン詳細設定>
		return buttonSettings;
	}
	
	/**
	 * ページを追加する
	 * @param pageLayout
	 */
	public void addPage(StampPageLayout pageLayout){
		// ページレイアウト設定.add(打刻ページ)
		this.lstStampPageLayout.add(pageLayout);
	}
	
	/**
	 * ページを更新する
	 * @param pageLayout
	 */
	public void updatePage(StampPageLayout pageLayout) {
		// $打刻ページレイアウト = @ページレイアウト設定 : filter not $.ページNO = ページNO
		List<StampPageLayout> layout = this.lstStampPageLayout.stream().filter(x -> x.getPageNo().v() != pageLayout.getPageNo().v()).collect(Collectors.toList());
		
		// $打刻ページリスト.add(打刻ページ)
		layout.add(pageLayout);
		
		// @ページレイアウト設定 = $打刻ページリスト sort $.ページNO 
		layout.sort((p1,p2) -> p1.getPageNo().v() - p2.getPageNo().v());
		
		this.lstStampPageLayout = layout;
	}
	
	/**
	 * ページを削除する
	 * @param pageNo
	 */
	public void delete(int pageNo){
		
		// $打刻ページレイアウト = @ページレイアウト設定 : filter not $.ページNO = ページNO
		List<StampPageLayout> layout = this.lstStampPageLayout.stream().filter(x -> x.getPageNo().v() != pageNo).collect(Collectors.toList());
		
		// @ページレイアウト設定 = $打刻ページリスト
		this.lstStampPageLayout = layout;
	}
	
}
