package nts.uk.ctx.bs.company.dom.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.company.dom.company.primitive.ComNameKana;
import nts.uk.ctx.bs.company.dom.company.primitive.ContractCd;
import nts.uk.ctx.bs.company.dom.company.primitive.RepName;
import nts.uk.ctx.bs.company.dom.company.primitive.RepPos;
import nts.uk.ctx.bs.company.dom.company.primitive.ShortComName;
@Getter
@AllArgsConstructor
public class CompanyInforNew extends AggregateRoot{
	/** The company code. */
	// 会社コード
	private CompanyCode companyCode;

	/** The company code. */
	// 会社名
	private CompanyName companyName;

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The start month. */
	// 期首月
	private StartMonthAtr startMonth;

	/** The Abolition */
	// 廃止区分
	private IsAbolition isAbolition;

	/** 代表者名 */
	private RepName repname;
	
	/** 代表者職位 */
	private RepPos repost;
	
	/** 会社名カナ */
	private ComNameKana comNameKana;
	
	/** 会社略名 */
	private ShortComName shortComName;
	
	/** 契約コード */
	private ContractCd contractCd;
	
	/** 法人マイナンバー */
	private String taxNum;
	private AddInfor addInfor;
	
	public static CompanyInforNew createFromJavaType(String companyCode, String companyName, 
			String companyId, int startMonth, int isAbolition,
			String repname, String repost, 
			String comNameKana, String shortComName,
			String contractCd, String taxNum, AddInfor addInfor) {
				return new CompanyInforNew(new CompanyCode(companyCode), 
				new CompanyName(companyName), 
				new CompanyId(companyId),
				EnumAdaptor.valueOf(startMonth, StartMonthAtr.class),
				EnumAdaptor.valueOf(isAbolition, IsAbolition.class),
				new RepName(repname),
				new RepPos(repost),
				new ComNameKana(comNameKana),
				new ShortComName(shortComName),
				new ContractCd(contractCd),
				taxNum, addInfor);
				}
	
	@Override
	public void validate(){
		super.validate();
	}
}
