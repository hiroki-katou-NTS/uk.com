package nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.banholidaytogether;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.BanHolidayTogether;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_ALCHK_BAN_HD_TOGETHER_DTL")
public class KscmtAlchkBanHdTogetherDtl extends ContractUkJpaEntity{
	
	public static final JpaEntityMapper<KscmtAlchkBanHdTogetherDtl> MAPPER = new JpaEntityMapper<>(KscmtAlchkBanHdTogetherDtl.class);
	
	@EmbeddedId
	public KscmtAlchkBanHdTogetherDtlPk pk;
	
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public static List<KscmtAlchkBanHdTogetherDtl> fromDomain(String cid, BanHolidayTogether domain) {
		return domain.getEmpsCanNotSameHolidays().stream().map(c -> {
			return new KscmtAlchkBanHdTogetherDtl(
					new KscmtAlchkBanHdTogetherDtlPk(cid, domain.getTargetOrg().getUnit().value,
							domain.getTargetOrg().getTargetId(), domain.getBanHolidayTogetherCode().v(), c));
		}).collect(Collectors.toList());
	}
}
