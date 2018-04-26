package nts.uk.ctx.sys.assist.infra.repo.category;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;


import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.category.CategoryRepository;
import nts.uk.ctx.sys.assist.infra.enity.category.SspmtCategory;
import nts.uk.ctx.sys.assist.infra.enity.category.SspmtCategoryPk;

@Stateless
public class JpaCategoryRepository extends JpaRepository implements CategoryRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspmtCategory f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";

   

    @Override
    public void remove(){
        this.commandProxy().remove(SspmtCategory.class, new SspmtCategoryPk()); 
    }



	@Override
	public List<Category> getAllCategory() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Optional<Category> getCategoryById() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void add(Category domain) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void update(Category domain) {
		// TODO Auto-generated method stub
		
	}
}
