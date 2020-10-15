package nts.uk.ctx.at.request.infra.entity.setting.company.emailset;

import java.util.Arrays;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.primitive.PrimitiveValue;
import nts.uk.ctx.at.request.dom.setting.company.emailset.AppEmailSet;
import nts.uk.ctx.at.request.dom.setting.company.emailset.Division;
import nts.uk.ctx.at.request.dom.setting.company.emailset.EmailContent;
import nts.uk.ctx.at.request.dom.setting.company.emailset.EmailSubject;
import nts.uk.ctx.at.request.dom.setting.company.emailset.EmailText;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KRQMT_APP_MAIL")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KrqmtAppMail extends ContractUkJpaEntity {
    @Id
    @Column(name = "CID")
    private String companyId;

    @Column(name = "URL_EMBEDDED")
    private int urlEmbedded;

    @Column(name = "SUBJECT_OT_INSTRUCTION ")
    private String subjectOTInstruction;

    @Column(name = "CONTENT_OT_INSTRUCTION")
    private String contentOTInstruction;

    @Column(name = "SUBJECT_HD_INSTRUCTION ")
    private String subjectHDInstruction;

    @Column(name = "CONTENT_HD_INSTRUCTION ")
    private String contentHDInstruction;

    @Column(name = "SUBJECT_APPROVAL")
    private String subjectApproval;

    @Column(name = "CONTENT_APPROVAL")
    private String contentApproval;

    @Column(name = "SUBJECT_REMAND")
    private String subjectRemand;

    @Column(name = "CONTENT_REMAND")
    private String contentRemand;

    public AppEmailSet toDomain() {
        return new AppEmailSet(
                companyId,
                EnumAdaptor.valueOf(urlEmbedded, NotUseAtr.class),
                Arrays.asList(
                        new EmailContent(
                                Division.APPLICATION_APPROVAL,
                                subjectApproval == null ? Optional.empty() : Optional.of(new EmailSubject(subjectApproval)),
                                contentApproval == null ? Optional.empty() : Optional.of(new EmailText(contentApproval))
                        ),
                        new EmailContent(
                                Division.REMAND,
                                subjectRemand == null ? Optional.empty() : Optional.of(new EmailSubject(subjectRemand)),
                                contentRemand == null ? Optional.empty() : Optional.of(new EmailText(contentRemand))
                        ),
                        new EmailContent(
                                Division.OVERTIME_INSTRUCTION,
                                subjectOTInstruction == null ? Optional.empty() : Optional.of(new EmailSubject(subjectOTInstruction)),
                                contentOTInstruction == null ? Optional.empty() : Optional.of(new EmailText(contentOTInstruction))
                        ),
                        new EmailContent(
                                Division.HOLIDAY_WORK_INSTRUCTION,
                                subjectHDInstruction == null ? Optional.empty() : Optional.of(new EmailSubject(subjectHDInstruction)),
                                contentHDInstruction == null ? Optional.empty() : Optional.of(new EmailText(contentHDInstruction))
                        )
                )
        );
    }

    public static KrqmtAppMail fromDomain(AppEmailSet domain) {
        KrqmtAppMail entity = new KrqmtAppMail();
        entity.companyId = domain.getCompanyID();
        entity.urlEmbedded = domain.getUrlReason().value;
        domain.getEmailContentLst().forEach(email -> {
            switch(email.getDivision()) {
                case APPLICATION_APPROVAL:
                    entity.subjectApproval = email.getOpEmailSubject().map(PrimitiveValue::v).orElse(null);
                    entity.contentApproval = email.getOpEmailText().map(PrimitiveValue::v).orElse(null);
                    break;
                case REMAND:
                    entity.subjectRemand = email.getOpEmailSubject().map(PrimitiveValue::v).orElse(null);
                    entity.contentRemand = email.getOpEmailText().map(PrimitiveValue::v).orElse(null);
                    break;
                case OVERTIME_INSTRUCTION:
                    entity.subjectOTInstruction = email.getOpEmailSubject().map(PrimitiveValue::v).orElse(null);
                    entity.contentOTInstruction = email.getOpEmailText().map(PrimitiveValue::v).orElse(null);
                    break;
                case HOLIDAY_WORK_INSTRUCTION:
                    entity.subjectHDInstruction = email.getOpEmailSubject().map(PrimitiveValue::v).orElse(null);
                    entity.contentHDInstruction = email.getOpEmailText().map(PrimitiveValue::v).orElse(null);
                    break;
            }
        });
        return entity;
    }

    public void update(AppEmailSet domain) {
        this.urlEmbedded = domain.getUrlReason().value;
        domain.getEmailContentLst().forEach(email -> {
            switch(email.getDivision()) {
                case APPLICATION_APPROVAL:
                    this.subjectApproval = email.getOpEmailSubject().map(PrimitiveValue::v).orElse(null);
                    this.contentApproval = email.getOpEmailText().map(PrimitiveValue::v).orElse(null);
                    break;
                case REMAND:
                    this.subjectRemand = email.getOpEmailSubject().map(PrimitiveValue::v).orElse(null);
                    this.contentRemand = email.getOpEmailText().map(PrimitiveValue::v).orElse(null);
                    break;
                case OVERTIME_INSTRUCTION:
                    this.subjectOTInstruction = email.getOpEmailSubject().map(PrimitiveValue::v).orElse(null);
                    this.contentOTInstruction = email.getOpEmailText().map(PrimitiveValue::v).orElse(null);
                    break;
                case HOLIDAY_WORK_INSTRUCTION:
                    this.subjectHDInstruction = email.getOpEmailSubject().map(PrimitiveValue::v).orElse(null);
                    this.contentHDInstruction = email.getOpEmailText().map(PrimitiveValue::v).orElse(null);
                    break;
            }
        });
    }

    @Override
    protected Object getKey() {
        return companyId;
    }
}
