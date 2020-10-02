/**
 * 
 */
package nts.uk.screen.at.app.ksu001.changepage;

import java.util.List;

import lombok.Value;
import nts.uk.screen.at.app.ksu001.displayinshift.ShiftMasterMapWithWorkStyle;

/**
 * @author laitv
 */

@Value
public class ChangePageParam {
	
	public int shiftPalletUnit;             
	public int pageNumberCom;                   
	public int pageNumberOrg; 
	public String workplaceId; 
	public String workplaceGroupId; 	     // ・対象組織：対象組織識別情報
	public int unit;
	public List<ShiftMasterMapWithWorkStyle> listShiftMasterNotNeedGetNew; // ・新たに取得する必要のないシフト一覧：List<シフトマスタ>
}
