package nts.uk.ctx.at.function.infra.repository.alarm.checkcondition;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.infra.enity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.ctx.at.function.infra.enity.alarm.checkcondition.KfnmtAlarmCheckConditionCategoryPk;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
@Transactional
public class JpaAlarmCheckConditionByCategoryRepository extends JpaRepository
		implements AlarmCheckConditionByCategoryRepository {

	private final String GET_All_BY_COMPANY = "SELECT c FROM KfnmtAlarmCheckConditionCategory c WHERE c.companyId = :companyId ";
	private final String GET_All_BY_COMPANY_CATEGORY = "SELECT c FROM KfnmtAlarmCheckConditionCategory c WHERE c.companyId = :companyId AND c.category = :category ";

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
	public void add(AlarmCheckConditionByCategory domain) {
		this.commandProxy().insert(KfnmtAlarmCheckConditionCategory.fromDomain(domain));
	}

	@Override
	public void update(AlarmCheckConditionByCategory domain) {
		// TODO Auto-generated method stub

	}

	// When alarm check condition by category is deleted
	// Delete the "time item check of work record (勤務実績の勤怠項目チェック)" and "error
	// item condition of time item (勤怠項目のエラーアラーム条件)"
	// linked to error work alarm check ID of work record
	@Override
	public void delete(AlarmCheckConditionByCategory domain) {
		// TODO Auto-generated method stub

	}

}
