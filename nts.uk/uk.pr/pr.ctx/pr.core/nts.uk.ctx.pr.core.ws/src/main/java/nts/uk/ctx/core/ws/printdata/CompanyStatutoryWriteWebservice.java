package nts.uk.ctx.core.ws.printdata;



import nts.uk.ctx.core.app.command.printdata.companystatuwrite.command.*;
import nts.uk.ctx.core.app.find.printdata.companystatuwrite.CompanyStatutoryWriteDto;
import nts.uk.ctx.core.app.find.printdata.companystatuwrite.CompanyStatutoryWriteFinder;
import nts.uk.ctx.core.app.find.printdata.companystatuwrite.CreateCompanyWriteDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;


@Path("pr/core/printdata")
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

        // ドメインモデル「現在処理年月」を取得する


        // ドメインモデル「給与支払日設定」を取得する - to do

        // ドメインモデル「法定調書用会社」をすべて取得する
        List<CompanyStatutoryWriteDto> response = companyStatutoryWriteFinder.getByCid().stream().map(x -> CompanyStatutoryWriteDto.fromDomain(x)).collect(Collectors.toList());
        return response;
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
