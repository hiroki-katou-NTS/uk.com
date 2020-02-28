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
@Table(name = "JCMMT_HRDEV_MENU")
public class JcmmtHRDevMenu extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PROGRAM_ID")
	public String programId;

	@Column(name = "PROGRAM_NAME")
	public String programName;

	@Column(name = "EVENT_ID")
	public int eventId;
	
	@Column(name = "AVAILABLE")
	public int availableMenu;
	
	@Column(name = "AVAILABLE_NOTICE")
	public int availableNotice;
	
	@Column(name = "AVAILABLE_APPROVAL")
	public int availableApproval;
	
	@Column(name = "DSP_ORDER")
	public int dispOrder;

	@Override
	public Object getKey() {
		return programId;
	}
}
