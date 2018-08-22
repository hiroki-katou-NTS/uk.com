package nts.uk.ctx.at.function.infra.repository.monthlyworkschedule;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarkInputContent;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyAttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyOutputItemSettingCode;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyOutputItemSettingName;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkScheduleGetMemento;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.PrintSettingRemarksColumn;
import nts.uk.ctx.at.function.infra.entity.monthlyworkschedule.KfnmtMonthlyWorkSche;
import nts.uk.ctx.at.function.infra.entity.monthlyworkschedule.KfnmtMonthlyWorkSchePK;
import nts.uk.shr.com.context.AppContexts;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaOutputItemMonthlyWorkScheduleGetMemento.
 */
public class JpaOutputItemMonthlyWorkScheduleGetMemento implements OutputItemMonthlyWorkScheduleGetMemento {

	/** The kfnmt monthly work sche. */
	private KfnmtMonthlyWorkSche kfnmtMonthlyWorkSche;

	/**
	 * Instantiates a new jpa output item monthly work schedule get memento.
	 *
	 * @param kfnmtMonthlyWorkSche
	 *            the kfnmt monthly work sche
	 */
	public JpaOutputItemMonthlyWorkScheduleGetMemento(KfnmtMonthlyWorkSche kfnmtMonthlyWorkSche) {
		this.kfnmtMonthlyWorkSche = kfnmtMonthlyWorkSche;
		if (kfnmtMonthlyWorkSche.getId() == null) {
			KfnmtMonthlyWorkSchePK key = new KfnmtMonthlyWorkSchePK();
			key.setCid(AppContexts.user().companyId());
			kfnmtMonthlyWorkSche.setId(key);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleGetMemento#getCompanyID()
	 */
	@Override
	public String getCompanyID() {
		// TODO Auto-generated method stub
		return this.kfnmtMonthlyWorkSche.getId().getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleGetMemento#getItemCode()
	 */
	@Override
	public MonthlyOutputItemSettingCode getItemCode() {
		// TODO Auto-generated method stub
		return new MonthlyOutputItemSettingCode(this.kfnmtMonthlyWorkSche.getId().getItemCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleGetMemento#getItemName()
	 */
	@Override
	public MonthlyOutputItemSettingName getItemName() {
		// TODO Auto-generated method stub
		return new MonthlyOutputItemSettingName(this.kfnmtMonthlyWorkSche.getItemName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleGetMemento#getLstDisplayedAttendance()
	 */
	@Override
	public List<MonthlyAttendanceItemsDisplay> getLstDisplayedAttendance() {
		// TODO Auto-generated method stub
		return this.kfnmtMonthlyWorkSche.getLstKfnmtMonAttenDisplay().stream().map(entity -> {
			return new MonthlyAttendanceItemsDisplay((int) entity.getId().getOrderNo(),
					entity.getAtdDisplay().intValue());
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.
	 * OutputItemMonthlyWorkScheduleGetMemento#getPrintSettingRemarksColumn()
	 */
	@Override
	public PrintSettingRemarksColumn getPrintSettingRemarksColumn() {
		// TODO Auto-generated method stub
		return PrintSettingRemarksColumn.valueOf(this.kfnmtMonthlyWorkSche.getIsPrint().intValue());
	}

	@Override
	public RemarkInputContent getRemarkInputNo() {
		return RemarkInputContent.valueOf(this.kfnmtMonthlyWorkSche.getRemarkInputNo().intValue());
	}

}
