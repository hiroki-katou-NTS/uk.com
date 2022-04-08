package nts.uk.ctx.at.shared.infra.entity.employeeworkway.medicalworkstyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.MedicalCareWorkStyle;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiCode;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "KSHMT_MEDICAL_WORK_STYLE")
@AllArgsConstructor
@NoArgsConstructor
public class KshmtMedicalWorkStyle extends ContractCompanyUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KshmtMedicalWorkStylePk pk;

	/** 開始日 **/
	@Column(name = "START_DATE")
	public GeneralDate startDate;

	/** 終了日 **/
	@Column(name = "END_DATE")
	public GeneralDate endDate;

	/** 夜勤専従者か **/
	@Column(name = "IS_ONLY_NIGHT_SHIFT")
	public boolean isOnlyNightShift;

	/** 医療介護勤務形態 **/
	@Column(name = "WORK_STYLE")
	public int workStyle;

	/** 看護区分 **/
	@Column(name = "NURSE_LICENSE_CD")
	public String nurseLicenseCd;

	/** 他部署兼務か **/
	@Column(name = "CONCURRENT_POST")
	public Boolean concurrentPost;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KshmtMedicalWorkStyle(KshmtMedicalWorkStylePk pk, GeneralDate startDate, GeneralDate endDate) {
		super();
		this.pk = pk;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public KshmtMedicalWorkStyle(KshmtMedicalWorkStylePk pk, boolean isOnlyNightShift, int workStyle,
			String nurseLicenseCd, boolean concurrentPost) {
		super();
		this.pk = pk;
		this.isOnlyNightShift = isOnlyNightShift;
		this.workStyle = workStyle;
		this.nurseLicenseCd = nurseLicenseCd;
		this.concurrentPost = concurrentPost;
	}

	public static KshmtMedicalWorkStyle toEntityMedicalWorkStyle(EmpMedicalWorkStyleHistory his,
			EmpMedicalWorkStyleHistoryItem hisItem) {
		KshmtMedicalWorkStyle kshmtMedicalWorkStyle = new KshmtMedicalWorkStyle();
		KshmtMedicalWorkStylePk pk = new KshmtMedicalWorkStylePk(his.getEmpID(), hisItem.getHistoryID());
		if (his.getEmpID().equals(hisItem.getEmpID())) {
			Optional<DateHistoryItem> optional = his.getListDateHistoryItem().stream()
					.filter(predicate -> predicate.identifier() == hisItem.getHistoryID()).findFirst();
			if (optional.isPresent()) {
				kshmtMedicalWorkStyle = new KshmtMedicalWorkStyle(
						pk, 
						optional.get().span().start(),
						optional.get().span().end(), 
						hisItem.isOnlyNightShift(),
						hisItem.getMedicalWorkStyle().value,
						hisItem.getNurseClassifiCode().v(),
						hisItem.isConcurrently());
			}
		}
		return kshmtMedicalWorkStyle;
	}

	public EmpMedicalWorkStyleHistoryItem toDomainHisItem() {	
		EmpMedicalWorkStyleHistoryItem domain  = new EmpMedicalWorkStyleHistoryItem(
				this.getPk().getSid(),
				this.getPk().getHistId(),
				new NurseClassifiCode(this.nurseLicenseCd),
				this.isOnlyNightShift ,
				EnumAdaptor.valueOf(this.workStyle, MedicalCareWorkStyle.class),
				this.concurrentPost);
		return domain;
	}
	
	public  EmpMedicalWorkStyleHistory toDomainHis(){
		DateHistoryItem dateHistoryItem = new DateHistoryItem(this.pk.getHistId(), new DatePeriod(this.startDate,this.endDate ));				
		List<DateHistoryItem> listDateHistoryItem = new ArrayList<DateHistoryItem>(Arrays.asList(dateHistoryItem));
		EmpMedicalWorkStyleHistory domain =  new EmpMedicalWorkStyleHistory(this.pk.getSid(), listDateHistoryItem);
		return domain;
	}
}
