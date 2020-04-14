package nts.uk.ctx.at.schedule.infra.entity.employeeinfo.rank;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author phongtq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_SYA_RANK")
public class KscmtSyaRank  extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtSyaRankPk pk;
	
	/** ランクコード */
	@Column(name = "RANK_CD")
	public String emplRankCode;

	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public static KscmtSyaRank toEntity(EmployeeRank employeeRank){
		String CID = AppContexts.user().companyId();
		return new KscmtSyaRank(new KscmtSyaRankPk(CID, employeeRank.getSID()), employeeRank.getEmplRankCode().v());
	}
	
	public EmployeeRank toDomain(){
		return new EmployeeRank(pk.SID, new RankCode(this.emplRankCode));
	}
}
