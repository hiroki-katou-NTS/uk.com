package nts.uk.ctx.command;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.bs.company.dom.company.AddInfor;
import nts.uk.ctx.bs.company.dom.company.CompanyInforNew;

/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class UpdateCompanyInforCommand {
	
	// 会社コード
		private String ccd;

		/** The company code. */
		// 会社名
		private String name;

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
		
		private CompanyInforNew toDomain(String contractCode) {
			AddInfor add = null; 
			if(this.getAddinfor() != null){
				add = this.getAddinfor().toDomainAdd(CompanyInforNew.createCompanyId(ccd, contractCode));
			}
			CompanyInforNew company =  CompanyInforNew.createFromJavaType(this.getCcd(), this.getName(), 
					this.getMonth(), 
					this.getAbolition(), this.getRepname(),
					this.getRepJob(), this.getComNameKana(), 
					this.getShortComName(), contractCd, 
					this.getTaxNo(), add);
			
			return company;
		}
}
