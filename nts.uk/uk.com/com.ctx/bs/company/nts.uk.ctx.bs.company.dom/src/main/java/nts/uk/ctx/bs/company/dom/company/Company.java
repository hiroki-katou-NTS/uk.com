package nts.uk.ctx.bs.company.dom.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.gul.text.StringUtil;
import nts.uk.ctx.bs.company.dom.company.primitive.ABName;
import nts.uk.ctx.bs.company.dom.company.primitive.ContractCd;
import nts.uk.ctx.bs.company.dom.company.primitive.KNName;
import nts.uk.ctx.bs.company.dom.company.primitive.RepJob;
import nts.uk.ctx.bs.company.dom.company.primitive.RepName;
import nts.uk.ctx.bs.company.dom.company.primitive.TaxNo;
/**
 * 会社情報
 * @author yennth
 *
 */
@Getter  
@AllArgsConstructor
@NoArgsConstructor
public class Company extends AggregateRoot {
	/** The company code. */
	// 会社コード
	private CompanyCode companyCode;

	/** The company code. */
	// 会社名
	private Name companyName;

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The start month. */
	// 期首月
	private MonthStr startMonth;

	/** The Abolition */
	// 廃止区分
	private AbolitionAtr isAbolition;

	/** 代表者名 */
	private RepName repname;

	/** 代表者職位 */
	private RepJob repjob;

	/** 会社名カナ */
	private KNName comNameKana;

	/** 会社略名 */
	private ABName shortComName;

	/** 契約コード */
	private ContractCd contractCd;

	/** 法人マイナンバー */
	private TaxNo taxNo;
	
	/** 住所情報 */
	private AddInfor addInfor;

	public Company(CompanyCode companyCode, Name companyName, MonthStr startMonth, AbolitionAtr isAbolition,
			RepName repname, RepJob repjob, KNName comNameKana, ABName shortComName, ContractCd contractCd, TaxNo taxNo,
			AddInfor addInfor) {
		super();
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.startMonth = startMonth;
		this.isAbolition = isAbolition;
		this.repname = repname;
		this.repjob = repjob;
		this.comNameKana = comNameKana;
		this.shortComName = shortComName;
		this.contractCd = contractCd;
		this.taxNo = taxNo;
		this.addInfor = addInfor;
		this.companyId = createCompanyId(this.companyCode.v(), this.contractCd.v());
	}
	
	public static Company createFromJavaType(String companyCode, String companyName, int startMonth,
			int isAbolition, String repname, String repjob, String comNameKana, String shortComName, String contractCd,
			String taxNo, AddInfor addInfor) {
		return new Company(new CompanyCode(companyCode), new Name(companyName),
				EnumAdaptor.valueOf(startMonth, MonthStr.class),
				EnumAdaptor.valueOf(isAbolition, AbolitionAtr.class), 
				!StringUtil.isNullOrEmpty(repname, true) ? new RepName(repname) : new RepName(""), 
				!StringUtil.isNullOrEmpty(repjob, true) ? new RepJob(repjob) : new RepJob(""),
				new KNName(comNameKana), 
				new ABName(shortComName), new ContractCd(contractCd), 
				taxNo != null ? new TaxNo(taxNo) : null,
				addInfor != null ? addInfor : null);
	}

	/**
	 * create companyId  with company code + "-" + contractCd
	 * company code received from UI
	 * contract code received when login
	 * @param companyCode
	 * @param contractCd
	 * @return
	 */
	public static String createCompanyId(String companyCode, String contractCd) {
		return contractCd + "-" + companyCode;
	}

	@Override
	public void validate() {
		super.validate();
		// company code: 0000
		if("0000".equals(this.companyCode.v())){
			throw new BusinessException("Msg_809");
		}
	}
	/** check number company discarded, can't discard all list company */
	public void checkAbolition(Boolean isChecked){
		if(isChecked){ 
			throw new BusinessException("Msg_810");
		}		
	}
	/** if company be discarded: true-1: be discarded, false-0: be not discarded*/
	public boolean isAbolition() {
		return AbolitionAtr.ABOLITION == this.isAbolition;
	}
	
	// 	[1] 暦の年月を指定して、年度を取得する
	
	public Year getYearBySpecifying(YearMonth yearMonth) {
		Year year = null;
		
		if(yearMonth.month() > this.startMonth.value ){
			year = new Year(yearMonth.year());
		}else {
			year = new Year(yearMonth.previousYear().v());
		}
		
		return year;
	}
	
	// [2]年度の期間を取得
	public YearMonthPeriod getPeriodTheYear (int year) {
		
		YearMonth yearStart = YearMonth.of(year, this.startMonth.value);
		YearMonth yearEnd = YearMonth.of(yearStart.nextYear().year(), this.startMonth.value);
		
		YearMonthPeriod result = new YearMonthPeriod(yearStart, yearEnd.previousMonth());
		
		return result;
	}
}
