package nts.uk.ctx.at.record.infra.repository.monthly.vtotalmethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.vtotalmethod.KrcstMonPayAbsnDays;
import nts.uk.ctx.at.record.infra.entity.monthly.vtotalmethod.KrcstMonPayAttnDays;
import nts.uk.ctx.at.record.infra.entity.monthly.vtotalmethod.KrcstMonPayAttnDaysPK;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.PayItemCountOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.PayItemCountOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * リポジトリ実装：月別実績の給与項目カウント
 * @author shuichi_ishida
 */
@Stateless
public class JpaPayItemCountOfMonthly extends JpaRepository implements PayItemCountOfMonthlyRepository {

	private static final String FIND_BY_CID_FOR_ATTN =
			"SELECT a FROM KrcstMonPayAttnDays a "
			+ "WHERE a.PK.companyId = :companyId ";
	
	private static final String FIND_BY_CID_FOR_ABSN =
			"SELECT a FROM KrcstMonPayAbsnDays a "
			+ "WHERE a.PK.companyId = :companyId ";
	
	private static final String REMOVE_BY_CID_FOR_ATTN =
			"DELETE FROM KrcstMonPayAttnDays a "
			+ "WHERE a.PK.companyId = :companyId ";
	
	private static final String REMOVE_BY_CID_FOR_ABSN =
			"DELETE FROM KrcstMonPayAbsnDays a "
			+ "WHERE a.PK.companyId = :companyId ";
	
	/** 検索 */
	@Override
	public Optional<PayItemCountOfMonthly> find(String companyId) {

		val attnDaysList = this.queryProxy()
				.query(FIND_BY_CID_FOR_ATTN, KrcstMonPayAttnDays.class)
				.setParameter("companyId", companyId)
				.getList();
		
		val absnDaysList = this.queryProxy()
				.query(FIND_BY_CID_FOR_ABSN, KrcstMonPayAbsnDays.class)
				.setParameter("companyId", companyId)
				.getList();
		
		return Optional.of(toDomain(companyId, attnDaysList, absnDaysList));
	}
	
	/**
	 * エンティティ→ドメイン
	 * @param companyId 会社ID
	 * @param attnDaysList エンティティ：給与出勤日数
	 * @param absnDaysList エンティティ：給与欠勤日数
	 * @return ドメイン：月別実績の給与項目カウント
	 */
	private static PayItemCountOfMonthly toDomain(String companyId,
			List<KrcstMonPayAttnDays> attnDaysList, List<KrcstMonPayAbsnDays> absnDaysList){
		
		List<WorkTypeCode> payAttendanceDays = new ArrayList<>();
		List<WorkTypeCode> payAbsenceDays = new ArrayList<>();
		
		for (val attnDays : attnDaysList){
			payAttendanceDays.add(new WorkTypeCode(attnDays.PK.workTypeCode));
		}
		for (val absnDays : absnDaysList){
			payAbsenceDays.add(new WorkTypeCode(absnDays.PK.workTypeCode));
		}
		
		val domain = PayItemCountOfMonthly.of(companyId, payAttendanceDays, payAbsenceDays);
		return domain;
	}
	
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(PayItemCountOfMonthly payItemCountOfMonthly) {

		// キー
		val companyId = payItemCountOfMonthly.getCompanyId();
		
		// 削除
		this.remove(companyId);
		
		// 追加
		val attendanceDays = payItemCountOfMonthly.getPayAttendanceDays();
		for (val workTypeCode : attendanceDays){
			KrcstMonPayAttnDays entity = new KrcstMonPayAttnDays();
			entity.PK = new KrcstMonPayAttnDaysPK(companyId, workTypeCode.v());
			this.getEntityManager().persist(entity);
		}
		val absenceDays = payItemCountOfMonthly.getPayAbsenceDays();
		for (val workTypeCode : absenceDays){
			KrcstMonPayAbsnDays entity = new KrcstMonPayAbsnDays();
			entity.PK = new KrcstMonPayAttnDaysPK(companyId, workTypeCode.v());
			this.getEntityManager().persist(entity);
		}
	}
	
	/** 削除 */
	@Override
	public void remove(String companyId) {

		this.getEntityManager().createQuery(REMOVE_BY_CID_FOR_ATTN)
				.setParameter("companyId", companyId)
				.executeUpdate();

		this.getEntityManager().createQuery(REMOVE_BY_CID_FOR_ABSN)
				.setParameter("companyId", companyId)
				.executeUpdate();
	}
}
