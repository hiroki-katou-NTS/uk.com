package nts.uk.ctx.at.function.infra.repository.alarm.checkcondition;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.DailyAlarmCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.fourweekfourdayoff.AlarmCheckCondition4W4D;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategoryPk;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategoryRole;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckTargetBusinessType;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckTargetClassification;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckTargetEmployment;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckTargetJobTitle;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.daily.KrcmtDailyErrorCode;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.daily.KrcmtDailyErrorCodePK;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.daily.KrcmtDailyWkRecord;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.daily.KrcmtDailyWkRecordPK;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
@Transactional
public class JpaAlarmCheckConditionByCategoryRepository extends JpaRepository
		implements AlarmCheckConditionByCategoryRepository {

	private final String GET_All_BY_COMPANY = "SELECT c FROM KfnmtAlarmCheckConditionCategory c WHERE c.pk.companyId = :companyId ";
	private final String GET_All_BY_COMPANY_CATEGORY = "SELECT c FROM KfnmtAlarmCheckConditionCategory c WHERE c.pk.companyId = :companyId AND c.pk.category = :category ";

	@Override
	public Optional<AlarmCheckConditionByCategory> find(String companyId, int category, String code) {
		Optional<KfnmtAlarmCheckConditionCategory> entity = this.queryProxy().find(
				new KfnmtAlarmCheckConditionCategoryPk(companyId, category, code),
				KfnmtAlarmCheckConditionCategory.class);
		if (entity.isPresent()) {
			return Optional.of(KfnmtAlarmCheckConditionCategory.toDomain(entity.get()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<AlarmCheckConditionByCategory> findAll(String companyId) {
		return this.queryProxy().query(GET_All_BY_COMPANY, KfnmtAlarmCheckConditionCategory.class)
				.setParameter("companyId", companyId).getList(c -> KfnmtAlarmCheckConditionCategory.toDomain(c));
	}

	@Override
	public List<AlarmCheckConditionByCategory> findByCategory(String companyId, int category) {
		return this.queryProxy().query(GET_All_BY_COMPANY_CATEGORY, KfnmtAlarmCheckConditionCategory.class)
				.setParameter("companyId", companyId).setParameter("category", category)
				.getList(c -> KfnmtAlarmCheckConditionCategory.toDomain(c));
	}
	
	@Override
	public List<AlarmCheckConditionByCategory> findByCategoryAndCode(String companyId, int category, List<String> codes) {
		String query = "SELECT c FROM KfnmtAlarmCheckConditionCategory c WHERE c.pk.companyId = :companyId AND c.pk.category = :category AND c.pk.code IN :codes ";
		return this.queryProxy().query(query, KfnmtAlarmCheckConditionCategory.class)
				.setParameter("companyId", companyId).setParameter("category", category).setParameter("codes", codes)
				.getList(c -> KfnmtAlarmCheckConditionCategory.toDomain(c));
	}

	@Override
	public void add(AlarmCheckConditionByCategory domain) {
		this.commandProxy().insert(KfnmtAlarmCheckConditionCategory.fromDomain(domain));
	}

	@Override
	public void update(AlarmCheckConditionByCategory domain) {
		Optional<KfnmtAlarmCheckConditionCategory> entityOpt = this.queryProxy()
				.find(new KfnmtAlarmCheckConditionCategoryPk(domain.getCompanyId(), domain.getCategory().value,
						domain.getCode().v()), KfnmtAlarmCheckConditionCategory.class);
		if (entityOpt.isPresent()) {
			KfnmtAlarmCheckConditionCategory entity = entityOpt.get();
			entity.name = domain.getName().v();
			List<KfnmtAlarmCheckConditionCategoryRole> oldListRole = entity.listAvailableRole;
			List<KfnmtAlarmCheckConditionCategoryRole> newListRole = domain.getListRoleId().stream()
					.map(item -> new KfnmtAlarmCheckConditionCategoryRole(domain.getCompanyId(),
							domain.getCategory().value, domain.getCode().v(), item))
					.collect(Collectors.toList());
			for (KfnmtAlarmCheckConditionCategoryRole newRole : newListRole) {
				for (KfnmtAlarmCheckConditionCategoryRole oldRole : oldListRole) {
					if (oldRole.pk.equals(newRole.pk)) {
						newListRole.set(newListRole.indexOf(newRole), oldRole);
						break;
					}
				}
			}
			entity.listAvailableRole = newListRole;

			entity.targetCondition.filterByBusinessType = domain.getExtractTargetCondition().isFilterByBusinessType() ? 1 : 0;
			entity.targetCondition.filterByClassification = domain.getExtractTargetCondition().isFilterByClassification() ? 1 : 0;
			entity.targetCondition.filterByEmployment = domain.getExtractTargetCondition().isFilterByEmployment() ? 1 : 0;
			entity.targetCondition.filterByJobTitle = domain.getExtractTargetCondition().isFilterByJobTitle() ? 1 : 0;

			List<KfnmtAlarmCheckTargetEmployment> oldListTargetEmployment = entity.targetCondition.listEmployment;
			List<KfnmtAlarmCheckTargetEmployment> newListTargetEmployment = domain.getExtractTargetCondition().getLstEmploymentCode().stream().map(item -> new KfnmtAlarmCheckTargetEmployment(domain.getExtractTargetCondition().getId(), item)).collect(Collectors.toList());
			for (KfnmtAlarmCheckTargetEmployment newTarget : newListTargetEmployment) {
				for (KfnmtAlarmCheckTargetEmployment oldTarget : oldListTargetEmployment) {
					if (oldTarget.pk.equals(newTarget.pk)) {
						newListTargetEmployment.set(newListTargetEmployment.indexOf(newTarget), oldTarget);
						break;
					}
				}
			}
			entity.targetCondition.listEmployment = newListTargetEmployment;
			
			List<KfnmtAlarmCheckTargetClassification> oldListTargetClassification = entity.targetCondition.listClassification;
			List<KfnmtAlarmCheckTargetClassification> newListTargetClassification = domain.getExtractTargetCondition().getLstClassificationCode().stream().map(item -> new KfnmtAlarmCheckTargetClassification(domain.getExtractTargetCondition().getId(), item)).collect(Collectors.toList());
			for (KfnmtAlarmCheckTargetClassification newTarget : newListTargetClassification) {
				for (KfnmtAlarmCheckTargetClassification oldTarget : oldListTargetClassification) {
					if (oldTarget.pk.equals(newTarget.pk)) {
						newListTargetClassification.set(newListTargetClassification.indexOf(newTarget), oldTarget);
						break;
					}
				}
			}
			entity.targetCondition.listClassification = newListTargetClassification;
			
			List<KfnmtAlarmCheckTargetJobTitle> oldListTargetJobTitle = entity.targetCondition.listJobTitle;
			List<KfnmtAlarmCheckTargetJobTitle> newListTargetJobTitle = domain.getExtractTargetCondition().getLstJobTitleId().stream().map(item -> new KfnmtAlarmCheckTargetJobTitle(domain.getExtractTargetCondition().getId(), item)).collect(Collectors.toList());
			for (KfnmtAlarmCheckTargetJobTitle newTarget : newListTargetJobTitle) {
				for (KfnmtAlarmCheckTargetJobTitle oldTarget : oldListTargetJobTitle) {
					if (oldTarget.pk.equals(newTarget.pk)) {
						newListTargetJobTitle.set(newListTargetJobTitle.indexOf(newTarget), oldTarget);
						break;
					}
				}
			}
			entity.targetCondition.listJobTitle = newListTargetJobTitle;
			
			List<KfnmtAlarmCheckTargetBusinessType> oldListTargetBusinessType = entity.targetCondition.listBusinessType;
			List<KfnmtAlarmCheckTargetBusinessType> newListTargetBusinessType = domain.getExtractTargetCondition().getLstBusinessTypeCode().stream().map(item -> new KfnmtAlarmCheckTargetBusinessType(domain.getExtractTargetCondition().getId(), item)).collect(Collectors.toList());
			for (KfnmtAlarmCheckTargetBusinessType newTarget : newListTargetBusinessType) {
				for (KfnmtAlarmCheckTargetBusinessType oldTarget : oldListTargetBusinessType) {
					if (oldTarget.pk.equals(newTarget.pk)) {
						newListTargetBusinessType.set(newListTargetBusinessType.indexOf(newTarget), oldTarget);
						break;
					}
				}
			}
			entity.targetCondition.listBusinessType = newListTargetBusinessType;
			
			if (entity.pk.category == AlarmCategory.DAILY.value) {
				DailyAlarmCondition dailyAlarmCondition = (DailyAlarmCondition) domain.getExtractionCondition();
				entity.dailyAlarmCondition.addApplication = dailyAlarmCondition.isAddApplication() ? 1 : 0;
				entity.dailyAlarmCondition.conExtractedDaily = dailyAlarmCondition.getConExtractedDaily().value;
				
				List<KrcmtDailyErrorCode> oldListErrorAlarmCode = entity.dailyAlarmCondition.listErrorAlarmCode;
				List<KrcmtDailyErrorCode> newListErrorAlarmCode = dailyAlarmCondition.getErrorAlarmCode().stream().map(item -> new KrcmtDailyErrorCode(new KrcmtDailyErrorCodePK(dailyAlarmCondition.getDailyAlarmConID(), item))).collect(Collectors.toList());
				for (KrcmtDailyErrorCode newTarget : newListErrorAlarmCode) {
					for (KrcmtDailyErrorCode oldTarget : oldListErrorAlarmCode) {
						if (oldTarget.krcmtDailyErrorCodePK.equals(newTarget.krcmtDailyErrorCodePK)) {
							newListErrorAlarmCode.set(newListErrorAlarmCode.indexOf(newTarget), oldTarget);
							break;
						}
					}
				}
				entity.dailyAlarmCondition.listErrorAlarmCode = newListErrorAlarmCode;
				
				List<KrcmtDailyWkRecord> oldListWorkRecord = entity.dailyAlarmCondition.listExtractConditionWorkRecord;
				List<KrcmtDailyWkRecord> newListWorkRecord = dailyAlarmCondition.getExtractConditionWorkRecord().stream().map(item -> new KrcmtDailyWkRecord(new KrcmtDailyWkRecordPK(dailyAlarmCondition.getDailyAlarmConID(), item))).collect(Collectors.toList());
				for (KrcmtDailyWkRecord newTarget : newListWorkRecord) {
					for (KrcmtDailyWkRecord oldTarget : oldListWorkRecord) {
						if (oldTarget.krcmtDailyWkRecordPK.equals(newTarget.krcmtDailyWkRecordPK)) {
							newListWorkRecord.set(newListWorkRecord.indexOf(newTarget), oldTarget);
							break;
						}
					}
				}
				entity.dailyAlarmCondition.listExtractConditionWorkRecord = newListWorkRecord;
				
//				List<KrcmtDailyFixExtra> oldListFixedWkRecord = entity.dailyAlarmCondition.listFixedExtractConditionWorkRecord;
//				List<KrcmtDailyFixExtra> newListFixedWkRecord = dailyAlarmCondition.getErrorAlarmCode().stream().map(item -> new KrcmtDailyFixExtra(new KrcmtDailyFixExtraPK(dailyAlarmCondition.getDailyAlarmConID(), item))).collect(Collectors.toList());
//				for (KrcmtDailyFixExtra newTarget : newListFixedWkRecord) {
//					for (KrcmtDailyFixExtra oldTarget : oldListFixedWkRecord) {
//						if (oldTarget.krcmtDailyFixExtraPK.equals(newTarget.krcmtDailyFixExtraPK)) {
//							newListFixedWkRecord.set(newListErrorAlarmCode.indexOf(newTarget), oldTarget);
//							break;
//						}
//					}
//				}
//				entity.dailyAlarmCondition.listFixedExtractConditionWorkRecord = newListFixedWkRecord;
			}
			
			if (entity.pk.category == AlarmCategory.SCHEDULE_4WEEK.value) {
				AlarmCheckCondition4W4D schedule4Week = (AlarmCheckCondition4W4D) domain.getExtractionCondition();
				entity.schedule4W4DAlarmCondition.fourW4DCheckCond = schedule4Week.getFourW4DCheckCond().value;
			}
			
			this.commandProxy().update(entity);
		}

	}

	@Override
	public void delete(String companyId, int category, String alarmConditionCode) {
		this.commandProxy().remove(KfnmtAlarmCheckConditionCategory.class, new KfnmtAlarmCheckConditionCategoryPk(
				companyId, category, alarmConditionCode));
	}

	@Override
	public boolean isCodeExist(String companyId, int category, String code) {
		return this.queryProxy().find(new KfnmtAlarmCheckConditionCategoryPk(companyId, category, code),
				KfnmtAlarmCheckConditionCategory.class).isPresent();
	}

}
