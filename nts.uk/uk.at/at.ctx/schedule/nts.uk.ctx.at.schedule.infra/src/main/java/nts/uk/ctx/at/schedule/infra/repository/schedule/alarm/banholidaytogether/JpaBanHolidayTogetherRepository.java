package nts.uk.ctx.at.schedule.infra.repository.schedule.alarm.banholidaytogether;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.BanHolidayTogether;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.BanHolidayTogetherCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.BanHolidayTogetherRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.banholidaytogether.KscmtAlchkBanHdTogether;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.banholidaytogether.KscmtAlchkBanHdTogetherDtl;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaBanHolidayTogetherRepository extends JpaRepository implements BanHolidayTogetherRepository{

	private static final String SELECT_ALL_BAN_HD = "SELECT * FROM KSCMT_ALCHK_BAN_HD_TOGETHER ";
	
	private static final String SELECT_ALL_BAN_HD_DETAIL = "SELECT * FROM KSCMT_ALCHK_BAN_HD_TOGETHER_DTL";
	
	private static final String SELECT_ALL_BAN_HD_BY_ORG = SELECT_ALL_BAN_HD 
			+ " WHERE CID  = @cid"
			+ " AND TARGET_UNIT = @targetUnit"
			+ " AND TARGET_ID = @targetId";
	
	private static final String SELECT_ALL_BAN_HD_DETAIL_BY_ORG= SELECT_ALL_BAN_HD_DETAIL 
			+ " WHERE CID  = @cid"
			+ " AND TARGET_UNIT = @targetUnit"
			+ " AND TARGET_ID = @targetId";
	
	private static final String SELECT_BAN_HD_BY_ORG_AND_CD = SELECT_ALL_BAN_HD_BY_ORG
			+ " AND CD = @code";
	
	private static final String SELECT_BAN_HD_DETAIL_BY_ORG_AND_CD = SELECT_ALL_BAN_HD_DETAIL
			+ " WHERE CID  = @cid"
			+ " AND TARGET_UNIT = @targetUnit"
			+ " AND TARGET_ID = @targetId"
			+ " AND CD = @code";
	
	private static final String DELETE_BAN_HD_BY_ORG_AND_CD = "DELETE KSCMT_ALCHK_BAN_HD_TOGETHER "
			+ " WHERE CID =  @cid"
			+ " AND TARGET_UNIT = @targetUnit"
			+ " AND TARGET_ID = @targetId"
			+ " AND CD = @code";
	
	private static final String DELETE_BAN_HD_DETAIL_BY_ORG_AND_CD = "DELETE KSCMT_ALCHK_BAN_HD_TOGETHER_DTL "
			+ " WHERE CID =  @cid"
			+ " AND TARGET_UNIT = @targetUnit"
			+ " AND TARGET_ID = @targetId"
			+ " AND CD = @code";
	
	
	@Override
	public void insert(String cid, BanHolidayTogether banHdTogether) {
		KscmtAlchkBanHdTogether banHdTogetherEntity = KscmtAlchkBanHdTogether.fromDomain(cid, banHdTogether);
		List<KscmtAlchkBanHdTogetherDtl> details = KscmtAlchkBanHdTogetherDtl.fromDomain(cid, banHdTogether);
		this.commandProxy().insert(banHdTogetherEntity);
		this.getEntityManager().flush();
		this.commandProxy().insertAll(details);
	}

	@Override
	public void update(String cid, BanHolidayTogether banHdTogether) {
		KscmtAlchkBanHdTogether banHdTogetherEntity = KscmtAlchkBanHdTogether.fromDomain(cid, banHdTogether);
		List<KscmtAlchkBanHdTogetherDtl> details = KscmtAlchkBanHdTogetherDtl.fromDomain(cid, banHdTogether);
		this.commandProxy().update(banHdTogetherEntity);
		this.getEntityManager().flush();
		this.commandProxy().updateAll(details);
	}

	@Override
	public List<BanHolidayTogether> getAll(String cid, TargetOrgIdenInfor targetOrg) {
		List<KscmtAlchkBanHdTogether> banHdTogethers = new NtsStatement(SELECT_ALL_BAN_HD_BY_ORG, 
				this.jdbcProxy()).paramString("cid", cid)
								 .paramInt("targetUnit", targetOrg.getUnit().value)
								 .paramString("targetId", targetOrg.getTargetId())
								 .getList(c -> KscmtAlchkBanHdTogether.MAPPER.toEntity(c));

		List<KscmtAlchkBanHdTogetherDtl> banHdTogetherDetails = new NtsStatement(SELECT_ALL_BAN_HD_DETAIL_BY_ORG,
				this.jdbcProxy()).paramString("cid", cid)
				                 .paramInt("targetUnit", targetOrg.getUnit().value)
						         .paramString("targetId", targetOrg.getTargetId())
						         .getList(c -> KscmtAlchkBanHdTogetherDtl.MAPPER.toEntity(c));

		return banHdTogethers.stream().map(c -> {
			List<KscmtAlchkBanHdTogetherDtl> details = banHdTogetherDetails.stream()
					.filter(d -> d.pk.code.equals(c.pk.code))
					.collect(Collectors.toList());
			return c.toDomain(details);
		}).collect(Collectors.toList());
	}

	@Override
	public Optional<BanHolidayTogether> get(String cid, TargetOrgIdenInfor targetOrg, BanHolidayTogetherCode code) {
		Optional<KscmtAlchkBanHdTogether> banHdTogether = new NtsStatement(SELECT_BAN_HD_BY_ORG_AND_CD, 
				this.jdbcProxy()).paramString("cid", cid)
								 .paramInt("targetUnit", targetOrg.getUnit().value)
								 .paramString("targetId", targetOrg.getTargetId())
								 .paramString("code", code.v())
								 .getSingle(c -> KscmtAlchkBanHdTogether.MAPPER.toEntity(c));
		
		if(banHdTogether.isPresent()) {
			List<KscmtAlchkBanHdTogetherDtl> banHdTogetherDetails = new NtsStatement(SELECT_BAN_HD_DETAIL_BY_ORG_AND_CD,
					this.jdbcProxy()).paramString("cid", cid)
					                 .paramInt("targetUnit", targetOrg.getUnit().value)
							         .paramString("targetId", targetOrg.getTargetId())
							         .paramString("code", code.v())
							         .getList(c -> KscmtAlchkBanHdTogetherDtl.MAPPER.toEntity(c));
			return Optional.ofNullable(banHdTogether.get().toDomain(banHdTogetherDetails));
		}
		
		return Optional.empty();
	}

	@Override
	public boolean exists(String cid, TargetOrgIdenInfor targetOrg, BanHolidayTogetherCode  code) {
		Optional<BanHolidayTogether> banHdTogetherOpt = this.get(cid, targetOrg, code);
		return banHdTogetherOpt.isPresent();
	}

	@Override
	public void delete(String cid, TargetOrgIdenInfor targetInfo, BanHolidayTogetherCode code) {
		this.jdbcProxy().query(DELETE_BAN_HD_BY_ORG_AND_CD).paramString("cid", cid)
				.paramInt("targetUnit", targetInfo.getUnit().value)
				.paramString("targetId", targetInfo.getTargetId())
				.paramString("code", code.v()).execute();
		
		this.jdbcProxy().query(DELETE_BAN_HD_DETAIL_BY_ORG_AND_CD).paramString("cid", cid)
				.paramInt("targetUnit", targetInfo.getUnit().value)
				.paramString("targetId", targetInfo.getTargetId())
				.paramString("code", code.v()).execute();
	}
}
