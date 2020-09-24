package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.bansamedayholiday;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_ALCHK_BAN_HD_TOGETHER_DTL")
public class KscmtAlchkBanHdTogetherDtl extends ContractUkJpaEntity{
	
	@EmbeddedId
	public KscmtAlchkBanHdTogetherDtlPk pk;
	
	
	@ManyToOne
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "TARGET_UNIT", referencedColumnName = "TARGET_UNIT"),
			@PrimaryKeyJoinColumn(name = "TARGET_ID", referencedColumnName = "TARGET_ID"),
			@PrimaryKeyJoinColumn(name = "CD", referencedColumnName = "CD")})
	public KscmtAlchkBanHdBanTogether kscmtAlchkBanHdBanTogether;
	
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

}
