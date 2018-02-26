package nts.uk.ctx.exio.app.find.exi.dataformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.exio.dom.exi.dataformat.NumDataFormatSetRepository;

@Stateless
/**
* 数値型データ形式設定
*/
public class NumDataFormatSetFinder
{

    @Inject
    private NumDataFormatSetRepository finder;

    public List<NumDataFormatSetDto> getAllNumDataFormatSet(){
        return finder.getAllNumDataFormatSet().stream().map(item -> NumDataFormatSetDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
