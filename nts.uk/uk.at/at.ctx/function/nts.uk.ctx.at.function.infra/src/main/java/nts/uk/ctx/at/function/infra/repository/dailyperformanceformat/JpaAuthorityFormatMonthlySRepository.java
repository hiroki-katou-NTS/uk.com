package nts.uk.ctx.at.function.infra.repository.dailyperformanceformat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthoritySFomatMonthly;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatMonthlySRepository;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KfnmtAuthorityMonthlySItem;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KfnmtAuthorityMonthlySItemPK;

@Stateless
public class JpaAuthorityFormatMonthlySRepository extends JpaRepository implements AuthorityFormatMonthlySRepository {

	private static final String FIND;

	private static final String FIND_BY_LIST_CODE;

	private static final String UPDATE_BY_KEY;

	private static final String DEL_BY_KEY;

	private static final String REMOVE_EXIST_DATA;

	private static final String IS_EXIST_CODE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KfnmtAuthorityMonthlySItem a ");
		builderString.append("WHERE a.kfnmtAuthorityMonthlySItemPK.companyId = :companyId ");
		builderString.append("AND a.kfnmtAuthorityMonthlySItemPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		FIND = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KfnmtAuthorityMonthlySItem a ");
		builderString.append("WHERE a.kfnmtAuthorityMonthlySItemPK.companyId = :companyId ");
		builderString.append("AND a.kfnmtAuthorityMonthlySItemPK.dailyPerformanceFormatCode IN :listDailyPerformanceFormatCode ");
		FIND_BY_LIST_CODE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("UPDATE KfnmtAuthorityMonthlySItem a ");
		builderString.append("SET a.displayOrder = :displayOrder ");
		builderString.append("WHERE a.kfnmtAuthorityMonthlySItemPK.companyId = :companyId ");
		builderString.append("AND a.kfnmtAuthorityMonthlySItemPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		builderString.append("AND a.kfnmtAuthorityMonthlySItemPK.attendanceItemId = :attendanceItemId ");
		UPDATE_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KfnmtAuthorityMonthlySItem a ");
		builderString.append("WHERE a.kfnmtAuthorityMonthlySItemPK.attendanceItemId IN :attendanceItemIds ");
		builderString.append("AND a.kfnmtAuthorityMonthlySItemPK.companyId  = :companyId ");
		builderString.append("AND a.kfnmtAuthorityMonthlySItemPK.dailyPerformanceFormatCode  = :dailyPerformanceFormatCode  ");
		REMOVE_EXIST_DATA = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KfnmtAuthorityMonthlySItem a ");
		builderString.append("WHERE a.kfnmtAuthorityMonthlySItemPK.companyId = :companyId ");
		builderString.append("AND a.kfnmtAuthorityMonthlySItemPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		DEL_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT COUNT(a) ");
		builderString.append("FROM KfnmtAuthorityMonthlySItem a ");
		builderString.append("WHERE a.kfnmtAuthorityMonthlySItemPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		builderString.append("AND a.kfnmtAuthorityMonthlySItemPK.companyId = :companyId ");
		IS_EXIST_CODE = builderString.toString();
	}

	@Override
	public List<AuthoritySFomatMonthly> getMonthlyDetail(String companyId,
			DailyPerformanceFormatCode dailyPerformanceFormatCode) {
		return this.queryProxy()
				.query(FIND, KfnmtAuthorityMonthlySItem.class)
				.setParameter("companyId", companyId)
				.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode.v())
				.getList(f -> toDomain(f));
	}

	@Override
	public void add(String companyId, DailyPerformanceFormatCode code, List<AuthoritySFomatMonthly> authorityFomatMonthlies) {
		authorityFomatMonthlies.forEach(f -> this.commandProxy().insert(toEntity(companyId, code.v() , f)));
	}

	@Override
	public void update(String companyId, DailyPerformanceFormatCode code, AuthoritySFomatMonthly authorityFomatMonthly) {
		this.getEntityManager().createQuery(UPDATE_BY_KEY)
				.setParameter("companyId", companyId)
				.setParameter("dailyPerformanceFormatCode", code.v())
				.setParameter("attendanceItemId", authorityFomatMonthly.getAttendanceItemId())
				.setParameter("displayOrder", authorityFomatMonthly.getDisplayOrder())
				.executeUpdate();
	}

	@Override
	public void deleteExistData(String companyId, String dailyPerformanceFormatCode,List<Integer> attendanceItemIds) {
		CollectionUtil.split(attendanceItemIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			this.getEntityManager().createQuery(REMOVE_EXIST_DATA)
				.setParameter("attendanceItemIds", subList)
				.setParameter("companyId", companyId)
				.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode)
				.executeUpdate();
		});
		
	}

	@Override
	public void remove(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode) {
		this.getEntityManager()
				.createQuery(DEL_BY_KEY)
				.setParameter("companyId", companyId)
				.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode.v())
				.executeUpdate();
	}

	@Override
	public boolean checkExistCode(String companyId,DailyPerformanceFormatCode dailyPerformanceFormatCode) {
		return this.queryProxy().query(IS_EXIST_CODE, long.class)
				.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode.v())
				.setParameter("companyId", companyId)
				.getSingle().get() > 0;
	}

	private AuthoritySFomatMonthly toDomain(KfnmtAuthorityMonthlySItem item) {
		return AuthoritySFomatMonthly.createFromJavaType(item.kfnmtAuthorityMonthlySItemPK.attendanceItemId,
				item.displayOrder);
	}
	
	private Object toEntity(String companyId, String dailyCode, AuthoritySFomatMonthly item) {
		val entity = new KfnmtAuthorityMonthlySItem();
		
		KfnmtAuthorityMonthlySItemPK pk = new KfnmtAuthorityMonthlySItemPK();
		pk.attendanceItemId = item.getAttendanceItemId();
		pk.companyId = companyId;
		pk.dailyPerformanceFormatCode = dailyCode;
		
		entity.kfnmtAuthorityMonthlySItemPK = pk;
		entity.displayOrder = item.getDisplayOrder();
		
		return entity;
	}

	@Override
	public List<AuthoritySFomatMonthly> getListAuthorityFormatDaily(String companyId,
			List<String> listDailyPerformanceFormatCode) {
		return getListAuthorityFormatDaily(companyId, listDailyPerformanceFormatCode);
	}
	
	@Override
	public List<AuthoritySFomatMonthly> getListAuthorityFormatDaily(String companyId,
			Collection<String> listDailyPerformanceFormatCode) {
		List<KfnmtAuthorityMonthlySItem> results = new ArrayList<>();
		CollectionUtil.split(new ArrayList<>(listDailyPerformanceFormatCode), DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			results.addAll(this.queryProxy().query(FIND_BY_LIST_CODE, KfnmtAuthorityMonthlySItem.class)
				.setParameter("companyId", companyId)
				.setParameter("listDailyPerformanceFormatCode", subList).getList());
		});
		return results.stream().map(f -> toDomain(f)).collect(Collectors.toList());
	}
}
