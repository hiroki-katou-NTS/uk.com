package nts.uk.ctx.at.record.app.command.stamp.management;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.app.command.kdp.kdp010.a.command.DisplaySettingsStampScreenCommand;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.HistoryDisplayMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageLayout;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Data
@NoArgsConstructor
public class AddStampSettingPersonCommand {
	
	/** 打刻ボタンを抑制する */
	private boolean buttonEmphasisArt;
	
	/** 打刻履歴表示方法 */
	private int historyDisplayMethod;
	
	/** 打刻画面のサーバー時刻補正間隔 */
	private DisplaySettingsStampScreenCommand stampingScreenSet;
	
	public StampSettingPerson toDomain(List<StampPageLayout> lstStampPageLayout){
		String companyId = AppContexts.user().companyId();
		return new StampSettingPerson(
				companyId, 
				buttonEmphasisArt, 
				this.stampingScreenSet.toDomain(), 
				lstStampPageLayout,
				EnumAdaptor.valueOf(historyDisplayMethod, HistoryDisplayMethod.class));
	}
}
