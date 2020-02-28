package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SpecPrintYmSetRepository;

@Stateless
/**
* 明細書印字年月設定
*/
public class SpecPrintYmSetFinder
{

    @Inject
    private SpecPrintYmSetRepository finder;

    public List<SpecPrintYmSetDto> getAllSpecPrintYmSet(){
        return finder.getAllSpecPrintYmSet().stream().map(item -> SpecPrintYmSetDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
