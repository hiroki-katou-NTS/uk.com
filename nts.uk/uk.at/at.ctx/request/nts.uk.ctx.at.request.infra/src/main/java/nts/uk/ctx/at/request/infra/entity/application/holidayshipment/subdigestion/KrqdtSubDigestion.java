package nts.uk.ctx.at.request.infra.entity.application.holidayshipment.subdigestion;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 消化対象振休管理
 * 
 * @author sonnlb
 */
@Entity
@Table(name = "KRQDT_SUP_DIGESTION")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtSubDigestion extends ContractUkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 振休申請ID
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "ABSENCE_LEAVE_APP_ID")
	private String absenceLeaveAppID;

	/**
	 * 使用日数
	 */
	@Basic(optional = false)
	@Column(name = "DAYS_USED_NO")
	private int daysUsedNo;

	/**
	 * 振出管理データ
	 */
	@Basic(optional = true)
	@Column(name = "PAYOUT_MNG_DATA_ID")
	private String payoutMngDataID;

	/**
	 * 振出状態
	 */
	@Basic(optional = false)
	@Column(name = "PICK_UP_STATE")
	private int pickUpState;

	/**
	 * 振休発生日
	 */
	@Basic(optional = true)
	@Column(name = "OCCURRENCE_DATE")
	private GeneralDate occurrenceDate;

	/**
	 * 休出状態
	 */
	@Basic(optional = false)
	@Column(name = "UNKNOWN_DATE_ATR")
	private int unknownDate;

	@Override
	protected Object getKey() {
		return absenceLeaveAppID;
	}

}
