package nts.uk.ctx.at.shared.infra.repository.specialholiday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYearMonthTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYearRepository;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshmtHdspGrantElapsedYearsTbl;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshmtHdspGrantElapsedYearsTblPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshmtHdspElapsedYearsTbl;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshmtHdspElapsedYearsTblPK;
import nts.uk.shr.com.context.AppContexts;

/**
 * 特別休暇付与経過年数テーブル
 * @author masaaki_jinno
 *
 */
@Stateless
public class JpaElapseYearRepository extends JpaRepository implements ElapseYearRepository{

	/**
	 * Create Elapse Year Domain From Entity
	 * @param c
	 * @return
	 */
	private ElapseYear createDomainFromEntity(KshmtHdspGrantElapsedYearsTbl e) {
		String companyId = AppContexts.user().companyId();
		int specialHolidayCode = e.pk.specialHolidayCode;
		boolean fixedAssign = e.fixedAssign == 1;
		int years = e.cycleYears;
		int months = e.cycleMonths;

		return ElapseYear.createFromJavaType(
			companyId, specialHolidayCode, fixedAssign, years, months);
	}

	/**
	 * Convert domain to entity
	 * @param domain
	 * @return
	 */
	private KshmtHdspGrantElapsedYearsTbl toEntity(ElapseYear domain) {
		KshmtHdspGrantElapsedYearsTblPK pk
			= new KshmtHdspGrantElapsedYearsTblPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v());
		int years = 0;
		int months = 0;
		if ( domain.getGrantCycleAfterTbl().isPresent() ){
			years = domain.getGrantCycleAfterTbl().get().getElapseYearMonth().getYear();
			months = domain.getGrantCycleAfterTbl().get().getElapseYearMonth().getMonth();
		}
		int fixedAssign = 0;
		if (domain.isFixedAssign()){
			fixedAssign = 1;
		}
		return new KshmtHdspGrantElapsedYearsTbl(pk, fixedAssign, years, months);
	}

	/**
	 * 取得
	 */
	@Override
	public Optional<ElapseYear> findByCode(
			CompanyId companyId, SpecialHolidayCode specialHolidayCode) {

		// 「特別休暇付与経過年数テーブル」
		Optional<ElapseYear> e = this.queryProxy()
			.find(new KshmtHdspGrantElapsedYearsTblPK(companyId.v(), specialHolidayCode.v()), KshmtHdspGrantElapsedYearsTbl.class)
			.map(x -> this.createDomainFromEntity(x));

		if ( !e.isPresent()){ // 取得できないとき
			return Optional.empty();
		}

		// 「特別休暇経過年数テーブル」
		List<ElapseYearMonthTbl> elapseYearMonthTblList
			= findElapseYearsTbl( companyId.v(), specialHolidayCode.v());

		e.get().setElapseYearMonthTblList(elapseYearMonthTblList);

		return e;
	}

	/**
	 * 削除
	 */
	@Override
	public void delete(String companyId, int specialHolidayCode) {

		// 「特別休暇付与経過年数テーブル」
		this.commandProxy().remove(
				KshmtHdspGrantElapsedYearsTbl.class,
				new KshmtHdspGrantElapsedYearsTblPK(companyId, specialHolidayCode));

		// 「特別休暇経過年数テーブル」
		deleteElapseYearsTbl(companyId, specialHolidayCode);
	}

	/**
	 * 更新
	 * @param elapseYear
	 */
	@Override
	public void update(ElapseYear elapseYear) {

		// 会社ID
		String companyId = elapseYear.getCompanyId();
		// 特別休暇コード
		int specialHolidayCode = elapseYear.getSpecialHolidayCode().v();

		// 「特別休暇付与経過年数テーブル」 前処理
		// Entityへ変換
		KshmtHdspGrantElapsedYearsTbl kshstElapseYears = this.toEntity(elapseYear);

		// 「特別休暇経過年数テーブル」 前処理
		// Entityへ変換
		List<ElapseYearMonthTbl> elapseYearMonthTblList
			= elapseYear.getElapseYearMonthTblList();

		List<KshmtHdspElapsedYearsTbl> kshstElapseYearsTblList
			= new ArrayList<KshmtHdspElapsedYearsTbl>();

		for(ElapseYearMonthTbl e: elapseYearMonthTblList){
			kshstElapseYearsTblList.add(toEntity(companyId, specialHolidayCode, e));
		}

		// 「特別休暇付与経過年数テーブル」
		// 削除
		this.delete(companyId, specialHolidayCode);
		
		this.getEntityManager().flush();
		
		// 追加
		this.commandProxy().insert(kshstElapseYears);

		// 「特別休暇経過年数テーブル」
		// 削除
		this.deleteElapseYearsTbl(companyId, specialHolidayCode);
		
		this.getEntityManager().flush();
		
		// 追加
		this.addElapseYearsTbl(kshstElapseYearsTblList);
	}

	// -----------------------------------------------------------------------
	// 以下、特別休暇経過年数テーブル ElapseYearMonthTbl

	private final static String SELECT_ELAPSE_YEARS_TBL_QUERY
		= "SELECT e "
		+ "FROM KshmtHdspElapsedYearsTbl e "
		+ "WHERE e.pk.companyId = :companyId AND e.pk.specialHolidayCode = :specialHolidayCode ";

	private final static String DELETE_ELAPSE_YEARS_TBL = "DELETE FROM KshmtHdspElapsedYearsTbl e "
		+ "WHERE e.pk.companyId =:companyId AND e.pk.specialHolidayCode = :specialHolidayCode ";

	/**
	 * Create Elapse Year Domain From Entity
	 * @param c
	 * @return
	 */
	private ElapseYearMonthTbl createDomainFromEntity(KshmtHdspElapsedYearsTbl e) {
//		String companyId = AppContexts.user().companyId();
//		int specialHolidayCode = e.pk.specialHolidayCode;
		int grantCnt = e.pk.grantCnt;
		int years = e.elapsedYears;
		int months = e.elapsedMonths;

		return ElapseYearMonthTbl.createFromJavaType(
			grantCnt, years, months);
	}

	/**
	 * Create Elapse Year Domain From Entity
	 * @param c
	 * @return
	 */
	private List<ElapseYearMonthTbl> createDomainFromEntityList(
			List<KshmtHdspElapsedYearsTbl> list) {

		List<ElapseYearMonthTbl> listOut = new ArrayList<ElapseYearMonthTbl>();
		for( KshmtHdspElapsedYearsTbl kshstElapseYearsTbl : list){
			listOut.add(createDomainFromEntity(kshstElapseYearsTbl));
		}
		return listOut;
	}

	/**
	 * Convert domain to entity
	 * @param domain
	 * @return
	 */
	private KshmtHdspElapsedYearsTbl toEntity(
			String companyId,
			int specialHolidayCode,
			ElapseYearMonthTbl domain) {

		KshmtHdspElapsedYearsTblPK pk
			= new KshmtHdspElapsedYearsTblPK(companyId, specialHolidayCode, domain.getGrantCnt());

		return new KshmtHdspElapsedYearsTbl(
				pk, domain.getElapseYearMonth().getYear(),
				domain.getElapseYearMonth().getMonth());
	}

	/**
	 * 取得
	 * @param companyId 会社ID
	 * @param specialHolidayCode　特別休暇コード
	 * @return List<ElapseYearMonthTbl>
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	private List<ElapseYearMonthTbl> findElapseYearsTbl(
			String companyId, int specialHolidayCode) {

		List<KshmtHdspElapsedYearsTbl> list
			= this.queryProxy().query(SELECT_ELAPSE_YEARS_TBL_QUERY, KshmtHdspElapsedYearsTbl.class)
				.setParameter("companyId", companyId)
				.setParameter("specialHolidayCode", specialHolidayCode)
				.getList();

		List<ElapseYearMonthTbl> listOut
			= createDomainFromEntityList(list);

		return listOut;
	}

	/**
	 * 削除
	 * @param companyId 会社ID
	 * @param specialHolidayCode 特別休暇コード
	 */
	private void deleteElapseYearsTbl(String companyId, int specialHolidayCode) {

		this.getEntityManager().createQuery(DELETE_ELAPSE_YEARS_TBL)
		.setParameter("companyId", companyId)
		.setParameter("specialHolidayCode", specialHolidayCode)
		.executeUpdate();
	}

	/**
	 * 追加
	 * @param list
	 */
	private void addElapseYearsTbl(List<KshmtHdspElapsedYearsTbl> list) {
		this.commandProxy().insertAll(list);
	}

}

