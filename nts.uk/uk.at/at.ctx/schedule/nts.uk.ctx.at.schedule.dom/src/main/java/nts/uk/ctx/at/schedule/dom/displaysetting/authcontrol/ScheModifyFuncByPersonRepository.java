package nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol;

import java.util.List;
import java.util.Optional;

public interface ScheModifyFuncByPersonRepository {
	
	/**
	 * @param domain スケジュール修正個人別の機能
	 */
	void insert(ScheModifyFuncByPerson domain);
	
	/**
	 * @param domain スケジュール修正個人別の機能
	 */
	void update(ScheModifyFuncByPerson domain);
	
	/**
	 * @param functionNo 機能NO
	 * @return
	 */
	Optional<ScheModifyFuncByPerson> get( int functionNo );
	
	/**
	 * @param functionNoList　機能NO一覧
	 * @return
	 */
	List<ScheModifyFuncByPerson> getList( List<Integer> functionNoList);

}
