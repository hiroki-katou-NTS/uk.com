/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getshiftpalette;

import java.util.List;

import lombok.Value;

/**
 * @author laitv
 */

@Value
public class GetShiftPaletteParam {
	
	public List<String> listShiftMasterCode; // ・新たに取得する必要のないシフト一覧：List<シフトマスタ>
	public int shiftPalletUnit;              // ・取得したいシフトパレット：Optional<単位, ページ>  単位
	public int pageNumber;                   // ・取得したいシフトパレット：Optional<単位, ページ>  ページ
	public String workplaceId;     	         // ・対象組織：対象組織識別情報
	public String workplaceGroupId; 	     // ・対象組織：対象組織識別情報
	public boolean dataLocalstorageEmpty; // lần đầu thì sẽ = false.
	 
}
