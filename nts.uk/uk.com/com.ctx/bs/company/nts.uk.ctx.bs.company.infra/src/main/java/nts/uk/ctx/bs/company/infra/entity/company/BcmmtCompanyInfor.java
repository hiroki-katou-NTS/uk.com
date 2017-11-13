package nts.uk.ctx.bs.company.infra.entity.company;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BCMMT_COM_INFOR")
public class BcmmtCompanyInfor extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public BcmmtCompanyInforPK bcmmtCompanyInforPK;
	
	/** 会社名 */
	@Column(name = "COM_NAME")
	public String companyName;
	
	/**  期首月 */
	@Column(name = "MONTH_STR")
	public int startMonth;
	
	/** 廃止区分 */
	@Column(name = "ABOLITION")
	public int isAbolition;
	
	/** 代表者名 */
	@Column(name = "REP_NAME")
	public String repname;
	
	/** 代表者職位 */
	@Column(name ="REP_POS")
	public String repost;
	
	/** 会社名カナ */
	@Column(name = "COM_NAME_KANA")
	public String comNameKana;
	
	/** 会社名カナ */
	@Column(name = "SHORT_COM_NAME")
	public String shortComName;
	
	/** 法人マイナンバー */
	@Column(name = "TAX_NUM")
	public String taxNum;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "bcmmtCompanyInfor", orphanRemoval = true)
	public BcmmtAddInfor bcmmtAddInfor;
	
	@Override
	protected Object getKey() {
		return bcmmtCompanyInforPK;
	}
}
