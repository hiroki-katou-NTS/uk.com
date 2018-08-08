package nts.uk.ctx.at.function.infra.repository.monthlyworkschedule;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarkInputContent;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyAttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyOutputItemSettingCode;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyOutputItemSettingName;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkScheduleSetMemento;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.PrintSettingRemarksColumn;
import nts.uk.ctx.at.function.infra.entity.monthlyworkschedule.KfnmtMonAttenDisplay;
import nts.uk.ctx.at.function.infra.entity.monthlyworkschedule.KfnmtMonAttenDisplayPK;
import nts.uk.ctx.at.function.infra.entity.monthlyworkschedule.KfnmtMonthlyWorkSche;
import nts.uk.ctx.at.function.infra.entity.monthlyworkschedule.KfnmtMonthlyWorkSchePK;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaOutputItemMonthlyWorkScheduleSetMemento.
 */
public class JpaOutputItemMonthlyWorkScheduleSetMemento implements OutputItemMonthlyWorkScheduleSetMemento {

	/** The kfnmt monthly work sche. */
	private KfnmtMonthlyWorkSche kfnmtMonthlyWorkSche;

	/**
	 * Instantiates a new jpa output item monthly work schedule set memento.
	 *
	 * @param kfnmtMonthlyWorkSche
	 *            the kfnmt monthly work sche
	 */
	public JpaOutputItemMonthlyWorkScheduleSetMemento(KfnmtMonthlyWorkSche kfnmtMonthlyWorkSche) {
		if (kfnmtMonthlyWorkSche.getId() == null) {
			KfnmtMonthlyWorkSchePK key = new KfnmtMonthlyWorkSchePK();
			kfnmtMonthlyWorkSche.setId(key);
		}
		this.kfnmtMonthlyWorkSche = kfnmtMonthlyWorkSche;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleSetMemento#setCompanyID(java.lang.String)
	 */
	@Override
	public void setCompanyID(String companyID) {
		// TODO Auto-generated method stub
		KfnmtMonthlyWorkSchePK key = kfnmtMonthlyWorkSche.getId();
		key.setCid(companyID);
		kfnmtMonthlyWorkSche.setId(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleSetMemento#setItemCode(nts.uk.ctx.at.
	 * function.dom.monthlyworkschedule.MonthlyOutputItemSettingCode)
	 */
	@Override
	public void setItemCode(MonthlyOutputItemSettingCode itemCode) {
		// TODO Auto-generated method stub
		KfnmtMonthlyWorkSchePK key = kfnmtMonthlyWorkSche.getId();
		key.setItemCd(itemCode.v());
		kfnmtMonthlyWorkSche.setId(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleSetMemento#setItemName(nts.uk.ctx.at.
	 * function.dom.monthlyworkschedule.MonthlyOutputItemSettingName)
	 */
	@Override
	public void setItemName(MonthlyOutputItemSettingName itemName) {
		// TODO Auto-generated method stub
		kfnmtMonthlyWorkSche.setItemName(itemName.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleSetMemento#setLstDisplayedAttendance(java.
	 * util.List)
	 */
	@Override
	public void setLstDisplayedAttendance(List<MonthlyAttendanceItemsDisplay> lstDisplayAttendance) {
		// TODO Auto-generated method stub
		List<KfnmtMonAttenDisplay> lstKfnmtMonAttenDisplay = lstDisplayAttendance.stream().map(obj -> {
			KfnmtMonAttenDisplay entity = new KfnmtMonAttenDisplay();
			KfnmtMonAttenDisplayPK key = new KfnmtMonAttenDisplayPK();
			key.setCid(this.kfnmtMonthlyWorkSche.getId().getCid());
			key.setItemCd(this.kfnmtMonthlyWorkSche.getId().getItemCd());
			key.setOrderNo(obj.getOrderNo());
			entity.setId(key);
			entity.setAtdDisplay(new BigDecimal(obj.getAttendanceDisplay()));
			return entity;
		}).collect(Collectors.toList());
		kfnmtMonthlyWorkSche.setLstKfnmtMonAttenDisplay(lstKfnmtMonAttenDisplay);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleSetMemento#setPrintRemarksColumn(nts.uk.ctx.
	 * at.function.dom.monthlyworkschedule.PrintSettingRemarksColumn)
	 */
	@Override
	public void setPrintRemarksColumn(PrintSettingRemarksColumn printSettingRemarksColumn) {
		// TODO Auto-generated method stub
		kfnmtMonthlyWorkSche.setIsPrint(new BigDecimal(printSettingRemarksColumn.value));

	}

	@Override
	public void setRemarkInputNo(RemarkInputContent remarkInputNo) {
		kfnmtMonthlyWorkSche.setRemarkInputNo(BigDecimal.valueOf(remarkInputNo.value));
	}

}
