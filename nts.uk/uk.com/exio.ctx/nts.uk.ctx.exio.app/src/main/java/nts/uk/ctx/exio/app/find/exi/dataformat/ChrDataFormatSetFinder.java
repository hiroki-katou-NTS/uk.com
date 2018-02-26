package nts.uk.ctx.exio.app.find.exi.dataformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.exio.dom.exi.dataformat.ChrDataFormatSetRepository;

@Stateless
/**
* 文字型データ形式設定
*/
public class ChrDataFormatSetFinder
{

    @Inject
    private ChrDataFormatSetRepository finder;

    public List<ChrDataFormatSetDto> getAllChrDataFormatSet(){
        return finder.getAllChrDataFormatSet().stream().map(item -> ChrDataFormatSetDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
