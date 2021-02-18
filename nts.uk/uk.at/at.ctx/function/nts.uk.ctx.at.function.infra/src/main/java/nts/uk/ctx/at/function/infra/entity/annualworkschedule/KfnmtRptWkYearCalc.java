package nts.uk.ctx.at.function.infra.entity.annualworkschedule;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * UKDesign.データベース.ER図.就業.contexts.就業機能.年間勤務表.項目の算出式
 * 
 * @author LienPTK
 *
 */
@Data
@Entity
@Table(name = "KFNMT_RPT_WK_YEAR_CALC")
@EqualsAndHashCode(callSuper = true)
public class KfnmtRptWkYearCalc extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	@EmbeddedId
	private KfnmtRptWkYearCalcPK id;
	
	/** 会社ID */
	@Column(name = "CID")
	private String cid;

	/** 排他バージョン */
	@Version
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** オペレーション: 加, 減 */
	@Column(name = "OPERATION")
	private int operator;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="LAYOUT_ID", referencedColumnName="LAYOUT_ID", insertable = false, updatable = false),
		@JoinColumn(name="ITEM_OUT_CD", referencedColumnName="ITEM_OUT_CD", insertable = false, updatable = false)
	})
	private KfnmtRptWkYearItem kfnmtRptWkYearItem;

	@Override
	protected Object getKey() {
		return this.id;
	}

}
