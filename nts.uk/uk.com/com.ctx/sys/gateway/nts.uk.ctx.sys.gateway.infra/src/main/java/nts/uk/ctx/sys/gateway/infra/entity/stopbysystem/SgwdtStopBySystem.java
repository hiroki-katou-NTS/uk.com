package nts.uk.ctx.sys.gateway.infra.entity.stopbysystem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * システム全体の利用停止の設定.
 * 
 * @author sonnlb
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "SGWMT_STOP_BY_SYSTEM")

public class SgwdtStopBySystem extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 契約コード */
	@Id
	@Column(name = "CONTRACT_CD")
	public String contractCd;

	/** システム利用状態 */
	@Column(name = "SYSTEM_STATUS_TYPE")
	public int systemStatus;

	/** 停止予告のメッセージ */
	@Column(name = "STOP_MESSAGE")
	public String stopMessage;

	/** 利用停止モード */
	@Column(name = "STOP_MODE_TYPE")
	public Integer stopMode;

	/** 利用停止のメッセージ */
	@Column(name = "USAGE_STOP_MESSAGE")
	public String usageStopMessage;

	@Override
	protected Object getKey() {
		return this.contractCd;
	}
}
