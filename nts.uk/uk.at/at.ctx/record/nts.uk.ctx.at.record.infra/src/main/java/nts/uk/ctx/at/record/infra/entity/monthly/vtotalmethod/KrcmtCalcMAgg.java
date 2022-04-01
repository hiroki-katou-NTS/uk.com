package nts.uk.ctx.at.record.infra.entity.monthly.vtotalmethod;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

//import java.math.BigDecimal;


/**
 * @author HoangNDH
 * The persistent class for the KRCMT_CALC_M_AGG database table.
 * 
 */
@Entity
@NoArgsConstructor
@Table(name="KRCMT_CALC_M_AGG")
public class KrcmtCalcMAgg extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// 会社ID
	@Id
	@Column(name="CID")
	public String cid;

	// 振出日数カウント条件
	@Column(name="TRANS_ATTEND_DAY")
	public int transAttendDay;

	// 連続勤務の日でもカウントする
	@Column(name="CONTINOUS_WORK_COUNT_ATR")
	public boolean countContiousWorkDay;

	// 計算対象外のカウント条件
	@Column(name="CALC_TARGET_OUT_COUNT_CON")
	public int calcTargetOutCountCondition;

	// 勤務日ではない日でもカウントする
	@Column(name="NO_WORK_DAY_COUNT_ATR")
	public boolean countNoWorkDay;

	// 週割増に前月の最終週を含めて計算するか
	@Column(name="WEEK_PREMIUM_WITH_LAST_MONTH")
	public boolean weekPremiumCalcWithPrevMonthLastWeek;
	
	/** 変形労働の途中入社退職の集計方法 */
	@Column(name="METHOD_ENTER_IN_MONTH_DEFORLABOR")
	public int methodEnterInMonthDeforLabor;
	
	/** フレックスの途中入社退職の集計方法 */
	@Column(name="METHOD_ENTER_IN_MONTH_FLEX")
	public boolean methodEnterInMonthFlex;
	
	@Override
	protected Object getKey() {
		return cid;
	}

}