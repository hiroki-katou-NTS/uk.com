package nts.uk.file.at.app.export.bento;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReservationWkpLedger {
	
	/**
	 * 職場コード
	 */
	private String wkpCD;
	
	/**
	 * 職場名称
	 */
	private String wkpName;
	
	private List<ReservationEmpLedger> empLedgerLst; 
	
}
