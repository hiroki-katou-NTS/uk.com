package nts.uk.ctx.at.shared.infra.repository.specialholiday;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateCode;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTblRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantElapseYearMonth;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshstGrantDateElapseYearsTbl;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshstGrantDateElapseYearsTblPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshstGrantDateTbl;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshstGrantDateTblPK;
import nts.uk.shr.com.context.AppContexts;

/**
 * 特別休暇付与日数テーブル
 * @author tanlv
 *
 */
@Stateless
public class JpaGrantDateTblRepository extends JpaRepository implements GrantDateTblRepository {

	/**
	 * Create Grant Date Domain From Entity
	 * @param c
	 * @return
	 */
	private GrantDateTbl createDomainFromEntity(KshstGrantDateTbl c) {

		return GrantDateTbl.createFromJavaType(
				c.pk.companyId,
				c.pk.specialHolidayCode,
				c.pk.grantDateCd,
				c.grantName,
				c.isSpecified == 1,
				c.numberOfDays);
	}

	/**
	 * Create Grant Date from Domain
	 * @param domain
	 * @return
	 */
	private KshstGrantDateTbl toEntity(GrantDateTbl domain) {

		// 特別休暇付与日数テーブル
		KshstGrantDateTblPK pk = new KshstGrantDateTblPK(
				domain.getCompanyId(), domain.getSpecialHolidayCode().v(), domain.getGrantDateCode().v());

		int grantDays = 0;
		if (domain.getGrantedDays().isPresent()){
			grantDays = domain.getGrantedDays().get().v();
		}

		KshstGrantDateTbl kshstGrantDateTbl
			= new KshstGrantDateTbl(
					pk,
					domain.getGrantDateName().v(),
					domain.isSpecified() ? 1 : 0,
					domain.getGrantedDays().get().v());

		return kshstGrantDateTbl;
	}

	/**
	 * 取得
	 */
	@Override
	public Optional<GrantDateTbl> findByCode(
			String companyId, int specialHolidayCode, String grantDateCode) {

		// 「特別休暇付与日数テーブル」
		Optional<GrantDateTbl> e = this.queryProxy()
				.find(new KshstGrantDateTblPK(
						companyId, specialHolidayCode, grantDateCode),
						KshstGrantDateTbl.class)
				.map(x -> this.createDomainFromEntity(x));

		if ( !e.isPresent()){ // 取得できないとき
			return Optional.empty();
		}

		// 「特別休暇経過付与日数テーブル」
		List<GrantElapseYearMonth> elapseYearList
			= findGrantElapseYearMonth(companyId, specialHolidayCode, grantDateCode);
		e.get().setElapseYear(elapseYearList);

		return e;
	}



	/**
	 * 削除
	 */
	@Override
	public void delete(String companyId, int specialHolidayCode, String grantDateCode) {

		// 「特別休暇付与日数テーブル」
		this.commandProxy().remove(
				KshstGrantDateTbl.class,
				new KshstGrantDateTblPK(companyId, specialHolidayCode, grantDateCode));

		// 「特別休暇経過付与日数テーブル」
		deleteGrantElapseYearMonth(companyId, specialHolidayCode, grantDateCode);
	}

	/**
	 * 更新
	 * @param grantDateTbl
	 */
	public void update(GrantDateTbl grantDateTbl) {

		// 会社ID
		String companyId = grantDateTbl.getCompanyId();
		// 特別休暇コード
		int specialHolidayCode = grantDateTbl.getSpecialHolidayCode().v();
		// 付与テーブルコード
		String grantDateCode = grantDateTbl.getGrantDateCode().v();

		// 「特別休暇付与日数テーブル」 前処理
		// Entityへ変換
		KshstGrantDateTbl kshstGrantDateTbls = this.toEntity(grantDateTbl);

		// 「特別休暇経過付与日数テーブル」 前処理
		// Entityへ変換
		List<GrantElapseYearMonth> grantElapseYearMonthList
			= grantDateTbl.getElapseYear();

		List<KshstGrantDateElapseYearsTbl> kshstGrantDateElapseYearsTblList
			= new ArrayList<KshstGrantDateElapseYearsTbl>();

		for(GrantElapseYearMonth e: grantElapseYearMonthList){
			kshstGrantDateElapseYearsTblList.add(
					toEntity(companyId, specialHolidayCode, grantDateCode, e));
		}

		// 「特別休暇付与日数テーブル」
		// 削除
		this.delete(companyId, specialHolidayCode, grantDateCode);
		// 追加
		this.commandProxy().insert(kshstGrantDateTbls);

		// 「特別休暇経過付与日数テーブル」
		// 削除
		this.deleteGrantElapseYearMonth(companyId, specialHolidayCode, grantDateCode);
		// 追加
		this.addGrantElapseYearMonth(kshstGrantDateElapseYearsTblList);
	}

	// -----------------------------------------------------------------------
	// 以下、特別休暇経過付与日数テーブル GrantElapseYearMonth

	private final static String SELECT_GRANT_DATE_ELAPSE_YEARS_TBL_QUERY
		= "SELECT e "
		+ "FROM KshstGrantDateElapseYearsTbl e "
		+ "WHERE e.pk.companyId = :companyId AND e.pk.specialHolidayCode = :specialHolidayCode "
		+ "AND e.pk.grantDateCd = :grantDateCd ";

	private final static String DELETE_GRANT_DATE_ELAPSE_YEARS_TBL = "DELETE FROM KshstGrantDateElapseYearsTbl e "
		+ "WHERE e.pk.companyId =:companyId AND e.pk.specialHolidayCode = :specialHolidayCode "
		+ "AND e.pk.grantDateCd = :grantDateCd ";

	/**
	 * Create Domain From Entity
	 * @param c
	 * @return
	 */
	private GrantElapseYearMonth createDomainFromEntity(KshstGrantDateElapseYearsTbl e) {
//		String companyId = e.pk.companyId;
//		int specialHolidayCode = e.pk.specialHolidayCode;
//		String grantDateCd = e.pk.grantDateCd;
		int grantCnt = e.grantCnt;
		int grantDays = e.grantDays;

		return GrantElapseYearMonth.createFromJavaType(grantCnt, grantDays);
	}

	/**
	 * Create Elapse Year Domain From Entity
	 * @param c
	 * @return
	 */
	private List<GrantElapseYearMonth> createDomainFromEntityList(
			List<KshstGrantDateElapseYearsTbl> list) {

		List<GrantElapseYearMonth> listOut = new ArrayList<GrantElapseYearMonth>();
		for( KshstGrantDateElapseYearsTbl kshstGrantDateElapseYearsTbl : list){
			listOut.add(createDomainFromEntity(kshstGrantDateElapseYearsTbl));
		}
		return listOut;
	}

	/**
	 * Convert domain to entity
	 * @param domain
	 * @return
	 */
	private KshstGrantDateElapseYearsTbl toEntity(
			String companyId,
			int specialHolidayCode,
			String grantDateCode,
			GrantElapseYearMonth domain) {

		KshstGrantDateElapseYearsTblPK pk
			= new KshstGrantDateElapseYearsTblPK(companyId, specialHolidayCode, grantDateCode);

		return new KshstGrantDateElapseYearsTbl(
				pk, domain.getElapseNo(), domain.getGrantedDays().v());
	}

	/**
	 * 取得
	 * @param companyId 会社ID
	 * @param specialHolidayCode　特別休暇コード
	 * @param grantDateCode　特別休暇コード
	 * @return List<GrantDateTbl>
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	private List<GrantElapseYearMonth> findGrantElapseYearMonth(
			String companyId, int specialHolidayCode, String grantDateCode) {

		List<KshstGrantDateElapseYearsTbl> list
			= this.queryProxy().query(
				SELECT_GRANT_DATE_ELAPSE_YEARS_TBL_QUERY, KshstGrantDateElapseYearsTbl.class)
				.setParameter("companyId", companyId)
				.setParameter("specialHolidayCode", specialHolidayCode)
				.setParameter("grantDateCode", grantDateCode)
				.getList();

		List<GrantElapseYearMonth> listOut
			= createDomainFromEntityList(list);

		return listOut;
	}

	/**
	 * 取得
	 */
	@Override
	public Optional<GrantDateTbl> findByCode(
			CompanyId companyId, SpecialHolidayCode specialHolidayCode,
			GrantDateCode grantDateCode) {
		return findByCode(companyId.v(), specialHolidayCode.v(), grantDateCode.v());
	}

	/**
	 * 削除
	 * @param companyId 会社ID
	 * @param specialHolidayCode 特別休暇コード
	 * @param grantDateCode 付与テーブルコード
	 */
	private void deleteGrantElapseYearMonth(
			String companyId, int specialHolidayCode, String grantDateCode) {

		this.getEntityManager().createQuery(DELETE_GRANT_DATE_ELAPSE_YEARS_TBL)
			.setParameter("companyID", companyId)
			.setParameter("specialHolidayCD", specialHolidayCode)
			.setParameter("grantDateCode", grantDateCode)
			.executeUpdate();
	}

	/**
	 * 追加
	 * @param list
	 */
	private void addGrantElapseYearMonth(List<KshstGrantDateElapseYearsTbl> list) {
		this.commandProxy().insert(list);
	}

	/**
	 * 更新
	 */
	public void updateGrantElapseYearMonth(
			String companyId, int specialHolidayCode, String grantDateCode,
			List<KshstGrantDateElapseYearsTbl> list) {

		// 削除
		deleteGrantElapseYearMonth(companyId, specialHolidayCode, grantDateCode);
		// 追加
		addGrantElapseYearMonth(list);
	}

	@Override
	public void add(GrantDateTbl specialHoliday) {
		// TODO 自動生成されたメソッド・スタブ

	}

//	@Override
//	public void changeAllProvision(int specialHolidayCode) {
//		String companyId = AppContexts.user().companyId();
//
//		this.getEntityManager().createQuery(CHANGE_ALL_PROVISION)
//					.setParameter("companyId", companyId)
//					.setParameter("specialHolidayCode", specialHolidayCode)
//					.executeUpdate();
//	}

//	@Override
//	public Optional<GrantDateTbl> findByCodeAndIsSpecified(String companyId, int specialHolidayCode) {
//		return this.queryProxy().query(SELECT_CODE_ISSPECIAL, KshstGrantDateTbl.class)
//				.setParameter("companyId", companyId)
//				.setParameter("specialHolidayCode", specialHolidayCode)
//				.getSingle(c -> {
//					String grantDateCd = String.valueOf(c.pk.grantDateCd);
//					String grantName = String.valueOf(c.grantName);
//					boolean isSpecified = Integer.parseInt(String.valueOf(c.isSpecified)) == 1 ? true : false;
////					boolean fixedAssign = Integer.parseInt(String.valueOf(c.fixedAssign)) == 1 ? true : false;
//					int numberOfDays = c.numberOfDays != null ? Integer.parseInt(String.valueOf(c.numberOfDays)) : 0;
//
//					return GrantDateTbl.createFromJavaType(grantDateCd, grantName, isSpecified, numberOfDays);
//				});
//	}
//
//	@Override
//	public Map<String, List<GrantDateTbl>> findElapseByGrantDateCdLst(String companyId, int specialHolidayCode, List<String> grantDateCode) {
//		if(grantDateCode.isEmpty()) return new HashMap<>();
//		List<GrantDateTbl> result = this.queryProxy().query(SELECT_ELAPSE_BY_GDCD_LST_QUERY, Object[].class)
//					.setParameter("companyId", companyId)
//					.setParameter("specialHolidayCode", specialHolidayCode)
//					.setParameter("grantDateCd", grantDateCode)
//				.getList(c -> {
//					return createDomainFromEntity(c);
//				});
//		 return result.stream().collect(Collectors.groupingBy(c -> c.getGrantDateCode()));
//	}

//	@Override
//	public List<GrantDateTbl> findElapseByGrantDateCd(String companyId, int specialHolidayCode, String grantDateCode) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void delete(String companyId, int specialHolidayCode, String grantDateCode) {
//		// TODO Auto-generated method stub
//
//	}
}
