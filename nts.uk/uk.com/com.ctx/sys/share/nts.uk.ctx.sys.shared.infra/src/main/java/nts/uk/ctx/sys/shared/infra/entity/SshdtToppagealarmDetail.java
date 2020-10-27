package nts.uk.ctx.sys.shared.infra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "SSHDT_TOPPAGEALARM_DETAIL")
@NoArgsConstructor
public class SshdtToppagealarmDetail extends ContractUkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public SshdtToppagealarmDetailPK sshdtToppagealarmDetailPK;

	/** エラーメッセージ */
	@Column(name = "ERROR_MESSAGE")
	public String errorMessage ;
	/** 対象社員ID */
	@Column(name = "TARGET_EMPLOYEE_ID")
	public String targerEmployee;
	
	@Override
	protected Object getKey() {
		return sshdtToppagealarmDetailPK;
	}

	public SshdtToppagealarmDetail(SshdtToppagealarmDetailPK sshdtToppagealarmDetailPK, String errorMessage,
			String targerEmployee) {
		super();
		this.sshdtToppagealarmDetailPK = sshdtToppagealarmDetailPK;
		this.errorMessage = errorMessage;
		this.targerEmployee = targerEmployee;
	}

	
}
