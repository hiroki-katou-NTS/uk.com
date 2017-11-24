package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.request.dom.application.workchange.IAppWorkChangeRepository;

@Stateless
/**
* 勤務変更申請
*/
public class AppWorkChangeFinder
{

    @Inject
    private IAppWorkChangeRepository finder;

    public List<AppWorkChangeDto> getAllAppWorkChange(){
       return finder.getAllAppWorkChange().stream().map(item -> AppWorkChangeDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
