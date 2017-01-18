package nts.uk.ctx.basic.infra.entity.system.bank;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author sonnh
 *
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="CBKMT_BANK")
public class CbkmtBank {
  @EmbeddedId
  public CbkmtBankPK cbkmtBankPK;
  
  @Column(name="BANK_NAME")
  public String bankName;
  
  @Column(name="BANK_KN_NAME")
  public String bankKnName;
  
  @Column(name="MEMO")
  public String memo;
}
