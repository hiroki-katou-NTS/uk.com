package nts.uk.smile.infra.entity.smilelinked;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/*
 * UKDesign.データベース.ER図.オフィス支援.在席照会.ステータス.OFIDT_PRESENT_STATUS
 * 雇用と連動月設定
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "LSMMT_EMPLINK_MONTHSET")
@AllArgsConstructor
@NoArgsConstructor
public class LsmmtEmplinkMonthSet extends UkJpaEntity implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// Embedded primary key 社員ID
	@EmbeddedId
	private LsmmtEmplinkMonthSetPK pk;

	// column 連動月調整
	@NotNull
	@Column(name = "MIOMT_EMPLINK_MONTHSET")
	private Integer miomtEmplinkMonthSet;

	@Override
	protected Object getKey() {
		return this.pk;
	}
}