package nts.uk.ctx.sys.env.dom.useatr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * システム利用設定
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SysUsageSet extends AggregateRoot{
	@Setter
	/**会社ID**/
	private String companyId;
	
	// 会社コード
	private CCD companyCode;
	
	/** 契約コード */
	private ContractCd contractCd;
	
	/** 人事システム **/
	private Jinji jinji;
	/** 就業システム **/
	private ShuGyo shugyo;
	/** 給与システム **/
	private Kyuyo kyuyo;
	public static SysUsageSet createFromJavaType(String companyCd, String contractCd, 
													int jinji, int shugyo, int kyuyo){
		return new SysUsageSet(new CCD(companyCd),
								new ContractCd(contractCd),
							EnumAdaptor.valueOf(jinji, Jinji.class),
							EnumAdaptor.valueOf(shugyo, ShuGyo.class),
							EnumAdaptor.valueOf(kyuyo, Kyuyo.class));
	}
	
	public static String createCompanyId(String companyCode, String contractCd) {
		return contractCd + "-" + companyCode;
	}
	
	@Override
	public void validate(){
		super.validate();
	}

	public SysUsageSet(CCD companyCode, ContractCd contractCd, Jinji jinji, ShuGyo shugyo, Kyuyo kyuyo) {
		super();
		this.companyCode = companyCode;
		this.contractCd = contractCd;
		this.jinji = jinji;
		this.shugyo = shugyo;
		this.kyuyo = kyuyo;
		this.companyId = createCompanyId(this.companyCode.v(), this.contractCd.v());
	}
}
