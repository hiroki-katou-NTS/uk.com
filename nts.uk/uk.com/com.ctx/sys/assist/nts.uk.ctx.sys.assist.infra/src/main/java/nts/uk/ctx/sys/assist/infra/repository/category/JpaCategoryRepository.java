package nts.uk.ctx.sys.assist.infra.repository.category;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.category.Category;
import nts.uk.ctx.sys.assist.dom.category.CategoryRepository;
import nts.uk.ctx.sys.assist.infra.entity.category.SspmtCategory;

@Stateless
public class JpaCategoryRepository extends JpaRepository implements CategoryRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspmtCategory f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.categoryPk.categoryId =:categoryId ";
    private static final String SELECT_BY_ATTENDANCE_SYSTEM = SELECT_ALL_QUERY_STRING + " WHERE f.attendanceSystem =:systemType";
    private static final String SELECT_BY_PAYMENT_AVAIABILITY = SELECT_ALL_QUERY_STRING + " WHERE f.paymentAvailability =:systemType";
    private static final String SELECT_BY_POSSIBILITY_SYSTEM = SELECT_ALL_QUERY_STRING + " WHERE f.possibilitySystem =:systemType";
    private static final String SELECT_BY_SCHELPER_SYSTEM   = SELECT_ALL_QUERY_STRING + " WHERE f.schelperSystem =:systemType";

    @Override
    public List<Category> getAllCategory(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspmtCategory.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<Category> getCategoryById(String categoryId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, SspmtCategory.class)
        .setParameter("categoryId", categoryId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(Category domain){
        this.commandProxy().insert(SspmtCategory.toEntity(domain));
    }

    @Override
    public void update(Category domain){
        this.commandProxy().update(SspmtCategory.toEntity(domain));
    }

    @Override
    public void remove(String categoryId){
        this.commandProxy().remove(SspmtCategory.class, categoryId); 
    }

	@Override
	public Optional<Category> getCategoryById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Category> findByAttendanceSystem(int systemType) {
		return this.queryProxy().query(SELECT_BY_ATTENDANCE_SYSTEM, SspmtCategory.class)
		        .setParameter("systemType", systemType)
		        .getList(c->c.toDomain());
	}

	@Override
	public List<Category> findByPaymentAvailability(int systemType) {
		return this.queryProxy().query(SELECT_BY_PAYMENT_AVAIABILITY, SspmtCategory.class)
		        .setParameter("systemType", systemType)
		        .getList(c->c.toDomain());
	}

	@Override
	public List<Category> findByPossibilitySystem(int systemType) {
		return this.queryProxy().query(SELECT_BY_POSSIBILITY_SYSTEM, SspmtCategory.class)
		        .setParameter("systemType", systemType)
		        .getList(c->c.toDomain());
	}

	@Override
	public List<Category> findBySchelperSystem(int systemType) {
		return this.queryProxy().query(SELECT_BY_SCHELPER_SYSTEM, SspmtCategory.class)
		        .setParameter("systemType", systemType)
		        .getList(c->c.toDomain());
	}

	

	
}
