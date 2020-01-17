package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empcomofficehis;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformationRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.SocialInsuranceOfficeCode;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomofficehis.QqsmtEmpCorpOffHis;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empcomofficehis.QqsmtEmpCorpOffHisPk;
import nts.uk.shr.com.context.AppContexts;


@Stateless
public class JpaAffOfficeInformationRepository extends JpaRepository implements AffOfficeInformationRepository{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpCorpOffHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empCorpOffHisPk.employeeId =:employeeId AND f.empCorpOffHisPk.historyId =:historyId AND  f.empCorpOffHisPk.cid =:cid ";
    private static final String SELECT_BY_HIST_IDS = SELECT_ALL_QUERY_STRING + " WHERE f.empCorpOffHisPk.historyId IN :hisIds AND  f.empCorpOffHisPk.cid =:cid";


    @Override
    public List<AffOfficeInformation> getAllAffOfficeInformation() {
        return null;
    }


    @Override
    public Optional<AffOfficeInformation> getAffOfficeInformationById(String empId, String hisId) {
        val result = this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpCorpOffHis.class)
                .setParameter("employeeId", empId)
                .setParameter("historyId",hisId)
                .setParameter("cid", AppContexts.user().companyId())
                .getSingle();
        return result.isPresent() ? Optional.of(new AffOfficeInformation(result.get().empCorpOffHisPk.historyId, new SocialInsuranceOfficeCode(result.get().socialInsuranceOfficeCd))) : Optional.empty();
    }

    @Override
    public List<AffOfficeInformation> getByHistIds(List<String> histIds){
        return this.queryProxy().query(SELECT_BY_HIST_IDS, QqsmtEmpCorpOffHis.class)
                .setParameter("historyId", histIds).setParameter("cid", AppContexts.user().companyId())
                .getList(x -> x.toDomain());
    }

    @Override
    public void add(AffOfficeInformation domain) {

    }

    @Override
    public void update(AffOfficeInformation domain) {

    }

    @Override
    public void remove(String socialInsuranceOfficeCd, String hisId) {

    }


	@Override
	public List<AffOfficeInformation> getAllAffOfficeInformationByHistId(String cid, List<String> histIds) {
		
		if (CollectionUtil.isEmpty(histIds)) {
			
			return new ArrayList<>();
		}
		
		List<AffOfficeInformation> result = new ArrayList<>();
		
		CollectionUtil.split(histIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			
			String sql = "SELECT * FROM QQSDT_SYAHO_OFFICE_INFO a"
					
					  + " WHERE  a.CID =? AND  a.HIST_ID in (" + NtsStatement.In.createParamsString(subList) + ")";
			
				try(PreparedStatement statement = this.connection().prepareStatement(sql)){
					
					statement.setString(1, cid);
					
					
					for (int i = 0; i < subList.size(); i++) {
						
						statement.setString(2 + i, subList.get(i));
					}
					
					List<AffOfficeInformation> domains = new NtsResultSet(statement.executeQuery()).getList(rec -> {
						
						QqsmtEmpCorpOffHisPk pk = new QqsmtEmpCorpOffHisPk(rec.getString("CID"), rec.getString("SID"), rec.getString("HIST_ID"));
						
						QqsmtEmpCorpOffHis entity=  new QqsmtEmpCorpOffHis(pk, rec.getGeneralDate("START_DATE"), rec.getGeneralDate("END_DATE"), rec.getString("SYAHO_OFFICE_CD") );
						
						return entity.toDomain();
					});
					
					if(!CollectionUtil.isEmpty(domains)) {
						
						result.addAll(domains);
						
					}
					
				} catch (SQLException e) {
					
					throw new RuntimeException(e);
					
				};
		});
		
		return result;
	}
}
