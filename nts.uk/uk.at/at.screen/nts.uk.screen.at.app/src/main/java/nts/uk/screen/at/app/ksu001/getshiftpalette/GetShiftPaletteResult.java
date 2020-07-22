package nts.uk.screen.at.app.ksu001.getshiftpalette;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	List<PageInfo> listPageInfo ; // List<ページ, 名称>
	Optional<TargetShiftPalette> targetShiftPalette ; // 対象のシフトパレット： Optional<ページ, シフトパレット>
	List<Shift> listOfShift; // 取得したシフト一覧：List<シフトマスタ, Optional<出勤休日区分>>
	
}
