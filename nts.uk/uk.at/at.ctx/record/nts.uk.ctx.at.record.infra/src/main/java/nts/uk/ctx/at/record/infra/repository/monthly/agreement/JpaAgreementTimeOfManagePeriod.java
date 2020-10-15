package nts.uk.ctx.at.record.infra.repository.monthly.agreement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.infra.entity.monthly.agreement.KrcdtAgreementTime;
import nts.uk.ctx.at.record.infra.entity.monthly.agreement.KrcdtAgreementTimePK;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriodRepository;

/**
 * リポジトリ実装：管理期間の36協定時間
 * @author shuichi_ishida
 */
@Stateless
public class JpaAgreementTimeOfManagePeriod extends JpaRepository implements AgreementTimeOfManagePeriodRepository {

	private static final String FIND_BY_EMPLOYEES = "SELECT a FROM KrcdtAgreementTime a "
			+ "WHERE a.id.employeeId IN :employeeIds "
			+ "AND a.id.yearMonth = :yearMonth "
			+ "ORDER BY a.id.employeeId ";
	
	private static final String FIND_BY_SIDS_AND_YEARMONTHS = "SELECT a FROM KrcdtAgreementTime a "
			+ "WHERE a.id.employeeId IN :employeeIds "
			+ "AND a.id.yearMonth IN :yearMonths "
			+ "ORDER BY a.id.employeeId, a.id.yearMonth ";
	
	private static final String REMOVE_BY_PK =
			"DELETE FROM KrcdtAgreementTime a "
			+ "WHERE a.id.employeeId = :employeeId "
			+ "AND a.id.yearMonth = :yearMonth ";
	
	/** 検索 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<AgreementTimeOfManagePeriod> find(String employeeId, YearMonth yearMonth) {

		return this.queryProxy()
				.find(new KrcdtAgreementTimePK(employeeId, yearMonth.v()), KrcdtAgreementTime.class)
				.map(c -> c.toDomain());
	}

	/** 検索　（社員IDリスト） */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<AgreementTimeOfManagePeriod> findByEmployees(List<String> employeeIds, YearMonth yearMonth) {
		
		List<AgreementTimeOfManagePeriod> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			results.addAll(this.queryProxy().query(FIND_BY_EMPLOYEES, KrcdtAgreementTime.class)
					.setParameter("employeeIds", splitData)
					.setParameter("yearMonth", yearMonth.v())
					.getList(c -> c.toDomain()));
		});
		results.sort(Comparator.comparing(AgreementTimeOfManagePeriod::getSid));
		return results;
	}
	
	/** 検索　（社員IDリストと年月リスト） */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<AgreementTimeOfManagePeriod> findBySidsAndYearMonths(List<String> employeeIds,
			List<YearMonth> yearMonths) {
		
		val yearMonthValues = yearMonths.stream().map(c -> c.v()).collect(Collectors.toList());
		
		List<AgreementTimeOfManagePeriod> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			CollectionUtil.split(yearMonthValues, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, lstYearMonth -> {
				results.addAll(this.queryProxy().query(FIND_BY_SIDS_AND_YEARMONTHS, KrcdtAgreementTime.class)
						.setParameter("employeeIds", splitData)
						.setParameter("yearMonths", lstYearMonth)
						.getList(c -> c.toDomain()));
			});
		});
		results.sort((o1, o2) -> {
			int tmp = o1.getSid().compareTo(o2.getSid());
			if (tmp != 0) return tmp;
			return o1.getYm().compareTo(o2.getYm());
		});
		return results;
	}
	
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(AgreementTimeOfManagePeriod domain) {

		// キー
		val key = new KrcdtAgreementTimePK(domain.getSid(), domain.getYm().v());

		// 登録・更新を判断　および　キー値設定
		KrcdtAgreementTime entity = this.getEntityManager().find(KrcdtAgreementTime.class, key);
		if (entity == null){
			entity = new KrcdtAgreementTime(key);
			entity.fromDomainForUpdate(domain);
			this.commandProxy().insert(entity);
		} else 
			entity.fromDomainForUpdate(domain);
	}
	
	/** 削除 */
	@Override
	public void remove(String employeeId, YearMonth yearMonth) {

		this.getEntityManager().createQuery(REMOVE_BY_PK)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.executeUpdate();
	}
}
