package nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol;

import java.util.List;
import java.util.Optional;

public interface ScheModifyFuncByWorkplaceRepository {
	
	/**
	 * @param domain スケジュール修正職場別の機能
	 */
	void insert(ScheModifyFuncByWorkplace domain);
	
	/**
	 * @param domain スケジュール修正職場別の機能
	 */
	void update(ScheModifyFuncByWorkplace domain);
	
	/**
	 * @param functionNo 機能NO
	 * @return
	 */
	Optional<ScheModifyFuncByWorkplace> get( int functionNo );
	
	/**
	 * @param functionNoList　機能NO一覧
	 * @return
	 */
	List<ScheModifyFuncByWorkplace> getList( List<Integer> functionNoList);

}
