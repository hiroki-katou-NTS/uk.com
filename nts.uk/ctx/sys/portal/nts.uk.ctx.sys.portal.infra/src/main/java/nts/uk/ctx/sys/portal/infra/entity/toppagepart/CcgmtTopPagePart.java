package nts.uk.ctx.sys.portal.infra.entity.toppagepart;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.portal.infra.entity.flowmenu.CcgmtFlowMenu;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author LamDT
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CCGMT_TOPPAGE_PART")
public class CcgmtTopPagePart extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public CcgmtTopPagePartPK ccgmtTopPagePartPK;

	@Column(name = "CODE")
	public String code;

	@Column(name = "NAME")
	public String name;

	@Column(name = "TOPPAGE_PART_TYPE")
	public int topPagePartType;

	@Column(name = "WIDTH")
	public int width;

	@Column(name = "HEIGHT")
	public int height;

	@OneToOne(fetch=FetchType.EAGER)
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="TOPPAGE_PART_ID", referencedColumnName="TOPPAGE_PART_ID"),
		@PrimaryKeyJoinColumn(name="CID", referencedColumnName="CID")
	})
	@JoinTable(name="CCGMT_FLOWMENU")
	public CcgmtFlowMenu ccgmtFlowMenu;
	
	@Override
	protected Object getKey() {
		return ccgmtTopPagePartPK;
	}

}