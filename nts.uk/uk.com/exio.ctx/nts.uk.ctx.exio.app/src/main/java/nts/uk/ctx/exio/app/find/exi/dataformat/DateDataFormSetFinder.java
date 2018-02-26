package nts.uk.ctx.exio.app.find.exi.dataformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.exio.dom.exi.dataformat.DateDataFormSetRepository;

@Stateless
/**
* 日付型データ形式設定
*/
public class DateDataFormSetFinder
{

    @Inject
    private DateDataFormSetRepository finder;

    public List<DateDataFormSetDto> getAllDateDataFormSet(){
        return finder.getAllDateDataFormSet().stream().map(item -> DateDataFormSetDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
