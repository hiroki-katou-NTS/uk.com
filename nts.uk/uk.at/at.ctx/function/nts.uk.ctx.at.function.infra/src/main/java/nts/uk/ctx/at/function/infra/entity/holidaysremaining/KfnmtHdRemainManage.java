package nts.uk.ctx.at.function.infra.entity.holidaysremaining;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.holidaysremaining.ChildNursingLeave;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;
import nts.uk.ctx.at.function.dom.holidaysremaining.ItemOutputForm;
import nts.uk.ctx.at.function.dom.holidaysremaining.ItemsOutputtedAlternate;
import nts.uk.ctx.at.function.dom.holidaysremaining.ItemsPublicOutput;
import nts.uk.ctx.at.function.dom.holidaysremaining.NursingCareLeave;
import nts.uk.ctx.at.function.dom.holidaysremaining.PauseItem;
import nts.uk.ctx.at.function.dom.holidaysremaining.YearlyItemsOutput;
import nts.uk.ctx.at.function.dom.holidaysremaining.YearlyReserved;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 休暇残数管理表の出力項目設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_HD_REMAIN_MANAGE")
public class KfnmtHdRemainManage extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public KfnmtHdRemainManagePk hdRemainManagePk;
	

	/**
	 * 名称
	 */
	@Basic(optional = false)
	@Column(name = "NAME")
	public String name;

	/**
	 * 年休の項目出力する
	 */
	@Basic(optional = false)
	@Column(name = "YEARLY_HOLIDAY")
	public int yearlyHoliday;

	/**
	 * ★内半日年休を出力する
	 */
	@Basic(optional = false)
	@Column(name = "INSIDE_HALF_DAY")
	public int insideHalfDay;

	/**
	 * 内時間年休残数を出力する
	 */
	@Basic(optional = false)
	@Column(name = "INSIDE_HOURS")
	public int insideHours;

	/**
	 * 積立年休の項目を出力する
	 */
	@Basic(optional = false)
	@Column(name = "YEARLY_RESERVED")
	public int yearlyReserved;

	/**
	 * 代休の項目を出力する
	 */
	@Basic(optional = false)
	@Column(name = "OUT_ITEM_SUB")
	public int outItemSub;

	/**
	 * 代休未消化出力する
	 */
	@Basic(optional = false)
	@Column(name = "REPRESENT_SUB")
	public int representSub;

	/**
	 * 代休残数を出力する
	 */
	@Basic(optional = false)
	@Column(name = "REMAIN_CHARGE_SUB")
	public int remainChargeSub;

	/**
	 * 振休の項目を出力する
	 */
	@Basic(optional = false)
	@Column(name = "PAUSE_ITEM")
	public int pauseItem;

	/**
	 * 振休未消化を出力する
	 */
	@Basic(optional = false)
	@Column(name = "UNDIGESTED_PAUSE")
	public int undigestedPause;

	/**
	 * 振休残数を出力する
	 */
	@Basic(optional = false)
	@Column(name = "NUM_REMAIN_PAUSE")
	public int numRemainPause;

	/**
	 * 公休の項目を出力する
	 */
	@Basic(optional = false)
	@Column(name = "OUTPUT_ITEMS_HOLIDAYS")
	public int outputItemsHolidays;

	/**
	 * 公休繰越数を出力する
	 */
	@Basic(optional = false)
	@Column(name = "OUTPUT_HOLIDAY_FORWARD")
	public int outputHolidayForward;

	/**
	 * 公休月度残を出力する
	 */
	@Basic(optional = false)
	@Column(name = "MONTHLY_PUBLIC")
	public int monthlyPublic;

	/**
	 * 子の看護休暇の項目を出力する
	 */
	@Basic(optional = false)
	@Column(name = "CHILD_CARE_LEAVE")
	public int childCareLeave;

	/**
	 * 介護休暇の項目を出力する
	 */
	@Basic(optional = false)
	@Column(name = "NURSING_CARE_LEAVE")
	public int nursingCareLeave;

	@Override
	protected Object getKey() {
		return hdRemainManagePk;
	}

	public HolidaysRemainingManagement toDomain() {
		return new HolidaysRemainingManagement(this.hdRemainManagePk.cd, this.hdRemainManagePk.cid, this.name,
				new ItemOutputForm(
						new NursingCareLeave(this.childCareLeave >0 ? true : false),
						new ItemsOutputtedAlternate(this.remainChargeSub >0 ? true : false, this.representSub >0 ? true : false, this.outItemSub >0 ? true : false),
						new ItemsPublicOutput(this.outputHolidayForward >0 ? true : false, this.monthlyPublic >0 ? true : false, this.outputItemsHolidays >0 ? true : false),
						new ChildNursingLeave(this.childCareLeave >0 ? true : false),
						new YearlyItemsOutput(this.yearlyHoliday >0 ? true : false, this.insideHours >0 ? true : false, this.insideHalfDay >0 ? true : false),
						new PauseItem(this.numRemainPause >0 ? true : false, this.undigestedPause >0 ? true : false, this.pauseItem >0 ? true : false),
						new YearlyReserved(this.yearlyReserved >0 ? true : false)
						)
		);
	}

	public static KfnmtHdRemainManage toEntity(HolidaysRemainingManagement domain) {
		return new KfnmtHdRemainManage(new KfnmtHdRemainManagePk(domain.getCompanyID(), domain.getCode()),
				domain.getName(), 
				domain.getListItemsOutput().getAnnualHoliday().isYearlyHoliday() ? 1 : 0,
				domain.getListItemsOutput().getAnnualHoliday().isInsideHalfDay() ? 1 : 0,
				domain.getListItemsOutput().getAnnualHoliday().isInsideHours() ? 1 : 0,
				domain.getListItemsOutput().getYearlyReserved().isYearlyReserved() ? 1 : 0,
				domain.getListItemsOutput().getSubstituteHoliday().isOutputItemSubstitute() ? 1 : 0,
				domain.getListItemsOutput().getSubstituteHoliday().isRepresentSubstitute() ? 1 : 0,
				domain.getListItemsOutput().getSubstituteHoliday().isRemainingChargeSubstitute() ? 1 : 0,
				domain.getListItemsOutput().getPause().isPauseItem() ? 1 : 0,
				domain.getListItemsOutput().getPause().isUndigestedPause() ? 1 : 0,
				domain.getListItemsOutput().getPause().isNumberRemainingPause() ? 1 : 0,
				domain.getListItemsOutput().getHolidays().isOutputitemsholidays() ? 1 : 0,
				domain.getListItemsOutput().getHolidays().isOutputholidayforward() ? 1 : 0,
				domain.getListItemsOutput().getHolidays().isMonthlyPublic() ? 1 : 0,
				domain.getListItemsOutput().getChildNursingVacation().isChildNursingLeave() ? 1 : 0,
				domain.getListItemsOutput().getNursingcareLeave().isNursingLeave() ? 1 : 0);
	}

}
