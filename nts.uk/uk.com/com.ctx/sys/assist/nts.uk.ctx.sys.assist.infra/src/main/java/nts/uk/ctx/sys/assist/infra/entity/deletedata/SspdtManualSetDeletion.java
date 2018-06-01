package nts.uk.ctx.sys.assist.infra.entity.deletedata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.deletedata.ManualSetDeletion;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "SSPDT_MANUAL_SET_DELETION")
@NoArgsConstructor
@AllArgsConstructor
public class SspdtManualSetDeletion extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
    public SspdtManualSetDeletionPK sspdtManualSetDeletionPK;
	
	/** The company Id. */
	/** 会社ID */
	@Basic(optional = false)
	@Column(name = "CID")
	public String companyID;
	
	/** The system type. */
	/** システム種類  */
	@Basic(optional = false)
	@Column(name = "SYSTEM_TYPE")
	public int systemType;
	
	/** The deletion name. */
	/** 削除名称 */
	@Basic(optional = false)
	@Column(name = "DEL_NAME")
	public String delName;
	
	/** The The saving before deletion flag. */
	/** 削除前に保存 */
	@Basic(optional = false)
	@Column(name = "IS_SAVE_BEFORE_DELETE_FLG")
	public int isSaveBeforeDeleteFlg;
	
	/** The existing compress pass flag. */
	/** パスワード有無 */
	@Basic(optional = false)
	@Column(name = "IS_EXIST_COMPRESS_PASS_FLG")
	public int isExistCompressPassFlg;
	
	/** The password encrypt for compress file. */
	/** 手動保存の圧縮パスワード */
	@Basic(optional = true)
	@Column(name = "PASSWORD_FOR_COMPRESS_FILE")
	public String passwordCompressFileEncrypt;
	
	/** The having employee specified flag. */
	/** 社員指定の有無 */
	@Basic(optional = false)
	@Column(name = "HAVE_EMPLOYEE_SPECIFIED_FLG")
	public int haveEmployeeSpecifiedFlg;
	
	/** The employee Id. */
	/** 実行者 */
	@Basic(optional = false)
	@Column(name = "SID")
	public String sId;
	
	/** The supplement explanation. */
	/**  補足説明 */
	@Basic(optional = true)
	@Column(name = "SUPPLEMENT_EXPLANATION")
	public String supplementExplanation;
	
	/** The reference date.*/
	/** 基準日 */
	@Basic(optional = true)
	@Column(name = "REFERENCE_DATE")
	public GeneralDate referenceDate;
	
	/** The execution date time. */
	/** 実行日時 */
	@Basic(optional = false)
	@Column(name = "EXECUTION_DATE_TIME")
	public GeneralDateTime executionDateTime;
	
	/** The start date of daily. */
	/** 日次削除開始日 */
	@Basic(optional = false)
	@Column(name = "START_DATE_OF_DAILY")
	public GeneralDate startDateOfDaily;
	
	/** The end date of daily. */
	/** 日次削除終了日 */
	@Basic(optional = false)
	@Column(name = "END_DATE_OF_DAILY")
	public GeneralDate endDateOfDaily;
	
	
	/** The start month of monthly. */
	/** 月次削除開始月  */
	@Basic(optional = false)
	@Column(name = "START_MONTH_OF_MONTHLY")
	public GeneralDate startMonthOfMonthly;
	
	/** The end month of monthly. */
	/** 月次削除終了月  */
	@Basic(optional = false)
	@Column(name = "END_MONTH_OF_MONTHLY")
	public GeneralDate endMonthOfMonthly;
	
	
	/** The start year of monthly. */
	/** 年次開始年  */
	@Basic(optional = false)
	@Column(name = "START_YEAR_OF_MONTHLY")
	public int startYearOfMonthly;
	
	
	/** The end year of monthly. */
	/** 年次終了年  */
	@Basic(optional = false)
	@Column(name = "END_YEAR_OF_MONTHLY")
	public int endYearOfMonthly;
	

	@Override
	protected Object getKey() {
		return sspdtManualSetDeletionPK;
	}

	public ManualSetDeletion toDomain() {
		boolean isSaveBeforeDeleteFlg = this.isSaveBeforeDeleteFlg == 1;
		boolean isExistCompressPassFlg = this.isExistCompressPassFlg == 1;
		boolean haveEmployeeSpecifiedFlg = this.haveEmployeeSpecifiedFlg == 1;
		return ManualSetDeletion.createFromJavatype(this.sspdtManualSetDeletionPK.delId, this.companyID, this.systemType, 
				this.delName, isSaveBeforeDeleteFlg, isExistCompressPassFlg, this.passwordCompressFileEncrypt,
				haveEmployeeSpecifiedFlg, this.sId, this.supplementExplanation, this.referenceDate,
				this.executionDateTime, this.startDateOfDaily, this.endDateOfDaily,
				this.startMonthOfMonthly, this.endMonthOfMonthly, this.startYearOfMonthly, this.endYearOfMonthly);
	}

	public static SspdtManualSetDeletion toEntity(ManualSetDeletion manualSetting) {
		int isSaveBeforeDeleteFlg = manualSetting.isSaveBeforeDeleteFlg() ? 1 : 0;
		int isExistCompressPassFlg = manualSetting.isExistCompressPassFlg() ? 1 : 0;
		int isHaveEmployeeSpecifiedFlg = manualSetting.isHaveEmployeeSpecifiedFlg() ? 1 : 0;
		
		return new SspdtManualSetDeletion(new SspdtManualSetDeletionPK(manualSetting.getDelId()),
				manualSetting.getCompanyId(), manualSetting.getSystemType(), manualSetting.getDelName().v(), isSaveBeforeDeleteFlg,
				isExistCompressPassFlg, manualSetting.getPasswordCompressFileEncrypt().v(), isHaveEmployeeSpecifiedFlg, 
				manualSetting.getSId(), manualSetting.getSupplementExplanation().v(), manualSetting.getReferenceDate(), 
				manualSetting.getExecutionDateTime(), manualSetting.getStartDateOfDaily(), manualSetting.getEndDateOfDaily(),
				manualSetting.getStartMonthOfMonthly(), manualSetting.getEndMonthOfMonthly(), 
				manualSetting.getStartYearOfMonthly(), manualSetting.getEndYearOfMonthly());
	}
}
