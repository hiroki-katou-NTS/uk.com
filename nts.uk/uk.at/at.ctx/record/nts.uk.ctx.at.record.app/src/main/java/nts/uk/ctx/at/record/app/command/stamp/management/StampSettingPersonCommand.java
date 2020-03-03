package nts.uk.ctx.at.record.app.command.stamp.management;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPerson;

@Data
public class StampSettingPersonCommand {
	
	private String companyId;
	
	/** 打刻ボタンを抑制する */
	private boolean buttonEmphasisArt;
	
	/** 打刻画面の表示設定 */
	private StampingScreenSetCommand stampingScreenSet;
	
	/** ページレイアウト設定 */
	private List<StampPageLayoutCommand> lstStampPageLayout;
	
	public StampSettingPerson toDomain(){
		return null;
		
	}
}
