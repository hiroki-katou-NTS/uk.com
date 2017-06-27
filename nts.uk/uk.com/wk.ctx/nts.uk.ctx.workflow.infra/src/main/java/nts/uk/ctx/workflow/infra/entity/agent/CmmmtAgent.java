package nts.uk.ctx.workflow.infra.entity.agent;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


@Entity
@Table(name = "CMMMT_AGENT")
@AllArgsConstructor
@NoArgsConstructor
public class CmmmtAgent extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public CmmmtAgentPK cmmmtAgentPK;
	
	@Basic(optional = false)
	@Column(name = "START_DATE")
	public GeneralDate startDate;

	@Basic(optional = false)
	@Column(name = "END_DATE")
	public GeneralDate endDate;

	@Basic(optional = false)
	@Column(name = "AGENT_SID1")
	public String agentSid1;
	
	@Basic(optional = false)
	@Column(name = "AGENT_APP_TYPE1")
	public int agentAppType1;

	@Basic(optional = false)
	@Column(name = "AGENT_SID2")
	public String agentSid2;
	
	@Basic(optional = false)
	@Column(name = "AGENT_APP_TYPE2")
	public int agentAppType2;

	@Basic(optional = false)
	@Column(name = "AGENT_SID3")
	public String agentSid3;

	@Basic(optional = false)
	@Column(name = "AGENT_APP_TYPE3")
	public int agentAppType3;

	@Basic(optional = false)
	@Column(name = "AGENT_SID4")
	public String agentSid4;
	
	@Basic(optional = false)
	@Column(name = "AGENT_APP_TYPE4")
	public int agentAppType4;


	@Override
	protected CmmmtAgentPK getKey() {
		return this.cmmmtAgentPK;
	}

}
