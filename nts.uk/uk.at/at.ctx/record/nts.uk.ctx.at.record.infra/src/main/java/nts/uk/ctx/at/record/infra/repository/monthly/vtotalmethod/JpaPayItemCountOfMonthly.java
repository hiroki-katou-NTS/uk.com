package nts.uk.ctx.at.record.infra.repository.monthly.vtotalmethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.vtotalmethod.KrcmtCalcMPayAbsn;
import nts.uk.ctx.at.record.infra.entity.monthly.vtotalmethod.KrcmtCalcMPayAttn;
import nts.uk.ctx.at.record.infra.entity.monthly.vtotalmethod.KrcmtCalcMPayAttnPK;
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
			"SELECT a FROM KrcmtCalcMPayAttn a "
			+ "WHERE a.PK.companyId = :companyId ";
	
	private static final String FIND_BY_CID_FOR_ABSN =
			"SELECT a FROM KrcmtCalcMPayAbsn a "
			+ "WHERE a.PK.companyId = :companyId ";
	
	private static final String REMOVE_BY_CID_FOR_ATTN =
			"DELETE FROM KrcmtCalcMPayAttn a "
			+ "WHERE a.PK.companyId = :companyId ";
	
	private static final String REMOVE_BY_CID_FOR_ABSN =
			"DELETE FROM KrcmtCalcMPayAbsn a "
			+ "WHERE a.PK.companyId = :companyId ";
	
	/** 検索 */
	@Override
	public Optional<PayItemCountOfMonthly> find(String companyId) {

		val attnDaysList = this.queryProxy()
				.query(FIND_BY_CID_FOR_ATTN, KrcmtCalcMPayAttn.class)
				.setParameter("companyId", companyId)
				.getList();
		
		val absnDaysList = this.queryProxy()
				.query(FIND_BY_CID_FOR_ABSN, KrcmtCalcMPayAbsn.class)
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
			List<KrcmtCalcMPayAttn> attnDaysList, List<KrcmtCalcMPayAbsn> absnDaysList){
		
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
			KrcmtCalcMPayAttn entity = new KrcmtCalcMPayAttn();
			entity.PK = new KrcmtCalcMPayAttnPK(companyId, workTypeCode.v());
			this.getEntityManager().persist(entity);
		}
		val absenceDays = payItemCountOfMonthly.getPayAbsenceDays();
		for (val workTypeCode : absenceDays){
			KrcmtCalcMPayAbsn entity = new KrcmtCalcMPayAbsn();
			entity.PK = new KrcmtCalcMPayAttnPK(companyId, workTypeCode.v());
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
