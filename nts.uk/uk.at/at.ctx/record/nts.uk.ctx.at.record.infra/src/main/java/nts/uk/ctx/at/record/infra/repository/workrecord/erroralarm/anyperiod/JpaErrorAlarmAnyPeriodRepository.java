package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.anyperiod;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.anyperiod.ErrorAlarmAnyPeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.anyperiod.ErrorAlarmAnyPeriodRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcmtEralCategoryCond;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.KrcmtEralCategoryCondPK;

public class JpaErrorAlarmAnyPeriodRepository extends JpaRepository implements ErrorAlarmAnyPeriodRepository{

	@Override
	public void add(ErrorAlarmAnyPeriod target) {
		val entity = KrcmtEralCategoryCond.toEntity(target);
		this.commandProxy().insert(entity);
	}

	@Override
	public void delete(String companyId, int category, String code) {
		this.commandProxy().remove(KrcmtEralCategoryCond.class, 
												  new KrcmtEralCategoryCondPK(companyId, category, code));
	}

	@Override
	public void update(ErrorAlarmAnyPeriod target) {
		val entity = KrcmtEralCategoryCond.toEntity(target);
		this.commandProxy().update(entity);
	}

	@Override
	public List<ErrorAlarmAnyPeriod> gets(String companyId, int category, List<String> codes) {
		String sql = "select * from KRCMT_ERAL_CATEGORY_COND"
				+ " where CID = @companyId"
				+ " and ERAL_CATEGORY = @category"
				+ " and ERAL_ALARM_CD in @codes";
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramInt("category", category)
				.paramString("codes", codes)
				.getList(rec -> KrcmtEralCategoryCond.MAPPER.toEntity(rec).toDomainAnyPeriod());		
	}

	@Override
	public Optional<ErrorAlarmAnyPeriod> get(String companyId, int category, String errorAlarmConditionCode) {
		String sql = "select * from KRCMT_ERAL_CATEGORY_COND"
				+ " where CID = @companyId"
				+ " and ERAL_CATEGORY = @category"
				+ " and ERAL_ALARM_CD = @eralCode"  ;
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramInt("category", category)
				.paramString("eralCode", errorAlarmConditionCode)
				.getSingle(rec -> KrcmtEralCategoryCond.MAPPER.toEntity(rec).toDomainAnyPeriod());
		
	}

}
