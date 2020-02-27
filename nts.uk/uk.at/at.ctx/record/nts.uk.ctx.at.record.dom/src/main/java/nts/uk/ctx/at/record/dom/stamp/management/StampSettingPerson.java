package nts.uk.ctx.at.record.dom.stamp.management;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 個人利用の打刻設定
 * @author phongtq
 *
 */
public class StampSettingPerson implements DomainAggregate{
	
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

	public StampSettingPerson(String companyId, boolean buttonEmphasisArt, StampingScreenSet stampingScreenSet,
			List<StampPageLayout> lstStampPageLayout) {
		this.companyId = companyId;
		this.buttonEmphasisArt = buttonEmphasisArt;
		this.stampingScreenSet = stampingScreenSet;
		this.lstStampPageLayout = lstStampPageLayout;
	}
	
	/**
	 * 
	 * @param pageNo (ページNO)
	 * @param buttonPositionNo (ボタン位置NO)
	 * @return Optional<ボタン詳細設定>
	 */
	public Optional<ButtonSettings> getButtonSet(int pageNo, int buttonPositionNo){
		
		// $打刻ページレイアウト = @ページレイアウト設定 : filter $.ページNO = ページNO
		StampPageLayout pageLayout = lstStampPageLayout.stream().filter(x -> x.getPageNo() == pageNo).findAny().get();
		
		// if $打刻ページレイアウト.isEmpty
		if(pageLayout == null){
			// return Optional.empty
			return Optional.empty();
		}
		
		Optional<ButtonSettings> buttonSettings = pageLayout.getLstButtonSet().stream().filter(x -> x.getButtonPositionNo() == buttonPositionNo).findFirst();
		
		return buttonSettings;
		
	}
	
}
