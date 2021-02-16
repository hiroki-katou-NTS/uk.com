package nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol;

import java.util.List;
import java.util.Optional;

public interface ScheModifyFunctionCommonRepository {
	
	/**
	 * @param domain スケジュール修正共通の機能
	 */
	void insert(ScheModifyFunctionCommon domain);
	
	/**
	 * @param domain スケジュール修正共通の機能
	 */
	void update(ScheModifyFunctionCommon domain);
	
	/**
	 * @param functionNo 機能NO
	 * @return
	 */
	Optional<ScheModifyFunctionCommon> get( int functionNo );
	
	/**
	 * @param functionNoList　機能NO一覧
	 * @return
	 */
	List<ScheModifyFunctionCommon> getList( List<Integer> functionNoList);

}
