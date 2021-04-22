package nts.uk.ctx.at.shared.infra.repository.remainingnumber.test;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.test.LengthServiceTest;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshstLengthServiceTbl;

@Stateless
public class JpaLengthServiceRepositoryImplTest extends JpaRepository implements LengthServiceTest{
	
	private static final String FIND_BY_COMPANY_ID = "SELECT c FROM KshstLengthServiceTbl c "
			+ "WHERE c.kshstLengthServiceTblPK.companyId = :companyId "
			+ " ORDER BY c.kshstLengthServiceTblPK.grantNum ";

	/**
	* convert from KshstLengthServiceTbl entity to LengthServiceTbl domain
	* @param entity
	* @return
	*/
	private LengthServiceTbl convertToDomain(KshstLengthServiceTbl entity){
	return LengthServiceTbl.createFromJavaType(entity.kshstLengthServiceTblPK.companyId, 
												entity.kshstLengthServiceTblPK.yearHolidayCode, entity.kshstLengthServiceTblPK.grantNum, 
												entity.allowStatus, entity.standGrantDay, entity.year, entity.month);
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
		return this.queryProxy().query(FIND_BY_COMPANY_ID, KshstLengthServiceTbl.class)
								.setParameter("companyId", companyId)
								.getList(c -> convertToDomain(c));
	}
	
}

