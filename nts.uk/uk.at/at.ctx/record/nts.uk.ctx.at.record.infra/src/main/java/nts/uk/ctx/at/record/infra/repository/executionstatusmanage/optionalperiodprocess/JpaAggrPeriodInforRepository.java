package nts.uk.ctx.at.record.infra.repository.executionstatusmanage.optionalperiodprocess;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInfor;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInforRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.periodinfor.ErrorMess;
import nts.uk.ctx.at.record.infra.entity.executionstatusmanage.optionalperiodprocess.KrcdtAnpPeriodErr;
import nts.uk.ctx.at.record.infra.entity.executionstatusmanage.optionalperiodprocess.KrcdtAnpPeriodErrPK;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaAggrPeriodInforRepository extends JpaRepository implements AggrPeriodInforRepository {

	@Override
	public List<AggrPeriodInfor> findAll(String anyPeriodAggrLogId) {
		String sql = "SELECT a FROM KrcdtAnpPeriodErr a WHERE a.krcdtAnpPeriodErrPK.periodArrgLogId = :periodArrgLogId";
		return this.queryProxy().query(sql, KrcdtAnpPeriodErr.class)
				.setParameter("periodArrgLogId", anyPeriodAggrLogId).getList(c -> convertEntityToDomain(c));
	}

	private AggrPeriodInfor convertEntityToDomain(KrcdtAnpPeriodErr entity) {
		AggrPeriodInfor domain = new AggrPeriodInfor(entity.krcdtAnpPeriodErrPK.memberId,
				entity.krcdtAnpPeriodErrPK.periodArrgLogId, entity.krcdtAnpPeriodErrPK.resourceId,
				entity.processDay, new ErrorMess(entity.errorMess));
		return domain;
	}
	
	@Override
	public void addPeriodInfor(AggrPeriodInfor periodInfor) {
		this.commandProxy().insert(convertToDbTypeApi(periodInfor));

	}
	
	private KrcdtAnpPeriodErr convertToDbTypeApi(AggrPeriodInfor periodInfor) {
		KrcdtAnpPeriodErr entity = new KrcdtAnpPeriodErr();
		entity.krcdtAnpPeriodErrPK = new KrcdtAnpPeriodErrPK(periodInfor.getMemberId(), periodInfor.getPeriodArrgLogId(), periodInfor.getResourceId());
		entity.processDay = periodInfor.getProcessDay();
		entity.errorMess = periodInfor.getErrorMess().v();
		return entity;
	}

}
