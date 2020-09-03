package nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
 * The persistent class for the KFNMT_RPT_WK_ATD_OUT database table.
 * 
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="KFNMT_RPT_WK_ATD_OUT")
public class KfnmtRptWkAtdOut extends UkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@Column(name = "LAYOUT_ID")
	@Basic(optional = false)
	private String layoutID;

	@Column(name="EXCLUS_VER")
	private String exclusVer;

	@Basic(optional = false)
	@Column(name="CONTRACT_CD")
	private String contractCD;
	
	@Basic(optional = false)
	@Column(name="ITEM_SEL_TYPE")
	private int itemSelType;
	
	@Basic(optional = false)
	@Column(name="CID")
	private String cid;
	
	@Column(name="SID")
	private String sid;
	
	@Basic(optional = false)
	@Column(name="EXPORT_CD")
	private String exportCD;
	
	@Basic(optional = false)
	@Column(name="NAME")
	private String name;
	
	@Basic(optional = false)
	@Column(name="SEAL_USE_ATR")
	private boolean sealUseAtr;
	
	@Basic(optional = false)
	@Column(name="NAME_USE_ATR")
	private int nameUseAtr;
	
	@Basic(optional = false)
	@Column(name="CHAR_SIZE_TYPE")
	private int charSizeType;
	
	@Basic(optional = false)
	@Column(name="MONTH_APP_DISP_ATR")
	private int monthAppDispAtr;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.layoutID;
	}

}