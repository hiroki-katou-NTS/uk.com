package nts.uk.ctx.at.record.infra.entity.worktime;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt
 * 出退勤
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_TIME_LEAVING_WORK")
public class KrcmtTimeLeavingWork extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtTimeLeavingWorkPK krcmtTimeLeavingWorkPK;
	
	@Column(name = "ATD_ACTUAL_ROUDING_TIME")
	public BigDecimal attendanceActualRoudingTime;

	@Column(name = "ATD_ACTUAL_TIME")
	public String attendanceActualTime;
	
	@Column(name = "ATD_ACTUAL_PLACE_CODE")
	public BigDecimal attendanceActualPlaceCode;

	@Column(name = "ATD_ACTUAL_SOURCE_INFO")
	public BigDecimal attendanceActualSourceInfo;
	
	@Column(name = "ATD_STAMP_ROUDING_TIME")
	public BigDecimal attendanceStampRoudingTime;

	@Column(name = "ATD_STAMP_TIME")
	public BigDecimal attendanceStampTime;

	@Column(name = "ATD_STAMP_PLACE_CODE")
	public String attendanceStampPlaceCode;
	
	@Column(name = "ATD_STAMP_SOURCE_INFO")
	public BigDecimal attendanceStampSourceInfo;

	@Column(name = "ATD_NUMBER_STAMP")
	public BigDecimal attendanceNumberStamp;

	@Column(name = "LWK_ACTUAL_ROUDING_TIME")
	public BigDecimal leaveWorkActualRoundingTime;
	
	@Column(name = "LWK_ACTUAL_TIME")
	public BigDecimal leaveWorkActualTime;
	
	@Column(name = "LWK_ACTUAL_PLACE_CODE")
	public String leaveWorkActualPlaceCode;
	
	@Column(name = "LWK_ACTUAL_SOURCE_INFO")
	public BigDecimal leaveActualSourceInfo;
	
	@Column(name = "LWK_STAMP_ROUDING_TIME")
	public BigDecimal leaveWorkStampRoundingTime;
	
	@Column(name = "LWK_STAMP_TIME")
	public BigDecimal leaveWorkStampTime;
	
	@Column(name = "LWK_STAMP_PLACE_CODE")
	public String leaveWorkStampPlaceCode;
	
	@Column(name = "LWK_STAMP_SOURCE_INFO")
	public BigDecimal leaveWorkStampSourceInfo;
	
	@Column(name = "LWK_NUMBER_STAMP")
	public String leaveWorkNumberStamp;
	
	@Override
	protected Object getKey() {
		return this.krcmtTimeLeavingWorkPK;
	}
}
