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
 * 在席のステータス
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "MIMMT_SMILE_COOPERATION_ACCEPSET")
public class MimmtSmileCooperationAccepset extends ContractUkJpaEntity implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// Embedded primary key 社員ID
	@EmbeddedId
	private MimmtSmileCooperationAccepsetPK pk;

	// column Smile連携受入区分
	@NotNull
	@Column(name = "SMILE_COOPER_ACCEPT_ATR")
	private Boolean smileCooperAcceptAtr;

	// column Smile連携受入条件
	@Null
	@Column(name = "CONDITION_SET_CD")
	private String conditionSetCd;

	@Override
	protected Object getKey() {
		return this.pk;
	}
}