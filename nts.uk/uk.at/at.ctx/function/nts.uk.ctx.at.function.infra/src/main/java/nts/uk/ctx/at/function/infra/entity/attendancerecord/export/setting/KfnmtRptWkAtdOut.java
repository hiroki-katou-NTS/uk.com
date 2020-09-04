package nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingSetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportFontSize;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingName;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.MonthlyConfirmedDisplay;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.SealColumnName;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
 * The persistent class for the KFNMT_RPT_WK_ATD_OUT database table.
 * 
 */
@Data
@Entity
@Table(name = "KFNMT_RPT_WK_ATD_OUT")
public class KfnmtRptWkAtdOut extends UkJpaEntity
		implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@Column(name = "LAYOUT_ID")
	@Basic(optional = false)
	private String layoutID;

	@Column(name="EXCLUS_VER")
	private String exclusVer;

	@Basic(optional = false)
	@Column(name="CONTRACT_CD")
	private String contractCD;
	
	@Basic(optional = false)
	@Column(name="ITEM_SEL_TYPE")
	private int itemSelType;
	
	@Basic(optional = false)
	@Column(name="CID")
	private String cid;
	
	@Column(name="SID")
	private String sid;
	
	@Basic(optional = false)
	@Column(name="EXPORT_CD")
	private String exportCD;
	
	@Basic(optional = false)
	@Column(name="NAME")
	private String name;
	
	@Basic(optional = false)
	@Column(name="SEAL_USE_ATR")
	private boolean sealUseAtr;
	
	@Basic(optional = false)
	@Column(name="NAME_USE_ATR")
	private int nameUseAtr;
	
	@Basic(optional = false)
	@Column(name="CHAR_SIZE_TYPE")
	private int charSizeType;
	
	@Basic(optional = false)
	@Column(name="MONTH_APP_DISP_ATR")
	private int monthAppDispAtr;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.layoutID;
	}

//	@Override
//	public void setCompanyId(String companyId) {
//		this.cid = companyId;
//		
//	}
//
//	@Override
//	public void setDailyExportItem(List<AttendanceRecordExport> attendanceList) {
//		
//	}
//
//	@Override
//	public void setMonthlyExportItem(List<AttendanceRecordExport> attendanceList) {
//		
//	}
//
//	@Override
//	public void setSealUseAtr(Boolean atr) {
//		this.sealUseAtr = atr;
//	}
//
//	@Override
//	public void setCode(ExportSettingCode code) {
//		this.
//	}
//
//	@Override
//	public void setName(ExportSettingName name) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setSealStamp(List<SealColumnName> seal) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setNameUseAtr(Integer nameUseAtr) {
//		// TODO Auto-generated method stub
//		
//	@Override
//	public String getCompanyId() {
//		
//		return this.cid;
//	}
//
//	@Override
//	public List<AttendanceRecordExport> getDailyExportItem() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<AttendanceRecordExport> getMonthlyExportItem() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Boolean getSealUseAtr() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ExportSettingCode getCode() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ExportSettingName getName() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<SealColumnName> getSealStamp() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Integer getNameUseAtr() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public int getExportFontSize() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getMonthlyConfirmedDisplay() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void setExportFontSize(ExportFontSize exportFontSize) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setMonthlyConfirmedDisplay(MonthlyConfirmedDisplay monthlyConfirmedDisplay) {
//		// TODO Auto-generated method stub
//		
//	}
}