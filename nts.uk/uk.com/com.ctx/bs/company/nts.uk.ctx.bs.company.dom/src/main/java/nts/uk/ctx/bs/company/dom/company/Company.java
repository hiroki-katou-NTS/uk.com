package nts.uk.ctx.bs.company.dom.company;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
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
	private Optional<RepName> repname;

	/** 代表者職位 */
	private Optional<RepJob> repjob;

	/** 会社名カナ */
	private Optional<KNName> comNameKana;

	/** 会社略名 */
	private Optional<ABName> shortComName;

	/** 契約コード */
	private ContractCd contractCd;

	/** 法人マイナンバー */
	private Optional<TaxNo> taxNo;
	
	/** 住所情報 */
	private Optional<AddInfor> addInfor;

	public Company(CompanyCode companyCode, Name companyName, MonthStr startMonth,
			AbolitionAtr isAbolition, Optional<RepName> repname, Optional<RepJob> repjob, Optional<KNName> comNameKana,
			Optional<ABName> shortComName, ContractCd contractCd, Optional<TaxNo> taxNo, Optional<AddInfor> addInfor) {
		super();
		this.contractCd = contractCd;
		this.companyCode = companyCode;
		this.companyName = companyName;
		this.companyId = createCompanyId(this.companyCode.v(), this.contractCd.v());
		this.startMonth = startMonth;
		this.isAbolition = isAbolition;
		this.repname = repname;
		this.repjob = repjob;
		this.comNameKana = comNameKana;
		this.shortComName = shortComName;
		this.taxNo = taxNo;
		this.addInfor = addInfor;
	}
	
	public static Company createFromJavaType(String companyCode, String companyName, int startMonth,
			int isAbolition, String repname, String repjob, String comNameKana, String shortComName, String contractCd,
			String taxNo, AddInfor addInfor) {
		return new Company(new CompanyCode(companyCode), new Name(companyName),
				EnumAdaptor.valueOf(startMonth, MonthStr.class),
				EnumAdaptor.valueOf(isAbolition, AbolitionAtr.class), 
				!StringUtil.isNullOrEmpty(repname, true) ? Optional.of(new RepName(repname)) : Optional.of(new RepName("")), 
				!StringUtil.isNullOrEmpty(repjob, true) ? Optional.of(new RepJob(repjob)) : Optional.of(new RepJob("")),
				Optional.of(new KNName(comNameKana)), 
				Optional.of(new ABName(shortComName)),
				new ContractCd(contractCd), 
				taxNo != null ? Optional.of(new TaxNo(taxNo)) : Optional.empty(),
				addInfor != null ? Optional.of(addInfor) : Optional.empty());
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

}
