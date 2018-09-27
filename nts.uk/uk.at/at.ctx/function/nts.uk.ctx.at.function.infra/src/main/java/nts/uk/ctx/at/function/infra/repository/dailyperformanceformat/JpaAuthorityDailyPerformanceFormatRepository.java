package nts.uk.ctx.at.function.infra.repository.dailyperformanceformat;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityDailyPerformanceFormat;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityDailyPerformanceFormatRepository;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KfnmtAuthorityDailyPerformanceFormat;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KfnmtAuthorityDailyPerformanceFormatPK;

@Stateless
public class JpaAuthorityDailyPerformanceFormatRepository extends JpaRepository
		implements AuthorityDailyPerformanceFormatRepository {

	private static final String FIND;

	private static final String UPDATE_BY_KEY;

	private static final String DEL_BY_KEY;

	private static final String IS_EXIST_DATA;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KfnmtAuthorityDailyPerformanceFormat a ");
		builderString.append("WHERE a.kfnmtAuthorityDailyPerformanceFormatPK.companyId = :companyId ");
		FIND = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("UPDATE KfnmtAuthorityDailyPerformanceFormat a ");
		builderString.append("SET a.dailyPerformanceFormatName = :dailyPerformanceFormatName ");
		builderString.append("WHERE a.kfnmtAuthorityDailyPerformanceFormatPK.companyId = :companyId ");
		builderString.append(
				"AND a.kfnmtAuthorityDailyPerformanceFormatPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		UPDATE_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KfnmtAuthorityDailyPerformanceFormat a ");
		builderString.append("WHERE a.kfnmtAuthorityDailyPerformanceFormatPK.companyId = :companyId ");
		builderString.append(
				"AND a.kfnmtAuthorityDailyPerformanceFormatPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		DEL_BY_KEY = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("SELECT COUNT(a) ");
		builderString.append("FROM KfnmtAuthorityDailyPerformanceFormat a ");
		builderString
				.append("WHERE a.kfnmtAuthorityDailyPerformanceFormatPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		builderString
				.append(" AND a.kfnmtAuthorityDailyPerformanceFormatPK.companyId = :companyId ");
		IS_EXIST_DATA = builderString.toString();
	}

	@Override
	public void add(AuthorityDailyPerformanceFormat authorityDailyPerformanceFormat) {
		this.commandProxy().insert(toEntity(authorityDailyPerformanceFormat));
		this.getEntityManager().flush();
	}

	@Override
	public void remove(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode) {
		this.getEntityManager().createQuery(DEL_BY_KEY, KfnmtAuthorityDailyPerformanceFormat.class)
				.setParameter("companyId", companyId)
				.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode.v()).executeUpdate();
	}

	@Override
	public boolean checkExistCode(String companyId,DailyPerformanceFormatCode dailyPerformanceFormatCode) {
		return this.queryProxy().query(IS_EXIST_DATA, long.class)
				.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode.v())
				.setParameter("companyId", companyId)
				.getSingle().get() > 0;
	}

	@Override
	public void update(AuthorityDailyPerformanceFormat authorityDailyPerformanceFormat) {
		this.getEntityManager().createQuery(UPDATE_BY_KEY, KfnmtAuthorityDailyPerformanceFormat.class)
				.setParameter("companyId", authorityDailyPerformanceFormat.getCompanyId())
				.setParameter("dailyPerformanceFormatCode", authorityDailyPerformanceFormat.getDailyPerformanceFormatCode().v())
				.setParameter("dailyPerformanceFormatName", authorityDailyPerformanceFormat.getDailyPerformanceFormatName().v())
				.executeUpdate();
	}

	@Override
	public List<AuthorityDailyPerformanceFormat> getListCode(String companyId) {
		return this.queryProxy().query(FIND, KfnmtAuthorityDailyPerformanceFormat.class)
				.setParameter("companyId", companyId).getList(f -> toDomain(f));
	}

	private static AuthorityDailyPerformanceFormat toDomain(
			KfnmtAuthorityDailyPerformanceFormat kfnmtAuthorityDailyPerformanceFormat) {
		AuthorityDailyPerformanceFormat authorityDailyPerformanceFormat = AuthorityDailyPerformanceFormat
				.createFromJavaType(
						kfnmtAuthorityDailyPerformanceFormat.kfnmtAuthorityDailyPerformanceFormatPK.companyId,
						kfnmtAuthorityDailyPerformanceFormat.kfnmtAuthorityDailyPerformanceFormatPK.dailyPerformanceFormatCode,
						kfnmtAuthorityDailyPerformanceFormat.dailyPerformanceFormatName);
		return authorityDailyPerformanceFormat;
	}

	private KfnmtAuthorityDailyPerformanceFormat toEntity(
			AuthorityDailyPerformanceFormat authorityDailyPerformanceFormat) {
		val entity = new KfnmtAuthorityDailyPerformanceFormat();

		entity.kfnmtAuthorityDailyPerformanceFormatPK = new KfnmtAuthorityDailyPerformanceFormatPK();
		entity.kfnmtAuthorityDailyPerformanceFormatPK.companyId = authorityDailyPerformanceFormat.getCompanyId();
		entity.kfnmtAuthorityDailyPerformanceFormatPK.dailyPerformanceFormatCode = authorityDailyPerformanceFormat
				.getDailyPerformanceFormatCode().v();
		entity.dailyPerformanceFormatName = authorityDailyPerformanceFormat.getDailyPerformanceFormatName().v();

		return entity;
	}

}
