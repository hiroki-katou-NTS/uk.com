package nts.uk.file.at.app.export.bento;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ReservationWkpLedger {
	
	/**
	 * 職場コード
	 */
	private String wkpCD;
	
	/**
	 * 職場名称
	 */
	private String wkpName;
	
	/**
	 * 階層コード
	 */
	private String hierarchyCode; 
	
	private List<ReservationEmpLedger> empLedgerLst; 
	
}
