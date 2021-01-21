package nts.uk.ctx.at.request.infra.entity.application.holidayshipment.recruitmentapp;

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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 振出申請
 * 
 * @author sonnlb
 */
@Entity
@Table(name = "KRQDT_APP_RECRUITMENT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppRecruitment extends ContractUkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 申請ID
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "APP_ID")
	private String appID;

	/**
	 * 勤務種類
	 */

	@Basic(optional = false)
	@Column(name = "WORK_TYPE_CD")
	private String workTypeCD;

	/**
	 * 就業時間帯
	 */

	@Basic(optional = false)
	@Column(name = "WORK_TIME_CD")
	private String workTimeCD;

	/**
	 * 時刻
	 */

	@Basic(optional = false)
	@Column(name = "START_WORK_TIME1")
	private int startWorkTime1;

	/**
	 * 時刻
	 */

	@Basic(optional = false)
	@Column(name = "END_WORK_TIME1")
	private int endWorkTime1;

	/**
	 * 直行
	 */

	@Basic(optional = false)
	@Column(name = "START_USE_ATR1")
	private int startUseAtr1;

	/**
	 * 直帰
	 */

	@Basic(optional = false)
	@Column(name = "END_USE_ATR1")
	private int endUseAtr1;

	/**
	 * 時刻
	 */

	@Basic(optional = true)
	@Column(name = "START_WORK_TIME2")
	private Integer startWorkTime2;

	/**
	 * 時刻
	 */
	@Basic(optional = true)
	@Column(name = "END_WORK_TIME2")
	private Integer endWorkTime2;

	/**
	 * 直行
	 */
	@Basic(optional = true)
	@Column(name = "START_USE_ATR2")
	private int startUseAtr2;

	/**
	 * 直帰
	 */
	@Basic(optional = true)
	@Column(name = "END_USE_ATR2")
	private int endUseAtr2;

	@Override
	protected Object getKey() {
		return appID;
	}

}
