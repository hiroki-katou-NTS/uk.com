/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktype;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.infra.entity.worktype.language.KshmtWorkTypeLanguage;
import nts.uk.ctx.at.shared.infra.entity.worktype.worktypedisporder.KshmtWorkTypeOrder;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name="KSHMT_WKTP")
@AllArgsConstructor
@NoArgsConstructor
public class KshmtWorkType extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/*主キー*/
	@EmbeddedId
    public KshmtWorkTypePK kshmtWorkTypePK;
	
	/*勤務種類記号名*/
	@Column(name = "SYNAME")
	public String symbolicName;
	
	/*勤務種類名称*/
	@Column(name = "NAME")
	public String name;
	
	/*勤務種類略名*/
	@Column(name = "ABNAME")
	public String abbreviationName;
	
	/*勤務種類備考*/
	@Column(name = "MEMO")
	public String memo;
		
	/*廃止区分*/
	@Column(name = "ABOLISH_ATR")
	public int deprecateAtr;
	
	/*勤務区分*/
	@Column(name = "WORK_ATR")
	public int worktypeAtr;
	
	/*1日*/
	@Column(name = "ONE_DAY_CLS")
	public int oneDayAtr;
	
	/*午前*/
	@Column(name = "MORNING_CLS")
	public int morningAtr;
	
	/*午後*/
	@Column(name = "AFTERNOON_CLS")
	public int afternoonAtr;
	
	/*出勤率の計算*/
    @Column(name = "CALC_METHOD")
    public int calculatorMethod;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy="workType", orphanRemoval = true, fetch = FetchType.LAZY)
    public List<KshmtWorkTypeSet> worktypeSetList;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy="workType", orphanRemoval = true, fetch = FetchType.LAZY)
    public KshmtWorkTypeOrder kshmtWorkTypeOrder;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="workType", orphanRemoval = true, fetch = FetchType.LAZY)
    public List<KshmtWorkTypeLanguage> workTypeLanguage;
    
	@Override
	protected Object getKey() {
		return kshmtWorkTypePK;
	}
}
