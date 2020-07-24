/**
 * 
 */
package nts.uk.screen.at.app.ksu001.start;

import lombok.Data;

/**
 * @author laitv
 *
 */
@Data
public class ShiftPaletteWantGet {
	public int shiftPalletUnit;              // ・取得したいシフトパレット：Optional<単位, ページ>  単位
	public int pageNumber;                   // ・取得したいシフトパレット：Optional<単位, ページ>  ページ
}
