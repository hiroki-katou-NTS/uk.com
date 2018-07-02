package nts.uk.ctx.exio.app.find.exo.outitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.exio.dom.exo.outitem.StdOutItemRepository;

@Stateless
/**
* 出力項目(定型)
*/
public class StdOutItemFinder
{

    @Inject
    private StdOutItemRepository finder;

    public List<StdOutItemDto> getAllStdOutItem(){
        return finder.getAllStdOutItem().stream().map(item -> StdOutItemDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
