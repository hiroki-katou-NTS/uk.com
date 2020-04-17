package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 打刻後の実績表示
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の機能設定.打刻後の実績表示
 * @author phongtq
 *
 */
@Getter
public class StampResultDisplay implements DomainAggregate{

	/** 会社ID */
	private final String companyId;
	
	/** 使用区分 */
	private NotUseAtr usrAtr;
	
	/** 表示項目一覧 */
	private List<StampAttenDisplay> lstDisplayItemId;

	public StampResultDisplay(String companyId, NotUseAtr usrAtr, List<StampAttenDisplay> lstDisplayItemId) {
		this.companyId = companyId;
		this.usrAtr = usrAtr;
		this.lstDisplayItemId = lstDisplayItemId;
	}
	
	public StampResultDisplay(String companyId, NotUseAtr usrAtr) {
		this.companyId = companyId;
		this.usrAtr = usrAtr;
	}
}
