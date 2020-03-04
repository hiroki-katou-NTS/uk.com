package nts.uk.ctx.hr.develop.infra.entity.humanresourcedevevent;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JCMMT_HRDEV_EVENT")
public class JcmmtHRDevEvent extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EVENT_ID")
	public int eventId;

	@Column(name = "EVENT_NAME")
	public String eventName;

	@Column(name = "AVAILABLE")
	public int availableEvent;
	
	@Column(name = "MEMO")
	public String memo;
	

	@Override
	public Object getKey() {
		return eventId;
	}
}
