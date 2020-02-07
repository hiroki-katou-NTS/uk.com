package nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ValPayDateSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 支払日の設定の規定値
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_VAL_PAY_DATE_SET")
public class QpbmtValPayDateSet extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public QpbmtValPayDateSetPk valPayDateSetPk;

	

	/**
	 * 支払日
	 */
	@Basic(optional = false)
	@Column(name = "PAYMENT_DATE")
	public int datePayMent;

	/**
	 * 参照月
	 */
	@Basic(optional = false)
	@Column(name = "REFE_MONTH")
	public int refeMonth;

	/**
	 * 参照日
	 */
	@Basic(optional = false)
	@Column(name = "REFE_DATE")
	public int refeDate;

	/**
	 * 処理月
	 */
	@Basic(optional = false)
	@Column(name = "PROCESS_MONTH")
	public int processMonth;

	/**
	 * 処理月
	 */
	@Basic(optional = false)
	@Column(name = "DISPOSAL_DAY")
	public int disposalDay;

	/**
	 * 要勤務日数
	 */
	@Basic(optional = false)
	@Column(name = "WORK_DAY")
	public BigDecimal workDay;
	
	/**
	 * 印字月
	 */
	@Basic(optional = false)
	@Column(name = "PRINTING_MONTH")
	public int printingMonth;

	/**
	 * 徴収月
	 */
	@Basic(optional = false)
	@Column(name = "MONTH_COLLECTED")
	public int socialInsuColleMonth;

	/**
	 * 基準日
	 */
	@Basic(optional = false)
	@Column(name = "INCOME_REFE_DATE")
	public int incomeRefeDate;

	/**
	 * 基準月
	 */
	@Basic(optional = false)
	@Column(name = "INCOME_BASE_MONTH")
	public int incomeBaseMonth;

	/**
	 * 基準日
	 */
	@Basic(optional = false)
	@Column(name = "INCOME_BASE_YEAR")
	public int incomeBaseYear;

	/**
	 * 基準月
	 */
	@Basic(optional = false)
	@Column(name = "SOCI_INSU_BASE_MONTH")
	public int sociInsuBaseMonth;

	/**
	 * 基準年
	 */
	@Basic(optional = false)
	@Column(name = "SOCI_INSU_BASE_YEAR")
	public int sociInsuBaseYear;

	/**
	 * 基準日
	 */
	@Basic(optional = false)
	@Column(name = "SOCI_INSU_REFE_DATE")
	public int sociInsuRefeDate;

	/**
	 * 勤怠締め日
	 */
	@Basic(optional = false)
	@Column(name = "TIME_CLOSE_DATE")
	public int timeCloseDate;

	/**
	 * 基準月
	 */
	@Basic(optional = true)
	@Column(name = "CLOSE_DATE_BASE_MONTH")
	public Integer closeDateBaseMonth;

	/**
	 * 基準年
	 */
	@Basic(optional = true)
	@Column(name = "CLOSE_DATE_BASE_YEAR")
	public Integer closeDateBaseYear;

	/**
	 * 基準日
	 */
	@Basic(optional = true)
	@Column(name = "CLOSE_DATE_REFE_DATE")
	public Integer closeDateRefeDate;

	/**
	 * 基準月
	 */
	@Basic(optional = false)
	@Column(name = "EMP_BASE_MONTH")
	public Integer empBaseMonth;

	/**
	 * 基準日
	 */
	@Basic(optional = false)
	@Column(name = "EMP_REFER_DATE")
	public Integer empReferDate;

	@Override
	protected Object getKey() {
		return valPayDateSetPk;
	}

	public ValPayDateSet toDomain() {
		return new ValPayDateSet(valPayDateSetPk.cid, valPayDateSetPk.processCateNo, processMonth, disposalDay,
				refeMonth, refeDate, datePayMent, workDay, printingMonth, socialInsuColleMonth, empReferDate,
				empBaseMonth, incomeRefeDate, incomeBaseYear, incomeBaseMonth, sociInsuBaseMonth, sociInsuBaseYear,
				sociInsuRefeDate, timeCloseDate, closeDateBaseMonth, closeDateBaseYear, closeDateRefeDate);

		//return new ValPayDateSet(valPayDateSetPk.cid, valPayDateSetPk.processCateNo, datePayMent)
	}

	public static QpbmtValPayDateSet toEntity(ValPayDateSet domain) {
		return new QpbmtValPayDateSet(new QpbmtValPayDateSetPk(domain.getCid(), domain.getProcessCateNo()),
				domain.getBasicSetting().getMonthlyPaymentDate().getDatePayMent().value,
				domain.getBasicSetting().getEmployeeExtractionReferenceDate().getRefeMonth().value,
				domain.getBasicSetting().getEmployeeExtractionReferenceDate().getRefeDate().value,
				domain.getBasicSetting().getAccountingClosureDate().getProcessMonth().value,
				domain.getBasicSetting().getAccountingClosureDate().getDisposalDay().value,
				domain.getBasicSetting().getWorkDay().v(),
				domain.getAdvancedSetting().getItemPrintingMonth().getPrintingMonth().value,
				domain.getAdvancedSetting().getSocialInsuColleMon().getMonthCollected().value,

				domain.getAdvancedSetting().getIncomTaxBaseYear().getInComRefeDate().value,
				domain.getAdvancedSetting().getIncomTaxBaseYear().getInComBaseMonth().value,
				domain.getAdvancedSetting().getIncomTaxBaseYear().getInComBaseYear().value,




				domain.getAdvancedSetting().getSociInsuStanDate().getSociInsuBaseMonth().value,
				domain.getAdvancedSetting().getSociInsuStanDate().getSociInsuBaseYear().value,
				domain.getAdvancedSetting().getSociInsuStanDate().getSociInsuRefeDate().value,
				domain.getAdvancedSetting().getCloseDate().getTimeCloseDate().value,
				domain.getAdvancedSetting().getCloseDate().getCloseDateBaseMonth().map(i -> i.value).orElse(null),
				domain.getAdvancedSetting().getCloseDate().getCloseDateBaseYear().map(i -> i.value).orElse(null),
				domain.getAdvancedSetting().getCloseDate().getCloseDateRefeDate().map(i -> i.value).orElse(null),
				domain.getAdvancedSetting().getEmpInsurStanDate().getEmpInsurBaseMonth().value,
				domain.getAdvancedSetting().getEmpInsurStanDate().getEmpInsurRefeDate().value
		);





//				domain.getBasicSetting().getAccountingClosureDate().getProcessMonth().value,
//				domain.getBasicSetting().getAccountingClosureDate().getDisposalDay().value,
//				domain.getBasicSetting().getEmployeeExtractionReferenceDate().getRefeMonth().value,
//				domain.getBasicSetting().getEmployeeExtractionReferenceDate().getRefeDate().value,
//				domain.getBasicSetting().getMonthlyPaymentDate().getDatePayMent().value,
//				domain.getBasicSetting().getWorkDay().v(),
//				domain.getAdvancedSetting().getItemPrintingMonth().getPrintingMonth().value,
//				domain.getAdvancedSetting().getSocialInsuColleMon().getMonthCollected().value,
//				domain.getAdvancedSetting().getEmpInsurStanDate().getEmpInsurRefeDate().value,
//				domain.getAdvancedSetting().getEmpInsurStanDate().getEmpInsurBaseMonth().value,
//				domain.getAdvancedSetting().getIncomTaxBaseYear().getInComRefeDate().value,
//				domain.getAdvancedSetting().getIncomTaxBaseYear().getInComBaseYear().value,
//				domain.getAdvancedSetting().getIncomTaxBaseYear().getInComBaseMonth().value,
//				domain.getAdvancedSetting().getSociInsuStanDate().getSociInsuBaseMonth().value,
//				domain.getAdvancedSetting().getSociInsuStanDate().getSociInsuBaseYear().value,
//				domain.getAdvancedSetting().getSociInsuStanDate().getSociInsuRefeDate().value,
//				domain.getAdvancedSetting().getCloseDate().getTimeCloseDate(),
//				domain.getAdvancedSetting().getCloseDate().getCloseDateBaseMonth().map(i -> i.value).orElse(null),
//				domain.getAdvancedSetting().getCloseDate().getCloseDateBaseYear().map(i -> i.value).orElse(null),
//				domain.getAdvancedSetting().getCloseDate().getCloseDateRefeDate().map(i -> i.value).orElse(null));
	}

}
