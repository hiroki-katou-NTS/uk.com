package nts.uk.ctx.hr.notice.dom.report;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.notice.dom.report.valueImported.HumanItemPub;

@Stateless
public class ProcessData {
	@Inject
	private HumanItemPub humanItemPub;
}
