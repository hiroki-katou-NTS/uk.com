package nts.uk.ctx.at.record.infra.entity.divergence.time.message;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * The persistent class for the KRCMT_DVGC_ERAL_MSG_BUS database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="KRCMT_DVGC_ERAL_MSG_BUS")
public class KrcmtDvgcEralMsgBus extends ContractUkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private KrcmtDvgcEralMsgBusPK id;

	/** The alarm message. */
	@Column(name="ALARM_MESSAGE")
	private String alarmMessage;

	/** The error message. */
	@Column(name="ERROR_MESSAGE")
	private String errorMessage;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.id;
	}

}