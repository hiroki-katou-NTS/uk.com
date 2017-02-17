package nts.uk.ctx.basic.dom.system.bank.linebank;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.StringUtil;
import nts.uk.ctx.basic.dom.company.CompanyCode;
import nts.uk.shr.com.primitive.Memo;

/**
 * 
 * @author sonnh1
 *
 */
public class LineBank extends AggregateRoot {
	@Getter
	private nts.uk.ctx.basic.dom.company.CompanyCode companyCode;
	@Getter
	private AccountAtr accountAtr;
	@Getter
	private AccountNo accountNo;
	@Getter
	private String bankCode;
	@Getter
	private String branchCode;
	@Getter
	private List<Consignor> consignor;
	@Getter
	private LineBankCode lineBankCode;
	@Getter
	private LineBankName lineBankName;
	@Getter
	private Memo memo;
	@Getter
	private RequesterName requesterName;

	public LineBank(CompanyCode companyCode, AccountAtr accountAtr, AccountNo accountNo, String bankCode,
			String branchCode, LineBankCode lineBankCode, LineBankName lineBankName, Memo memo,
			RequesterName requesterName) {
		super();
		this.companyCode = companyCode;
		this.accountAtr = accountAtr;
		this.accountNo = accountNo;
		this.bankCode = bankCode;
		this.branchCode = branchCode;
		this.lineBankCode = lineBankCode;
		this.lineBankName = lineBankName;
		this.memo = memo;
		this.requesterName = requesterName;
	}

	// convert to type of LineBank
	public static LineBank createFromJavaType(String companyCode, int accountAtr, String accountNo, String bankCode,
			String branchCode, String lineBankCode, String lineBankName, String memo, String requesterName) {
		if (StringUtil.isNullOrEmpty(lineBankCode, true) || StringUtil.isNullOrEmpty(lineBankName, true) || StringUtil.isNullOrEmpty(accountNo, true)) {
			throw new BusinessException("�ｼ翫′蜈･蜉帙＆繧後※縺�縺ｾ縺帙ｓ縲�");//ER001
		}
		
		if(StringUtil.isNullOrEmpty(bankCode, true)||StringUtil.isNullOrEmpty(branchCode, true)){
			throw new BusinessException("�ｼ翫′驕ｸ謚槭＆繧後※縺�縺ｾ縺帙ｓ縲�");//ER007
		}
		
		return new LineBank(new CompanyCode(companyCode), EnumAdaptor.valueOf(accountAtr, AccountAtr.class),
				new AccountNo(accountNo), bankCode, branchCode, new LineBankCode(lineBankCode),
				new LineBankName(lineBankName), new Memo(memo), new RequesterName(requesterName));
	}

	public void createConsignorFromJavaType(String code1, String memo1, String code2, String memo2, String code3,
			String memo3, String code4, String memo4, String code5, String memo5) {
		this.consignor = new ArrayList<>();
		consignor.add(new Consignor(new ConsignorCode(code1), new ConsignorMemo(memo1)));
		consignor.add(new Consignor(new ConsignorCode(code2), new ConsignorMemo(memo2)));
		consignor.add(new Consignor(new ConsignorCode(code3), new ConsignorMemo(memo3)));
		consignor.add(new Consignor(new ConsignorCode(code4), new ConsignorMemo(memo4)));
		consignor.add(new Consignor(new ConsignorCode(code5), new ConsignorMemo(memo5)));
	}

	// check condition
	@Override
	public void validate() {
		super.validate();

	}
}
