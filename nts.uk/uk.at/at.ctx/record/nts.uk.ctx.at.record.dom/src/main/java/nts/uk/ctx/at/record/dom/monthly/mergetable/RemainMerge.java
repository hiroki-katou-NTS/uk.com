package nts.uk.ctx.at.record.dom.monthly.mergetable;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.monthly.information.care.MonCareHdRemain;
import nts.uk.ctx.at.record.dom.monthly.information.childnursing.MonChildHdRemain;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AbsenceLeaveRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata.MonthlyDayoffRemainData;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainData;

/**
 * 残数系データ
 * @author shuichi_ishida
 */
@Getter
@Setter
public class RemainMerge {
	MonthMergeKey monthMergeKey;
	AnnLeaRemNumEachMonth annLeaRemNumEachMonth;
	RsvLeaRemNumEachMonth rsvLeaRemNumEachMonth;
	SpecialHolidayRemainDataMerge specialHolidayRemainDataMerge;
	MonthlyDayoffRemainData monthlyDayoffRemainData;
	AbsenceLeaveRemainData absenceLeaveRemainData;
	MonChildHdRemain monChildHdRemain;
	MonCareHdRemain monCareHdRemain;
	
	public RemainMerge(){
		this.specialHolidayRemainDataMerge = new SpecialHolidayRemainDataMerge();
	}
	
	public List<SpecialHolidayRemainData> getSpecialHolidayRemainList(){
		List<SpecialHolidayRemainData> results = new ArrayList<>();
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData1());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData2());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData3());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData4());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData5());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData6());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData7());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData8());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData9());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData10());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData11());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData12());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData13());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData14());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData15());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData16());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData17());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData18());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData19());
		results.add(this.specialHolidayRemainDataMerge.getSpecialHolidayRemainData20());
		return results;
	}
	
	public void setSpecialHolidayRemainDataMerge(SpecialHolidayRemainDataMerge datas){
		if (datas == null) this.specialHolidayRemainDataMerge = new SpecialHolidayRemainDataMerge();
		this.specialHolidayRemainDataMerge = datas;
	}
	
	public void setSpecialHolidayRemainDataMerge(List<SpecialHolidayRemainData> datas){
		for (SpecialHolidayRemainData data : datas){
			switch (data.getSpecialHolidayCd()){
			case 1:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData1(data);
				break;
			case 2:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData2(data);
				break;
			case 3:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData3(data);
				break;
			case 4:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData4(data);
				break;
			case 5:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData5(data);
				break;
			case 6:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData6(data);
				break;
			case 7:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData7(data);
				break;
			case 8:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData8(data);
				break;
			case 9:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData9(data);
				break;
			case 10:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData10(data);
				break;
			case 11:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData11(data);
				break;
			case 12:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData12(data);
				break;
			case 13:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData13(data);
				break;
			case 14:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData14(data);
				break;
			case 15:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData15(data);
				break;
			case 16:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData16(data);
				break;
			case 17:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData17(data);
				break;
			case 18:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData18(data);
				break;
			case 19:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData19(data);
				break;
			case 20:
				this.specialHolidayRemainDataMerge.setSpecialHolidayRemainData20(data);
				break;
			}
		}
	}
	
	public boolean isEmpty(){
		if (this.monthMergeKey != null) return false;
		if (this.annLeaRemNumEachMonth != null) return false;
		if (this.rsvLeaRemNumEachMonth != null) return false;
		if (!this.specialHolidayRemainDataMerge.isEmpty()) return false;
		if (this.monthlyDayoffRemainData != null) return false;
		if (this.absenceLeaveRemainData != null) return false;
		if (this.monChildHdRemain != null) return false;
		if (this.monCareHdRemain != null) return false;
		return true;
	}
}
