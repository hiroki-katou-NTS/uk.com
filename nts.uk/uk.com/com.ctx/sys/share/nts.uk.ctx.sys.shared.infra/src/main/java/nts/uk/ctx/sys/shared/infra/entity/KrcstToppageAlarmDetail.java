package nts.uk.ctx.sys.shared.infra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "KRCST_TOPPAGEALARM_DETAIL")
public class KrcstToppageAlarmDetail extends UkJpaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcstToppageAlarmDetailPK krcstToppageAlarmDetailPK;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;
	/** 管理社員ID */
	@Column(name = "MANAGER_ID")
	public String managerId;
	/** 実行完了日時 */
	@Column(name = "FINISH_DATE_TIME")
	public GeneralDateTime finishDateTime;
	/** エラーメッセージ */
	@Column(name = "ERROR_MESSAGE")
	public String errorMessage ;
	/** 対象社員ID */
	@Column(name = "TARGET_EMPLOYEE_ID")
	public String targerEmployee;
	
	@Override
	protected Object getKey() {
		return null;
	}

}
