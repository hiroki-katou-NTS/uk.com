package nts.uk.ctx.at.function.ac.appreflectmanager;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.adapter.appreflectmanager.AppReflectManagerAdapter;
import nts.uk.ctx.at.function.dom.adapter.appreflectmanager.ProcessStateReflectImport;
import nts.uk.ctx.at.request.pub.aplicationreflect.AppReflectManagerFromRecordPub;
import nts.uk.ctx.at.request.pub.aplicationreflect.ProcessStateReflectExport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AppReflectManagerAc implements AppReflectManagerAdapter {

	@Inject
	private AppReflectManagerFromRecordPub appReflectManagerFromRecordPub;
	
	@Override
	public ProcessStateReflectImport reflectAppOfEmployeeTotal(String workId, String sid, DatePeriod datePeriod) {
		ProcessStateReflectExport processStateReflectExport = appReflectManagerFromRecordPub.reflectAppOfEmployeeTotal(workId, sid, datePeriod);
		return EnumAdaptor.valueOf(processStateReflectExport.value, ProcessStateReflectImport.class);
	}

}
