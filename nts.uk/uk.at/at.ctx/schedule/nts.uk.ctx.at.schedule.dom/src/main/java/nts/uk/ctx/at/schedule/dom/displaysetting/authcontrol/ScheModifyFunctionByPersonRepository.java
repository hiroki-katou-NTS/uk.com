package nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol;

import java.util.List;
import java.util.Optional;

public interface ScheModifyFunctionByPersonRepository {
	
	/**
	 * @param domain スケジュール修正個人別の機能
	 */
	void insert(ScheModifyFunctionByPerson domain);
	
	/**
	 * @param domain スケジュール修正個人別の機能
	 */
	void update(ScheModifyFunctionByPerson domain);
	
	/**
	 * @param functionNo 機能NO
	 * @return
	 */
	Optional<ScheModifyFunctionByPerson> get( int functionNo );
	
	/**
	 * @param functionNoList　機能NO一覧
	 * @return
	 */
	List<ScheModifyFunctionByPerson> getList( List<Integer> functionNoList);

}
