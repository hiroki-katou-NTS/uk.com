package nts.uk.ctx.at.record.infra.entity.monthly.information.childnursing;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCDT_MON_CHILD_HD_REMAIN")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonChildHdRemain extends UkJpaEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcdtMonChildHdRemainPK hdRemainPK;
	
	/** 締め処理状態 */
	@Column(name = "CLOSURE_STATUS")
	private String closureStatus;
	
	/** 締め処理状態 */
	@Column(name = "START_DATE")
	private GeneralDate startDate;
	
	/** 締め処理状態 */
	@Column(name = "END_DATE")
	private GeneralDate endDate;
	
	/** 締め処理状態 */
	@Column(name = "USED_DAYS")
	private BigDecimal usedDays;
	
	/** 締め処理状態 */
	@Column(name = "USED_DAYS_BEFORE")
	private BigDecimal usedDaysBefore;
	
	/** 締め処理状態 */
	@Column(name = "USED_DAYS_AFTER")
	private BigDecimal usedDaysAfter;
	
	/** 締め処理状態 */
	@Column(name = "USED_MINUTES")
	private BigDecimal usedMinutes;
	
	/** 締め処理状態 */
	@Column(name = "USED_MINUTES_BEFORE")
	private BigDecimal usedMinutesBefore;
	
	/** 締め処理状態 */
	@Column(name = "USED_MINUTES_AFTER")
	private BigDecimal usedMinutesAfter;

	@Override
	protected Object getKey() {
		return hdRemainPK;
	}
	

}
