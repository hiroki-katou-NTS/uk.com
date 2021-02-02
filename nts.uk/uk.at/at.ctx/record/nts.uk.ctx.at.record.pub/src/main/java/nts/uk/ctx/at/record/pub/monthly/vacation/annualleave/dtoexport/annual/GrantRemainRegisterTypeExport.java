package nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GrantRemainRegisterTypeExport {
	
	/**
	 *  手動
	 */
	MANUAL(0),
	
	/**
	 *  月締め
	 */
	MONTH_CLOSE(1);
	
	public final int value;

}
