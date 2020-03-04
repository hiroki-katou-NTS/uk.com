package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.DetailPrintingMonRepository;

@Stateless
/**
* 明細印字月
*/
public class DetailPrintingMonFinder
{

    @Inject
    private DetailPrintingMonRepository finder;

    public List<DetailPrintingMonDto> getAllDetailPrintingMon(){
        return finder.getAllDetailPrintingMon().stream().map(item -> DetailPrintingMonDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
