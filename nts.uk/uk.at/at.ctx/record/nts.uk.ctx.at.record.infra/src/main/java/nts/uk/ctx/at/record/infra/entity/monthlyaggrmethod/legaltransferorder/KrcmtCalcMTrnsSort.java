package nts.uk.ctx.at.record.infra.entity.monthlyaggrmethod.legaltransferorder;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * エンティティ：月別実績の法定内振替順設定
 * @author shuichi_ishida
 */
@Entity
@Table(name = "KRCMT_CALC_M_TRNS_SORT")
@NoArgsConstructor
public class KrcmtCalcMTrnsSort extends ContractUkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcmtCalcMTrnsSortPK PK;
	
	/** 残業並び順01 */
	@Column(name = "OVER_TIME_ORDER_01")
	public int overTimeOrder01;
	/** 残業並び順01 */
	@Column(name = "OVER_TIME_ORDER_02")
	public int overTimeOrder02;
	/** 残業並び順01 */
	@Column(name = "OVER_TIME_ORDER_03")
	public int overTimeOrder03;
	/** 残業並び順01 */
	@Column(name = "OVER_TIME_ORDER_04")
	public int overTimeOrder04;
	/** 残業並び順01 */
	@Column(name = "OVER_TIME_ORDER_05")
	public int overTimeOrder05;
	/** 残業並び順01 */
	@Column(name = "OVER_TIME_ORDER_06")
	public int overTimeOrder06;
	/** 残業並び順01 */
	@Column(name = "OVER_TIME_ORDER_07")
	public int overTimeOrder07;
	/** 残業並び順01 */
	@Column(name = "OVER_TIME_ORDER_08")
	public int overTimeOrder08;
	/** 残業並び順01 */
	@Column(name = "OVER_TIME_ORDER_09")
	public int overTimeOrder09;
	/** 残業並び順01 */
	@Column(name = "OVER_TIME_ORDER_10")
	public int overTimeOrder10;

	/** 休出並び順01 */
	@Column(name = "HDWK_TIME_ORDER_01")
	public int holidayWorkTimeOrder01;
	/** 休出並び順01 */
	@Column(name = "HDWK_TIME_ORDER_02")
	public int holidayWorkTimeOrder02;
	/** 休出並び順01 */
	@Column(name = "HDWK_TIME_ORDER_03")
	public int holidayWorkTimeOrder03;
	/** 休出並び順01 */
	@Column(name = "HDWK_TIME_ORDER_04")
	public int holidayWorkTimeOrder04;
	/** 休出並び順01 */
	@Column(name = "HDWK_TIME_ORDER_05")
	public int holidayWorkTimeOrder05;
	/** 休出並び順01 */
	@Column(name = "HDWK_TIME_ORDER_06")
	public int holidayWorkTimeOrder06;
	/** 休出並び順01 */
	@Column(name = "HDWK_TIME_ORDER_07")
	public int holidayWorkTimeOrder07;
	/** 休出並び順01 */
	@Column(name = "HDWK_TIME_ORDER_08")
	public int holidayWorkTimeOrder08;
	/** 休出並び順01 */
	@Column(name = "HDWK_TIME_ORDER_09")
	public int holidayWorkTimeOrder09;
	/** 休出並び順01 */
	@Column(name = "HDWK_TIME_ORDER_10")
	public int holidayWorkTimeOrder10;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
}
