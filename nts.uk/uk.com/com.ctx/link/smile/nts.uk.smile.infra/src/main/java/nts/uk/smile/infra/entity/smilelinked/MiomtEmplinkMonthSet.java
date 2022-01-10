package nts.uk.smile.infra.entity.smilelinked;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/*
 * UKDesign.データベース.ER図.オフィス支援.在席照会.ステータス.OFIDT_PRESENT_STATUS
 * 雇用と連動月設定
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "MIOMT_EMPLINK_MONTHSET")
public class MiomtEmplinkMonthSet extends ContractUkJpaEntity implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// Embedded primary key 社員ID
	@EmbeddedId
	private MiomtEmplinkMonthSetPK pk;

	// column 連動月調整
	@NotNull
	@Column(name = "MIOMT_EMPLINK_MONTHSET")
	private Integer miomtEmplinkMonthSet;

	@Override
	protected Object getKey() {
		return this.pk;
	}
}