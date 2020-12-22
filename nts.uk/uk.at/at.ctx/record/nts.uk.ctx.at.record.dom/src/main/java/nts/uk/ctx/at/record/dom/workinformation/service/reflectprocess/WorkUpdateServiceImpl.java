package nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;

@Stateless
public class WorkUpdateServiceImpl implements WorkUpdateService {

	/**
	 * 日別実績の編集状態
	 * @param employeeId
	 * @param dateData
	 * @param lstItem
	 */
	@Override
	public void editStateOfDailyPerformance(String sid, GeneralDate ymd, 
			List<EditStateOfDailyAttd> lstEditState, List<Integer> lstItem) {		
		lstItem.stream().forEach(z -> {
			List<EditStateOfDailyAttd> optItemData = lstEditState.stream().filter(a -> a.getAttendanceItemId() == z)
					.collect(Collectors.toList());
			if(!optItemData.isEmpty()) {
				EditStateOfDailyAttd itemData = optItemData.get(0);
				itemData.setEditStateSetting(EditStateSetting.REFLECT_APPLICATION);
			}else {
				EditStateOfDailyPerformance insertData = new EditStateOfDailyPerformance(sid, z, ymd, EditStateSetting.REFLECT_APPLICATION);
				lstEditState.add(insertData.getEditState());
			}
		});
	}
	
	/**
	 * 予定項目ID=残業時間(枠番)の項目ID: 事前申請
	 * @return
	 */
	@Override
	public List<Integer> lstPreOvertimeItem(){
		List<Integer> lstItem = new ArrayList<Integer>();
		lstItem.add(220);
		lstItem.add(225);
		lstItem.add(230);
		lstItem.add(235);
		lstItem.add(240);
		lstItem.add(245);
		lstItem.add(250);
		lstItem.add(255);
		lstItem.add(260);
		lstItem.add(265);
		return lstItem;		
	}
	/**
	 * 予定項目ID=残業時間(枠番)の項目ID: 事後申請
	 * @return
	 */
	@Override
	public List<Integer> lstAfterOvertimeItem(){
		List<Integer> lstItem = new ArrayList<Integer>();
		lstItem.add(216);
		lstItem.add(221);
		lstItem.add(226);
		lstItem.add(231);
		lstItem.add(236);
		lstItem.add(241);
		lstItem.add(245);
		lstItem.add(251);
		lstItem.add(256);
		lstItem.add(261);
		return lstItem;		
	}
	
	/**
	 * 事前休日出勤時間の項目ID
	 * @return
	 */
	@Override
	public List<Integer> lstPreWorktimeFrameItem(){
		List<Integer> lstItem = new ArrayList<>();
		lstItem.add(270);
		lstItem.add(275);
		lstItem.add(280);
		lstItem.add(285);
		lstItem.add(290);
		lstItem.add(295);
		lstItem.add(300);
		lstItem.add(305);
		lstItem.add(310);
		lstItem.add(315);
		return lstItem;
	}
	/**
	 * 事後休日出勤時間帯の項目ID
	 * @return
	 */
	@Override
	public List<Integer> lstAfterWorktimeFrameItem(){
		List<Integer> lstItem = new ArrayList<>();
		lstItem.add(266);
		lstItem.add(271);
		lstItem.add(276);
		lstItem.add(281);
		lstItem.add(286);
		lstItem.add(291);
		lstItem.add(296);
		lstItem.add(301);
		lstItem.add(306);
		lstItem.add(311);
		return lstItem;
	}
	/**
	 * 振替時間の項目ID
	 * @return
	 */
	@Override
	public List<Integer> lstTranfertimeFrameItem(){
		List<Integer> lstItem = new ArrayList<>();		
		lstItem.add(267);
		lstItem.add(272);
		lstItem.add(277);
		lstItem.add(282);
		lstItem.add(287);
		lstItem.add(292);
		lstItem.add(297);
		lstItem.add(302);
		lstItem.add(307);
		lstItem.add(312);
		return lstItem;
	}
	
	@Override
	public List<Integer> lstTransferTimeOtItem() {
		List<Integer> lstItem = new ArrayList<>();		
		lstItem.add(269);
		lstItem.add(274);
		lstItem.add(279);
		lstItem.add(284);
		lstItem.add(289);
		lstItem.add(294);
		lstItem.add(299);
		lstItem.add(304);
		lstItem.add(309);
		lstItem.add(314);
		return lstItem;
	}
	
	@Override
	public List<Integer> lstBreakStartTime() {
		List<Integer> lstItem = new ArrayList<>();		
		lstItem.add(157);
		lstItem.add(163);
		lstItem.add(169);
		lstItem.add(175);
		lstItem.add(181);
		lstItem.add(187);
		lstItem.add(193);
		lstItem.add(199);
		lstItem.add(205);
		lstItem.add(211);
		return lstItem;
	}
	@Override
	public List<Integer> lstBreakEndTime() {
		List<Integer> lstItem = new ArrayList<>();		
		lstItem.add(159);
		lstItem.add(165);
		lstItem.add(171);
		lstItem.add(177);
		lstItem.add(183);
		lstItem.add(189);
		lstItem.add(195);
		lstItem.add(201);
		lstItem.add(207);
		lstItem.add(213);
		return lstItem;
	}
	@Override
	public List<Integer> lstScheBreakStartTime() {
		List<Integer> lstItem = new ArrayList<>();		
		lstItem.add(7);
		lstItem.add(9);
		lstItem.add(11);
		lstItem.add(13);
		lstItem.add(15);
		lstItem.add(17);
		lstItem.add(19);
		lstItem.add(21);
		lstItem.add(23);
		lstItem.add(25);
		return lstItem;
	}
	@Override
	public List<Integer> lstScheBreakEndTime() {
		List<Integer> lstItem = new ArrayList<>();		
		lstItem.add(8);
		lstItem.add(10);
		lstItem.add(12);
		lstItem.add(14);
		lstItem.add(16);
		lstItem.add(18);
		lstItem.add(20);
		lstItem.add(22);
		lstItem.add(24);
		lstItem.add(26);
		return lstItem;
	}
}
