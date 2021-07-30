package nts.uk.ctx.at.shared.infra.entity.employeeworkway.medicalworkstyle;

import java.io.Serializable;
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
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
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
		KshmtMedicalWorkStyle kscmtMedicalWorkStyle = new KshmtMedicalWorkStyle();
		KshmtMedicalWorkStylePk pk = new KshmtMedicalWorkStylePk(his.getEmpID(), hisItem.getHistoryID());
		if (his.getEmpID().equals(hisItem.getEmpID())) {
			Optional<DateHistoryItem> optional = his.getListDateHistoryItem().stream()
					.filter(predicate -> predicate.identifier() == hisItem.getHistoryID()).findFirst();
			if (optional.isPresent()) {
//TODO 社員の医療勤務形態履歴項目を変更したので、修正お願いいたします。
/*				kscmtMedicalWorkStyle = new KscmtMedicalWorkStyle(pk, optional.get().span().start(),
						optional.get().span().end(), 
						hisItem.isNightShiftFullTime(),
						hisItem.getOptMedicalWorkFormInfor().isPresent()
								? hisItem.getOptMedicalWorkFormInfor().get().getMedicalCareWorkStyle().value
								: hisItem.getOpyNursingWorkFormInfor().get().getMedicalCareWorkStyle().value, 
						hisItem.getOptMedicalWorkFormInfor().isPresent()
								? hisItem.getOptMedicalWorkFormInfor().get().getNurseClassifiCode().v() : null,
						hisItem.getOptMedicalWorkFormInfor().isPresent()
								? hisItem.getOptMedicalWorkFormInfor().get().isOtherDepartmentConcurrently() : null,
										
						hisItem.getOpyNursingWorkFormInfor().isPresent()
								? hisItem.getOpyNursingWorkFormInfor().get().isAsNursingCare() : null,
						hisItem.getOpyNursingWorkFormInfor().isPresent()
								? hisItem.getOpyNursingWorkFormInfor().get().getFulltimeRemarks().v() : null,
						hisItem.getOpyNursingWorkFormInfor().isPresent()
								? hisItem.getOpyNursingWorkFormInfor().get().getNightShiftRemarks().v() : null);*/
			}
		}

		return kscmtMedicalWorkStyle;

	}

	public EmpMedicalWorkStyleHistoryItem toDomainHisItem() {
//TODO 社員の医療勤務形態履歴項目を変更したので、修正お願いいたします。		
/*		EmpMedicalWorkFormHisItem domain  = new EmpMedicalWorkFormHisItem(
				this.getPk().getSid(),
				this.getPk().getHistId(),
				this.isOnlyNightShift ,
				Optional.ofNullable( new MedicalWorkFormInfor((EnumAdaptor.valueOf(this.getMedicalCareWorkStyle(), MedicalCareWorkStyle.class)) ,new NurseClassifiCode(this.getNurseLicenseCd()),this.getMedicalConcurrentPost()) ),
				Optional.ofNullable(new NursingWorkFormInfor( (EnumAdaptor.valueOf(this.getMedicalCareWorkStyle(), MedicalCareWorkStyle.class)), this.getCareConcurrentPost() , new FulltimeRemarks(this.getCareRptNote()), new NightShiftRemarks(this.getCareNightRptNote())  ) ));
		return domain;*/
		return null;
	}
	
	public  EmpMedicalWorkStyleHistory toDomainHis(){
		DateHistoryItem dateHistoryItem = new DateHistoryItem(this.pk.getHistId(), new DatePeriod(this.startDate,this.endDate ));				
		List<DateHistoryItem> listDateHistoryItem = Arrays.asList(dateHistoryItem);
		EmpMedicalWorkStyleHistory domain =  new EmpMedicalWorkStyleHistory(this.pk.getSid(), listDateHistoryItem);
		return domain;
	}
}
