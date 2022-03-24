package nts.uk.ctx.at.shared.infra.repository.remainingnumber.test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.test.LengthServiceTest;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthOfService;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayCode;
import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshstLengthServiceTbl;

@Stateless
public class JpaLengthServiceRepositoryImplTest extends JpaRepository implements LengthServiceTest{

	/**
	* convert from KshstLengthServiceTbl entity to LengthServiceTbl domain
	* @param entity
	* @return
	*/

	private LengthOfService convertToDomain(KshstLengthServiceTbl entity){
	return LengthOfService.createFromJavaType(entity.kshstLengthServiceTblPK.grantNum, entity.allowStatus,
			entity.standGrantDay, entity.year, entity.month);
	}
	
//	private KshstLengthServiceTbl toEntity(LengthServiceTbl domain){
//	val entity = new KshstLengthServiceTbl();
//	entity.kshstLengthServiceTblPK = new KshstLengthServiceTblPK(domain.getCompanyId(), domain.getYearHolidayCode().v(), domain.getGrantNum().v());
//	entity.allowStatus = domain.getAllowStatus().value;
//	entity.month = domain.getMonth() != null ? domain.getMonth().v() : 0;
//	entity.year = domain.getYear()!= null ? domain.getYear().v() : 0;
//	entity.standGrantDay = domain.getStandGrantDay().value;
//	return entity;
//	}
	
	/**
	* find by companyId 
	*/
	//@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<LengthServiceTbl> findByCompanyId(String companyId) {
		String query = "SELECT c FROM KshstLengthServiceTbl c "
				+ "WHERE c.kshstLengthServiceTblPK.companyId = :companyId "
				+ " ORDER BY c.kshstLengthServiceTblPK.grantNum ";

		Map<String, List<KshstLengthServiceTbl>> entitys = this.queryProxy().query(query, KshstLengthServiceTbl.class)
				.setParameter("companyId", companyId).getList(c -> c)
				.stream().collect(Collectors.groupingBy(i -> i.kshstLengthServiceTblPK.yearHolidayCode));

		return entitys.entrySet().stream().map(c -> toDomain(c.getValue()).get()).collect(Collectors.toList());
	}

	private Optional<LengthServiceTbl> toDomain(List<KshstLengthServiceTbl> entitys) {
		if (entitys.isEmpty())
			return Optional.empty();

		List<LengthOfService> lengthOfServices = entitys.stream().map(c -> convertToDomain(c))
				.collect((Collectors.toList()));

		String companyId = entitys.get(0).kshstLengthServiceTblPK.companyId;
		String code = entitys.get(0).kshstLengthServiceTblPK.yearHolidayCode;

		return Optional.of(new LengthServiceTbl(companyId, new YearHolidayCode(code), lengthOfServices));
	}

}

