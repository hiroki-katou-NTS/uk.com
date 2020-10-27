package nts.uk.ctx.workflow.infra.entity.agent;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


@Entity
@Table(name = "WWFMT_AGENT")
@AllArgsConstructor
@NoArgsConstructor
public class WwfmtAgent extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<WwfmtAgent> MAPPER = new JpaEntityMapper<>(WwfmtAgent.class);

	@EmbeddedId
	public WwfmtAgentPK wwfmtAgentPK;

	/**開始日*/
	@Basic(optional = false)
	@Column(name = "START_DATE")
	public GeneralDate startDate;
	
	/**終了日*/
	@Basic(optional = false)
	@Column(name = "END_DATE")
	public GeneralDate endDate;
	
	/**就業承認: 社員ID*/
	@Basic(optional = false)
	@Column(name = "AGENT_SID1")
	public String agentSid1;
	
	/**就業承認: 代行承認種類1*/
	@Basic(optional = false)
	@Column(name = "AGENT_APP_TYPE1")
	public int agentAppType1;
	
	/**人事承認: 社員ID*/
	@Basic(optional = false)
	@Column(name = "AGENT_SID2")
	public String agentSid2;
	
	/**人事承認: 代行承認種類2*/
	@Basic(optional = false)
	@Column(name = "AGENT_APP_TYPE2")
	public int agentAppType2;

	/**給与承認: 社員ID*/
	@Basic(optional = false)
	@Column(name = "AGENT_SID3")
	public String agentSid3;
	
	/**給与承認: 代行承認種類3*/
	@Basic(optional = false)
	@Column(name = "AGENT_APP_TYPE3")
	public int agentAppType3;
	
	/**経理承認: 社員ID*/
	@Basic(optional = false)
	@Column(name = "AGENT_SID4")
	public String agentSid4;

	/**経理承認: 代行承認種類4*/
	@Basic(optional = false)
	@Column(name = "AGENT_APP_TYPE4")
	public int agentAppType4;


	@Override
	protected WwfmtAgentPK getKey() {
		return this.wwfmtAgentPK;
	}

	public static WwfmtAgent fromJdbc(NtsResultSet.NtsResultRecord rec) {
		WwfmtAgent ent = new WwfmtAgent();
		ent.wwfmtAgentPK = new WwfmtAgentPK(
				rec.getString("CID"),
				rec.getString("SID"),
				rec.getString("REQUEST_ID"));
		ent.startDate = rec.getGeneralDate("START_DATE");
		ent.endDate = rec.getGeneralDate("END_DATE");
		ent.agentSid1 = rec.getString("AGENT_SID1");
		ent.agentAppType1 = rec.getInt("AGENT_APP_TYPE1");
		ent.agentSid2 = rec.getString("AGENT_SID2");
		ent.agentAppType2 = rec.getInt("AGENT_APP_TYPE2");
		ent.agentSid3 = rec.getString("AGENT_SID3");
		ent.agentAppType3 = rec.getInt("AGENT_APP_TYPE3");
		ent.agentSid4 = rec.getString("AGENT_SID4");
		ent.agentAppType4 = rec.getInt("AGENT_APP_TYPE4");
		return ent;
	}
}
