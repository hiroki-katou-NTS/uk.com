package nts.uk.ctx.pr.core.infra.repository.wageprovision.processdatecls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYear;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYearRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmploymentCode;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtEmpTiedProYear;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtEmpTiedProYearPk;

@Stateless
public class JpaEmpTiedProYearRepository extends JpaRepository implements EmpTiedProYearRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpTiedProYear f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empTiedProYearPk.cid =:cid AND  f.empTiedProYearPk.processCateNo =:processCateNo ";
    private static final String SELECT_BY_EMPLOYMENT = SELECT_ALL_QUERY_STRING + " WHERE f.empTiedProYearPk.cid =:cid AND f.empTiedProYearPk.employmentCode =:employmentCode";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.empTiedProYearPk.cid =:cid ";

    private static final String DELETE_BY_PROCESSCATENO = "DELETE FROM QpbmtEmpTiedProYear f WHERE f.empTiedProYearPk.cid =:cid AND  f.empTiedProYearPk.processCateNo =:processCateNo ";

    @Override
    public Optional<EmpTiedProYear> getEmpTiedProYearById(String cid, int processCateNo) {
        List<QpbmtEmpTiedProYear> item = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtEmpTiedProYear.class)
                .setParameter("cid", cid)
                .setParameter("processCateNo", processCateNo)
                .getList();
        if (item.isEmpty()) return Optional.empty();
        return Optional.of(QpbmtEmpTiedProYear.toDomain(item));
    }

    @Override
    public Optional<EmpTiedProYear> getEmpTiedProYearByEmployment(String cid, String employmentCode) {
        List<QpbmtEmpTiedProYear> item = this.queryProxy().query(SELECT_BY_EMPLOYMENT, QpbmtEmpTiedProYear.class)
                .setParameter("cid", cid)
                .setParameter("employmentCode", employmentCode)
                .getList();
        if (item.isEmpty()) return Optional.empty();
        return Optional.of(QpbmtEmpTiedProYear.toDomain(item));
    }


    @Override
    public void add(EmpTiedProYear domain) {
        this.commandProxy().insertAll(QpbmtEmpTiedProYear.toEntity(domain));
    }

    @Override
    public void update(EmpTiedProYear oldDomain, EmpTiedProYear newDomain) {
        List<EmploymentCode> list = newDomain.getEmploymentCodes().stream().collect(Collectors.toList());
        newDomain.getEmploymentCodes().removeAll(oldDomain.getEmploymentCodes());
        oldDomain.getEmploymentCodes().removeAll(list);
        List<QpbmtEmpTiedProYearPk> pks = oldDomain.getEmploymentCodes().stream().map(x -> new QpbmtEmpTiedProYearPk(oldDomain.getCid(), oldDomain.getProcessCateNo(), x.v())).collect(Collectors.toList());
        this.commandProxy().removeAll(QpbmtEmpTiedProYear.class, pks);
        this.commandProxy().insertAll(QpbmtEmpTiedProYear.toEntity(newDomain));
    }

    @Override
    public void remove(String cid, int processCateNo) {
        this.getEntityManager().createQuery(DELETE_BY_PROCESSCATENO)
                .setParameter("cid", cid)
                .setParameter("processCateNo", processCateNo).executeUpdate();
    }

	@Override
	public List<EmpTiedProYear> getEmpTiedProYearById(String cid, List<Integer> processCateNo) {
		List<EmpTiedProYear> result = new ArrayList<>();
		for (Integer procNo : processCateNo) {
			val data = this.getEmpTiedProYearById(cid, procNo);
			if (data.isPresent())
				result.add(data.get());
		}
		return result;
	}

	@Override
	public List<EmpTiedProYear> getByCid(String cId) {
		
		List<QpbmtEmpTiedProYear> items = this.queryProxy().query(SELECT_BY_CID, QpbmtEmpTiedProYear.class)
				.setParameter("cid", cId).getList();
		
		return mapByProcessCateNo(items);
	}

	private List<EmpTiedProYear> mapByProcessCateNo(List<QpbmtEmpTiedProYear> items) {
		
		List<EmpTiedProYear>  result = new ArrayList<EmpTiedProYear>();
		
		if (items.isEmpty()){
			return result;
		}
		
		
		items.stream().forEach(item->{
			
			Optional<EmpTiedProYear> categoryOpt =  result.stream().filter(x -> x.getProcessCateNo() == item.empTiedProYearPk.processCateNo).findFirst();
			
			if(!categoryOpt.isPresent()){
				
				List<EmploymentCode> employmentCodes = new ArrayList<EmploymentCode>();
				employmentCodes.add(new EmploymentCode(item.empTiedProYearPk.employmentCode));
				result.add(new EmpTiedProYear(item.empTiedProYearPk.cid, item.empTiedProYearPk.processCateNo,
						employmentCodes));
				
			}else{
				
				EmpTiedProYear  category =   categoryOpt.get();
				category.getEmploymentCodes().add(new EmploymentCode(item.empTiedProYearPk.employmentCode));
				
			}
		});
		
		
		return result;
	}

}
