package nts.uk.ctx.exio.infra.entity.exi.condset;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exi.condset.AcScreenCondSet;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.infra.entity.exi.item.OiomtExAcItem;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 受入選別条件設定
 */

@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_AC_SCREEN_COND")
public class OiomtExAcScreenCond extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtExAcScreenCondPk acScreenCondSetPk;

	/**
	 * 比較条件選択
	 */
	@Basic(optional = true)
	@Column(name = "SEL_COMPARE_COND")
	public int selCompareCond;

	/**
	 * 時間‗条件値2
	 */
	@Basic(optional = true)
	@Column(name = "TIME_COND_VAL2")
	public Integer timeCondVal2;

	/**
	 * 時間‗条件値1
	 */
	@Basic(optional = true)
	@Column(name = "TIME_COND_VAL1")
	public Integer timeCondVal1;

	/**
	 * 時刻‗条件値2
	 */
	@Basic(optional = true)
	@Column(name = "TIME_MO_COND_VAL2")
	public Integer timeMoCondVal2;

	/**
	 * 時刻‗条件値1
	 */
	@Basic(optional = true)
	@Column(name = "TIME_MO_COND_VAL1")
	public Integer timeMoCondVal1;

	/**
	 * 日付‗条件値2
	 */
	@Basic(optional = true)
	@Column(name = "DATE_COND_VAL2")
	public GeneralDate dateCondVal2;

	/**
	 * 日付‗条件値1
	 */
	@Basic(optional = true)
	@Column(name = "DATE_COND_VAL1")
	public GeneralDate dateCondVal1;

	/**
	 * 文字‗条件値2
	 */
	@Basic(optional = true)
	@Column(name = "CHAR_COND_VAL2")
	public String charCondVal2;

	/**
	 * 文字‗条件値1
	 */
	@Basic(optional = true)
	@Column(name = "CHAR_COND_VAL1")
	public String charCondVal1;

	/**
	 * 数値‗条件値2
	 */
	@Basic(optional = true)
	@Column(name = "NUM_COND_VAL2")
	public BigDecimal numCondVal2;

	/**
	 * 数値‗条件値1
	 */
	@Basic(optional = true)
	@Column(name = "NUM_COND_VAL1")
	public BigDecimal numCondVal1;

	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "SYSTEM_TYPE", referencedColumnName = "SYSTEM_TYPE", insertable = false, updatable = false),
			@JoinColumn(name = "CONDITION_SET_CD", referencedColumnName = "CONDITION_SET_CD", insertable = false, updatable = false),
			@JoinColumn(name = "ACCEPT_ITEM_NUM", referencedColumnName = "ACCEPT_ITEM_NUMBER", insertable = false, updatable = false) })
	public OiomtExAcItem acceptItem;

	@Override
	protected Object getKey() {
		return acScreenCondSetPk;
	}

	public OiomtExAcScreenCond(String cid, int sysType, String conditionCode, int acceptItemNum,
			Integer selCompareCond, Integer timeCondVal1, Integer timeCondVal2, Integer timeMoCondVal1,
			Integer timeMoCondVal2, GeneralDate dateCondVal1, GeneralDate dateCondVal2, String charCondVal1,
			String charCondVal2, BigDecimal numCondVal1, BigDecimal numCondVal2) {
		super();
		this.acScreenCondSetPk = new OiomtExAcScreenCondPk(cid, sysType, conditionCode, acceptItemNum);
		this.selCompareCond = selCompareCond;
		this.timeCondVal2 = timeCondVal2;
		this.timeCondVal1 = timeCondVal1;
		this.timeMoCondVal2 = timeMoCondVal2;
		this.timeMoCondVal1 = timeMoCondVal1;
		this.dateCondVal2 = dateCondVal2;
		this.dateCondVal1 = dateCondVal1;
		this.charCondVal2 = charCondVal2;
		this.charCondVal1 = charCondVal1;
		this.numCondVal2 = numCondVal2;
		this.numCondVal1 = numCondVal1;
	}

	public static OiomtExAcScreenCond fromDomain(StdAcceptItem item, AcScreenCondSet domain) {
		return new OiomtExAcScreenCond(item.getCid(), item.getSystemType().value, item.getConditionSetCd().v(),
				item.getAcceptItemNumber(),
				domain.getSelectComparisonCondition().isPresent() ? domain.getSelectComparisonCondition().get().value
						: null,
				domain.getTimeConditionValue1().isPresent() ? domain.getTimeConditionValue1().get().v() : null,
				domain.getTimeConditionValue2().isPresent() ? domain.getTimeConditionValue2().get().v() : null,
				domain.getTimeMomentConditionValue1().isPresent() ? domain.getTimeMomentConditionValue1().get().v()
						: null,
				domain.getTimeMomentConditionValue2().isPresent() ? domain.getTimeMomentConditionValue2().get().v()
						: null,
				domain.getDateConditionValue1().isPresent() ? domain.getDateConditionValue1().get() : null,
				domain.getDateConditionValue2().isPresent() ? domain.getDateConditionValue2().get() : null,
				domain.getCharacterConditionValue1().isPresent() ? domain.getCharacterConditionValue1().get().v()
						: null,
				domain.getCharacterConditionValue2().isPresent() ? domain.getCharacterConditionValue2().get().v()
						: null,
				domain.getNumberConditionValue1().isPresent() ? domain.getNumberConditionValue1().get().v() : null,
				domain.getNumberConditionValue2().isPresent() ? domain.getNumberConditionValue2().get().v() : null);
	}

	public AcScreenCondSet toDomain() {
		return new AcScreenCondSet(this.acScreenCondSetPk.conditionSetCd, this.acScreenCondSetPk.acceptItemNum,
				this.selCompareCond, this.timeCondVal1, this.timeCondVal2, this.timeMoCondVal1, this.timeMoCondVal2,
				this.dateCondVal1, this.dateCondVal2, this.charCondVal1, this.charCondVal2, this.numCondVal1,
				this.numCondVal2);
	}

}
