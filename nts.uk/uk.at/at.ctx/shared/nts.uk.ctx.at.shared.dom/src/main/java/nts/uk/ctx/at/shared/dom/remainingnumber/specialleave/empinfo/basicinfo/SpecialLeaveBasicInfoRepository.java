package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UseAtr;

public interface SpecialLeaveBasicInfoRepository {
	List<SpecialLeaveBasicInfo> listSPLeav(String sid);
	
	Optional<SpecialLeaveBasicInfo> getBySidLeaveCd(String sid, int spLeaveCD);
	
	void add(SpecialLeaveBasicInfo domain);
	
	void addAll(List<SpecialLeaveBasicInfo> domains);
	
	void update(SpecialLeaveBasicInfo domain);
	
	void updateAll(List<SpecialLeaveBasicInfo> domains);
	
	void delete(String sID, int spLeavCD);
	/**
	 * 特別休暇基本情報
	 * @param sid
	 * @param spLeaveCD
	 * @param use 特別休暇基本情報
	 * @return
	 */
	Optional<SpecialLeaveBasicInfo> getBySidLeaveCdUser(String sid, int spLeaveCD, UseAtr use);
	
	/**
	 * 特別休暇基本情報
	 * getBySidsLeaveCd
	 * @param cid
	 * @param sids
	 * @param spLeaveCD
	 * @return
	 */
	List<SpecialLeaveBasicInfo> getAllBySidsLeaveCd(String cid, List<String> sids, int spLeaveCD);
	
	/**
	 * for cps013
	 * 特別休暇基本情報
	 * getBySidsLeaveCd
	 * @param cid
	 * @param sids
	 * @param spLeaveCD
	 * @return
	 */
	List<Object[]> getAllBySidsAndLeaveCd(String cid, List<String> sids, int spLeaveCD);
}
