package nts.uk.ctx.pr.report.ws.printdata.comlegalrecord;

import nts.uk.ctx.pr.report.app.command.printdata.comlegalrecord.*;
import nts.uk.ctx.pr.report.app.find.printdata.comlegalrecord.CompanyStatutoryWriteDto;
import nts.uk.ctx.pr.report.app.find.printdata.comlegalrecord.CompanyStatutoryWriteFinder;
import nts.uk.ctx.pr.report.app.find.printdata.comlegalrecord.CreateCompanyWriteDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;


@Path("ctx/pr/report/printdata/comlegalrecord")
@Produces("application/json")
public class CompanyStatutoryWriteWebservice {

    @Inject
    private CompanyStatutoryWriteFinder companyStatutoryWriteFinder;

    @Inject
    private CreateCompanyWriteHandler createCompanyWriteHandler;

    @Inject
    private DeleteCompanyWriteHandler deleteCompanyWriteHandler;

    @Inject
    private UpdateCompanyWriteHandler updateCompanyWriteHandler;


    @POST
    @Path("/start")
    public List<CompanyStatutoryWriteDto> startScreen() {
        // ドメインモデル「法定調書用会社」をすべて取得する
        return companyStatutoryWriteFinder.getByCid().stream().map(CompanyStatutoryWriteDto::fromDomain).collect(Collectors.toList());
    }

    @POST
    @Path("/getbycode/{code}")
    public CompanyStatutoryWriteDto getByCode(@PathParam("code") String code) {
        return CompanyStatutoryWriteDto.fromDomain(companyStatutoryWriteFinder.getByKey(code).get());
    }

    @POST
    @Path("/create")
    public CreateCompanyWriteDto createCompany(CompanyStatutoryWriteCommand data) {
        return createCompanyWriteHandler.handle(data);
    }

    @POST
    @Path("/delete")
    public List<String> deleteCompany(DeleteCompanyWriteCommand data) {
        return deleteCompanyWriteHandler.handle(data);
    }

    @POST
    @Path("/update")
    public List<String> updateCompany(CompanyStatutoryWriteCommand data) {
        return updateCompanyWriteHandler.handle(data);
    }
}