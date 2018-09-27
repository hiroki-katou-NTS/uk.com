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
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtComPattern;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtComPatternItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtComPatternItemPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtComPatternPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtComWorkPairSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtComWorkPairSetPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtWkpPattern;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtWkpPatternItem;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtWkpPatternItemPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtWkpPatternPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtWkpWorkPairSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern.KscmtWkpWorkPairSetPK;

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
	private KscmtComPattern toEntityComPattern(ComPattern domain) {
		KscmtComPatternPK kscmtComPatternPk = new KscmtComPatternPK(domain.getCompanyId(), domain.getGroupNo());
		return new KscmtComPattern(kscmtComPatternPk, domain.getGroupName().v(), domain.getGroupUsageAtr().value,
				domain.getNote(), domain.getListComPatternItem().stream().map(x -> toEntityComPatternItem(x))
						.collect(Collectors.toList()));
	}

	private KscmtComPatternItem toEntityComPatternItem(ComPatternItem domain) {
		KscmtComPatternItemPK kscmtComPatternItemPK = new KscmtComPatternItemPK(domain.getCompanyId(),
				domain.getGroupNo(), domain.getPatternNo());
		return new KscmtComPatternItem(kscmtComPatternItemPK, domain.getPatternName().v(), domain
				.getListComWorkPairSet().stream().map(x -> toEntityComWorkPairSet(x)).collect(Collectors.toList()));
	}

	private KscmtComWorkPairSet toEntityComWorkPairSet(ComWorkPairSet domain) {
		KscmtComWorkPairSetPK kscmtComWorkPairSetPk = new KscmtComWorkPairSetPK(domain.getCompanyId(),
				domain.getGroupNo(), domain.getPatternNo(), domain.getPairNo());
		return new KscmtComWorkPairSet(kscmtComWorkPairSetPk, domain.getWorkTypeCode().v(),
				domain.getWorkTimeCode() == null ? null : domain.getWorkTimeCode().v());
	}

	/**
	 * To Entity Workplace
	 */
	private KscmtWkpPattern toEntityWkpPattern(WorkplacePattern domain) {
		KscmtWkpPatternPK kscmtComPatternPk = new KscmtWkpPatternPK(domain.getWorkplaceId(), domain.getGroupNo());
		return new KscmtWkpPattern(kscmtComPatternPk, domain.getGroupName().v(), domain.getGroupUsageAtr().value,
				domain.getNote(), domain.getListWorkplacePatternItem().stream().map(x -> toEntityWkpPatternItem(x))
						.collect(Collectors.toList()));
	}

	private KscmtWkpPatternItem toEntityWkpPatternItem(WorkplacePatternItem domain) {
		KscmtWkpPatternItemPK kscmtWkpPatternItemPK = new KscmtWkpPatternItemPK(domain.getWorkplaceId(),
				domain.getGroupNo(), domain.getPatternNo());
		return new KscmtWkpPatternItem(kscmtWkpPatternItemPK, domain.getPatternName().v(),
				domain.getListWorkplaceWorkPairSet().stream().map(x -> toEntityWkpWorkPairSet(x))
						.collect(Collectors.toList()));
	}

	private KscmtWkpWorkPairSet toEntityWkpWorkPairSet(WorkplaceWorkPairSet domain) {
		KscmtWkpWorkPairSetPK kscmtWkpWorkPairSetPk = new KscmtWkpWorkPairSetPK(domain.getWorkplaceId(),
				domain.getGroupNo(), domain.getPatternNo(), domain.getPairNo());
		return new KscmtWkpWorkPairSet(kscmtWkpWorkPairSetPk, domain.getWorkTypeCode().v(),
				domain.getWorkTimeCode() == null ? null : domain.getWorkTimeCode().v());
	}

	/**
	 * To Domain Company
	 */
	private ComPattern toDomainComPattern(KscmtComPattern entity) {
		return ComPattern.convertFromJavaType(entity.kscmtComPatternPk.companyId, entity.kscmtComPatternPk.groupNo,
				entity.groupName, entity.groupUsageAtr, entity.note,
				entity.kscmtComPatternItem.stream().map(x -> toDomainComPatternItem(x)).collect(Collectors.toList()));
	}

	private ComPatternItem toDomainComPatternItem(KscmtComPatternItem entity) {
		return ComPatternItem.convertFromJavaType(entity.kscmtComPatternItemPk.companyId,
				entity.kscmtComPatternItemPk.groupNo, entity.kscmtComPatternItemPk.patternNo, entity.patternName,
				entity.kscmtComWorkPairSet.stream().map(x -> toDomainComWorkPairSet(x)).collect(Collectors.toList()));
	}

	private ComWorkPairSet toDomainComWorkPairSet(KscmtComWorkPairSet entity) {
		return ComWorkPairSet.convertFromJavaType(entity.kscmtComWorkPairSetPk.companyId,
				entity.kscmtComWorkPairSetPk.groupNo, entity.kscmtComWorkPairSetPk.patternNo,
				entity.kscmtComWorkPairSetPk.pairNo, entity.workTypeCode, entity.workTimeCode);
	}

	/**
	 * To Domain Workplace
	 */
	private WorkplacePattern toDomainWkpPattern(KscmtWkpPattern entity) {
		return WorkplacePattern.convertFromJavaType(entity.kscmtWkpPatternPk.workplaceId,
				entity.kscmtWkpPatternPk.groupNo, entity.groupName, entity.groupUsageAtr, entity.note,
				entity.kscmtWkpPatternItem.stream().map(x -> toDomainWkpPatternItem(x)).collect(Collectors.toList()));
	}

	private WorkplacePatternItem toDomainWkpPatternItem(KscmtWkpPatternItem entity) {
		return WorkplacePatternItem.convertFromJavaType(entity.kscmtWkpPatternItemPk.workplaceId,
				entity.kscmtWkpPatternItemPk.groupNo, entity.kscmtWkpPatternItemPk.patternNo, entity.patternName,
				entity.kscmtWkpWorkPairSet.stream().map(x -> toDomainWkpWorkPairSet(x)).collect(Collectors.toList()));
	}

	private WorkplaceWorkPairSet toDomainWkpWorkPairSet(KscmtWkpWorkPairSet entity) {
		return WorkplaceWorkPairSet.convertFromJavaType(entity.kscmtWkpWorkPairSetPk.workplaceId,
				entity.kscmtWkpWorkPairSetPk.groupNo, entity.kscmtWkpWorkPairSetPk.patternNo,
				entity.kscmtWkpWorkPairSetPk.pairNo, entity.workTypeCode, entity.workTimeCode);
	}

	@Override
	public void addComWorkPairPattern(ComPattern comPattern) {
		this.commandProxy().insert(toEntityComPattern(comPattern));
	}

	@Override
	public void updateComWorkPairPattern(ComPattern domain) {
		KscmtComPatternPK pk = new KscmtComPatternPK(domain.getCompanyId(), domain.getGroupNo());
		KscmtComPattern entity = this.queryProxy().find(pk, KscmtComPattern.class).get();
		entity.groupName = domain.getGroupName().v();
		entity.groupUsageAtr = domain.getGroupUsageAtr().value;
		entity.kscmtComPatternPk = pk;
		entity.kscmtComPatternItem = domain.getListComPatternItem().stream().map(x -> toEntityComPatternItem(x))
				.collect(Collectors.toList());
		entity.note = domain.getNote();
		this.commandProxy().update(entity);
	}

	@Override
	public void removeComWorkPairPattern(String companyId, int groupNo) {
		KscmtComPatternPK pk = new KscmtComPatternPK(companyId, groupNo);
		this.commandProxy().remove(KscmtComPattern.class, pk);
	}

	@Override
	public Optional<ComPattern> findComPatternById(String companyId, int groupNo) {
		KscmtComPatternPK pk = new KscmtComPatternPK(companyId, groupNo);
		return this.queryProxy().find(pk, KscmtComPattern.class).map(x -> toDomainComPattern(x));
	}

	@Override
	public Optional<WorkplacePattern> findWorkplacePatternById(String workplaceId, int groupNo) {
		KscmtWkpPatternPK pk = new KscmtWkpPatternPK(workplaceId, groupNo);
		return this.queryProxy().find(pk, KscmtWkpPattern.class).map(x -> toDomainWkpPattern(x));
	}

	@Override
	public void addWorkplaceWorkPairPattern(WorkplacePattern workplacePattern) {
		this.commandProxy().insert(toEntityWkpPattern(workplacePattern));
	}

	@Override
	public void updateWorkplaceWorkPairPattern(WorkplacePattern domain) {
		KscmtWkpPatternPK pk = new KscmtWkpPatternPK(domain.getWorkplaceId(), domain.getGroupNo());
		KscmtWkpPattern entity = this.queryProxy().find(pk, KscmtWkpPattern.class).get();
		entity.groupName = domain.getGroupName().v();
		entity.groupUsageAtr = domain.getGroupUsageAtr().value;
		entity.kscmtWkpPatternPk = pk;
		entity.kscmtWkpPatternItem = domain.getListWorkplacePatternItem().stream().map(x -> toEntityWkpPatternItem(x))
				.collect(Collectors.toList());
		entity.note = domain.getNote();
		this.commandProxy().update(entity);
	}

	@Override
	public void removeWorkplaceWorkPairPattern(String workplaceId, int groupNo) {
		KscmtWkpPatternPK pk = new KscmtWkpPatternPK(workplaceId, groupNo);
		this.commandProxy().remove(KscmtWkpPattern.class, pk);
	}
}
