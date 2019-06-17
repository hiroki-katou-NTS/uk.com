package nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerpath;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.CareerRequirement;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.MasterItem;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.MasterRequirement;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.RequirementType;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.YearRequirement;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name = "JHCMT_CAREER_PATH_REQ")
public class JhcmtCareerPathReq extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JhcmtCareerPathReqPK PK_JHCMT_CAREER_PATH_REQUIREMENT;

	@Column(name = "CID")
	public String companyID;

	@Column(name = "HIST_ID")
	public String histId;

	@Column(name = "REQ_TYPE")
	public Integer reqType;

	@Column(name = "YEAR_TYPE")
	public Integer yearType;

	@Column(name = "YEAR_MIN_NUM")
	public Integer yearMinNum;

	@Column(name = "YEAR_STD_NUM")
	public Integer yearStdNum;

	@Column(name = "MASTER_TYPE")
	public String masterType;

	@Column(name = "INPUT_REQ")
	public String inputReq;

	@OneToMany(mappedBy = "masterItemList", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "JHCMT_CAREER_PATH_REQITEM")
	public List<JhcmtCareerPathReqitem> masterItemList;

	@Override
	public Object getKey() {
		return PK_JHCMT_CAREER_PATH_REQUIREMENT;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false),
			@JoinColumn(name = "CAREER_ID", referencedColumnName = "CAREER_ID", insertable = false, updatable = false) })
	public JhcmtCareerPathCareer careerRequirementList;

	public JhcmtCareerPathReq(CareerRequirement domain, String companyID, String histId, String careerId) {
		super();
		this.PK_JHCMT_CAREER_PATH_REQUIREMENT = new JhcmtCareerPathReqPK(careerId, domain.getDisplayNumber().v());
		this.companyID = companyID;
		this.histId = histId;
		this.reqType = domain.getRequirementType().value;
		this.yearType = domain.getYearRequirement().isPresent()?domain.getYearRequirement().get().getYearType().value:null;
		this.yearMinNum = domain.getYearRequirement().isPresent()?domain.getYearRequirement().get().getYearMinimumNumber().v():null;
		this.yearStdNum = domain.getYearRequirement().isPresent()?domain.getYearRequirement().get().getYearStandardNumber().v():null;
		this.masterType = domain.getMasterRequirement().isPresent()?domain.getMasterRequirement().get().getMasterType():null;
		this.inputReq = domain.getInputRequirement().isPresent()?domain.getInputRequirement().get().v():null;
		this.masterItemList = domain.getMasterRequirement().isPresent()?
				domain.getMasterRequirement().get().getMasterItemList().stream().map(c->new JhcmtCareerPathReqitem(companyID, careerId, domain.getDisplayNumber().v(), c.getMasterItemId().orElse(null), c.getMasterItemCd(),c.getMasterItemName())).collect(Collectors.toList())
				:new ArrayList<>();
	}
	
	public CareerRequirement toDomain() {
		Optional<YearRequirement> yearReq = Optional.empty();
		Optional<MasterRequirement> masterReq = Optional.empty();
		if(this.reqType == RequirementType.YEARS.value) {
			yearReq = Optional.ofNullable(YearRequirement.createFromJavaType(this.yearType, this.yearMinNum, this.yearStdNum));
		}else if(this.reqType == RequirementType.SELECT_FROM_MASTER.value) {
			masterReq = Optional.ofNullable(new MasterRequirement(this.masterType, this.masterItemList.stream().map(c -> new MasterItem(Optional.ofNullable(c.masterItemId),c.masterItemCd,c.masterItemName)).collect(Collectors.toList())));
		}
		return CareerRequirement.createFromJavaType(
				this.PK_JHCMT_CAREER_PATH_REQUIREMENT.dispNum, 
				this.reqType, 
				yearReq, 
				masterReq, 
				Optional.ofNullable(this.inputReq));
	}
}
