package nts.uk.ctx.pr.core.app.find.salary;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.salary.ValPayDateSetRepository;

@Stateless
/**
* 支払日の設定の規定値
*/
public class ValPayDateSetFinder
{

    @Inject
    private ValPayDateSetRepository finder;

    public List<ValPayDateSetDto> getAllValPayDateSet(){
        return finder.getAllValPayDateSet().stream().map(item -> ValPayDateSetDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
