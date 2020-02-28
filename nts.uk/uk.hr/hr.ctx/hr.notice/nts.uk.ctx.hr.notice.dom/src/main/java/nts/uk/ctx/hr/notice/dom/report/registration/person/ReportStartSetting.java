/**
 * 
 */
package nts.uk.ctx.hr.notice.dom.report.registration.person;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author laitv
 * domain 届出の開始設定
 */
@Getter
@NoArgsConstructor
public class ReportStartSetting {
	
	private String cid; //会社ID
	private boolean changeDisp; //差し戻しを否認に表示変更  true:否認、false:差し戻し	

	public ReportStartSetting(String cid, boolean changeDisp) {
		super();
		this.cid = cid;
		this.changeDisp = changeDisp;
	}
	
	public static ReportStartSetting createFromJavaType(String cid, boolean changeDisp){
		return new ReportStartSetting(cid, changeDisp);
	}
	
}
