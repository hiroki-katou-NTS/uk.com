/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getshiftpalette;

import java.util.List;

import lombok.Value;
import nts.uk.screen.at.app.ksu001.displayinshift.ShiftMasterMapWithWorkStyle;
import nts.uk.screen.at.app.ksu001.start.ShiftPaletteWantGet;

/**
 * @author laitv
 */

@Value
public class GetShiftPaletteParam {
	
	public List<ShiftMasterMapWithWorkStyle> listShiftMasterNotNeedGetNew; // ・新たに取得する必要のないシフト一覧：List<シフトマスタ>
	public ShiftPaletteWantGet shiftPaletteWantGet; // ・取得したいシフトパレット：Optional<単位, ページ>  単位
	public String workplaceId;     	         // ・対象組織：対象組織識別情報
	public String workplaceGroupId; 	     // ・対象組織：対象組織識別情報
	public int unit;
}
