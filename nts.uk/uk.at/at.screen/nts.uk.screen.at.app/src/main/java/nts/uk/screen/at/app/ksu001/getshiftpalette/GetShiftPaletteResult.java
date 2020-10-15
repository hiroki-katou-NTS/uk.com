package nts.uk.screen.at.app.ksu001.getshiftpalette;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.screen.at.app.ksu001.displayinshift.ShiftMasterMapWithWorkStyle;

/**
 * 
 * @author laitv 
 *
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetShiftPaletteResult {

	public List<PageInfo> listPageInfo ; // List<ページ, 名称>
	public TargetShiftPalette targetShiftPalette ; // 対象のシフトパレット： Optional<ページ, シフトパレット>
	public List<ShiftMasterMapWithWorkStyle> listShiftMaster; // 取得したシフト一覧：List<シフトマスタ, Optional<出勤休日区分>> , list nay chỉ gồm nhưungx thằng chưa đc lưu ở localStorage
	
	List<String> listShiftMasterCodeOfPageSelectd; // truong nay khong co trong output cua thuat toan. 
}
