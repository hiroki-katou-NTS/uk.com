package nts.uk.ctx.at.schedule.infra.repository.shift.workpairpattern;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.ComPattern;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.ComPatternItem;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.ComWorkPairSet;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.WorkPairPatternRepository;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.WorkplacePattern;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.WorkplacePatternItem;
import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.WorkplaceWorkPairSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtPairGrpCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtPairPatrnCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtPairPatrnComPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtPairGrpComPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtPairCom;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtPairComPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtPairGrpWkp;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtPairPatrnWkp;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtPairPatrnWkpPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtPairGrpWkpPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtPairWkp;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtPairWkpPK;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaWorkPairPatternRepository extends JpaRepository implements WorkPairPatternRepository {

	/**
	 * To Entity Company
	 */
	private KscmtPairGrpCom toEntityComPattern(ComPattern domain) {
		KscmtPairGrpComPK kscmtPairGrpComPk = new KscmtPairGrpComPK(domain.getCompanyId(), domain.getGroupNo());
		return new KscmtPairGrpCom(kscmtPairGrpComPk, domain.getGroupName().v(), domain.getGroupUsageAtr().value,
				domain.getNote(), domain.getListComPatternItem().stream().map(x -> toEntityComPatternItem(x))
						.collect(Collectors.toList()));
	}

	private KscmtPairPatrnCom toEntityComPatternItem(ComPatternItem domain) {
		KscmtPairPatrnComPK kscmtPairPatrnComPK = new KscmtPairPatrnComPK(domain.getCompanyId(),
				domain.getGroupNo(), domain.getPatternNo());
		return new KscmtPairPatrnCom(kscmtPairPatrnComPK, domain.getPatternName().v(), domain
				.getListComWorkPairSet().stream().map(x -> toEntityComWorkPairSet(x)).collect(Collectors.toList()));
	}

	private KscmtPairCom toEntityComWorkPairSet(ComWorkPairSet domain) {
		KscmtPairComPK kscmtPairComPk = new KscmtPairComPK(domain.getCompanyId(),
				domain.getGroupNo(), domain.getPatternNo(), domain.getPairNo());
		return new KscmtPairCom(kscmtPairComPk, domain.getWorkTypeCode().v(),
				domain.getWorkTimeCode() == null ? null : domain.getWorkTimeCode().v());
	}

	/**
	 * To Entity Workplace
	 */
	private KscmtPairGrpWkp toEntityWkpPattern(WorkplacePattern domain) {
		KscmtPairGrpWkpPK kscmtPairGrpComPk = new KscmtPairGrpWkpPK(domain.getWorkplaceId(), domain.getGroupNo());
		return new KscmtPairGrpWkp(kscmtPairGrpComPk, domain.getGroupName().v(), domain.getGroupUsageAtr().value,
				domain.getNote(), domain.getListWorkplacePatternItem().stream().map(x -> toEntityWkpPatternItem(x))
						.collect(Collectors.toList()));
	}

	private KscmtPairPatrnWkp toEntityWkpPatternItem(WorkplacePatternItem domain) {
		KscmtPairPatrnWkpPK kscmtPairPatrnWkpPK = new KscmtPairPatrnWkpPK(domain.getWorkplaceId(),
				domain.getGroupNo(), domain.getPatternNo());
		return new KscmtPairPatrnWkp(kscmtPairPatrnWkpPK, domain.getPatternName().v(),
				domain.getListWorkplaceWorkPairSet().stream().map(x -> toEntityWkpWorkPairSet(x))
						.collect(Collectors.toList()));
	}

	private KscmtPairWkp toEntityWkpWorkPairSet(WorkplaceWorkPairSet domain) {
		KscmtPairWkpPK kscmtPairWkpPk = new KscmtPairWkpPK(domain.getWorkplaceId(),
				domain.getGroupNo(), domain.getPatternNo(), domain.getPairNo());
		return new KscmtPairWkp(kscmtPairWkpPk, domain.getWorkTypeCode().v(),
				domain.getWorkTimeCode() == null ? null : domain.getWorkTimeCode().v());
	}

	/**
	 * To Domain Company
	 */
	private ComPattern toDomainComPattern(KscmtPairGrpCom entity) {
		return ComPattern.convertFromJavaType(entity.kscmtPairGrpComPk.companyId, entity.kscmtPairGrpComPk.groupNo,
				entity.groupName, entity.groupUsageAtr, entity.note,
				entity.kscmtPairPatrnCom.stream().map(x -> toDomainComPatternItem(x)).collect(Collectors.toList()));
	}

	private ComPatternItem toDomainComPatternItem(KscmtPairPatrnCom entity) {
		return ComPatternItem.convertFromJavaType(entity.kscmtPairPatrnComPk.companyId,
				entity.kscmtPairPatrnComPk.groupNo, entity.kscmtPairPatrnComPk.patternNo, entity.patternName,
				entity.kscmtPairCom.stream().map(x -> toDomainComWorkPairSet(x)).collect(Collectors.toList()));
	}

	private ComWorkPairSet toDomainComWorkPairSet(KscmtPairCom entity) {
		return ComWorkPairSet.convertFromJavaType(entity.kscmtPairComPk.companyId,
				entity.kscmtPairComPk.groupNo, entity.kscmtPairComPk.patternNo,
				entity.kscmtPairComPk.pairNo, entity.workTypeCode, entity.workTimeCode);
	}

	/**
	 * To Domain Workplace
	 */
	private WorkplacePattern toDomainWkpPattern(KscmtPairGrpWkp entity) {
		return WorkplacePattern.convertFromJavaType(entity.kscmtPairGrpWkpPk.workplaceId,
				entity.kscmtPairGrpWkpPk.groupNo, entity.groupName, entity.groupUsageAtr, entity.note,
				entity.kscmtPairPatrnWkp.stream().map(x -> toDomainWkpPatternItem(x)).collect(Collectors.toList()));
	}

	private WorkplacePatternItem toDomainWkpPatternItem(KscmtPairPatrnWkp entity) {
		return WorkplacePatternItem.convertFromJavaType(entity.kscmtPairPatrnWkpPk.workplaceId,
				entity.kscmtPairPatrnWkpPk.groupNo, entity.kscmtPairPatrnWkpPk.patternNo, entity.patternName,
				entity.kscmtPairWkp.stream().map(x -> toDomainWkpWorkPairSet(x)).collect(Collectors.toList()));
	}

	private WorkplaceWorkPairSet toDomainWkpWorkPairSet(KscmtPairWkp entity) {
		return WorkplaceWorkPairSet.convertFromJavaType(entity.kscmtPairWkpPk.workplaceId,
				entity.kscmtPairWkpPk.groupNo, entity.kscmtPairWkpPk.patternNo,
				entity.kscmtPairWkpPk.pairNo, entity.workTypeCode, entity.workTimeCode);
	}

	@Override
	public void addComWorkPairPattern(ComPattern comPattern) {
		this.commandProxy().insert(toEntityComPattern(comPattern));
	}

	@Override
	public void updateComWorkPairPattern(ComPattern domain) {
		KscmtPairGrpComPK pk = new KscmtPairGrpComPK(domain.getCompanyId(), domain.getGroupNo());
		KscmtPairGrpCom entity = this.queryProxy().find(pk, KscmtPairGrpCom.class).get();
		entity.groupName = domain.getGroupName().v();
		entity.groupUsageAtr = domain.getGroupUsageAtr().value;
		entity.kscmtPairGrpComPk = pk;
		entity.kscmtPairPatrnCom = domain.getListComPatternItem().stream().map(x -> toEntityComPatternItem(x))
				.collect(Collectors.toList());
		entity.note = domain.getNote();
		this.commandProxy().update(entity);
	}

	@Override
	public void removeComWorkPairPattern(String companyId, int groupNo) {
		KscmtPairGrpComPK pk = new KscmtPairGrpComPK(companyId, groupNo);
		this.commandProxy().remove(KscmtPairGrpCom.class, pk);
	}

	@Override
	public Optional<ComPattern> findComPatternById(String companyId, int groupNo) {
		KscmtPairGrpComPK pk = new KscmtPairGrpComPK(companyId, groupNo);
		return this.queryProxy().find(pk, KscmtPairGrpCom.class).map(x -> toDomainComPattern(x));
	}

	@Override
	public Optional<WorkplacePattern> findWorkplacePatternById(String workplaceId, int groupNo) {
		KscmtPairGrpWkpPK pk = new KscmtPairGrpWkpPK(workplaceId, groupNo);
		return this.queryProxy().find(pk, KscmtPairGrpWkp.class).map(x -> toDomainWkpPattern(x));
	}

	@Override
	public void addWorkplaceWorkPairPattern(WorkplacePattern workplacePattern) {
		this.commandProxy().insert(toEntityWkpPattern(workplacePattern));
	}

	@Override
	public void updateWorkplaceWorkPairPattern(WorkplacePattern domain) {
		KscmtPairGrpWkpPK pk = new KscmtPairGrpWkpPK(domain.getWorkplaceId(), domain.getGroupNo());
		KscmtPairGrpWkp entity = this.queryProxy().find(pk, KscmtPairGrpWkp.class).get();
		entity.groupName = domain.getGroupName().v();
		entity.groupUsageAtr = domain.getGroupUsageAtr().value;
		entity.kscmtPairGrpWkpPk = pk;
		entity.kscmtPairPatrnWkp = domain.getListWorkplacePatternItem().stream().map(x -> toEntityWkpPatternItem(x))
				.collect(Collectors.toList());
		entity.note = domain.getNote();
		this.commandProxy().update(entity);
	}

	@Override
	public void removeWorkplaceWorkPairPattern(String workplaceId, int groupNo) {
		KscmtPairGrpWkpPK pk = new KscmtPairGrpWkpPK(workplaceId, groupNo);
		this.commandProxy().remove(KscmtPairGrpWkp.class, pk);
	}
}
