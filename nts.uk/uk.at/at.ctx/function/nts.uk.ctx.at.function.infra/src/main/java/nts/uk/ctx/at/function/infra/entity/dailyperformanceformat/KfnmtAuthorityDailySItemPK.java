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
 * 日次表示項目シート一覧
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtAuthorityDailySItemPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 勤怠項目ID
	 */
	@Column(name = "CID")
	public String companyId;
	
	/**
	 * 日次表示項目一覧
	 */
	@Column(name = "DAILY_PERFORMANCE_FORMAT_CD")
	public String dailyPerformanceFormatCode;
	
	/**
	 * 勤怠項目ID
	 */
	@Column(name= "ATTENDANCE_ITEM_ID")
	public Integer attendanceItemId;
	

}
