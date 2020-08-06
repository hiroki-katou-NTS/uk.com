/**
 * 
 */
package nts.uk.screen.at.app.ksu001.start;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author laitv
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ShiftPaletteWantGet {
	public int shiftPalletUnit;              // Company | workPlace
	public int pageNumber;                   // ・取得したいシフトパレット：Optional<単位, ページ>  ページ
}
