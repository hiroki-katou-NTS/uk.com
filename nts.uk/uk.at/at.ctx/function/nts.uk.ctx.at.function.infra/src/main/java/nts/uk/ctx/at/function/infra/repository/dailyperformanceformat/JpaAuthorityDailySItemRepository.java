package nts.uk.ctx.at.function.infra.repository.dailyperformanceformat;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthoritySFomatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailySItemRepository;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KfnmtAuthorityDailySItem;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KfnmtAuthorityDailySItemPK;

/**
 * 
 * @author anhdt
 *
 */
@Stateless
public class JpaAuthorityDailySItemRepository extends JpaRepository implements AuthorityDailySItemRepository {

	private static final String FIND;

	private static final String UPDATE_BY_KEY;

	private static final String DEL_BY_KEY;

	private static final String REMOVE_EXIST_DATA;

	static {
		
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KfnmtAuthorityDailySItem a ");
		builderString.append("WHERE a.kfnmtAuthorityDailyMobileItemPK.companyId = :companyId ");
		builderString.append("AND a.kfnmtAuthorityDailyMobileItemPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		FIND = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("UPDATE KfnmtAuthorityDailySItem a ");
		builderString.append("SET a.displayOrder = :displayOrder ");
		builderString.append("WHERE a.kfnmtAuthorityDailyMobileItemPK.companyId = :companyId ");
		builderString.append("AND a.kfnmtAuthorityDailyMobileItemPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		builderString.append("AND a.kfnmtAuthorityDailyMobileItemPK.attendanceItemId = :attendanceItemId ");
		UPDATE_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE FROM KfnmtAuthorityDailySItem a ");
		builderString.append("WHERE a.kfnmtAuthorityDailyMobileItemPK.companyId = :companyId ");
		builderString.append("AND a.kfnmtAuthorityDailyMobileItemPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");		
		DEL_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString
				.append("DELETE ");
		builderString
				.append("FROM KfnmtAuthorityDailySItem a ");
		builderString
				.append("WHERE a.kfnmtAuthorityDailyMobileItemPK.attendanceItemId IN :attendanceItemIds ");
		builderString
				.append("AND a.kfnmtAuthorityDailyMobileItemPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		builderString
				.append("AND a.kfnmtAuthorityDailyMobileItemPK.companyId = :companyId ");
		REMOVE_EXIST_DATA = builderString.toString();
	}
	
	/**
	 * Find AuthorityMobileFomatDaily
	 */
	@Override
	public List<AuthoritySFomatDaily> getAuthorityFormatDailyDetail(String companyId,
			DailyPerformanceFormatCode dailyPerformanceFormatCode) {
		return this.queryProxy()
				.query(FIND, KfnmtAuthorityDailySItem.class)
				.setParameter("companyId", companyId)
				.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode.v())
				.getList(f -> toDomain(f));
	}
	
	/**
	 * Update AuthorityMobileFomatDaily
	 */
	@Override
	public void update(String companyId, String dailyPCode, AuthoritySFomatDaily item) {
		this.getEntityManager()
			.createQuery(UPDATE_BY_KEY)
			.setParameter("companyId", companyId)
			.setParameter("dailyPerformanceFormatCode", dailyPCode)
			.setParameter("attendanceItemId", item.getAttendanceItemId())
			.setParameter("displayOrder", item.getDisplayOrder())
			.executeUpdate();
	}
	
	/**
	 * Add AuthorityMobileFomatDaily
	 */
	@Override
	public void add(String companyId, String dailyCode, List<AuthoritySFomatDaily> authorityFomatDailies) {
		authorityFomatDailies.forEach(f -> this.commandProxy().insert(toEntity(companyId, dailyCode, f)));
		this.getEntityManager().flush();
	}

	/**
	 * Update AuthorityMobileFomatDaily
	 */
	@Override
	public void remove(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode) {
		this.getEntityManager()
			.createQuery(DEL_BY_KEY)
			.setParameter("companyId", companyId)
			.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode.v())
			.executeUpdate();
	}
	/**
	 * Delete AuthorityMobileFomatDaily
	 */
	@Override
	public void deleteExistData(String companyId, String dailyPerformanceFormatCode, List<Integer> attendanceItemIds) {
		CollectionUtil.split(attendanceItemIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			this.getEntityManager()
					.createQuery(REMOVE_EXIST_DATA)
					.setParameter("attendanceItemIds", subList)
					.setParameter("companyId", companyId)
					.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode)
					.executeUpdate();
		});

	}

	private AuthoritySFomatDaily toDomain(KfnmtAuthorityDailySItem item) {
		return AuthoritySFomatDaily.createFromJavaType(item.kfnmtAuthorityDailyMobileItemPK.attendanceItemId,
				item.displayOrder);
	}
	
	private Object toEntity(String companyId, String dailyCode, AuthoritySFomatDaily item) {
		val entity = new KfnmtAuthorityDailySItem();
		
		KfnmtAuthorityDailySItemPK pk = new KfnmtAuthorityDailySItemPK();
		pk.attendanceItemId = item.getAttendanceItemId();
		pk.companyId = companyId;
		pk.dailyPerformanceFormatCode = dailyCode;
		
		entity.kfnmtAuthorityDailyMobileItemPK = pk;
		entity.displayOrder = item.getDisplayOrder();
		
		return entity;
	}

}
