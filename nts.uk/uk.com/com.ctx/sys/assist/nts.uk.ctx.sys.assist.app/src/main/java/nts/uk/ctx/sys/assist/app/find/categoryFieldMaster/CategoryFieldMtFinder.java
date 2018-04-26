package nts.uk.ctx.sys.assist.app.find.categoryFieldMaster;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.categoryFieldMaster.CategoryFieldMtRepository;
@Stateless
/**
* カテゴリ項目マスタ
*/
public class CategoryFieldMtFinder
{

    @Inject
    private CategoryFieldMtRepository finder;

    public List<CategoryFieldMtDto> getAllCategoryFieldMt(){
        return finder.getAllCategoryFieldMt().stream().map(item -> CategoryFieldMtDto.fromDomain(item))
                .collect(Collectors.toList());
    }

}
