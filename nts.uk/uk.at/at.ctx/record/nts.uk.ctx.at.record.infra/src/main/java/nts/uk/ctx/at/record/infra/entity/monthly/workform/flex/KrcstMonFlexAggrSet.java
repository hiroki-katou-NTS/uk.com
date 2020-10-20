package nts.uk.ctx.at.record.infra.entity.monthly.workform.flex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.HolidayWorkFlexAddition;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.MonthlyAggrSetOfFlex;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.OutsideTimeFlexAddition;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.workform.flex.OverTimeFlexAddition;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：フレックス勤務の月別集計設定
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCST_MON_FLEX_AGGR_SET")
@NoArgsConstructor
public class KrcstMonFlexAggrSet extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcstMonFlexAggrSetPK PK;
	
	/** 残業加算する01 */
	@Column(name = "OVER_TIME_ADD_01")
	public int overTimeAdd01;
	/** 残業加算する02 */
	@Column(name = "OVER_TIME_ADD_02")
	public int overTimeAdd02;
	/** 残業加算する03 */
	@Column(name = "OVER_TIME_ADD_03")
	public int overTimeAdd03;
	/** 残業加算する04 */
	@Column(name = "OVER_TIME_ADD_04")
	public int overTimeAdd04;
	/** 残業加算する05 */
	@Column(name = "OVER_TIME_ADD_05")
	public int overTimeAdd05;
	/** 残業加算する06 */
	@Column(name = "OVER_TIME_ADD_06")
	public int overTimeAdd06;
	/** 残業加算する07 */
	@Column(name = "OVER_TIME_ADD_07")
	public int overTimeAdd07;
	/** 残業加算する08 */
	@Column(name = "OVER_TIME_ADD_08")
	public int overTimeAdd08;
	/** 残業加算する09 */
	@Column(name = "OVER_TIME_ADD_09")
	public int overTimeAdd09;
	/** 残業加算する10 */
	@Column(name = "OVER_TIME_ADD_10")
	public int overTimeAdd10;
	
	/** 休出加算する01 */
	@Column(name = "HOLIDAY_WORK_ADD_01")
	public int holidayWorkAdd01;
	/** 休出加算する02 */
	@Column(name = "HOLIDAY_WORK_ADD_02")
	public int holidayWorkAdd02;
	/** 休出加算する03 */
	@Column(name = "HOLIDAY_WORK_ADD_03")
	public int holidayWorkAdd03;
	/** 休出加算する04 */
	@Column(name = "HOLIDAY_WORK_ADD_04")
	public int holidayWorkAdd04;
	/** 休出加算する05 */
	@Column(name = "HOLIDAY_WORK_ADD_05")
	public int holidayWorkAdd05;
	/** 休出加算する06 */
	@Column(name = "HOLIDAY_WORK_ADD_06")
	public int holidayWorkAdd06;
	/** 休出加算する07 */
	@Column(name = "HOLIDAY_WORK_ADD_07")
	public int holidayWorkAdd07;
	/** 休出加算する08 */
	@Column(name = "HOLIDAY_WORK_ADD_08")
	public int holidayWorkAdd08;
	/** 休出加算する09 */
	@Column(name = "HOLIDAY_WORK_ADD_09")
	public int holidayWorkAdd09;
	/** 休出加算する10 */
	@Column(name = "HOLIDAY_WORK_ADD_10")
	public int holidayWorkAdd10;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @return フレックス勤務の月別集計設定
	 */
	public MonthlyAggrSetOfFlex toDomain(){

		// 時間外加算設定
		List<OverTimeFlexAddition> overTimeList = new ArrayList<>();
		overTimeList.add(OverTimeFlexAddition.of(new OverTimeFrameNo(1),
				EnumAdaptor.valueOf(this.overTimeAdd01, UseAtr.class)));
		overTimeList.add(OverTimeFlexAddition.of(new OverTimeFrameNo(2),
				EnumAdaptor.valueOf(this.overTimeAdd02, UseAtr.class)));
		overTimeList.add(OverTimeFlexAddition.of(new OverTimeFrameNo(3),
				EnumAdaptor.valueOf(this.overTimeAdd03, UseAtr.class)));
		overTimeList.add(OverTimeFlexAddition.of(new OverTimeFrameNo(4),
				EnumAdaptor.valueOf(this.overTimeAdd04, UseAtr.class)));
		overTimeList.add(OverTimeFlexAddition.of(new OverTimeFrameNo(5),
				EnumAdaptor.valueOf(this.overTimeAdd05, UseAtr.class)));
		overTimeList.add(OverTimeFlexAddition.of(new OverTimeFrameNo(6),
				EnumAdaptor.valueOf(this.overTimeAdd06, UseAtr.class)));
		overTimeList.add(OverTimeFlexAddition.of(new OverTimeFrameNo(7),
				EnumAdaptor.valueOf(this.overTimeAdd07, UseAtr.class)));
		overTimeList.add(OverTimeFlexAddition.of(new OverTimeFrameNo(8),
				EnumAdaptor.valueOf(this.overTimeAdd08, UseAtr.class)));
		overTimeList.add(OverTimeFlexAddition.of(new OverTimeFrameNo(9),
				EnumAdaptor.valueOf(this.overTimeAdd09, UseAtr.class)));
		overTimeList.add(OverTimeFlexAddition.of(new OverTimeFrameNo(10),
				EnumAdaptor.valueOf(this.overTimeAdd10, UseAtr.class)));
		List<HolidayWorkFlexAddition> holidayWorkList = new ArrayList<>();
		holidayWorkList.add(HolidayWorkFlexAddition.of(new HolidayWorkFrameNo(1),
				EnumAdaptor.valueOf(this.holidayWorkAdd01, UseAtr.class)));
		holidayWorkList.add(HolidayWorkFlexAddition.of(new HolidayWorkFrameNo(2),
				EnumAdaptor.valueOf(this.holidayWorkAdd02, UseAtr.class)));
		holidayWorkList.add(HolidayWorkFlexAddition.of(new HolidayWorkFrameNo(3),
				EnumAdaptor.valueOf(this.holidayWorkAdd03, UseAtr.class)));
		holidayWorkList.add(HolidayWorkFlexAddition.of(new HolidayWorkFrameNo(4),
				EnumAdaptor.valueOf(this.holidayWorkAdd04, UseAtr.class)));
		holidayWorkList.add(HolidayWorkFlexAddition.of(new HolidayWorkFrameNo(5),
				EnumAdaptor.valueOf(this.holidayWorkAdd05, UseAtr.class)));
		holidayWorkList.add(HolidayWorkFlexAddition.of(new HolidayWorkFrameNo(6),
				EnumAdaptor.valueOf(this.holidayWorkAdd06, UseAtr.class)));
		holidayWorkList.add(HolidayWorkFlexAddition.of(new HolidayWorkFrameNo(7),
				EnumAdaptor.valueOf(this.holidayWorkAdd07, UseAtr.class)));
		holidayWorkList.add(HolidayWorkFlexAddition.of(new HolidayWorkFrameNo(8),
				EnumAdaptor.valueOf(this.holidayWorkAdd08, UseAtr.class)));
		holidayWorkList.add(HolidayWorkFlexAddition.of(new HolidayWorkFrameNo(9),
				EnumAdaptor.valueOf(this.holidayWorkAdd09, UseAtr.class)));
		holidayWorkList.add(HolidayWorkFlexAddition.of(new HolidayWorkFrameNo(10),
				EnumAdaptor.valueOf(this.holidayWorkAdd10, UseAtr.class)));
		val outsideTimeAddSet = OutsideTimeFlexAddition.of(overTimeList, holidayWorkList);
		
		return MonthlyAggrSetOfFlex.of(
				this.PK.companyId,
				outsideTimeAddSet);
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param domain フレックス勤務の月別集計設定
	 */
	public void fromDomainForPersist(MonthlyAggrSetOfFlex domain){
		
		this.PK = new KrcstMonFlexAggrSetPK(domain.getCompanyId());
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain フレックス勤務の月別集計設定
	 */
	public void fromDomainForUpdate(MonthlyAggrSetOfFlex domain){
		
		val outsideTimeAddSet = domain.getOutsideTimeAddSet();
		
		this.overTimeAdd01 = 0;
		this.overTimeAdd02 = 0;
		this.overTimeAdd03 = 0;
		this.overTimeAdd04 = 0;
		this.overTimeAdd05 = 0;
		this.overTimeAdd06 = 0;
		this.overTimeAdd07 = 0;
		this.overTimeAdd08 = 0;
		this.overTimeAdd09 = 0;
		this.overTimeAdd10 = 0;
		val overTimeMap = outsideTimeAddSet.getOverTimeMap();
		for (int i = 1; i <= 10; i++){
			val overTimeFrameNo = new OverTimeFrameNo(i);
			if (overTimeMap.containsKey(overTimeFrameNo)){
				switch(i){
				case 1:
					this.overTimeAdd01 = overTimeMap.get(overTimeFrameNo).getAddition().value;
					break;
				case 2:
					this.overTimeAdd02 = overTimeMap.get(overTimeFrameNo).getAddition().value;
					break;
				case 3:
					this.overTimeAdd03 = overTimeMap.get(overTimeFrameNo).getAddition().value;
					break;
				case 4:
					this.overTimeAdd04 = overTimeMap.get(overTimeFrameNo).getAddition().value;
					break;
				case 5:
					this.overTimeAdd05 = overTimeMap.get(overTimeFrameNo).getAddition().value;
					break;
				case 6:
					this.overTimeAdd06 = overTimeMap.get(overTimeFrameNo).getAddition().value;
					break;
				case 7:
					this.overTimeAdd07 = overTimeMap.get(overTimeFrameNo).getAddition().value;
					break;
				case 8:
					this.overTimeAdd08 = overTimeMap.get(overTimeFrameNo).getAddition().value;
					break;
				case 9:
					this.overTimeAdd09 = overTimeMap.get(overTimeFrameNo).getAddition().value;
					break;
				case 10:
					this.overTimeAdd10 = overTimeMap.get(overTimeFrameNo).getAddition().value;
					break;
				}
			}
		}

		this.holidayWorkAdd01 = 0;
		this.holidayWorkAdd02 = 0;
		this.holidayWorkAdd03 = 0;
		this.holidayWorkAdd04 = 0;
		this.holidayWorkAdd05 = 0;
		this.holidayWorkAdd06 = 0;
		this.holidayWorkAdd07 = 0;
		this.holidayWorkAdd08 = 0;
		this.holidayWorkAdd09 = 0;
		this.holidayWorkAdd10 = 0;
		val holidayWorkMap = outsideTimeAddSet.getHolidayWorkMap();
		for (int i = 1; i <= 10; i++){
			val holidayWorkFrameNo = new HolidayWorkFrameNo(i);
			if (holidayWorkMap.containsKey(holidayWorkFrameNo)){
				switch(i){
				case 1:
					this.holidayWorkAdd01 = holidayWorkMap.get(holidayWorkFrameNo).getAddition().value;
					break;
				case 2:
					this.holidayWorkAdd02 = holidayWorkMap.get(holidayWorkFrameNo).getAddition().value;
					break;
				case 3:
					this.holidayWorkAdd03 = holidayWorkMap.get(holidayWorkFrameNo).getAddition().value;
					break;
				case 4:
					this.holidayWorkAdd04 = holidayWorkMap.get(holidayWorkFrameNo).getAddition().value;
					break;
				case 5:
					this.holidayWorkAdd05 = holidayWorkMap.get(holidayWorkFrameNo).getAddition().value;
					break;
				case 6:
					this.holidayWorkAdd06 = holidayWorkMap.get(holidayWorkFrameNo).getAddition().value;
					break;
				case 7:
					this.holidayWorkAdd07 = holidayWorkMap.get(holidayWorkFrameNo).getAddition().value;
					break;
				case 8:
					this.holidayWorkAdd08 = holidayWorkMap.get(holidayWorkFrameNo).getAddition().value;
					break;
				case 9:
					this.holidayWorkAdd09 = holidayWorkMap.get(holidayWorkFrameNo).getAddition().value;
					break;
				case 10:
					this.holidayWorkAdd10 = holidayWorkMap.get(holidayWorkFrameNo).getAddition().value;
					break;
				}
			}
		}
	}
}
