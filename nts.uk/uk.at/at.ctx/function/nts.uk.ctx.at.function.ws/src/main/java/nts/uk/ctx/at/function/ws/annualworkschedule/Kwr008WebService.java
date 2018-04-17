package nts.uk.ctx.at.function.ws.annualworkschedule;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.dom.annualworkschedule.PageBreakIndicator;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

/**
 * Web Service for KWR008_年間勤務表
 */

@Path("at/function/annualworkschedule")
@Produces("application/json")
public class Kwr008WebService extends WebService {
	
	@Inject
	private I18NResourcesForUK i18n;
	
	/**
	 * KWR008 A
	 * 改頁選択 - Page break selection
	 * 
	 * */
	@POST
	@Path("get/enum/annualworkschedule/pagebreak")
	public List<EnumConstant> getEnumPageBreakSelection(){
		return EnumAdaptor.convertToValueNameList(PageBreakIndicator.class, i18n);
	}
}
