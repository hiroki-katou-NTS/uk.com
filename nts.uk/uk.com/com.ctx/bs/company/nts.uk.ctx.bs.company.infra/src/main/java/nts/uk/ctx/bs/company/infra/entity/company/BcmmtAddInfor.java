package nts.uk.ctx.bs.company.infra.entity.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BCMMT_ADD_INFOR")
public class BcmmtAddInfor extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BcmmtAddInforPK bcmmtAddInforPK;
	
	/** FAX番号*/
	@Column(name = "FAX_NUM")
	public String faxNum;
	
	/** 住所１ */
	@Column(name = "ADD_1")
	public String add_1;

	/** 住所２ */
	@Column(name = "ADD_2")
	public String add_2;
	
	/**  住所カナ１ */
	@Column(name = "ADD_KANA_1")
	public String addKana_1;
	
	/** 住所カナ２ */
	@Column(name = "ADD_KANA_2")
	public String addKana_2;
	
	/** 郵便番号 */
	@Column(name = "POST_CD")
	public String postCd;
	
	/** 電話番号 */
	@Column(name = "PHONE_NUM")
	public String phoneNum;
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
		@JoinColumn(name = "COM_CD", referencedColumnName = "COM_CD", insertable = false, updatable = false),
		@JoinColumn(name = "CONTRACT_CD", referencedColumnName = "CONTRACT_CD", insertable = false, updatable = false)
	})
	public BcmmtCompanyInfor bcmmtCompanyInfor;
	
	@Override
	protected Object getKey() {
		return bcmmtAddInforPK;
	}
}
