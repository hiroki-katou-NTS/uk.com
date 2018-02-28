package nts.uk.ctx.exio.app.find.exi.dataformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.exio.dom.exi.dataformat.InsTimeDatFmSetRepository;

@Stateless
/**
* 時刻型データ形式設定
*/
public class InsTimeDatFmSetFinder
{

    @Inject
    private InsTimeDatFmSetRepository finder;

    public List<InsTimeDatFmSetDto> getAllInsTimeDatFmSet(){
        return finder.getAllInsTimeDatFmSet().stream().map(item -> InsTimeDatFmSetDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
