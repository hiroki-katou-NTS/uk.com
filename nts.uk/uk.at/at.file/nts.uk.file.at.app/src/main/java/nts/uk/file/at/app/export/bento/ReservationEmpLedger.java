package nts.uk.file.at.app.export.bento;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReservationEmpLedger {
	
	/**
	 * 社員コード	
	 */
	private String empCD;
	
	/**
	 * 社員名	
	 */
	private String empName;
	
	/**
	 * 数量合計
	 */
	private Integer totalBentoQuantity;
	
	/**
	 * 金額１合計
	 */
	private Integer totalBentoAmount1;
	
	/**
	 * 金額２合計
	 */
	private Integer totalBentoAmount2;
	
	private List<ReservationBentoLedger> bentoLedgerLst;
	
}
