package nts.uk.ctx.at.function.dom.adapter.stampcard;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class StampCardImport {

	private String contractCd;
	
	/**
	 * 番号
	 */
	private String stampNumber;
	
	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 登録日付
	 */
	private GeneralDate registerDate;

	public StampCardImport(String contractCd, String stampNumber, String employeeId, GeneralDate registerDate) {
		this.contractCd = contractCd;
		this.stampNumber = stampNumber;
		this.employeeId = employeeId;
		this.registerDate = registerDate;
	}
}
