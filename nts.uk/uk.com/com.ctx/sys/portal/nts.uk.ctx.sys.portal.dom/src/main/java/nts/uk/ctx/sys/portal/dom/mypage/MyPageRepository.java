package nts.uk.ctx.sys.portal.dom.mypage;

/**
 * The Interface MyPageRepository.
 */
public interface MyPageRepository {
	/**
	 * hoatt
	 * get my page
	 * @param employeeId
	 * @param layoutId
	 * @return
	 */
	MyPage getMyPage(String employeeId, String layoutId);
}
