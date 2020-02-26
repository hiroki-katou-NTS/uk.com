package nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerclass;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.careermgmt.careertype.MasterMapping;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JHCMT_CAREER_CLASS_MP")
public class JhcmtCareerClassMp extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JhcmtCareerClassMpPK PK_JHCMT_CAREER_CLASS_MASTER_MAPPING;

	@Column(name = "CID")
	public String cId;
	
	@Column(name = "MASTER_TYPE")
	public String masterType;

	@OneToMany(mappedBy = "itemList", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "JHCMT_CAREER_CLASS_MPITEM")
	public List<JhcmtCareerClassMpItem> itemList;

	@Override
	public Object getKey() {
		return PK_JHCMT_CAREER_CLASS_MASTER_MAPPING;
	}
	
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "CAREER_CLASS_ID", referencedColumnName = "CAREER_CLASS_ID", insertable = false, updatable = false),
			@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false) })
	public JhcmtCareerClass masterMappingList;

	public MasterMapping toDomain() {

		return MasterMapping.createFromJavaType(
				this.PK_JHCMT_CAREER_CLASS_MASTER_MAPPING.dispNum, 
				this.masterType,
				this.itemList.stream().map(c -> c.masterItem).collect(Collectors.toList()));
	}
}
