package nts.uk.ctx.exio.app.find.exo.outcnddetail;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItemRepository;

/**
* 出力条件詳細項目
*/
@Stateless
public class OutCndDetailItemFinder
{

    @Inject
    private OutCndDetailItemRepository finder;

    public List<OutCndDetailItemDto> getAllOutCndDetailItem(){
        return finder.getAllOutCndDetailItem().stream().map(item -> OutCndDetailItemDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}