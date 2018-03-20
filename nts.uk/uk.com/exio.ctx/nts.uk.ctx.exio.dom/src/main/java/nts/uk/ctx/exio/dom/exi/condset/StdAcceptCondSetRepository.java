package nts.uk.ctx.exio.dom.exi.condset;

import java.util.Optional;
import java.util.List;

/**
 * 受入条件設定（定型）
 */
public interface StdAcceptCondSetRepository {

	List<StdAcceptCondSet> getStdAcceptCondSetBySysType(String cid, int sysType);

	Optional<StdAcceptCondSet> getStdAcceptCondSetById(String cid, int sysType, String conditionSetCd);

	void add(StdAcceptCondSet domain);

	void update(StdAcceptCondSet domain);
	
	void updateFromD(StdAcceptCondSet domain);

	void remove(String cid, int sysType, String conditionSetCd);

	boolean isSettingCodeExist(String cid, int sysType, String conditionSetCd);

}
