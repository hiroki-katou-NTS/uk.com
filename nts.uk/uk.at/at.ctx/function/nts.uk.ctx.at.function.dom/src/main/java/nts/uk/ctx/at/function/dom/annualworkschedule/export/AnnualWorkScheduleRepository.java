package nts.uk.ctx.at.function.dom.annualworkschedule.export;

public interface AnnualWorkScheduleRepository {
	ExportData getData(String cid, String setItemsOutputCd);
}
