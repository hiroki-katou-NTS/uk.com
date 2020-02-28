package nts.uk.ctx.hr.develop.app.sysoperationset.eventoperation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.HRDevMenu;

@Data
@AllArgsConstructor
public class HRDevMenuDto {
	// イベントID
	private int eventId;
	// プログラムID
	private String programId;
	// プログラム名
	private String programName;
	// 利用できる
	private int availableMenu;
	//承認機能が利用できる
	private int availableApproval;
	// 表示順
	private int dispOrder;
	// 通知機能が利用できる
	private int availableNotice;
	
	public static HRDevMenuDto fromDomain(HRDevMenu domain){
		return new HRDevMenuDto(domain.getEventId().value, domain.getProgramId().v(), 
								domain.getProgramName().v(), domain.getAvailableMenu().value, 
								domain.getAvailableApproval().value, domain.getDispOrder(), domain.getAvailableNotice().value);
	}
}
