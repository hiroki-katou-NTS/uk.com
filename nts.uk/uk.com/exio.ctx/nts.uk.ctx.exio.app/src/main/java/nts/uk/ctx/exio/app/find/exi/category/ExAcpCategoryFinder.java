package nts.uk.ctx.exio.app.find.exi.category;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.exio.dom.exi.category.ExAcpCategoryRepository;

@Stateless
/**
* 外部受入カテゴリ
*/
public class ExAcpCategoryFinder
{

    @Inject
    private ExAcpCategoryRepository finder;

    public List<ExAcpCategoryDto> getAllExAcpCategory(){
        return finder.getAllExAcpCategory().stream().map(item -> ExAcpCategoryDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
