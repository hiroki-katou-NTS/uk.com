package nts.uk.ctx.hr.develop.infra.repository.careermgmt.careertype;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.careermgmt.careertype.CareerType;
import nts.uk.ctx.hr.develop.dom.careermgmt.careertype.CareerTypeRepository;
import nts.uk.ctx.hr.develop.infra.entity.careermgmt.careertype.JhcmtCareerType;

@Stateless
public class CareerTypeRepositoryImpl extends JpaRepository implements CareerTypeRepository {

	private static final String SELECT_BY_CID_REFERDATE = "SELECT c FROM JhcmtCareerType c WHERE c.cId = :cId AND c.startDate <=:referDate  AND c.endDate >=:referDate AND c.disableFlg = 0 ORDER BY c.commonFlg DESC, c.careerTypeCd ASC";
	
	@Override
	public List<CareerType> getLisHisId(String cId, GeneralDate referDate) {
		return this.queryProxy().query(SELECT_BY_CID_REFERDATE, JhcmtCareerType.class)
				.setParameter("cId", cId)
				.setParameter("referDate", referDate)
				.getList(c->c.toDomain());
	}

}
