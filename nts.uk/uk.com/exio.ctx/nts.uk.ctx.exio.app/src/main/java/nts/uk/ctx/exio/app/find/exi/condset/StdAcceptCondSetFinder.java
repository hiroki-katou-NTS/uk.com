package nts.uk.ctx.exio.app.find.exi.condset;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.exio.dom.exi.condset.StdAcceptCondSetRepository;

@Stateless
/**
* 受入条件設定（定型）
*/
public class StdAcceptCondSetFinder
{

    @Inject
    private StdAcceptCondSetRepository finder;

    public List<StdAcceptCondSetDto> getAllStdAcceptCondSet(){
        return finder.getAllStdAcceptCondSet().stream().map(item -> StdAcceptCondSetDto.fromDomain(item))
                .collect(Collectors.toList());
    }

    public List<StdAcceptCondSetDto> getStdAcceptCondSetBySystemType(int systemType){
        return finder.getStdAcceptCondSetBySysType(systemType).stream().map(item -> StdAcceptCondSetDto.fromDomain(item))
                .collect(Collectors.toList());
    }
}
