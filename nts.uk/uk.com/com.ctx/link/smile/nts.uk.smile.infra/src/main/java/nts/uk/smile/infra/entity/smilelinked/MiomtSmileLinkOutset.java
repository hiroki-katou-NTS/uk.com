package nts.uk.smile.infra.entity.smilelinked;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/*
 * UKDesign.データベース.ER図.オフィス支援.在席照会.ステータス.OFIDT_PRESENT_STATUS
 * Smile連携出力設定
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "MIOMT_SMILE_LINK_OUTSET")
public class MiomtSmileLinkOutset extends ContractUkJpaEntity implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// Embedded primary key 社員ID
	@EmbeddedId
	private MiomtSmileLinkOutsetPK pk;

	// column 給与連携区分
	@NotNull
	@Column(name = "SALARY_COOPERATION_ATR")
	private Integer salaryCooperationtAtr;

	// column 給与連携条件
	@Null
	@Column(name = "SALARY_COOPERATION_COND")
	private String salaryCooperationCond;

	// column 月次ロック区分
	@NotNull
	@Column(name = "MONTH_LOCK_ATR")
	private Integer monthLockAtr;

	// column 月次承認区分
	@NotNull
	@Column(name = "MONTH_APPRO_ATR")
	private Integer monthApproAtr;

	@Override
	protected Object getKey() {
		return this.pk;
	}
}