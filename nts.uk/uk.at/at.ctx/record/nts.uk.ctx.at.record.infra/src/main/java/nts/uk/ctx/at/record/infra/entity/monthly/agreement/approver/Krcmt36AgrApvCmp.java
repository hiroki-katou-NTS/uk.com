package nts.uk.ctx.at.record.infra.entity.monthly.agreement.approver;

import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompany;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * エンティティ：会社別の承認者（36協定）
 *
 * @author khai.dh
 */
@Entity
@Table(name = "KRCMT_36AGR_APV_CMP")
@NoArgsConstructor
public class Krcmt36AgrApvCmp extends UkJpaEntity implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@NotNull
	@Column(name = "CONTRACT_CD")
	public String ccd;

	@EmbeddedId
	public Krcmt36AgrApvCmpPK pk;

	@Basic(optional = false)
	@NotNull
	@Column(name = "END_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate endDate;

	@Basic(optional = false)
	@NotNull
	@Column(name = "APV_SID_1")
	public String approverSid1;

	@Column(name = "APV_SID_2")
	public String approverSid2;

	@Column(name = "APV_SID_3")
	public String approverSid3;

	@Column(name = "APV_SID_4")
	public String approverSid4;

	@Column(name = "APV_SID_5")
	public String approverSid5;

	@Column(name = "CFM_SID_1")
	public String confirmerSid1;

	@Column(name = "CFM_SID_2")
	public String confirmerSid2;

	@Column(name = "CFM_SID_3")
	public String confirmerSid3;

	@Column(name = "CFM_SID_4")
	public String confirmerSid4;

	@Column(name = "CFM_SID_5")
	public String confirmerSid5;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public Approver36AgrByCompany toDomain() {
		List<String> approverIds = new ArrayList<>();
		approverIds.add(this.approverSid1);
		if (!StringUtil.isNullOrEmpty(this.approverSid2, true)) approverIds.add(this.approverSid2);
		if (!StringUtil.isNullOrEmpty(this.approverSid3, true)) approverIds.add(this.approverSid3);
		if (!StringUtil.isNullOrEmpty(this.approverSid4, true)) approverIds.add(this.approverSid4);
		if (!StringUtil.isNullOrEmpty(this.approverSid5, true)) approverIds.add(this.approverSid5);

		List<String> confirmerIds = new ArrayList<>();

		if (!StringUtil.isNullOrEmpty(this.confirmerSid1, true)) confirmerIds.add(this.confirmerSid1);
		if (!StringUtil.isNullOrEmpty(this.confirmerSid2, true)) confirmerIds.add(this.confirmerSid2);
		if (!StringUtil.isNullOrEmpty(this.confirmerSid3, true)) confirmerIds.add(this.confirmerSid3);
		if (!StringUtil.isNullOrEmpty(this.confirmerSid4, true)) confirmerIds.add(this.confirmerSid4);
		if (!StringUtil.isNullOrEmpty(this.confirmerSid5, true)) confirmerIds.add(this.confirmerSid5);

		return Approver36AgrByCompany.create(
				this.pk.companyId,
				new DatePeriod(this.pk.startDate, this.endDate),
				approverIds,
				confirmerIds
		);
	}

	public static Krcmt36AgrApvCmp fromDomain(Approver36AgrByCompany domain) {
		val entity = new Krcmt36AgrApvCmp();
		List<String> approverIds = domain.getApproverList();

		entity.approverSid1 = approverIds.get(0);
		if (approverIds.size() > 1) entity.approverSid2 = approverIds.get(1);
		if (approverIds.size() > 2) entity.approverSid3 = approverIds.get(2);
		if (approverIds.size() > 3) entity.approverSid4 = approverIds.get(3);
		if (approverIds.size() > 4) entity.approverSid5 = approverIds.get(4);

		List<String> confirmerIds = domain.getConfirmerList();
		if (confirmerIds.size() > 0) entity.confirmerSid1 = confirmerIds.get(0);
		if (confirmerIds.size() > 1) entity.confirmerSid2 = confirmerIds.get(1);
		if (confirmerIds.size() > 2) entity.confirmerSid3 = confirmerIds.get(2);
		if (confirmerIds.size() > 3) entity.confirmerSid4 = confirmerIds.get(3);
		if (confirmerIds.size() > 4) entity.confirmerSid5 = confirmerIds.get(4);
		entity.endDate = domain.getPeriod().end();
		entity.pk = new Krcmt36AgrApvCmpPK(domain.getCompanyId(),domain.getPeriod().start());
		val cd = AppContexts.user().contractCode();
		entity.ccd = cd;
		return entity;
	}

	private void fromDomainNoPK(Approver36AgrByCompany domain) {
		this.ccd = AppContexts.user().contractCode();
		this.endDate = domain.getPeriod().end();

		List<String> approverIds = domain.getApproverList();
		this.approverSid1 = approverIds.get(0);
		if (approverIds.size() > 1) this.approverSid2 = approverIds.get(1);
		if (approverIds.size() > 2) this.approverSid3 = approverIds.get(2);
		if (approverIds.size() > 3) this.approverSid4 = approverIds.get(3);
		if (approverIds.size() > 4) this.approverSid5 = approverIds.get(4);

		List<String> confirmerIds = domain.getConfirmerList();
		if (confirmerIds.size() > 0) this.confirmerSid1 = confirmerIds.get(0);
		if (confirmerIds.size() > 1) this.confirmerSid2 = confirmerIds.get(1);
		if (confirmerIds.size() > 2) this.confirmerSid3 = confirmerIds.get(2);
		if (confirmerIds.size() > 3) this.confirmerSid4 = confirmerIds.get(3);
		if (confirmerIds.size() > 4) this.confirmerSid5 = confirmerIds.get(4);
	}

	@StaticMetamodel(Krcmt36AgrApvCmp.class)
	public static class Meta_ {
		public static volatile SingularAttribute<Krcmt36AgrApvCmp, Krcmt36AgrApvCmpPK> pk;
		public static volatile SingularAttribute<Krcmt36AgrApvCmp, GeneralDate> endDate;
	}
}