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
@Table(name = "WWFST_JOBTITLE_SEARCH_SET")
@AllArgsConstructor
@NoArgsConstructor
public class WwfstJobtitleSearchSet  extends ContractUkJpaEntity implements Serializable{
	/**主キー*/
	@EmbeddedId
	public WwfstJobtitleSearchSetPK wwfstJobtitleSearchSetPK;
	
	/**サーチ設定*/
	@Column(name = "SEARCH_SET_FLG")
	public int searchSetFlg;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@Override
	protected Object getKey() {
		return wwfstJobtitleSearchSetPK;
	}

}
