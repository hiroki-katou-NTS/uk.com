package nts.uk.ctx.pr.core.dom.payment.banktranfer;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.paymentdata.PayBonusAtr;
import nts.uk.ctx.pr.core.dom.paymentdata.SparePayAtr;
import nts.uk.shr.com.primitive.PersonId;

@Getter
public class BankTranfer extends AggregateRoot {
	private PersonId personId;
	private CompanyCode companyCode;
	private String companyNameKana;
	private String departmentCode;
	private BankInfo fromBank;
	private BankInfo toBank;
	private GeneralDate paymentDate;
	private PayBonusAtr paymentBonusAtr;
	private PayMoney paymentMoney;
	private int processingNo;
	private YearMonth yearMonth;
	private SparePayAtr sparePaymentAtr;
		
	/**
	 * convert java type to domain type
	 * @param personId person Id
	 * @param companyCode company Code
	 * @param companyNameKana company Name Kana
	 * @param departmentCode department Code
	 * @param paymentDate payment Date
	 * @param paymentBonusAtr payment Bonus Attribute
	 * @param paymentMoney payment Money
	 * @param processingNo processingNo
	 * @param yearMonth year Month
	 * @param sparePaymentAtr spare Payment Attribute
	 * @return BankTranfer
	 */
	public static BankTranfer createFromJavaType(
			String personId, String companyCode, String companyNameKana, String departmentCode, GeneralDate paymentDate, int paymentBonusAtr,
			BigDecimal paymentMoney, int processingNo, int yearMonth, int sparePaymentAtr) {
		return new BankTranfer(
				new PersonId(personId),
				new CompanyCode(companyCode), 
				companyNameKana, 
				departmentCode, 
				null,
				null,
				paymentDate,
				EnumAdaptor.valueOf(paymentBonusAtr, PayBonusAtr.class),
				new PayMoney(paymentMoney),
				processingNo,
				new YearMonth(yearMonth),
				EnumAdaptor.valueOf(sparePaymentAtr, SparePayAtr.class)
				);
	}

	/**
	 * Constructor with full parameter
	 * @param personId person Id
	 * @param companyCode company Code
	 * @param companyNameKana company Name Kana
	 * @param departmentCode department Code
	 * @param fromBank from bank information
	 * @param toBank to bank information
	 * @param paymentDate payment Date
	 * @param paymentBonusAtr payment Bonus Attribute
	 * @param paymentMoney payment Money
	 * @param processingNo processingNo
	 * @param yearMonth year Month
	 * @param sparePaymentAtr spare Payment Attribute
	 */
	public BankTranfer(PersonId personId, CompanyCode companyCode, String companyNameKana, String departmentCode,
			BankInfo fromBank, BankInfo toBank, GeneralDate paymentDate, PayBonusAtr paymentBonusAtr,
			PayMoney paymentMoney, int processingNo, YearMonth yearMonth, SparePayAtr sparePaymentAtr) {
		super();
		this.personId = personId;
		this.companyCode = companyCode;
		this.companyNameKana = companyNameKana;
		this.departmentCode = departmentCode;
		this.fromBank = fromBank;
		this.toBank = toBank;
		this.paymentDate = paymentDate;
		this.paymentBonusAtr = paymentBonusAtr;
		this.paymentMoney = paymentMoney;
		this.processingNo = processingNo;
		this.yearMonth = yearMonth;
		this.sparePaymentAtr = sparePaymentAtr;
	}

	/**
	 * Create from bank information
	 * @param accountAtr account Attribute
	 * @param accountNameKana account Name Kana
	 * @param accountNo account No
	 * @param bankCode bank Code
	 * @param bankNameKana bank Name Kana
	 * @param branchCode branch Code
	 * @param branchNameKana branch Name Kana
	 */
	public void fromBank(int accountAtr, String accountNameKana, String accountNo, String bankCode, String bankNameKana, String branchCode, String branchNameKana) {
		this.fromBank = createBank(accountAtr, accountNameKana, accountNo, bankCode, bankNameKana, branchCode, branchNameKana);
	}
	
	/**
	 * Create to bank information
	 * @param accountAtr account Attribute
	 * @param accountNameKana account Name Kana
	 * @param accountNo account No
	 * @param bankCode bank Code
	 * @param bankNameKana bank Name Kana
	 * @param branchCode branch Code
	 * @param branchNameKana branch Name Kana
	 */
	public void toBank(int accountAtr, String accountNameKana, String accountNo, String bankCode, String bankNameKana, String branchCode, String branchNameKana) {
		this.toBank = createBank(accountAtr, accountNameKana, accountNo, bankCode, bankNameKana, branchCode, branchNameKana);
	}

	/**
	 * Create bank information
	 * @param accountAtr account Attribute
	 * @param accountNameKana account Name Kana
	 * @param accountNo account No
	 * @param bankCode bank Code
	 * @param bankNameKana bank Name Kana
	 * @param branchCode branch Code
	 * @param branchNameKana branch Name Kana
	 * @return BankInfo
	 */
	private BankInfo createBank(int accountAtr, String accountNameKana, String accountNo, String bankCode, String bankNameKana, String branchCode, String branchNameKana) {
		return new BankInfo(accountAtr, accountNameKana, accountNo, bankCode, bankNameKana, branchCode, branchNameKana);
	}

}
