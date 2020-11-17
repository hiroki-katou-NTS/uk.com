package nts.uk.ctx.at.schedule.infra.repository.schedule.alarm.worktogether.ban;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogether;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogetherCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban.BanWorkTogetherRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.worktogether.ban.KscmtAlchkBanWorkTogether;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.worktogether.ban.KscmtAlchkBanWorkTogetherDtl;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.worktogether.ban.KscmtAlchkBanWorkTogetherDtlPk;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.worktogether.ban.KscmtAlchkBanWorkTogetherPk;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 同時出勤禁止Repository
 * @author hiroko_miura
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaBanWorkTogetherRepository extends JpaRepository implements BanWorkTogetherRepository {

	private static String SELECT_HEADER_WHERE_ORG = "SELECT * FROM KSCMT_ALCHK_BAN_WORK_TOGETHER"
			+ " WHERE CID = @companyId"
			+ " AND TARGET_UNIT = @targetUnit"
			+ " AND TARGET_ID = @targetId";
	
	private static String SELECT_HEADER_WHERE_ORG_CODE = SELECT_HEADER_WHERE_ORG + " AND CD = @code";
	
	private static String SESECT_DETAIL_WHERE_ORG = "SELECT * FROM KSCMT_ALCHK_BAN_WORK_TOGETHER_DTL"
			+ " WHERE CID = @companyId"
			+ " AND TARGET_UNIT = @targetUnit"
			+ " AND TARGET_ID = @targetId";
	
	private static String SESECT_DETAIL_WHERE_ORG_CODE = SESECT_DETAIL_WHERE_ORG +" AND CD = @code";
	
	@Override
	public void insert(String companyId, BanWorkTogether banWorkTogether) {
		this.commandProxy().insert(KscmtAlchkBanWorkTogether.of(banWorkTogether, companyId));
		this.commandProxy().insertAll(KscmtAlchkBanWorkTogetherDtl.toDetailEntityList(banWorkTogether, companyId));
	}

	@Override
	public void update(String companyId, BanWorkTogether banWorkTogether) {
		Optional<BanWorkTogether> domain = this.get(companyId, banWorkTogether.getTargetOrg(), banWorkTogether.getCode());

		if (domain.isPresent()) {
			List<KscmtAlchkBanWorkTogetherDtlPk> dtlEntityKey = KscmtAlchkBanWorkTogetherDtl.toDetailEntityList(domain.get(), companyId)
					.stream().map(i -> i.pk).collect(Collectors.toList());
			this.commandProxy().removeAll(KscmtAlchkBanWorkTogetherDtl.class, dtlEntityKey);
			this.getEntityManager().flush();
			this.commandProxy().insertAll(KscmtAlchkBanWorkTogetherDtl.toDetailEntityList(banWorkTogether, companyId));
			this.commandProxy().update(KscmtAlchkBanWorkTogether.of(banWorkTogether, companyId));
		}

	}

	@Override
	public void delete(String companyId, TargetOrgIdenInfor targetOrg, BanWorkTogetherCode code) {
		
		Optional<BanWorkTogether> domain = this.get(companyId, targetOrg, code);
		
		if (domain.isPresent()) {
			KscmtAlchkBanWorkTogetherPk key = KscmtAlchkBanWorkTogether.of(domain.get(), companyId).pk;
			List<KscmtAlchkBanWorkTogetherDtlPk> dtlEntityKey = KscmtAlchkBanWorkTogetherDtl.toDetailEntityList(domain.get(), companyId)
					.stream().map(i -> i.pk).collect(Collectors.toList());
			this.commandProxy().remove(KscmtAlchkBanWorkTogether.class, key);
			this.commandProxy().removeAll(KscmtAlchkBanWorkTogetherDtl.class, dtlEntityKey);
		}
	}

	@Override
	public List<BanWorkTogether> getAll(String companyId, TargetOrgIdenInfor targetOrg) {
		List<KscmtAlchkBanWorkTogether> headers = new NtsStatement(SELECT_HEADER_WHERE_ORG, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramInt("targetUnit", targetOrg.getUnit().value)
				.paramString("targetId", targetOrg.getTargetId())
				.getList(x -> KscmtAlchkBanWorkTogether.MAPPER.toEntity(x));
		
		
		if(headers.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<KscmtAlchkBanWorkTogetherDtl> alldetails = new NtsStatement(SESECT_DETAIL_WHERE_ORG, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramInt("targetUnit", targetOrg.getUnit().value)
				.paramString("targetId", targetOrg.getTargetId())
				.getList(x -> KscmtAlchkBanWorkTogetherDtl.MAPPER.toEntity(x));
		
		return headers.stream().map(head -> {

			List<KscmtAlchkBanWorkTogetherDtl> details = alldetails.stream()
					.filter(d -> d.pk.code.equals(head.pk.code)).collect(Collectors.toList());
			
			return head.toDomain(details);
			
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<BanWorkTogether> get(String companyId, TargetOrgIdenInfor targetOrg, BanWorkTogetherCode code) {
		Optional<KscmtAlchkBanWorkTogether> header = new NtsStatement(SELECT_HEADER_WHERE_ORG_CODE, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramInt("targetUnit", targetOrg.getUnit().value)
				.paramString("targetId", targetOrg.getTargetId())
				.paramString("code", code.v())
				.getSingle(x -> KscmtAlchkBanWorkTogether.MAPPER.toEntity(x));
		
		if (!header.isPresent()) {
			return Optional.empty();
		}
		
		List<KscmtAlchkBanWorkTogetherDtl> details = new NtsStatement(SESECT_DETAIL_WHERE_ORG_CODE, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramInt("targetUnit", targetOrg.getUnit().value)
				.paramString("targetId", targetOrg.getTargetId())
				.paramString("code", code.v())
				.getList(x -> KscmtAlchkBanWorkTogetherDtl.MAPPER.toEntity(x));
		
		
		return Optional.of(header.get().toDomain(details));
	}

	@Override
	public boolean exists(String companyId, TargetOrgIdenInfor targetOrg, BanWorkTogetherCode code) {
		return this.get(companyId, targetOrg, code).isPresent();
	}
}
