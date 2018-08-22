package nts.uk.ctx.at.function.infra.repository.dailyperformanceformat;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFomatDaily;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatDailyRepository;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KfnmtAuthorityDailyItem;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KfnmtAuthorityDailyItemPK;

@Stateless
public class JpaAuthorityFormatDailyRepository extends JpaRepository implements AuthorityFormatDailyRepository {

	private static final String FIND;

	private static final String FIND_DETAIL;

	private static final String UPDATE_BY_KEY;

	private static final String DEL_BY_KEY;

	private static final String REMOVE_EXIST_DATA;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KfnmtAuthorityDailyItem a ");
		builderString.append("WHERE a.kfnmtAuthorityDailyItemPK.companyId = :companyId ");
		builderString
				.append("AND a.kfnmtAuthorityDailyItemPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		FIND = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KfnmtAuthorityDailyItem a ");
		builderString.append("WHERE a.kfnmtAuthorityDailyItemPK.companyId = :companyId ");
		builderString
				.append("AND a.kfnmtAuthorityDailyItemPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		builderString.append("AND a.kfnmtAuthorityDailyItemPK.sheetNo = :sheetNo ");
		FIND_DETAIL = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("UPDATE KfnmtAuthorityDailyItem a ");
		builderString.append(
				"SET a.displayOrder = :displayOrder , a.columnWidth = :columnWidth ");
		builderString.append("WHERE a.kfnmtAuthorityDailyItemPK.companyId = :companyId ");
		builderString.append("AND a.kfnmtAuthorityDailyItemPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		builderString.append("AND a.kfnmtAuthorityDailyItemPK.attendanceItemId = :attendanceItemId ");
		builderString.append("AND a.kfnmtAuthorityDailyItemPK.sheetNo = :sheetNo ");
		UPDATE_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KfnmtAuthorityDailyItem a ");
		builderString.append("WHERE a.kfnmtAuthorityDailyItemPK.companyId = :companyId ");
		builderString
				.append("AND a.kfnmtAuthorityDailyItemPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		DEL_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KfnmtAuthorityDailyItem a ");
		builderString.append("WHERE a.kfnmtAuthorityDailyItemPK.attendanceItemId IN :attendanceItemIds ");
		builderString.append("AND a.kfnmtAuthorityDailyItemPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		builderString.append("AND a.kfnmtAuthorityDailyItemPK.companyId = :companyId ");
		builderString.append("AND a.kfnmtAuthorityDailyItemPK.sheetNo = :sheetNo ");
		REMOVE_EXIST_DATA = builderString.toString();
	}

	@Override
	public List<AuthorityFomatDaily> getAuthorityFormatDaily(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode) {
		return this.queryProxy().query(FIND, KfnmtAuthorityDailyItem.class).setParameter("companyId", companyId)
				.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode.v()).getList(f -> toDomain(f));
	}

	@Override
	public List<AuthorityFomatDaily> getAuthorityFormatDailyDetail(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode,
			BigDecimal sheetNo) {
		return this.queryProxy().query(FIND_DETAIL, KfnmtAuthorityDailyItem.class).setParameter("companyId", companyId)
				.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode.v()).setParameter("sheetNo", sheetNo)
				.getList(f -> toDomain(f));
	}

	@Override
	public void update(AuthorityFomatDaily authorityFomatDaily) {
		this.getEntityManager().createQuery(UPDATE_BY_KEY).setParameter("companyId", authorityFomatDaily.getCompanyId())
				.setParameter("dailyPerformanceFormatCode", authorityFomatDaily.getDailyPerformanceFormatCode().v())
				.setParameter("attendanceItemId", authorityFomatDaily.getAttendanceItemId())
				.setParameter("sheetNo", authorityFomatDaily.getSheetNo())
				.setParameter("displayOrder", authorityFomatDaily.getDisplayOrder())
				.setParameter("columnWidth", authorityFomatDaily.getColumnWidth()).executeUpdate();
	}

	@Override
	public void add(List<AuthorityFomatDaily> authorityFomatDailies) {
		authorityFomatDailies.forEach(f -> this.commandProxy().insert(toEntity(f)));
		this.getEntityManager().flush();
	}

	@Override
	public void remove(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode) {
		this.getEntityManager().createQuery(DEL_BY_KEY).setParameter("companyId", companyId)
				.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode.v()).executeUpdate();
	}

	@Override
	public void deleteExistData(String companyId, String  dailyPerformanceFormatCode,
			BigDecimal sheetNo,List<Integer> attendanceItemIds) {
		this.getEntityManager().createQuery(REMOVE_EXIST_DATA)
			.setParameter("attendanceItemIds", attendanceItemIds)
			.setParameter("companyId", companyId)
			.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode)
			.setParameter("sheetNo", sheetNo)
			.executeUpdate();
	}

	private static AuthorityFomatDaily toDomain(KfnmtAuthorityDailyItem kfnmtAuthorityDailyItem) {
		AuthorityFomatDaily authorityFomatDaily = AuthorityFomatDaily.createFromJavaType(
				kfnmtAuthorityDailyItem.kfnmtAuthorityDailyItemPK.companyId,
				kfnmtAuthorityDailyItem.kfnmtAuthorityDailyItemPK.dailyPerformanceFormatCode,
				kfnmtAuthorityDailyItem.kfnmtAuthorityDailyItemPK.attendanceItemId,
				kfnmtAuthorityDailyItem.kfnmtAuthorityDailyItemPK.sheetNo,
				kfnmtAuthorityDailyItem.displayOrder,
				kfnmtAuthorityDailyItem.columnWidth);
		return authorityFomatDaily;
	}

	private KfnmtAuthorityDailyItem toEntity(AuthorityFomatDaily authorityFomatDaily) {
		val entity = new KfnmtAuthorityDailyItem();

		entity.kfnmtAuthorityDailyItemPK = new KfnmtAuthorityDailyItemPK();
		entity.kfnmtAuthorityDailyItemPK.companyId = authorityFomatDaily.getCompanyId();
		entity.kfnmtAuthorityDailyItemPK.dailyPerformanceFormatCode = authorityFomatDaily
				.getDailyPerformanceFormatCode().v();
		entity.kfnmtAuthorityDailyItemPK.attendanceItemId = authorityFomatDaily.getAttendanceItemId();
		entity.kfnmtAuthorityDailyItemPK.sheetNo = authorityFomatDaily.getSheetNo();
		entity.columnWidth = authorityFomatDaily.getColumnWidth();
		entity.displayOrder = authorityFomatDaily.getDisplayOrder();

		return entity;
	}

}
