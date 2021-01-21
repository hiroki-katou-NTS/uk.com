package nts.uk.file.at.infra.export.attendanceitemprepare;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemAuthority;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.file.at.app.export.attendanceitemprepare.AttItemNameByAuth;
import nts.uk.file.at.app.export.attendanceitemprepare.AttItemNameByAuthId;

@Stateless
public class JpaAttItemNameByAuth extends JpaRepository implements AttItemNameByAuth {

	private static final String GET_ALL_BY_COMPANY = "SELECT c.AUTHORITY_DAILY_ID,c.ITEM_DAILY_ID,c.USE_ATR,c.CHANGED_BY_OTHERS,c.CHANGED_BY_YOU FROM KSHMT_DAY_ITEM_DISP_CTR c WHERE c.CID =?companyId";
	private static final String GET_ALL_MONTHLY_BY_COMPANY = "SELECT c.AUTHORITY_MON_ID,c.ITEM_MONTHLY_ID,c.USE_ATR,c.CHANGED_BY_OTHERS,c.CHANGED_BY_YOU from KSHMT_MON_ITEM_DISP_CTR c WHERE c.CID =?companyId";

	@Override
	public Map<String, List<AttItemName>> getAllByComp(String companyId) {
		List<?> data = this.getEntityManager().createNativeQuery(GET_ALL_BY_COMPANY)
				.setParameter("companyId", companyId).getResultList();
		Map<String, List<AttItemNameByAuthId>> mapListAttItemName = new HashMap<>();
		List<AttItemNameByAuthId> list = new ArrayList<>();
		data.stream().forEach(x -> {
			putRowToResult(list, (Object[])x);
		});
		
		Map<String, List<AttItemName>> map = new HashMap<>();
		mapListAttItemName = list.stream().collect(Collectors.groupingBy(AttItemNameByAuthId::getAuthId));
		for (Map.Entry<String, List<AttItemNameByAuthId>> entry : mapListAttItemName.entrySet()) {
			List<AttItemNameByAuthId> listAttNameByAu = entry.getValue();
			List<AttItemName> listAttName = new ArrayList<>();
			if(!CollectionUtil.isEmpty(listAttNameByAu)){
				for (AttItemNameByAuthId attItemNameByAuthId : listAttNameByAu) {
					AttItemName attItemName = new AttItemName();
					attItemName.setAttendanceItemId(attItemNameByAuthId.getAttendanceItemId());
					AttItemAuthority auth = new AttItemAuthority();
					auth.setToUse(attItemNameByAuthId.isToUse());
					auth.setYouCanChangeIt(attItemNameByAuthId.isYouCanChangeIt());
					auth.setCanBeChangedByOthers(attItemNameByAuthId.isCanBeChangedByOthers());
					attItemName.setAuthority(auth);
					listAttName.add(attItemName);
				}
			}
			map.put(entry.getKey(), listAttName);
		}
		return map;
		
	}
	@Override
	public Map<String, List<AttItemName>> getAllMonthlyByComp(String companyId) {
		List<?> data = this.getEntityManager().createNativeQuery(GET_ALL_MONTHLY_BY_COMPANY)
				.setParameter("companyId", companyId).getResultList();
		Map<String, List<AttItemNameByAuthId>> mapListAttItemName = new HashMap<>();
		List<AttItemNameByAuthId> list = new ArrayList<>();
		data.stream().forEach(x -> {
			putRowToResult(list, (Object[])x);
		});
		Map<String, List<AttItemName>> map = new HashMap<>();
		mapListAttItemName = list.stream().collect(Collectors.groupingBy(AttItemNameByAuthId::getAuthId));
		for (Map.Entry<String, List<AttItemNameByAuthId>> entry : mapListAttItemName.entrySet()) {
			List<AttItemNameByAuthId> listAttNameByAu = entry.getValue();
			List<AttItemName> listAttName = new ArrayList<>();
			if(!CollectionUtil.isEmpty(listAttNameByAu)){
				for (AttItemNameByAuthId attItemNameByAuthId : listAttNameByAu) {
					AttItemName attItemName = new AttItemName();
					attItemName.setAttendanceItemId(attItemNameByAuthId.getAttendanceItemId());
					AttItemAuthority auth = new AttItemAuthority();
					auth.setToUse(attItemNameByAuthId.isToUse());
					auth.setYouCanChangeIt(attItemNameByAuthId.isYouCanChangeIt());
					auth.setCanBeChangedByOthers(attItemNameByAuthId.isCanBeChangedByOthers());
					attItemName.setAuthority(auth);
					listAttName.add(attItemName);
				}
			}
			map.put(entry.getKey(), listAttName);
		}
		return map;
	}
	private void putRowToResult(List<AttItemNameByAuthId> list, Object[] x) {
		AttItemNameByAuthId attItemName = new AttItemNameByAuthId();
		attItemName.setAuthId((String) x[0]);
		attItemName.setAttendanceItemId(((BigDecimal) x[1]).intValue());
		attItemName.setToUse(((BigDecimal) x[2]).intValue() ==1?true:false);
		attItemName.setCanBeChangedByOthers(((BigDecimal) x[3]).intValue() ==1?true:false);
		attItemName.setYouCanChangeIt(((BigDecimal) x[4]).intValue() ==1?true:false);
		list.add(attItemName);
	}



}
