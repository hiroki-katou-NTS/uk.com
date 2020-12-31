package nts.uk.ctx.at.record.pub.stampcard;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class StampCardExport {

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

	public StampCardExport(String contractCd, String stampNumber, String employeeId, GeneralDate regDate) {
		this.contractCd = contractCd;
		this.stampNumber = stampNumber;
		this.employeeId = employeeId;
		this.registerDate = regDate;
	}
}
