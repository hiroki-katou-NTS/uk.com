package nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.common.timestamp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.PriorityTimeReflectAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimePriority;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author tutk
 *
 */
@Getter
@Setter
@Entity
@Table(name = "KRCMT_DAY_CLOCK_PRIORITY")
@NoArgsConstructor
public class KrcmtTimePriority extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID
	 */
	@Id
	@Column(name = "CID")
	private String cid;

	/**
	 * 反映時刻優先
	 */
	/** The exclus ver. */
	@Column(name = "REFLECT_TIME_PRIORITY")
	private int reflectTimePriority;
	
	@Override
	protected Object getKey() {
		return cid;
	}

	public TimePriority todomain() {
		return new TimePriority(this.cid, EnumAdaptor.valueOf(reflectTimePriority, PriorityTimeReflectAtr.class));
	}

	public static KrcmtTimePriority toEntity(TimePriority domain) {
		return new KrcmtTimePriority(domain.getCompanyId(), domain.getPriorityTimeReflectAtr().value);

	}

	public KrcmtTimePriority(String cid, int reflectTimePriority) {
		super();
		this.cid = cid;
		this.reflectTimePriority = reflectTimePriority;
	}
	

}
