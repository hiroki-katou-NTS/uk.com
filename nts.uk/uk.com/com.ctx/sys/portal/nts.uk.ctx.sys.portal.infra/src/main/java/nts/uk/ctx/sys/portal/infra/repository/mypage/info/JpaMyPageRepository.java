package nts.uk.ctx.sys.portal.infra.repository.mypage.info;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.mypage.MyPage;
import nts.uk.ctx.sys.portal.dom.mypage.MyPageRepository;
import nts.uk.ctx.sys.portal.infra.entity.mypage.CcgptMyPage;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class JpaMyPageRepository extends JpaRepository implements MyPageRepository {

	private final String SELECT_MYPAGE = "SELECT c FROM CcgptMyPage c"
			+ " WHERE c.ccgptMyPagePK.employeeId = :employeeId"
			+ " AND c.ccgptMyPagePK.layoutId = :layoutId";
	private static MyPage toDomain(CcgptMyPage entity){
		val domain = MyPage.createFromJavaType(entity.ccgptMyPagePK.employeeId, entity.ccgptMyPagePK.layoutId);
		return domain;
	}
	@Override
	public MyPage getMyPage(String employeeId, String layoutId) {
		Optional<MyPage> myPage =  this.queryProxy().query(SELECT_MYPAGE, CcgptMyPage.class)
				.setParameter("employeeId", employeeId)
				.setParameter("layoutId", layoutId)
				.getSingle(c->toDomain(c));
		if(myPage.isPresent()){
			return null;
		}else{
			return myPage.get();
		}
	}

}
