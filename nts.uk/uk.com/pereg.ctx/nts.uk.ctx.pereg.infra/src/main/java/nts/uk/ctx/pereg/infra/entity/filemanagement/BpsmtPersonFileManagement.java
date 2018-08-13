package nts.uk.ctx.pereg.infra.entity.filemanagement;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pereg.dom.filemanagement.PersonFileManagement;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BPSMT_PER_FILE_MANAGEMENT")
public class BpsmtPersonFileManagement extends UkJpaEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public BpsmtPersonFileManagementPK bsymtPerFileManagementPK;
	
	/** 個人ID */
	@Basic(optional = false)
	@Column(name = "PID")
	public String pid;
	
	/** ファイルの種類 */
	@Basic(optional = true)
	@Column(name = "FILE_TYPE")
	public int filetype;
	
	/** 表示順*/
	@Basic(optional = true)
	@Column(name = "DISPORDER")
	public Integer disPOrder;
		
	@Override
	protected Object getKey() {
		return bsymtPerFileManagementPK;
	}


	public BpsmtPersonFileManagement updateFromDomain(PersonFileManagement domain) {
		this.filetype = domain.getTypeFile().value;
		this.disPOrder = domain.getUploadOrder();
		return this;
	}
}
