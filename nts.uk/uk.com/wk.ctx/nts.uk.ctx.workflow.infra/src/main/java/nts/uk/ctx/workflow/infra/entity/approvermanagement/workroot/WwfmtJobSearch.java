package nts.uk.ctx.workflow.infra.entity.approvermanagement.workroot;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 職位別のサーチ設定
 * @author dudt
 *
 */
@Setter
@Entity
@Table(name = "WWFMT_JOB_SEARCH")
@AllArgsConstructor
@NoArgsConstructor
public class WwfmtJobSearch  extends ContractUkJpaEntity implements Serializable{
	/**主キー*/
	@EmbeddedId
	public WwfmtJobSearchPK wwfmtJobSearchPK;
	
	/**サーチ設定*/
	@Column(name = "SEARCH_SET_FLG")
	public int searchSetFlg;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@Override
	protected Object getKey() {
		return wwfmtJobSearchPK;
	}

}
