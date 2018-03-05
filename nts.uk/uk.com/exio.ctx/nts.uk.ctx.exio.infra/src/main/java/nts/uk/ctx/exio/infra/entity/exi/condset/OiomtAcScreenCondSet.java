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
import nts.uk.ctx.exio.infra.entity.exi.item.OiomtStdAcceptItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 受入選別条件設定
 */

@NoArgsConstructor
@Entity
@Table(name = "OIOMT_AC_SCREEN_COND_SET")
public class OiomtAcScreenCondSet extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtAcScreenCondSetPk acScreenCondSetPk;

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
			@JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID", insertable = false, updatable = false),
			@JoinColumn(name = "ACCEPT_ITEM_NUM", referencedColumnName = "ACCEPT_ITEM_NUMBER", insertable = false, updatable = false) })
	public OiomtStdAcceptItem acceptItem;

	@Override
	protected Object getKey() {
		return acScreenCondSetPk;
	}

	public OiomtAcScreenCondSet(String cid, int sysType, String conditionCode, String categoryId, int acceptItemNum, Integer selCompareCond,
			Integer timeCondVal1, Integer timeCondVal2, Integer timeMoCondVal1, Integer timeMoCondVal2,
			GeneralDate dateCondVal1, GeneralDate dateCondVal2, String charCondVal1, String charCondVal2,
			BigDecimal numCondVal1, BigDecimal numCondVal2) {
		super();
		this.acScreenCondSetPk = new OiomtAcScreenCondSetPk(cid, sysType, conditionCode, categoryId, acceptItemNum);
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

	public static OiomtAcScreenCondSet fromDomain(AcScreenCondSet domain) {
		return new OiomtAcScreenCondSet();
//		return new OiomtAcScreenCondSet(domain.getCid(), domain.getConditionSetCd(), domain.getAcceptItemNum(),
//				domain.getSelCompareCond(), domain.getTimeCondVal1(), domain.getTimeCondVal2(),
//				domain.getTimeMoCondVal1(), domain.getTimeMoCondVal2(), domain.getDateCondVal1(),
//				domain.getDateCondVal2(), domain.getDateCondVal2(), domain.getCharCondVal1(), domain.getCharCondVal2(),
//				domain.getNumCondVal1(), domain.getNumCondVal2());
	}

	public static AcScreenCondSet toDomain(OiomtAcScreenCondSet entity) {
		return null;//new AcScreenCondSet();
	}

}
