package nts.uk.ctx.at.function.infra.repository.dailyperformanceformat;

import java.util.List;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityDailyPerformanceSFormat;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthoritySFomatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailyPerformanceSFormatRepository;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KfnmtAuthorityDailyPerformanceFormat;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KfnmtAuthorityDailyPerformanceSFormat;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KfnmtAuthorityDailyPerformanceSFormatPK;

/**
 * @author anhdt
 * 会社の日別実績の修正のフォーマット
 */
@Stateless
public class JpaAuthorityDailyPerformanceSFormatRepository extends JpaRepository
		implements AuthorityDailyPerformanceSFormatRepository {

	private static final String FIND;
	
	private static final String FIND_ALL_BY_CID;

	private static final String UPDATE_BY_KEY;

	private static final String DEL_BY_KEY;

	private static final String IS_EXIST_DATA;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT b.ATTENDANCE_ITEM_ID, b.DISPLAY_ORDER, a.CID, a.DAILY_PERFORMANCE_FORMAT_CD, a.DAILY_PERFORMANCE_FORMAT_NAME ");
		builderString.append("FROM KFNMT_DAY_FORM_S a left join KFNMT_DAY_FORM_S_DAY_ITEM b ON a.CID = b.CID AND a.DAILY_PERFORMANCE_FORMAT_CD = b.DAILY_PERFORMANCE_FORMAT_CD ");
		builderString.append("WHERE a.CID = ? and a.DAILY_PERFORMANCE_FORMAT_CD = ? ");
		FIND = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT a FROM  KfnmtAuthorityDailyPerformanceSFormat a ");
		builderString.append("WHERE a.kfnmtAuthorityDailyPerformanceMobileFormatPK.companyId = :companyId ");
		FIND_ALL_BY_CID = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("UPDATE KfnmtAuthorityDailyPerformanceSFormat a ");
		builderString.append("SET a.dailyPerformanceFormatName = :dailyPerformanceFormatName ");
		builderString.append("WHERE a.kfnmtAuthorityDailyPerformanceMobileFormatPK.companyId = :companyId ");
		builderString.append("AND a.kfnmtAuthorityDailyPerformanceMobileFormatPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		UPDATE_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KfnmtAuthorityDailyPerformanceSFormat a ");
		builderString.append("WHERE a.kfnmtAuthorityDailyPerformanceMobileFormatPK.companyId = :companyId ");
		builderString.append("AND a.kfnmtAuthorityDailyPerformanceMobileFormatPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		DEL_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT COUNT(a) ");
		builderString.append("FROM KfnmtAuthorityDailyPerformanceSFormat a ");
		builderString.append("WHERE a.kfnmtAuthorityDailyPerformanceMobileFormatPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		builderString.append("AND a.kfnmtAuthorityDailyPerformanceMobileFormatPK.companyId = :companyId ");
		IS_EXIST_DATA = builderString.toString();
	}
	/*
	 * Add AuthorityDailyPerformanceMobileFormat
	 * 
	 * */
	@Override
	public void add(AuthorityDailyPerformanceSFormat authorityDailyPerformanceFormat) {
		this.commandProxy().insert(toEntity(authorityDailyPerformanceFormat));
	}
	
	/*
	 * Remove AuthorityDailyPerformanceMobileFormat
	 * 
	 * */
	@Override
	public void remove(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode) {
		this.getEntityManager()
				.createQuery(DEL_BY_KEY, KfnmtAuthorityDailyPerformanceSFormat.class)
				.setParameter("companyId", companyId)
				.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode.v())
				.executeUpdate();
	}
	/*
	 * check data exist for update
	 * */
	@Override
	public boolean checkExistCode(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode) {
		return this.queryProxy()
				.query(IS_EXIST_DATA, long.class)
				.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode.v())
				.setParameter("companyId", companyId)
				.getSingle().get() > 0;
	}
	
	
	/*
	 * Update AuthorityDailyPerformanceMobileFormat
	 * 
	 * */
	@Override
	public void update(AuthorityDailyPerformanceSFormat authorityDailyPerformanceFormat) {
		this.getEntityManager()
				.createQuery(UPDATE_BY_KEY, KfnmtAuthorityDailyPerformanceSFormat.class)
				.setParameter("companyId", authorityDailyPerformanceFormat.getCompanyId())
				.setParameter("dailyPerformanceFormatCode",authorityDailyPerformanceFormat.getDailyPerformanceFormatCode().v())
				.setParameter("dailyPerformanceFormatName",authorityDailyPerformanceFormat.getDailyPerformanceFormatName().v())
				.executeUpdate();
	}
	
	/*
	 * find AuthorityDailyPerformanceMobileFormat by companyId
	 * 
	 * */
	@Override
	public List<AuthorityDailyPerformanceSFormat> getAllByCompanyId(String companyId) {
		 return this.queryProxy()
				 .query(FIND_ALL_BY_CID, KfnmtAuthorityDailyPerformanceSFormat.class)
				 .setParameter("companyId", companyId)
				 .getList(f -> toDomain(f));
	}

	private static AuthorityDailyPerformanceSFormat toDomain(KfnmtAuthorityDailyPerformanceSFormat authMobFormat) {
		return AuthorityDailyPerformanceSFormat
				 	.createFromJavaType(
				 			authMobFormat.kfnmtAuthorityDailyPerformanceMobileFormatPK.companyId,
				 			authMobFormat.kfnmtAuthorityDailyPerformanceMobileFormatPK.dailyPerformanceFormatCode,
				 			authMobFormat.dailyPerformanceFormatName);
	}

	private KfnmtAuthorityDailyPerformanceSFormat toEntity(
			AuthorityDailyPerformanceSFormat authorityDailyPerformanceFormat) {
		val entity = new KfnmtAuthorityDailyPerformanceSFormat();

		entity.kfnmtAuthorityDailyPerformanceMobileFormatPK = new KfnmtAuthorityDailyPerformanceSFormatPK();
		entity.kfnmtAuthorityDailyPerformanceMobileFormatPK.companyId = authorityDailyPerformanceFormat.getCompanyId();
		entity.kfnmtAuthorityDailyPerformanceMobileFormatPK.dailyPerformanceFormatCode = authorityDailyPerformanceFormat
				.getDailyPerformanceFormatCode().v();
		entity.dailyPerformanceFormatName = authorityDailyPerformanceFormat.getDailyPerformanceFormatName().v();

		return entity;
	}
	
	/*
	 * find all by company and code
	 * */
	@Override
	@SneakyThrows
	public AuthorityDailyPerformanceSFormat getAllByCompanyIdAndCode(String companyId, String formatCode) {

		try (val statement = this.connection().prepareStatement(FIND)) {
			statement.setString(1, companyId);
			statement.setString(2, formatCode);

			List<AuthoritySFomatDaily> results = new NtsResultSet(statement.executeQuery()).getList(res -> {
				return new AuthoritySFomatDaily(res.getInt("ATTENDANCE_ITEM_ID"), res.getInt("DISPLAY_ORDER"), null);
			});

			return new AuthorityDailyPerformanceSFormat(null, null, null, results, null);
		}

	}

}
