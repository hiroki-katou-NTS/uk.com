package nts.uk.ctx.basic.infra.entity.system.bank.branch;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnh
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class CbkmtBranchPK {
   @Column(name="CCD")
   public String companyCode;
   
   @Column(name="BANK_CD")
   public String bankCode;
   
   @Column(name="BRANCH_CD")
   public String branchCode;
}
