package nts.uk.ctx.at.schedule.infra.repository.schedule.alarm.consecutivework.continuousworktime;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.ConsecutiveWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeOrganization;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeOrganizationRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.continuousworktime.KscmtAlchkConsecutiveWktmOrg;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.continuousworktime.KscmtAlchkConsecutiveWktmOrgDtl;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 
 * @author hiroko_miura
 *
 */
public class JpaMaxNumberDaysOfContWorkTimeOrgRepository extends JpaRepository implements MaxDaysOfContinuousWorkTimeOrganizationRepository {
	
	private static String SELECT_HEADER_WHERE_ORG = "SELECT * FROM KSCMT_ALCHK_CONSECUTIVE_WKTM_ORG"
			+ " WHERE CID = @companyId"
			+ " AND TARGET_UNIT = @targetUnit"
			+ " AND TARGET_ID = @targetId";
	
	private static String SELECT_HEADER_WHERE_ORG_AND_CODE = SELECT_HEADER_WHERE_ORG + " AND CD = @code";
	
	private static String SELECT_DETAIL_WHERE_ORG = "SELECT * FROM KSCMT_ALCHK_CONSECUTIVE_WKTM_ORG_DTL"
			+ " WHERE CID = @companyId"
			+ " AND TARGET_UNIT = @targetUnit"
			+ " AND TARGET_ID = @targetId";
	
	private static String SELECT_DETAIL_WHERE_ORG_AND_CODE = SELECT_DETAIL_WHERE_ORG + " AND CD = @code";
	

	@Override
	public void insert(String companyId, MaxDaysOfContinuousWorkTimeOrganization domain) {
		this.commandProxy().insert(KscmtAlchkConsecutiveWktmOrg.of(companyId, domain));
		this.commandProxy().insertAll(KscmtAlchkConsecutiveWktmOrgDtl.toDetailEntityList(companyId, domain));
	}

	@Override
	public void update(String companyId, MaxDaysOfContinuousWorkTimeOrganization domain) {
		this.commandProxy().update(KscmtAlchkConsecutiveWktmOrg.of(companyId, domain));
		this.commandProxy().updateAll(KscmtAlchkConsecutiveWktmOrgDtl.toDetailEntityList(companyId, domain));
	}

	@Override
	public void delete(String companyId, TargetOrgIdenInfor targeOrg, ConsecutiveWorkTimeCode code) {
		Optional<MaxDaysOfContinuousWorkTimeOrganization> domain = this.get(companyId, targeOrg, code);
		
		if (domain.isPresent()) {
			KscmtAlchkConsecutiveWktmOrg entity = KscmtAlchkConsecutiveWktmOrg.of(companyId, domain.get());
			List<KscmtAlchkConsecutiveWktmOrgDtl> dtlEntity = KscmtAlchkConsecutiveWktmOrgDtl.toDetailEntityList(companyId, domain.get());
			this.commandProxy().remove(entity);
			this.commandProxy().removeAll(dtlEntity);
		}
	}

	@Override
	public boolean exists(String companyId, TargetOrgIdenInfor targeOrg, ConsecutiveWorkTimeCode code) {
		return this.get(companyId, targeOrg, code).isPresent();
	}

	@Override
	public Optional<MaxDaysOfContinuousWorkTimeOrganization> get(String companyId, TargetOrgIdenInfor targeOrg, ConsecutiveWorkTimeCode code) {
		Optional<KscmtAlchkConsecutiveWktmOrg> header = new NtsStatement(SELECT_HEADER_WHERE_ORG_AND_CODE, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramInt("targetUnit", targeOrg.getUnit().value)
				.paramString("targetId", targeOrg.getTargetId())
				.paramString("code", code.v())
				.getSingle(x -> KscmtAlchkConsecutiveWktmOrg.MAPPER.toEntity(x));
		
		if (!header.isPresent())
			return Optional.empty();
		
		List<KscmtAlchkConsecutiveWktmOrgDtl> details = new NtsStatement(SELECT_DETAIL_WHERE_ORG_AND_CODE, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramInt("targetUnit", targeOrg.getUnit().value)
				.paramString("targetId", targeOrg.getTargetId())
				.paramString("code", code.v())
				.getList(x -> KscmtAlchkConsecutiveWktmOrgDtl.MAPPER.toEntity(x));
		
		return Optional.of(header.get().toDomain(details));
	}

	@Override
	public List<MaxDaysOfContinuousWorkTimeOrganization> getAll(String companyId, TargetOrgIdenInfor targeOrg) {
		List<KscmtAlchkConsecutiveWktmOrg> headers = new NtsStatement(SELECT_HEADER_WHERE_ORG, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramInt("targetUnit", targeOrg.getUnit().value)
				.paramString("targetId", targeOrg.getTargetId())
				.getList(x -> KscmtAlchkConsecutiveWktmOrg.MAPPER.toEntity(x));
		
		if (headers.isEmpty())
			return Collections.emptyList();
		
		List<KscmtAlchkConsecutiveWktmOrgDtl> alldetails = new NtsStatement(SELECT_DETAIL_WHERE_ORG, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramInt("targetUnit", targeOrg.getUnit().value)
				.paramString("targetId", targeOrg.getTargetId())
				.getList(x -> KscmtAlchkConsecutiveWktmOrgDtl.MAPPER.toEntity(x));
		
		return headers.stream().map(head -> {

			List<KscmtAlchkConsecutiveWktmOrgDtl> details = alldetails.stream()
					.filter(d -> d.pk.code == head.pk.code)
					.collect(Collectors.toList());
			
			return head.toDomain(details);
			
		}).collect(Collectors.toList());
	}
}
