package nts.uk.ctx.hr.develop.infra.repository.careermgmt.careerclass;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerclass.CareerClass;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerclass.CareerClassRepository;
import nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerclass.JhcmtCareerClass;

@Stateless
public class CareerClassRepositoryImpl extends JpaRepository implements CareerClassRepository{

	private static final String SELECT_BY_CID_REFERDATE = "SELECT c FROM JhcmtCareerClass c WHERE c.cId = :cId AND c.startDate <=:referDate  AND c.endDate >=:referDate AND c.disableFlg = 0 ORDER BY c.careerClassCd ASC";
	
	@Override
	public List<CareerClass> getCareerClassList(String cId, GeneralDate referDate) {
		return this.queryProxy().query(SELECT_BY_CID_REFERDATE, JhcmtCareerClass.class)
				.setParameter("cId", cId)
				.setParameter("referDate", referDate)
				.getList(c->c.toDomain());
	}

}
