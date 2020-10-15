package nts.uk.screen.at.ws.kaf.kaf020.a;

import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemAppSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemAppSetFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;

@Path("screen/at/kaf020/a")
@Produces("application/json")
public class Kaf020AWebService {

    @Inject
    private OptionalItemAppSetFinder finder;

    @POST
    @Path("/get")
    public List<OptionalItemAppSetDto> get() {
        int UsageClassification = 1;
        return finder.findAllByCompany().stream().filter(optionalItemAppSetDto -> optionalItemAppSetDto.getUseAtr() == UsageClassification).collect(Collectors.toList());
    }

}
