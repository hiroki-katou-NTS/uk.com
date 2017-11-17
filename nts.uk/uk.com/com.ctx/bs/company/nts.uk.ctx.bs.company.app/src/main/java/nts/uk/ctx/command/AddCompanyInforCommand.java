package nts.uk.ctx.command;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class AddCompanyInforCommand {
	// 会社コード
	private String ccd;  

	/** The company code. */
	// 会社名
	private String name;

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The start month. */
	// 期首月
	private int month;

	/** The Abolition */
	// 廃止区分
	private int abolition;

	/** 代表者名 */
	private String repname;
	
	/** 代表者職位 */
	private String repJob;
	
	/** 会社名カナ */
	private String comNameKana;
	
	/** 会社略名 */
	private String shortComName;
	
	/** 契約コード */
	private String contractCd;
	
	/** 法人マイナンバー */
	private BigDecimal taxNo;
	
	private AddInforCommand addinfor;
}
