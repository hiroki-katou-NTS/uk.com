package nts.uk.ctx.exio.app.find.exo.outcnddetail;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailRepository;

/**
* 出力条件詳細(定型)
*/
@Stateless
public class OutCndDetailFinder
{

    @Inject
    private OutCndDetailRepository finder;

    public List<OutCndDetailDto> getAllOutCndDetail(){
        return finder.getAllOutCndDetail().stream().map(item -> OutCndDetailDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}