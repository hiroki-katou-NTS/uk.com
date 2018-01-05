/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.ws.outsideot;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.company.dom.company.AbolitionAtr;
import nts.uk.ctx.bs.company.dom.company.AddInfor;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyCode;
import nts.uk.ctx.bs.company.dom.company.MonthStr;
import nts.uk.ctx.bs.company.dom.company.Name;
import nts.uk.ctx.bs.company.dom.company.primitive.ABName;
import nts.uk.ctx.bs.company.dom.company.primitive.ContractCd;
import nts.uk.ctx.bs.company.dom.company.primitive.KNName;
import nts.uk.ctx.bs.company.dom.company.primitive.RepJob;
import nts.uk.ctx.bs.company.dom.company.primitive.RepName;
import nts.uk.ctx.bs.company.dom.company.primitive.TaxNo;
import nts.uk.file.at.app.outsideot.OutsideOTSettingExportService;
import nts.uk.file.at.app.outsideot.OutsideOTSettingQuery;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutsideOTSettingExportWs.
 */
@Path("at/shared/outsideot/export")
@Produces(MediaType.APPLICATION_JSON)
public class OutsideOTSettingExportWs extends WebService{

	/** The service. */
	@Inject
	private OutsideOTSettingExportService service;
	
	/**
	 * Export excel.
	 *
	 * @param query the query
	 * @return the export service result
	 */
    @POST
    @Path("excel")
    public ExportServiceResult exportExcel(OutsideOTSettingQuery query) {
        return this.service.start(query);
    }
    
    @POST
    @Path("getCid")
    public Company getCid() {
    	Company company = new Company(new CompanyCode(AppContexts.user().companyId()), new Name(""), MonthStr.EIGHT, AbolitionAtr.ABOLITION,
    			new RepName(""), new RepJob(""), new KNName(""), new ABName(""), new ContractCd(""), new TaxNo(""),
    			new AddInfor());
    	
        return company;
    }
}
