package nts.uk.ctx.at.aggregation.infra.repository.schedulecounter.criterion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmployment;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmploymentRepository;
import nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion.KagmtCriterionMoneyEmp;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaCriterionAmountForEmploymentRepository extends JpaRepository
		implements CriterionAmountForEmploymentRepository {

	
	public  static final String SELECT;
	
	public static final String FIND_BY_CID_AND_EMPLOYMENT_CODE;
	
	public static final String FIND_BY_CID;
	
	static {
		
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append(" FROM KagmtCriterionMoneyEmp a ");
        SELECT = builderString.toString();
        
        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.pk.companyId = :companyId and a.pk.employmentCd = : employmentCd");
        FIND_BY_CID_AND_EMPLOYMENT_CODE = builderString.toString();
        
        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.pk.companyId = :companyId");
        FIND_BY_CID = builderString.toString();
	}
	@Override
	public void insert(String cid, CriterionAmountForEmployment criterion) {
		List<KagmtCriterionMoneyEmp> entities = KagmtCriterionMoneyEmp.toEntity(criterion);
		entities.forEach(domain -> {
			this.commandProxy().insert(domain);
		});
	}

	@Override
	public void update(String cid, CriterionAmountForEmployment criterion) {
		List<KagmtCriterionMoneyEmp> entities = this.queryProxy()
				.query(FIND_BY_CID_AND_EMPLOYMENT_CODE, KagmtCriterionMoneyEmp.class)
				.getList();
		this.commandProxy().removeAll(entities);
		this.getEntityManager().flush();
		KagmtCriterionMoneyEmp.toEntity(criterion)
			.forEach(entity -> {
				this.commandProxy().insert(entity);
				this.getEntityManager().flush();
			});
	}

	@Override
	public void delete(String cid, EmploymentCode employmentCd) {
		
		List<KagmtCriterionMoneyEmp> entities = this.queryProxy()
				.query(FIND_BY_CID_AND_EMPLOYMENT_CODE, KagmtCriterionMoneyEmp.class)
				.getList();
		this.commandProxy().removeAll(entities);
		this.getEntityManager().flush();

	}

	@Override
	public boolean exist(String cid, EmploymentCode employmentCd) {
		
		List<KagmtCriterionMoneyEmp> entities = this.queryProxy()
				.query(FIND_BY_CID_AND_EMPLOYMENT_CODE, KagmtCriterionMoneyEmp.class)
				.getList();
		return !CollectionUtil.isEmpty(entities);
	}

	@Override
	public Optional<CriterionAmountForEmployment> get(String cid, EmploymentCode employmentCd) {
		
		List<KagmtCriterionMoneyEmp> entities = this.queryProxy()
				.query(FIND_BY_CID_AND_EMPLOYMENT_CODE, KagmtCriterionMoneyEmp.class)
				.getList();
		
		return Optional.ofNullable(KagmtCriterionMoneyEmp.toDomain(entities));
	}

	@Override
	public List<CriterionAmountForEmployment> getAll(String cid) {
		
		List<CriterionAmountForEmployment> result = new ArrayList<>();
		
		List<KagmtCriterionMoneyEmp> entities = this.queryProxy()
				.query(FIND_BY_CID, KagmtCriterionMoneyEmp.class)
				.getList();
		entities.stream()
			.collect(Collectors.groupingBy(x -> x.pk.companyId, Collectors.groupingBy(x -> x.pk.employmentCd)))
			.entrySet()
			.stream()
			.map(x -> x.getValue())
			.collect(Collectors.toList())
			.stream()
			.forEach(x -> {
				x.entrySet().stream().forEach(y -> {
					result.add(KagmtCriterionMoneyEmp.toDomain(y.getValue()));					
				});
			});
	

		return result;
	}

}
