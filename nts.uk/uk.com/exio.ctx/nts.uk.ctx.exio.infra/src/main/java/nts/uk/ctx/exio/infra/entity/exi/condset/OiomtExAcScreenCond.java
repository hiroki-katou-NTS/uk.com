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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exi.condset.AcScreenCondSet;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;
import nts.uk.ctx.exio.infra.entity.exi.item.OiomtExAcItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 受入選別条件設定
 */

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "OIOMT_EX_AC_SCREEN_COND")
public class OiomtExAcScreenCond extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtAcScreenCondSetPk acScreenCondSetPk;
	/**	契約コード */
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	public String contractCd;
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
			@JoinColumn(name = "CONDITION_SET_CD", referencedColumnName = "CONDITION_SET_CD", insertable = false, updatable = false),
			@JoinColumn(name = "ACCEPT_ITEM_NUM", referencedColumnName = "ACCEPT_ITEM_NUMBER", insertable = false, updatable = false) })
	public OiomtExAcItem acceptItem;

	@Override
	protected Object getKey() {
		return acScreenCondSetPk;
	}

	public OiomtExAcScreenCond(String cid, String conditionCode, int acceptItemNum) {
		super();
		this.acScreenCondSetPk = new OiomtAcScreenCondSetPk(cid, conditionCode, acceptItemNum);
		this.contractCd = AppContexts.user().contractCode();
	}

	public static OiomtExAcScreenCond fromDomain(StdAcceptItem item, AcScreenCondSet domain) {
		return new OiomtExAcScreenCond(item.getCid(), item.getConditionSetCd().v(),
				item.getAcceptItemNumber());
	}

	public AcScreenCondSet toDomain() {
		return new AcScreenCondSet(this.acScreenCondSetPk.conditionSetCd, this.acScreenCondSetPk.acceptItemNum);
	}

}
