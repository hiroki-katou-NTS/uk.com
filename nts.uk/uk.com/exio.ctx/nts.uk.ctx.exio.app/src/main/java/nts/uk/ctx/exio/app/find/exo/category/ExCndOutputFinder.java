package nts.uk.ctx.exio.app.find.exo.category;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.exio.dom.exo.category.*;

@Stateless
/**
* 外部出力リンクテーブル
*/
public class ExCndOutputFinder
{

    @Inject
    private ExOutLinkTableRepository finder;

    public List<ExCndOutputDto> getAllExCndOutput(){
        return finder.getAllExCndOutput().stream().map(item -> ExCndOutputDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
