package nts.uk.ctx.sys.assist.infra.repo.categoryFieldMaster;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.categoryFieldMaster.CategoryFieldMt;
import nts.uk.ctx.sys.assist.dom.categoryFieldMaster.CategoryFieldMtRepository;

@Stateless
public class JpaCategoryFieldMtRepository extends JpaRepository implements CategoryFieldMtRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspmtCategoryFieldMt f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE ";

    

    
    @Override
    public void remove(){
       // this.commandProxy().remove(SspmtCategoryFieldMt.class, new SspmtCategoryFieldMtPk()); 
    }




	@Override
	public List<CategoryFieldMt> getAllCategoryFieldMt() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public Optional<CategoryFieldMt> getCategoryFieldMtById() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public void add(CategoryFieldMt domain) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void update(CategoryFieldMt domain) {
		// TODO Auto-generated method stub
		
	}
}
