package nts.uk.ctx.sys.shared.infra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "KRCST_TOPPAGEALARM_DETAIL")
@NoArgsConstructor
public class KrcstToppageAlarmDetail extends UkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcstToppageAlarmDetailPK krcstToppageAlarmDetailPK;

	/** エラーメッセージ */
	@Column(name = "ERROR_MESSAGE")
	public String errorMessage ;
	/** 対象社員ID */
	@Column(name = "TARGET_EMPLOYEE_ID")
	public String targerEmployee;
	
	@Override
	protected Object getKey() {
		return krcstToppageAlarmDetailPK;
	}

	public KrcstToppageAlarmDetail(KrcstToppageAlarmDetailPK krcstToppageAlarmDetailPK, String errorMessage,
			String targerEmployee) {
		super();
		this.krcstToppageAlarmDetailPK = krcstToppageAlarmDetailPK;
		this.errorMessage = errorMessage;
		this.targerEmployee = targerEmployee;
	}

	
}
