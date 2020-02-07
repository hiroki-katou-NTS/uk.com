package nts.uk.ctx.hr.shared.infra.repository.reflect;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.PreReflectAnyData;
import nts.uk.ctx.hr.shared.dom.notice.report.registration.person.PreReflectAnyDataRepository;
import nts.uk.ctx.hr.shared.infra.entity.report.registration.PpedtPreRefectAnyData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaPreReflectAnyDataRepository extends JpaRepository implements PreReflectAnyDataRepository{

	@Override
	public void insert(PreReflectAnyData domain) {
		this.commandProxy().insert(toEntity(domain));
	}
	
	private PpedtPreRefectAnyData toEntity(PreReflectAnyData  domain) {
		PpedtPreRefectAnyData entity = new PpedtPreRefectAnyData( domain.getHistId(),
				AppContexts.user().contractCode(),
				domain.getCompanyId(),
				domain.getCompanyCd(),
				domain.getWorkId(),
				null,
				domain.getRequestFlg(),
				domain.getRegisDate(),
				null,
				1,
				null,
				null,
				domain.getPid(),
				domain.getSid(),
				domain.getScd(),
				domain.getPersonName(),
				domain.getReportId(),
				domain.getReportCode(),
				domain.getReportName(),
				domain.getInputDate());
		return entity;
	}
}
