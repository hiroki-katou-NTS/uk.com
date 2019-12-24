package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.PerProcessClsSetRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 個人処理区分設定: Finder
*/
public class PerProcessClsSetFinder
{

    @Inject
    private PerProcessClsSetRepository finder;

    public List<PerProcessClsSetDto> getAllPerProcessClsSet(){
        return finder.getAllPerProcessClsSet().stream().map(item -> PerProcessClsSetDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
