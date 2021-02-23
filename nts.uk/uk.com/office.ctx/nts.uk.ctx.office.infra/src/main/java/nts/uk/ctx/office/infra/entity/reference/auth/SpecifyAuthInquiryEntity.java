package nts.uk.ctx.office.infra.entity.reference.auth;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/*
 * UKDesign.データベース.ER図.オフィス支援.在席照会.運用方法.OFIMT_AUTH_REFER
 *  在席照会の参照権限設定
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "OFIMT_AUTH_REFER")
public class SpecifyAuthInquiryEntity extends UkJpaEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// column 排他バージョン
	@Column(name = "EXCLUS_VER")
	private long version;

	// column 契約コード
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	private String contractCd;

	// Embedded primary key 会社ID and 就業ロールID and 見られる職位ID
	@EmbeddedId
	private SpecifyAuthInquiryEntityPK pk;

	@Override
	protected Object getKey() {
		return this.pk;
	}
}
