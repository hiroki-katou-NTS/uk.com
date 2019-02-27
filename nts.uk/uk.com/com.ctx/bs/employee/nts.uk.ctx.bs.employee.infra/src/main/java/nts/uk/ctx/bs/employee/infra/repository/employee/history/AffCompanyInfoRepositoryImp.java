package nts.uk.ctx.bs.employee.infra.repository.employee.history;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyInfo;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyInfoRepository;
import nts.uk.ctx.bs.employee.infra.entity.employee.history.BsymtAffCompanyInfo;
import nts.uk.ctx.bs.employee.infra.entity.employee.history.BsymtAffCompanyInfoPk;

@Stateless
public class AffCompanyInfoRepositoryImp extends JpaRepository implements AffCompanyInfoRepository {

	private static final String SELECT_NO_PARAM = String.join(" ", "SELECT c FROM BsymtAffCompanyInfo c");

	private static final String SELECT_BY_HISTID = String.join(" ", SELECT_NO_PARAM,
			"WHERE c.bsymtAffCompanyInfoPk.historyId = :histId");

	@Override
	public void add(AffCompanyInfo domain) {
		commandProxy().insert(toEntity(domain));
		this.getEntityManager().flush();
	}

	@Override
	public void update(AffCompanyInfo domain) {
		BsymtAffCompanyInfo entity = this.queryProxy().query(SELECT_BY_HISTID, BsymtAffCompanyInfo.class)
				.setParameter("histId", domain.getHistoryId()).getSingleOrNull();
		if (entity != null) {
			entity.sid = domain.getSid();
			entity.adoptionDate = domain.getAdoptionDate();
			entity.retirementAllowanceCalcStartDate = domain.getRetirementAllowanceCalcStartDate();
			entity.recruitmentCategoryCode = domain.getRecruitmentClassification().v();
			
			this.commandProxy().update(entity);
		}
	}

	@Override
	public void remove(AffCompanyInfo domain) {
		this.remove(domain.getHistoryId());
	}

	@Override
	public void remove(String histId) {
		this.commandProxy().remove(BsymtAffCompanyInfo.class, new BsymtAffCompanyInfoPk(histId));
	}

	@Override
	public AffCompanyInfo getAffCompanyInfoByHistId(String histId) {
		return this.queryProxy().query(SELECT_BY_HISTID, BsymtAffCompanyInfo.class).setParameter("histId", histId)
				.getSingle(m -> toDomain(m)).orElse(null);
	}

	private AffCompanyInfo toDomain(BsymtAffCompanyInfo entity) {
		return AffCompanyInfo.createFromJavaType(entity.sid, entity.bsymtAffCompanyInfoPk.historyId, entity.recruitmentCategoryCode,
				entity.adoptionDate, entity.retirementAllowanceCalcStartDate);
	}

	private BsymtAffCompanyInfo toEntity(AffCompanyInfo domain) {
		BsymtAffCompanyInfoPk entityPk = new BsymtAffCompanyInfoPk(domain.getHistoryId());

		return new BsymtAffCompanyInfo(entityPk, domain.getSid(), domain.getRecruitmentClassification().v(), domain.getAdoptionDate(),
				domain.getRetirementAllowanceCalcStartDate(), null);
	}

	@Override
	@SneakyThrows
	public List<AffCompanyInfo> getAffCompanyInfoByHistId(List<String> histIds) {
		List<AffCompanyInfo> resultList = new ArrayList<>();
		
		CollectionUtil.split(histIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String sql = "SELECT * FROM BSYMT_AFF_COM_INFO i WHERE i.HIST_ID IN ("
					+ NtsStatement.In.createParamsString(subList) + ")";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(i + 1, subList.get(i));
				}

				List<AffCompanyInfo> lstObj = new NtsResultSet(stmt.executeQuery()).getList(r -> {
					BsymtAffCompanyInfo entity = new BsymtAffCompanyInfo();
					entity.bsymtAffCompanyInfoPk = new BsymtAffCompanyInfoPk(r.getString("HIST_ID"));
					entity.sid = r.getString("SID");
					entity.recruitmentCategoryCode = r.getString("RECRUIMENT_CATEGORY_CD");
					entity.adoptionDate = r.getGeneralDate("ADOPTION_DATE");
					entity.retirementAllowanceCalcStartDate = r.getGeneralDate("RETIREMENT_CALC_STR_D");
					return toDomain(entity);
				}).stream().collect(Collectors.toList());
				resultList.addAll(lstObj);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});

		return resultList;
	}
}
