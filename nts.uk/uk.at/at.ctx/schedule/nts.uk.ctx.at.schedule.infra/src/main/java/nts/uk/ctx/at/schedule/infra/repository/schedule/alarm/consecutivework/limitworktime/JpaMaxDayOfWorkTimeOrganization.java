package nts.uk.ctx.at.schedule.infra.repository.schedule.alarm.consecutivework.limitworktime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeOrganization;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeOrganizationRepo;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.limitworktime.*;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaMaxDayOfWorkTimeOrganization extends JpaRepository implements MaxDayOfWorkTimeOrganizationRepo {
	
	public static final String SELECT_MAX_DAYS_WKTM = "select * from KSCMT_ALCHK_MAXDAYS_WKTM_ORG"
			+ " where CID = @companyId"
			+ " and TARGET_UNIT = @targetUnit"
			+ " and TARGET_ID = @targetId"
			+ " and CD = @code";
	
	public static final String SELECT_MAX_DAYS_WKTM_DTL = "select * from KSCMT_ALCHK_MAXDAYS_WKTM_ORG_DTL"
			+ " where CID = @companyId"
			+ " and TARGET_UNIT = @targetUnit"
			+ " and TARGET_ID = @targetId"
			+ " and CD = @code";
	
	public static final String SELECT_ALL_MAX_DAYS_WKTM = "select * from KSCMT_ALCHK_MAXDAYS_WKTM_ORG"
			+ " where CID = @companyId"
			+ " and TARGET_UNIT = @targetUnit"
			+ " and TARGET_ID = @targetId";
	
	public static final String SELECT_ALL_MAX_DAYS_WKTM_DTL = "select * from KSCMT_ALCHK_MAXDAYS_WKTM_ORG_DTL"
			+ " where CID = @companyId"
			+ " and TARGET_UNIT = @targetUnit"
			+ " and TARGET_ID = @targetId";

	@Override
	public void insert(String companyId, MaxDayOfWorkTimeOrganization domain) {
		KscmtAlchkMaxdaysWktmOrg maxDaysWktm = KscmtAlchkMaxdaysWktmOrg.fromDomain(companyId, domain);
		List<KscmtAlchkMaxdaysWktmOrgDtl> maxDaysWktmDtl = KscmtAlchkMaxdaysWktmOrgDtl.fromDomain(companyId, domain);
		
		this.commandProxy().insert(maxDaysWktm);
		this.commandProxy().insertAll(maxDaysWktmDtl);
		
	}

	@Override
	public void update(String companyId, MaxDayOfWorkTimeOrganization domain) {
		KscmtAlchkMaxdaysWktmOrg maxDaysWktm = KscmtAlchkMaxdaysWktmOrg.fromDomain(companyId, domain);
		maxDaysWktm.contractCd= AppContexts.user().contractCode();
		List<KscmtAlchkMaxdaysWktmOrgDtl> maxDaysWktmDtl = KscmtAlchkMaxdaysWktmOrgDtl.fromDomain(companyId, domain);
		List<WorkTimeCode> codeList=getWithCode(companyId,domain.getTargeOrg(),new MaxDayOfWorkTimeCode(domain.getCode().v())).orElseGet(null).getMaxDayOfWorkTime().getWorkTimeCodeList();
		for (WorkTimeCode codeDB : codeList) {
			boolean isExist = false;
			for (KscmtAlchkMaxdaysWktmOrgDtl dtl : maxDaysWktmDtl) {
				if (codeDB.v().equals(dtl.pk.workTimeCode)) {
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				this.commandProxy().remove(KscmtAlchkMaxdaysWktmOrgDtl.class, new KscmtAlchkMaxdaysWktmOrgDtlPk(companyId,
						domain.getTargeOrg().getUnit().value,
						domain.getTargeOrg().getTargetId(),
						maxDaysWktm.pk.code, codeDB.v()));
			}
		}
		maxDaysWktmDtl.forEach(item->{
			item.setContractCd(AppContexts.user().contractCode());
		});
		this.commandProxy().update(maxDaysWktm);
		this.commandProxy().updateAll(maxDaysWktmDtl);
		
	}

	@Override
	public boolean exists(String companyId, TargetOrgIdenInfor targetOrg, MaxDayOfWorkTimeCode code) {
		
		return this.queryProxy()
				.find(new KscmtAlchkMaxdaysWktmOrgPk(
							companyId, 
							targetOrg.getUnit().value, 
							targetOrg.getTargetId(), 
							code.v()), 
						KscmtAlchkMaxdaysWktmOrg.class)
				.isPresent();
	}

	@Override
	public void delete(String companyId, TargetOrgIdenInfor targetOrg, MaxDayOfWorkTimeCode code) {
		
		List<KscmtAlchkMaxdaysWktmOrgDtl> maxDaysWktmDtlList = 
				new NtsStatement( SELECT_MAX_DAYS_WKTM_DTL, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramInt("targetUnit", targetOrg.getUnit().value)
				.paramString("targetId", targetOrg.getTargetId())
				.paramString("code", code.v())
				.getList( KscmtAlchkMaxdaysWktmOrgDtl.mapper );
		
		this.commandProxy().remove(
				KscmtAlchkMaxdaysWktmOrg.class, 
				new KscmtAlchkMaxdaysWktmOrgPk(
						companyId, 
						targetOrg.getUnit().value, 
						targetOrg.getTargetId(), 
						code.v()));
		maxDaysWktmDtlList.forEach(item->{
			this.commandProxy().remove(KscmtAlchkMaxdaysWktmOrgDtl.class,item.pk);
		});
	}

	@Override
	public Optional<MaxDayOfWorkTimeOrganization> getWithCode(String companyId, 
														TargetOrgIdenInfor targetOrg,
														MaxDayOfWorkTimeCode code) {
		
		Optional<KscmtAlchkMaxdaysWktmOrg> maxDaysWktm = 
				new NtsStatement( SELECT_MAX_DAYS_WKTM, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramInt("targetUnit", targetOrg.getUnit().value)
				.paramString("targetId", targetOrg.getTargetId())
				.paramString("code", code.v())
				.getSingle( KscmtAlchkMaxdaysWktmOrg.mapper );
		
		if (!maxDaysWktm.isPresent()) {
			return Optional.empty();
		}
		
		List<KscmtAlchkMaxdaysWktmOrgDtl> maxDaysWktmDtlList = 
				new NtsStatement( SELECT_MAX_DAYS_WKTM_DTL, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramInt("targetUnit", targetOrg.getUnit().value)
				.paramString("targetId", targetOrg.getTargetId())
				.paramString("code", code.v())
				.getList( KscmtAlchkMaxdaysWktmOrgDtl.mapper );
		
		return Optional.of(maxDaysWktm.get().toDomain(maxDaysWktmDtlList));
	}

	@Override
	public List<MaxDayOfWorkTimeOrganization> getAll(String companyId, TargetOrgIdenInfor targetOrg) {
		
		List<KscmtAlchkMaxdaysWktmOrg> maxDaysWktmList = 
				new NtsStatement( SELECT_ALL_MAX_DAYS_WKTM, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramInt("targetUnit", targetOrg.getUnit().value)
				.paramString("targetId", targetOrg.getTargetId())
				.getList( KscmtAlchkMaxdaysWktmOrg.mapper );
		
		List<KscmtAlchkMaxdaysWktmOrgDtl> maxDaysWktmDtlList = 
				new NtsStatement( SELECT_ALL_MAX_DAYS_WKTM_DTL, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramInt("targetUnit", targetOrg.getUnit().value)
				.paramString("targetId", targetOrg.getTargetId())
				.getList( KscmtAlchkMaxdaysWktmOrgDtl.mapper );
		
		return maxDaysWktmList.stream().map( mdw -> {
			
			List<KscmtAlchkMaxdaysWktmOrgDtl> dtlList = maxDaysWktmDtlList.stream()
					.filter( dtl -> dtl.pk.code == mdw.pk.code)
					.collect(Collectors.toList());
			
			return mdw.toDomain(dtlList);
			
		}).collect(Collectors.toList());
	}

}
