package nts.uk.ctx.exio.app.find.exi.condset;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.exio.dom.exi.condset.AcScreenCondSetRepository;

@Stateless
/**
* 受入選別条件設定
*/
public class AcScreenCondSetFinder
{

    @Inject
    private AcScreenCondSetRepository finder;

    public List<AcScreenCondSetDto> getAllAcScreenCondSet(){
        return finder.getAllAcScreenCondSet().stream().map(item -> AcScreenCondSetDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
