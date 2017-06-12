package nts.uk.ctx.workflow.infra.entity.agent;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "CMMMT_AGENT")
@AllArgsConstructor
@NoArgsConstructor
public class CmmmtAgent extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public CmmmtAgentPK cmmmtAgentPK;

	@Basic(optional = false)
	@Column(name = "END_DATE")
	public String endDate;

	@Basic(optional = false)
	@Column(name = "AGENT_APP_TYPE1")
	public String agentAppType1;

	@Basic(optional = false)
	@Column(name = "AGENT_SID1")
	public String agentSid1;

	@Basic(optional = false)
	@Column(name = "AGENT_APP_TYPE2")
	public String agentAppType2;

	@Basic(optional = false)
	@Column(name = "AGENT_SID2")
	public String agentSid2;

	@Basic(optional = false)
	@Column(name = "AGENT_APP_TYPE3")
	public String agentAppType3;

	@Basic(optional = false)
	@Column(name = "AGENT_SID3")
	public String agentSid3;
	
	@Basic(optional = false)
	@Column(name = "AGENT_APP_TYPE4")
	public String agentAppType4;

	@Basic(optional = false)
	@Column(name = "AGENT_SID4")
	public String agentSid4;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
