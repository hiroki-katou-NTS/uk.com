package nts.uk.ctx.at.function.infra.entity.dailyperformanceformat;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author anhdt
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtBusinessTypeSDailyPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 勤怠項目ID
	 */
	@Column(name = "CID")
	public String companyId;
	
	/**
	 * 勤務種別コード
	 */
	@Column(name = "BUSINESS_TYPE_CD")
	public String businessTypeCode;
	
	/**
	 * 日次表示項目一覧
	 */
	@Column(name = "ATTENDANCE_ITEM_ID")
	public int attendanceItemId;
	
}
