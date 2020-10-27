package nts.uk.ctx.at.shared.infra.entity.remainingnumber.spLea.basicInfo;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name="KRCMT_HDSP_BASIC")
public class KrcmtHdspBasic extends ContractUkJpaEntity {
	
	@EmbeddedId
	public KrcmtHdspBasicPK key;
    
	@Column(name = "CID")
    public String cID;
	
    @Column(name = "USE_ATR")
    public int useCls;
    
    @Column(name = "APPLICATION_SET")
    public int appSetting;
    
    @Column(name = "GRANT_DATE")
    public GeneralDate grantDate;
    
    @Column(name = "GRANTED_DAYS")
    public Integer grantNumber;
    
    @Column(name = "GRANT_TABLE")
    public String grantTable;

	@Override
	protected Object getKey() {
		return key;
	}
    

}
