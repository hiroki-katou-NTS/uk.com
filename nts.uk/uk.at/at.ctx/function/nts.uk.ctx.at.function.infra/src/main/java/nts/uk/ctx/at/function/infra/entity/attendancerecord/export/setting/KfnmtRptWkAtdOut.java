package nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingGetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingSetMemento;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingName;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.SealColumnName;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutframe;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
 * The persistent class for the KFNMT_RPT_WK_ATD_OUT database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KFNMT_RPT_WK_ATD_OUT")
public class KfnmtRptWkAtdOut extends UkJpaEntity {
//	implements Serializable , AttendanceRecordExportSettingGetMemento, AttendanceRecordExportSettingSetMemento{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	
	
	/** The id. */
	@Id
	@Column(name = "LAYOUT_ID")
	@Basic(optional = false)
	private String layoutId;
	
	@Basic(optional = false)
	@Column(name = "EXCLUS_VER")
	public int exclusVer;
	
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	public String contractCd;
	
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
	private BigDecimal sealUseAtr;
	
	@Basic(optional = false)
	@Column(name="NAME_USE_ATR")
	private BigDecimal nameUseAtr;
	
	@Basic(optional = false)
	@Column(name="CHAR_SIZE_TYPE")
	private BigDecimal charSizeType;
	
	@Basic(optional = false)
	@Column(name="MONTH_APP_DISP_ATR")
	private BigDecimal monthAppDispAtr;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="LAYOUT_ID", referencedColumnName="LAYOUT_ID")
	private List<KfnmtRptWkAtdOutframe> lstKfnmtRptWkAtdOutframe;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.layoutId;
	}

//	@Override
//	public void setCompanyId(String companyId) {
//		this.cid = companyId;
//	}
//
//	@Override
//	public void setDailyExportItem(List<AttendanceRecordExport> attendanceList) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setMonthlyExportItem(List<AttendanceRecordExport> attendanceList) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setSealUseAtr(Boolean atr) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setCode(ExportSettingCode code) {
//		// TODO Auto-generated method stub
//		
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
//	}
//
//	@Override
//	public void setExportFontSize(Integer exportFontSize) {
//		this.charSizeType = BigDecimal.valueOf(exportFontSize);
//		
//	}
//
//	@Override
//	public void setMonthlyConfirmedDisplay(Integer monthlyConfirmedDisplay) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public String getCompanyId() {
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
//	public Integer getExportFontSize() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Integer getMonthlyConfirmedDisplay() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}