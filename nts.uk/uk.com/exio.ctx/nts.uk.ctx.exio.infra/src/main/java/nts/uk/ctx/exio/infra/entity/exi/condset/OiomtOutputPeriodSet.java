package nts.uk.ctx.exio.infra.entity.exi.condset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exo.condset.OutputPeriodSetting;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * Entity 出力期間設定
 */
@Data
@Entity
@Table(name = "OIOMT_OUTPUT_PERIOD_SET")
@EqualsAndHashCode(callSuper = true)
public class OiomtOutputPeriodSet extends ContractUkJpaEntity implements OutputPeriodSetting.MementoGetter, OutputPeriodSetting.MementoSetter, Serializable {
	private static final long serialVersionUID = 1L;
	
	// column 排他バージョン
	@Version
	@Column(name = "EXCLUS_VER")
	private long version;
	
	// Embedded primary key 会社ID + 条件設定コード
	@EmbeddedId
	private OiomtOutputPeriodSetPk pk;
	
	// column 期間設定
	@Basic(optional = false)
	@Column(name = "PERIOD_SET")
	private int periodSetting;
	
	// column 締め日区分
	@Basic(optional = true)
	@Column(name = "CLOSUREDAY_ATR")
	private Integer closureDayAtr;

	// column 開始日区分
	@Basic(optional = true)
	@Column(name = "STARTDATE_CD_ATR")
	private Integer startDateClassification;

	// column 終了日区分
	@Basic(optional = true)
	@Column(name = "ENDDATE_CD_ATR")
	private Integer endDateClassification;

	// column 基準日区分
	@Basic(optional = true)
	@Column(name = "BASEDATE_ATR")
	private Integer baseDateClassification;

	// column 開始日調整
	@Basic(optional = true)
	@Column(name = "STARTDATE_ADJUST")
	private Integer startDateAdjustment;
	
	// column 終了日調整
	@Basic(optional = true)
	@Column(name = "ENDDATE_ADJUST")
	private Integer endDateAdjustment;
	
	// column 開始日指定
	@Basic(optional = true)
	@Column(name = "SPECIFY_STARTDATE")
	private GeneralDate startDateSpecify;
	
	// column 終了日指定
	@Basic(optional = true)
	@Column(name = "SPECIFY_ENDDATE")
	private GeneralDate endDateSpecify;
	
	// column 基準日指定
	@Basic(optional = true)
	@Column(name = "SPECIFY_BASE_DATE")
	private GeneralDate baseDateSpecify;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	@Override
	public void setCid(String cid) {
		if (this.pk == null) {
			this.pk = new OiomtOutputPeriodSetPk();
		}
		this.pk.setCId(cid);
	}

	@Override
	public void setConditionSetCode(String conditionSetCode) {
		if (this.pk == null) {
			this.pk = new OiomtOutputPeriodSetPk();
		}
		this.pk.setConditionSetCd(conditionSetCode);
	}

	@Override
	public String getCid() {
		if (this.pk != null) {
			return this.pk.cId;
		}
		return null;
	}

	@Override
	public String getConditionSetCode() {
		if (this.pk != null) {
			return this.pk.conditionSetCd;
		}
		return null;
	}
	
}
