package nts.uk.ctx.at.record.dom.stamp.management;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 個人利用の打刻設定
 * @author phongtq
 *
 */
public class StampSettingPerson implements DomainAggregate {
	
	/** 会社ID */
	@Getter
	private final String companyId;
	
	/** 打刻ボタンを抑制する */
	@Getter
	private boolean buttonEmphasisArt;
	
	/** 打刻画面の表示設定 */
	@Getter
	private StampingScreenSet stampingScreenSet;
	
	/** ページレイアウト設定 */
	@Getter
	private List<StampPageLayout> lstStampPageLayout;

	/**
	 * [C-0] 個人利用の打刻設定(会社ID, 打刻画面の表示設定, ページレイアウト設定, 打刻ボタンを抑制する)
	 * @param companyId
	 * @param buttonEmphasisArt
	 * @param stampingScreenSet
	 * @param lstStampPageLayout
	 */
	public StampSettingPerson(String companyId, boolean buttonEmphasisArt, StampingScreenSet stampingScreenSet,
			List<StampPageLayout> lstStampPageLayout) {
		this.companyId = companyId;
		this.buttonEmphasisArt = buttonEmphasisArt;
		this.stampingScreenSet = stampingScreenSet;
		this.lstStampPageLayout = lstStampPageLayout;
	}
	
	public StampSettingPerson(String companyId, boolean buttonEmphasisArt, StampingScreenSet stampingScreenSet) {
		this.companyId = companyId;
		this.buttonEmphasisArt = buttonEmphasisArt;
		this.stampingScreenSet = stampingScreenSet;
	}
	
	/**t
	 * ボタン詳細設定を取得する
	 * @param pageNo (ページNO)
	 * @param buttonPositionNo (ボタン位置NO)
	 * @return Optional<ボタン詳細設定>
	 */
	public Optional<ButtonSettings> getButtonSet(int pageNo, int buttonPositionNo){
		
		// $打刻ページレイアウト = @ページレイアウト設定 : filter $.ページNO = ページNO
		Optional<StampPageLayout> pageLayout = lstStampPageLayout.stream().filter(x -> x.getPageNo().v() == pageNo).findFirst();
		
		// if $打刻ページレイアウト.isEmpty
		if(!pageLayout.isPresent()){
			// return Optional.empty
			return Optional.empty();
		}
		
		// $打刻ページレイアウト.ボタン詳細設定リスト : filter $ボタン位置NO = ボタン位置NO 
		Optional<ButtonSettings> buttonSettings = pageLayout.get().getLstButtonSet().stream().filter(x -> x.getButtonPositionNo().v() == buttonPositionNo).findFirst();
		
		// return Optional<ボタン詳細設定>
		return buttonSettings;
	}
	
	/**
	 * ページを追加する
	 * @param pageLayout
	 */
	public void insert(StampPageLayout pageLayout){
		// ページレイアウト設定.add(打刻ページ)
		lstStampPageLayout.add(pageLayout);
	}
	
	/**
	 * ページを更新する
	 * @param pageLayout
	 */
	public void update(StampPageLayout pageLayout) {
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
