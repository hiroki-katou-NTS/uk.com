package nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol;

import java.util.List;
import java.util.Optional;

public interface ScheModifyFuncCommonRepository {
	
	/**
	 * @param domain スケジュール修正共通の機能
	 */
	void insert(ScheModifyFuncCommon domain);
	
	/**
	 * @param domain スケジュール修正共通の機能
	 */
	void update(ScheModifyFuncCommon domain);
	
	/**
	 * @param functionNo 機能NO
	 * @return
	 */
	Optional<ScheModifyFuncCommon> get( int functionNo );
	
	/**
	 * @param functionNoList　機能NO一覧
	 * @return
	 */
	List<ScheModifyFuncCommon> getList( List<Integer> functionNoList);

}
