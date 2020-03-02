package nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerpath;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.Career;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JHCMT_CAREER_PATH_CAREER")
public class JhcmtCareerPathCareer extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JhcmtCareerPathCareerPK PK_JHCMT_CAREER_PATH_CAREER;

	@Column(name = "CAREER_TYPE_ITEM")
	public String careerTypeItem;

	@Column(name = "LEVEL")
	public Integer level;

	@Column(name = "CAREER_CLASS_ITEM")
	public String careerClassItem;

	@Column(name = "CAREER_CLASS_ROLE")
	public String careerClassRole;

	@OneToMany(mappedBy = "careerRequirementList", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "JHCMT_CAREER_PATH_REQ")
	public List<JhcmtCareerPathReq> careerRequirementList;

	@Override
	public Object getKey() {
		return PK_JHCMT_CAREER_PATH_CAREER;
	}

	public Career toDomain() {

		return Career.createFromJavaType(
				this.careerTypeItem, 
				this.level, 
				this.careerClassItem,
				Optional.ofNullable(this.careerClassRole),
				this.careerRequirementList.stream().map(c -> c.toDomain()).collect(Collectors.toList()));
	}
}
