package nts.uk.ctx.at.schedule.infra.repository.schedule.alarm.simultaneousattendance.ban;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.Value;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban.ApplicableTimeZoneCls;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban.MaxOfNumberEmployeeTogether;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban.BanWorkTogether;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban.BanWorkTogetherCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban.BanWorkTogetherName;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban.BanWorkTogetherRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.simultaneousattendance.ban.KscmtAlchkBanWorkTogether;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.simultaneousattendance.ban.KscmtAlchkBanWorkTogetherDtl;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.simultaneousattendance.ban.KscmtAlchkBanWorkTogetherPk;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

/**
 * 
 * @author hiroko_miura
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaBanWorkTogetherRepository extends JpaRepository implements BanWorkTogetherRepository {

	private static String SELECT_TARGETUNIT = "SELECT * FROM KSCMT_ALCHK_BAN_WORK_TOGETHER"
			+ " WHERE CID = @companyId"
			+ " AND TARGET_UNIT = @targetUnit"
			+ " AND TARGET_ID = @targetId";
	
	private static String SELECT_TARGETUNIT_CODE = SELECT_TARGETUNIT + " AND CD = @code";
	
	@Override
	public void insert(String companyId, BanWorkTogether simulAttBan) {
		this.commandProxy().insert(KscmtAlchkBanWorkTogether.of(simulAttBan, companyId));
		this.commandProxy().insertAll(KscmtAlchkBanWorkTogetherDtl.toDetailList(simulAttBan, companyId));
	}

	@Override
	public void update(String companyId, BanWorkTogether simulAttBan) {
		val pk = new KscmtAlchkBanWorkTogetherPk();
		pk.companyId = companyId;
		pk.targetUnit = simulAttBan.getTargetOrg().getUnit().value;
		pk.targetId = simulAttBan.getTargetOrg().getTargetId();
		pk.code = simulAttBan.getBanWorkTogetherCode().v();
		
		KscmtAlchkBanWorkTogether updata = this.queryProxy().find(pk, KscmtAlchkBanWorkTogether.class).get();
		updata.name = simulAttBan.getBanWorkTogetherName().v();
		updata.upperLimit = simulAttBan.getUpperLimit().v();
		this.commandProxy().update(updata);
		
		
	}

	@Override
	public void delete(String companyId, TargetOrgIdenInfor targetOrg, BanWorkTogetherCode code) {
		
		Optional<BanWorkTogether> domain = this.get(companyId, targetOrg, code);
		
		if (domain.isPresent()) {
			KscmtAlchkBanWorkTogether entity = KscmtAlchkBanWorkTogether.of(domain.get(), companyId);
			List<KscmtAlchkBanWorkTogetherDtl> dtlEntity = KscmtAlchkBanWorkTogetherDtl.toDetailList(domain.get(), companyId);
			this.commandProxy().remove(entity);
			this.commandProxy().removeAll(dtlEntity);
		}
	}

	@Override
	public List<BanWorkTogether> getAll(String companyId, TargetOrgIdenInfor targetOrg) {
		String sql = SELECT_TARGETUNIT;
		
		
		return null;
	}

	@Override
	public Optional<BanWorkTogether> get(String companyId, TargetOrgIdenInfor targetOrg, BanWorkTogetherCode code) {
		Optional<KscmtAlchkBanWorkTogether> header = new NtsStatement(SELECT_TARGETUNIT_CODE, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramInt("targetUnit", targetOrg.getUnit().value)
				.paramString("targetId", targetOrg.getTargetId())
				.paramString("code", code.v())
				.getSingle(x -> KscmtAlchkBanWorkTogether.MAPPER.toEntity(x));
		
		
		return null;
	}

	@Override
	public boolean exists(String companyId, TargetOrgIdenInfor targetOrg, BanWorkTogetherCode code) {
		return this.get(companyId, targetOrg, code).isPresent();
	}

	
	@Value
	public static class Entities {
		KscmtAlchkBanWorkTogether header;
		
		List<KscmtAlchkBanWorkTogetherDtl> detailList;
		
		public BanWorkTogether toDomain() {
			TargetOrgIdenInfor targetOrg = TargetOrgIdenInfor.createFromTargetUnit(
					  TargetOrganizationUnit.valueOf(this.header.pk.targetUnit)
					, this.header.pk.targetId);
			
			List<String> sidList = this.detailList.stream().map(x -> x.pk.employeeId).collect(Collectors.toList());
			
			return new BanWorkTogether(
					  targetOrg
					, new BanWorkTogetherCode(this.header.pk.code)
					, new BanWorkTogetherName(this.header.name)
					, ApplicableTimeZoneCls.of(this.header.applyTs)
					, new MaxOfNumberEmployeeTogether(this.header.upperLimit)
					, sidList);
		}
	}
}
