package nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth;

import java.util.List;

import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.systemday.SystemDay;


public interface SystemDayRepository {

	List<SystemDay> findAll(String companyCode, String processingNo);

}
