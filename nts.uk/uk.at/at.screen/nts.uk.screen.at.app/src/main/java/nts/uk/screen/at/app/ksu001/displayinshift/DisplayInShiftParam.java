package nts.uk.screen.at.app.ksu001.displayinshift;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class DisplayInShiftParam {
	
	public List<String> listSid;         // ・社員IDリスト：List<社員ID>
	public GeneralDate startDate;        // ・期間
	public GeneralDate endDate;    	     // ・期間
	public String workplaceId;     	     // ・対象組織：対象組織識別情報
	public String workplaceGroupId; 	 // ・対象組織：対象組織識別情報
	public List<String> listShiftMaster; // ・新たに取得する必要のないシフト一覧：List<シフトマスタ>
	public int shiftPalletUnit;          // ・取得したいシフトパレット：Optional<単位, ページ>  単位
	public int pageNumber;               // ・取得したいシフトパレット：Optional<単位, ページ>  ページ
	public boolean getActualData;       // ・実績も取得するか：boolean : có lấy data thực tế không
	
}
