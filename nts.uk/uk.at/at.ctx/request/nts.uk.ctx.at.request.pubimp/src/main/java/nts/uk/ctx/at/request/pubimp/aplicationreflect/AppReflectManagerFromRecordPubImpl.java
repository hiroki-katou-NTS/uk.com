package nts.uk.ctx.at.request.pubimp.aplicationreflect;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.uk.ctx.at.request.dom.applicationreflect.service.AppReflectManagerFromRecord;
import nts.uk.ctx.at.request.dom.applicationreflect.service.ProcessStateReflect;
import nts.uk.ctx.at.request.pub.aplicationreflect.AppReflectManagerFromRecordPub;
import nts.uk.ctx.at.request.pub.aplicationreflect.ProcessStateReflectExport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class AppReflectManagerFromRecordPubImpl implements AppReflectManagerFromRecordPub{
	@Inject
	private AppReflectManagerFromRecord appReflectService;
	@Override
	public ProcessStateReflectExport applicationRellect(String workId, DatePeriod workDate,
			AsyncCommandHandlerContext asyncContext) {
		ProcessStateReflect outPut = appReflectService.applicationRellect(workId, workDate, asyncContext);
		return EnumAdaptor.valueOf(outPut.value, ProcessStateReflectExport.class);
	}
	@Override
	public ProcessStateReflectExport reflectAppOfEmployeeTotal(String workId, String sid, DatePeriod datePeriod) {
		ProcessStateReflect outPut = appReflectService.reflectAppOfEmployeeTotal(workId, sid, datePeriod);
		return EnumAdaptor.valueOf(outPut.value, ProcessStateReflectExport.class);
	}

}
