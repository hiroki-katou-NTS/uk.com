package nts.uk.ctx.office.dom.equipment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.設備マスタ.設備情報
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EquipmentInformation extends AggregateRoot {
	
	/**
	 * コード
	 */
	private EquipmentCode equipmentCode;
	
	/**
	 * 名称
	 */
	private EquipmentName equipmentName;
	
	/**
	 * 有効期間
	 */
	private DatePeriod validPeriod;
	
	/**
	 * 備考
	 */
	private EquipmentRemark equipmentRemark;
	
	public static EquipmentInformation createFromMemento(MementoGetter mementoGetter) {
		EquipmentInformation domain = new EquipmentInformation();
		domain.getMemento(mementoGetter);
		return domain;
	}
	
	public void getMemento(MementoGetter mementoGetter) {
		this.equipmentCode = new EquipmentCode(mementoGetter.getCode());
		this.equipmentName = new EquipmentName(mementoGetter.getName());
		this.validPeriod = new DatePeriod(mementoGetter.getEffectiveStartDate(), mementoGetter.getEffectiveEndDate());
		this.equipmentRemark = new EquipmentRemark(mementoGetter.getRemark());
	}
	
	public void setMemento(MementoSetter mementoSetter) {
		mementoSetter.setCode(this.equipmentCode.v());
		mementoSetter.setName(this.equipmentName.v());
		mementoSetter.setEffectiveStartDate(this.validPeriod.start());
		mementoSetter.setEffectiveEndDate(this.validPeriod.end());
		mementoSetter.setRemark(this.equipmentRemark.v());
	}
	
	public interface MementoGetter {
		String getCode();
		String getName();
		GeneralDate getEffectiveStartDate();
		GeneralDate getEffectiveEndDate();
		String getRemark();
	}
	
	public interface MementoSetter {
		void setCode(String code);
		void setName(String name);
		void setEffectiveStartDate(GeneralDate startDate);
		void setEffectiveEndDate(GeneralDate endDate);
		void setRemark(String remark);
	}
}
