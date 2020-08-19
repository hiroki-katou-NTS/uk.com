package nts.uk.ctx.exio.infra.entity.exi.condset;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exo.condset.BaseDateClassificationCode;
import nts.uk.ctx.exio.dom.exo.condset.DateAdjustment;
import nts.uk.ctx.exio.dom.exo.condset.EndDateClassificationCode;
import nts.uk.ctx.exio.dom.exo.condset.ExternalOutputConditionCode;
import nts.uk.ctx.exio.dom.exo.condset.OutputPeriodSetting;
import nts.uk.ctx.exio.dom.exo.condset.StartDateClassificationCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * Entity 出力期間設定
 */
@Entity
@Table(name = "OIOMT_OUTPUT_PERIOD_SET")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OiomtOutputPeriodSet extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// column 排他バージョン
	@Version
	@Column(name = "EXCLUS_VER")
	private Long version;
	
	// Embedded primary key 会社ID + 条件設定コード
	@EmbeddedId
	private OiomtOutputPeriodSetPk pk;

	// column 契約コード
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	private String contractCd;
	
	// column 期間設定
	@Basic(optional = false)
	@Column(name = "PERIOD_SET")
	private Boolean periodSet;
	
	// column 締め日区分
	@Basic(optional = true)
	@Column(name = "CLOSUREDAY_ATR")
	private Integer closuredayAtr;

	// column 開始日区分
	@Basic(optional = true)
	@Column(name = "STARTDATE_CD_ATR")
	private Integer startDateCdAtr;

	// column 終了日区分
	@Basic(optional = true)
	@Column(name = "ENDDATE_CD_ATR")
	private Integer endDateCdAtr;

	// column 基準日区分
	@Basic(optional = true)
	@Column(name = "BASEDATE_ATR")
	private Integer baseDateAtr;

	// column 開始日調整
	@Basic(optional = true)
	@Column(name = "STARTDATE_ADJUST")
	private Integer startDateAdjust;
	
	// column 終了日調整
	@Basic(optional = true)
	@Column(name = "ENDDATE_ADJUST")
	private Integer endDateAdjust;
	
	// column 開始日指定
	@Basic(optional = true)
	@Column(name = "SPECIFY_STARTDATE")
	private GeneralDate specifyStartDate;
	
	// column 終了日指定
	@Basic(optional = true)
	@Column(name = "SPECIFY_ENDDATE")
	private GeneralDate specifyEndDate;
	
	// column 基準日指定
	@Basic(optional = true)
	@Column(name = "SPECIFY_BASE_DATE")
	private GeneralDate specifyBaseDate;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public OutputPeriodSetting toDomain() {
		return OutputPeriodSetting.builder()
				.cid(this.pk.cId)
				.periodSetting(this.periodSet ? NotUseAtr.USE : NotUseAtr.NOT_USE)
				.conditionSetCode(new ExternalOutputConditionCode(this.pk.conditionSetCd))
				.deadlineClassification(Optional.ofNullable(this.closuredayAtr))
				.baseDateClassification(this.baseDateAtr != null 
						? Optional.of(EnumAdaptor.valueOf(this.baseDateAtr, BaseDateClassificationCode.class))
						: Optional.empty())
				.baseDateSpecify(Optional.ofNullable(this.specifyBaseDate))
				.startDateClassification(this.startDateCdAtr != null 
						? Optional.of(EnumAdaptor.valueOf(this.startDateCdAtr, StartDateClassificationCode.class))
						: Optional.empty())
				.startDateSpecify(Optional.ofNullable(this.specifyStartDate))
				.startDateAdjustment(this.startDateAdjust != null 
						? Optional.of(EnumAdaptor.valueOf(this.startDateAdjust, DateAdjustment.class))
						: Optional.empty())
				.endDateClassification(this.endDateCdAtr != null 
						? Optional.of(EnumAdaptor.valueOf(this.endDateCdAtr, EndDateClassificationCode.class))
						: Optional.empty())
				.endDateSpecify(Optional.ofNullable(this.specifyEndDate))
				.endDateAdjustment(this.endDateAdjust != null 
						? Optional.of(EnumAdaptor.valueOf(this.endDateAdjust, DateAdjustment.class))
						: Optional.empty())
				.build();
	}
	
}
