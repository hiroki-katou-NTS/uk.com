package nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol;

import java.util.List;
import java.util.Optional;

public interface ScheModifyFunctionByWorkplaceRepository {
	
	/**
	 * @param domain スケジュール修正職場別の機能
	 */
	void insert(ScheModifyFunctionByWorkplace domain);
	
	/**
	 * @param domain スケジュール修正職場別の機能
	 */
	void update(ScheModifyFunctionByWorkplace domain);
	
	/**
	 * @param functionNo 機能NO
	 * @return
	 */
	Optional<ScheModifyFunctionByWorkplace> get( int functionNo );
	
	/**
	 * @param functionNoList　機能NO一覧
	 * @return
	 */
	List<ScheModifyFunctionByWorkplace> getList( List<Integer> functionNoList);

}
