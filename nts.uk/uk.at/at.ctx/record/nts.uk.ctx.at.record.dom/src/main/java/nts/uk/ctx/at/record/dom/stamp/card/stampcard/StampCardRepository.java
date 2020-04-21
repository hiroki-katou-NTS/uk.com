package nts.uk.ctx.at.record.dom.stamp.card.stampcard;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 
 * 打刻カードRepository
 */
public interface StampCardRepository {


	List<StampCard> getListStampCard(String sid);
	
	List<String> getListStampCardByContractCode(String contractCode);
	
	List<StampCard> getLstStampCardByContractCode(String contractCode);
	
	List<StampCard> getLstStampCardByLstSid(List<String> sids);

	Optional<StampCard> getByStampCardId(String stampCardId);
	
	Map<String, List<StampCard>> getByStampCardId(String contractCd, List<String> stampCardId);
	
	Optional<StampCard> getByCardNoAndContractCode(String cardNo , String contractCd);
	
	Map<String, StampCard> getByCardNoAndContractCode(Map<String, String> cardNos , String contractCd);
	
	Optional<String> getLastCardNo(String contractCode, String startCardNoLetters, int length);

	/**
	 * 追加する(打刻カード)
	 * 
	 * [1] insert(打刻カード)
	 * 
	 * @param domain
	 */
	void add(StampCard domain);
	/**
	 * @author lanlt
	 * @param domains
	 */
	void addAll(List<StampCard> domains);

	void update(StampCard domain);
	
	/**
	 * @author lanlt
	 * @param domains
	 */
	void updateAll(List<StampCard> domains);

	void delete(String stampCardId);
	
	void deleteBySid(String sid);
	
	/**
	 * @author lanlt
	 * @param sids
	 * @param contractCd
	 * @return
	 */
	List<StampCard> getLstStampCardByLstSidAndContractCd(List<String> sids, String contractCd);

}
