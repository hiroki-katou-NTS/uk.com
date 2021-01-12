package nts.uk.ctx.at.record.pub.stamp;

import lombok.Value;

/**
 * @author ThanhNX
 * 
 *         打刻カード
 */
@Value
public class StampCardExport {

	/**
	 * 打刻カードID
	 */
	private String stampCardId;

	/**
	 * 社員ID
	 */
	private String employeeId;

	public StampCardExport(String stampCardId, String employeeId) {
		super();
		this.stampCardId = stampCardId;
		this.employeeId = employeeId;
	}

}
