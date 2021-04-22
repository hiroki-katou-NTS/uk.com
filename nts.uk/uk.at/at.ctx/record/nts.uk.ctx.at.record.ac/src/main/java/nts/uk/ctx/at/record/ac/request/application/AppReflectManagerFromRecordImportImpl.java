package nts.uk.ctx.at.record.ac.request.application;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.AppReflectManagerFromRecordImport;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.ProcessState;
import nts.uk.ctx.at.request.pub.aplicationreflect.AppReflectManagerFromRecordPub;
import nts.uk.ctx.at.request.pub.aplicationreflect.ProcessStateReflectExport;
@Stateless
public class AppReflectManagerFromRecordImportImpl implements AppReflectManagerFromRecordImport{
	@Inject
	private AppReflectManagerFromRecordPub appReflectPub;
	@SuppressWarnings("rawtypes")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public ProcessState applicationRellect(String workId, DatePeriod workDate,
			AsyncCommandHandlerContext asyncContext) {
		ProcessStateReflectExport outPut = appReflectPub.applicationRellect(workId, workDate, asyncContext);
		return EnumAdaptor.valueOf(outPut.value, ProcessState.class);
	}

}
