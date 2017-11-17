package nts.uk.ctx.bs.company.dom.company;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.company.dom.company.primitive.ABName;
import nts.uk.ctx.bs.company.dom.company.primitive.ContractCd;
import nts.uk.ctx.bs.company.dom.company.primitive.KNName;
import nts.uk.ctx.bs.company.dom.company.primitive.RepJob;
import nts.uk.ctx.bs.company.dom.company.primitive.RepName;
import nts.uk.ctx.bs.company.dom.company.primitive.TaxNo;
@Getter
@AllArgsConstructor
public class CompanyInforNew extends AggregateRoot{
	/** The company code. */
	// 会社コード
	private CCD companyCode;

	/** The company code. */
	// 会社名
	private Name companyName;

	/** The company id. */
	// 会社ID
	@Setter
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
	private AddInfor addInfor;
	
	public void createCompanyId(String companyCode, String contractCd){
		this.setCompanyId("contractCd" + "-" + "companyCode");
	}
	
	public static CompanyInforNew createFromJavaType(String companyCode, String companyName, 
			String companyId, int startMonth, int isAbolition,
			String repname, String repjob, 
			String comNameKana, String shortComName,
			String contractCd, BigDecimal taxNo, AddInfor addInfor) {
				return new CompanyInforNew(new CCD(companyCode), 
				new Name(companyName), 
				companyId,
				EnumAdaptor.valueOf(startMonth, MonthStr.class),
				EnumAdaptor.valueOf(isAbolition, AbolitionAtr.class),
				new RepName(repname),
				new RepJob(repjob),
				new KNName(comNameKana),
				new ABName(shortComName),
				new ContractCd(contractCd),
				new TaxNo(taxNo), 
				addInfor);
				}
	
	@Override
	public void validate(){
		super.validate();
	}
}
