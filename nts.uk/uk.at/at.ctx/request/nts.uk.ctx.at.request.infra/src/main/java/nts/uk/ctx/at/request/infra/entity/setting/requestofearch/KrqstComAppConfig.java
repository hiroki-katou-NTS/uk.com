package nts.uk.ctx.at.request.infra.entity.setting.requestofearch;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppStampDetail;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQST_COM_APP_CONFIG")
public class KrqstComAppConfig extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**会社ID*/
	@Id
	@Column(name = "CID")
	public String companyId;	
	/**
	 * 申請時の承認者の選択
	 */
	@Column(name = "SELECT_OF_APPROVERS_FLG")
	public int selectOfApproversFlg;
		
	@OneToMany(targetEntity=KrqstComAppConfigDetail.class, cascade = CascadeType.ALL, mappedBy = "krqstComAppConfig", orphanRemoval = true)
	@JoinTable(name = "KRQST_COM_APP_CF_DETAIL")
	public List<KrqstComAppConfigDetail> krqstComAppConfigDetails;
	
	@Override
	protected Object getKey() {
		return companyId;
	}

}
