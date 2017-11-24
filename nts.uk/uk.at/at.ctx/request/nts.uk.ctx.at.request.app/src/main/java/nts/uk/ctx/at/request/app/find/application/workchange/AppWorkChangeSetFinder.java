package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.request.dom.setting.request.application.workchange.IAppWorkChangeSetRepository;

@Stateless
/**
* 勤務変更申請設定
*/
public class AppWorkChangeSetFinder
{

    @Inject
    private IAppWorkChangeSetRepository finder;

     public List<AppWorkChangeSetDto> getAllAppWorkChangeSet(){
        return finder.getAllAppWorkChangeSet().stream().map(item -> AppWorkChangeSetDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
