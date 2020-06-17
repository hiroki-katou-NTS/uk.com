package nts.uk.ctx.at.record.app.find.kdp.kdp001.a;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetStampTypeToSuppressService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampToSuppress;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KDP001_打刻入力(ポータル).A:打刻入力(ポータル).メニュー別OCD.打刻入力(ポータル)の打刻ボタンを抑制の表示をする
 * 
 * @author sonnlb
 *
 */
@Stateless
public class DisplaySuppressStampButtonInStampInput {

	@Inject
	private GetStampTypeToSuppressService getStampToSuppressService;

	public StampToSuppress getStampToSuppress() {
		GetStampTypeToSuppressServiceRequireImpl require = new GetStampTypeToSuppressServiceRequireImpl();

		String employeeId = AppContexts.user().employeeId();
		// 取得する(Require, 社員ID, 打刻手段)
		return this.getStampToSuppressService.get(require, employeeId, StampMeans.PORTAL);
	}

	private class GetStampTypeToSuppressServiceRequireImpl implements GetStampTypeToSuppressService.Require {

		@Override
		public List<StampCard> getListStampCard(String sid) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<StampRecord> getStampRecord(List<StampNumber> stampNumbers, GeneralDate date) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Stamp> getStamp(List<StampNumber> stampNumbers, GeneralDate date) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<StampSettingPerson> getStampSet() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<WorkingConditionItem> findWorkConditionByEmployee(String employeeId, GeneralDate baseDate) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<PredetemineTimeSetting> findByWorkTimeCode(String workTimeCode) {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
