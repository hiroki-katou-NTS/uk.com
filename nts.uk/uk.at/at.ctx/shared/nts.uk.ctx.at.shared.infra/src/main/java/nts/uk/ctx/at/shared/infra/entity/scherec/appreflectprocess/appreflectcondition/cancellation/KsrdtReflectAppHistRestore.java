package nts.uk.ctx.at.shared.infra.entity.scherec.appreflectprocess.appreflectcondition.cancellation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 申請反映前の勤怠
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KSRDT_REFLECT_APP_HIST_RESTORE")
public class KsrdtReflectAppHistRestore extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KsrdtReflectAppHistRestorePK pk;

	// 会社ID
	@Column(name = "CID")
	public String cid;

	// 値
	@Column(name = "VALUE")
	public String value;

	// 編集状態
	@Column(name = "STATUS")
	public Integer status;

	@Override
	protected Object getKey() {
		return pk;
	}

}
