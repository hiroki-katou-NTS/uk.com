package nts.uk.ctx.at.function.infra.entity.monthlyworkschedule;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import nts.uk.ctx.at.function.dom.dailyworkschedule.AttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.dailyworkschedule.NameWorkTypeOrHourZone;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleGetMemento;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleSetMemento;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.dailyworkschedule.PrintRemarksContent;
import nts.uk.ctx.at.function.dom.dailyworkschedule.RemarkInputContent;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.ItemSelectionEnum;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyAttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyOutputItemSettingCode;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyOutputItemSettingName;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkScheduleGetMemento;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkScheduleSetMemento;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.PrintSettingRemarksColumn;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.TextSizeCommonEnum;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * Entity 月別勤務表の出力項目
 */
@Data
@Entity
@Table(name = "KDNMT_RPT_WK_MON_OUT")
@EqualsAndHashCode(callSuper = true)
public class KfnmtRptWkMonOut extends UkJpaEntity
		implements OutputItemMonthlyWorkScheduleGetMemento, OutputItemMonthlyWorkScheduleSetMemento, Serializable {

	// column 出力項目ID 
	@Id
	@Column(name = "LAYOUT_ID")
	private String layoutID;
	// column 排他バージョン
	@Version
	@Column(name = "EXCLUS_VER")
	private long version;

	// column 契約コード
	@Column(name = "CONTRACT_CD")
	private String contractCd;

	// column 会社ID
	@Column(name = "CID")
	private String companyID;

	// column コード
	@Column(name = "ITEM_CD")
	private String itemCode;

	// column 名称
	@Column(name = "ITEM_NAME")
	private String itemName;

	// column 項目選択種類
	@Column(name = "ITEM_TYPE")
	private Integer itemType;

	// column 社員ID
	@Column(name = "EMPLOYEE_ID")
	private String employeeID;

	// column 文字の大きさ
	@Column(name = "TEXT_SIZE")
	private int textSize;

	// column 備考欄の印字設定
	@Column(name = "IS_REMARK_PRINTED")
	private Boolean isRemarkPrinted;

	// column 備考入力No
	@Column(name = "REMARK_INPUT_NO")
	private Integer remarkInputNo;
	
	/** The lst kfnmt rpt mon outtd . */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "LAYOUT_ID", referencedColumnName = "LAYOUT_ID")
	private List<KfnmtRptWkMonOuttd> lstKfnmtRptWkMonOuttds;
	
	@Override
	protected Object getKey() {
		return null;
	}

	@Override
	public void setItemCode(MonthlyOutputItemSettingCode itemCode) {
		this.itemCode = itemCode.v();
	}

	@Override
	public void setItemName(MonthlyOutputItemSettingName itemName) {
		this.itemName = itemName.v();
	}

	@Override
	public void setLstDisplayedAttendance(List<MonthlyAttendanceItemsDisplay> lstDisplayAttendance) {
		this.lstKfnmtRptWkMonOuttds = lstDisplayAttendance.stream().map(item -> {
			KfnmtRptWkMonOuttdPK kfnmtRptWkMonOuttdPK = new KfnmtRptWkMonOuttdPK();
			kfnmtRptWkMonOuttdPK.setOrderNo(item.getOrderNo());
			kfnmtRptWkMonOuttdPK.setLayoutID(this.layoutID);
			KfnmtRptWkMonOuttd kfnmtRptWkMonOuttd = new KfnmtRptWkMonOuttd();
			kfnmtRptWkMonOuttd.setPk(kfnmtRptWkMonOuttdPK);	
			return kfnmtRptWkMonOuttd;
		}).collect(Collectors.toList());
	}

	@Override
	public void setPrintRemarksColumn(PrintSettingRemarksColumn printSettingRemarksColumn) {
		if (printSettingRemarksColumn.value == 0) {
			this.isRemarkPrinted = false;
		} else if (printSettingRemarksColumn.value == 1) {
			this.isRemarkPrinted = true;
		}
	}

	@Override
	public void setRemarkInputNo(RemarkInputContent remarkInputNo) {
		this.remarkInputNo = remarkInputNo.value;

	}

	@Override
	public void setItemSelectionEnum(ItemSelectionEnum itemSelectionEnum) {
		this.itemType = itemSelectionEnum.value;

	}

	@Override
	public void setTextSize(TextSizeCommonEnum textSize) {
		this.textSize = textSize.value;
	}

	@Override
	public void setLayoutID(String layoutID) {
		this.layoutID = layoutID ;
	}

	@Override
	public MonthlyOutputItemSettingCode getItemCode() {
		return new MonthlyOutputItemSettingCode(this.itemCode);
	}

	@Override
	public MonthlyOutputItemSettingName getItemName() {
		return new MonthlyOutputItemSettingName(this.itemName);
	}

	@Override
	public List<MonthlyAttendanceItemsDisplay> getLstDisplayedAttendance() {
		List<MonthlyAttendanceItemsDisplay> monthlyAttendanceItemsDisplays = new ArrayList<>();
		monthlyAttendanceItemsDisplays = this.lstKfnmtRptWkMonOuttds.stream().map(item ->{
			MonthlyAttendanceItemsDisplay monthlyAttendanceItemsDisplay = new MonthlyAttendanceItemsDisplay();
			monthlyAttendanceItemsDisplay.setAttendanceDisplay(item.getAtdDisplay());
			monthlyAttendanceItemsDisplay.setOrderNo(item.getPk().getOrderNo());
			return monthlyAttendanceItemsDisplay;
		}).collect(Collectors.toList());
		return monthlyAttendanceItemsDisplays;
	}

	@Override
	public PrintSettingRemarksColumn getPrintSettingRemarksColumn() {
		if (this.isRemarkPrinted==true) {
			return PrintSettingRemarksColumn.valueOf(1);
		}else {
			return PrintSettingRemarksColumn.valueOf(0);
		}
	}

	@Override
	public RemarkInputContent getRemarkInputNo() {
		return RemarkInputContent.valueOf(this.remarkInputNo);
	}

	@Override
	public ItemSelectionEnum getItemSelectionEnum() {
		return ItemSelectionEnum.valueOf(this.itemType);
	}

	@Override
	public TextSizeCommonEnum getTextSize() {
		return TextSizeCommonEnum.valueOf(this.textSize);
	}
	@Override
	public String getLayoutID() {
		return this.layoutID;
	}
}
