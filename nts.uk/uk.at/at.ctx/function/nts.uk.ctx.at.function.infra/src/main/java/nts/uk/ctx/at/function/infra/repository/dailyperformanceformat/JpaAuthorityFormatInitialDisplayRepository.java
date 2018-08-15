package nts.uk.ctx.at.function.infra.repository.dailyperformanceformat;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthorityFormatInitialDisplay;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatInitialDisplayRepository;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KfnmtDailyPerformanceDisplay;
import nts.uk.ctx.at.function.infra.entity.dailyperformanceformat.KfnmtDailyPerformanceDisplayPK;

@Stateless
public class JpaAuthorityFormatInitialDisplayRepository extends JpaRepository
		implements AuthorityFormatInitialDisplayRepository {

	private static final String DEL_BY_KEY;

	private static final String UPDATE_BY_KEY;

	private static final String IS_EXIST_DATA;

	private static final String IS_EXIST_DATA_BY_CID;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KfnmtDailyPerformanceDisplay a ");
		builderString.append("WHERE a.kfnmtDailyPerformanceDisplayPK.companyId = :companyId ");
		builderString.append(
				"AND a.kfnmtDailyPerformanceDisplayPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		DEL_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("UPDATE KfnmtDailyPerformanceDisplay a ");
		builderString.append(
				"SET a.kfnmtDailyPerformanceDisplayPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		builderString.append("WHERE a.kfnmtDailyPerformanceDisplayPK.companyId = :companyId ");
		UPDATE_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT COUNT(a) ");
		builderString.append("FROM KfnmtDailyPerformanceDisplay a ");
		builderString.append("WHERE a.kfnmtDailyPerformanceDisplayPK.dailyPerformanceFormatCode = :dailyPerformanceFormatCode ");
		builderString.append("AND a.kfnmtDailyPerformanceDisplayPK.companyId = :companyId ");
		IS_EXIST_DATA = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT COUNT(a) ");
		builderString.append("FROM KfnmtDailyPerformanceDisplay a ");
		builderString.append("WHERE a.kfnmtDailyPerformanceDisplayPK.companyId = :companyId ");
		IS_EXIST_DATA_BY_CID = builderString.toString();
	}

	@Override
	public void add(AuthorityFormatInitialDisplay authorityFormatInitialDisplay) {
		this.commandProxy().insert(toEntity(authorityFormatInitialDisplay));
		this.getEntityManager().flush();
	}

	@Override
	public void update(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode) {
		this.getEntityManager().createQuery(UPDATE_BY_KEY).setParameter("companyId", companyId)
				.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode.v()).executeUpdate();
	}

	@Override
	public void remove(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode) {
		this.getEntityManager().createQuery(DEL_BY_KEY).setParameter("companyId", companyId)
				.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode.v()).executeUpdate();
	}

	@Override
	public boolean checkExistData(String companyId,DailyPerformanceFormatCode dailyPerformanceFormatCode) {
		return this.queryProxy().query(IS_EXIST_DATA, long.class)
				.setParameter("dailyPerformanceFormatCode", dailyPerformanceFormatCode.v())
				.setParameter("companyId", companyId)
				.getSingle().get() > 0;
	}

	@Override
	public boolean checkExistDataByCompanyId(String companyId) {
		return this.queryProxy().query(IS_EXIST_DATA_BY_CID, long.class).setParameter("companyId", companyId)
				.getSingle().get() > 0;
	}

	private KfnmtDailyPerformanceDisplay toEntity(AuthorityFormatInitialDisplay authorityFormatInitialDisplay) {
		val entity = new KfnmtDailyPerformanceDisplay();

		entity.kfnmtDailyPerformanceDisplayPK = new KfnmtDailyPerformanceDisplayPK();
		entity.kfnmtDailyPerformanceDisplayPK.companyId = authorityFormatInitialDisplay.getCompanyId();
		entity.kfnmtDailyPerformanceDisplayPK.dailyPerformanceFormatCode = authorityFormatInitialDisplay
				.getDailyPerformanceFormatCode().v();

		return entity;
	}

	private static final String GET_ALL_BY_CID = "SELECT c FROM KfnmtDailyPerformanceDisplay c"
			+ " WHERE c.kfnmtDailyPerformanceDisplayPK.companyId = :companyId ";
	
	@Override
	public void removeByCid(String companyId) {
		List<KfnmtDailyPerformanceDisplay> data = this.queryProxy().query(GET_ALL_BY_CID,KfnmtDailyPerformanceDisplay.class)
				.setParameter("companyId", companyId)
				.getList();
		this.commandProxy().removeAll(data);
		this.getEntityManager().flush();
		
	}

}
