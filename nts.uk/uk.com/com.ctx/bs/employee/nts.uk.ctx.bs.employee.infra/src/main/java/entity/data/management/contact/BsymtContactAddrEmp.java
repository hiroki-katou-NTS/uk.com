package entity.data.management.contact;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.bs.employee.dom.employee.data.management.contact.EmployeeContact;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "BSYMT_CONTACT")
@EqualsAndHashCode(callSuper = true)
public class BsymtContactAddrEmp extends UkJpaEntity implements EmployeeContact.MementoGetter, EmployeeContact.MementoSetter, Serializable {

    private static final long serialVersionUID = 1L;

    // column 排他バージョン
    @Version
    @Column(name = "EXCLUS_VER")
    private long version;

    // column 契約コード
    @Basic(optional = false)
    @Column(name = "CONTRACT_CD")
    private String contractCd;

    // Embedded primary key 社員ID + 会社ID
    @EmbeddedId
    private BsymtContactAddrEmpPK bsymtContactAddrEmpPK;

    @Basic(optional = false)
    @Column(name = "CID")
    private String companyId;

    // column メールアドレス
    @Basic(optional = true)
    @Column(name = "MAIL_ADDRESS")
    private String mailAddress;

    // column メールアドレスが在席照会に表示するか
    @Basic(optional = true)
    @Column(name = "MAIL_ADDRESS_DISP")
    private Integer isMailAddressDisplay;

    // column 座席ダイヤルイン
    @Basic(optional = true)
    @Column(name = "SEAT_DAIL_IN")
    private String seatDialIn;

    // column 座席ダイヤルインが在席照会に表示するか
    @Basic(optional = true)
    @Column(name = "SEAT_DAIL_IN_DISP")
    private Integer isSeatDialInDisplay;

    // column 座席内線番号
    @Basic(optional = true)
    @Column(name = "SEAT_EXTENSION_NUMBER")
    private String seatExtensionNumber;

    // column 座席内線番号が在席照会に表示するか
    @Basic(optional = true)
    @Column(name = "SEAT_EXTENSION_NUMBER_DISP")
    private Integer isSeatExtensionNumberDisplay;

    // column 携帯メールアドレス
    @Basic(optional = true)
    @Column(name = "PHONE_MAIL_ADDRESS")
    private String mobileMailAddress;

    // column 携帯メールアドレスが在席照会に表示するか
    @Basic(optional = true)
    @Column(name = "PHONE_MAIL_ADDRESS_DISP")
    private Integer isMobileMailAddressDisplay;

    // column 携帯電話番号
    @Basic(optional = true)
    @Column(name = "PHONE_NUMBER")
    private String cellPhoneNumber;

    // column 携帯電話番号が在席照会に表示するか
    @Basic(optional = true)
    @Column(name = "PHONE_NUMBER_DISP")
    private Integer isCellPhoneNumberDisplay;


    @Override
    protected Object getKey() {
        return this.bsymtContactAddrEmpPK;
    }

    @Override
    public String getEmployeeId() {
        if (this.bsymtContactAddrEmpPK != null) {
            return this.bsymtContactAddrEmpPK.getEmployeeId();
        }
        return null;
    }

    @Override
    public void setEmployeeId(String employeeId) {
        if (this.bsymtContactAddrEmpPK == null) {
            this.bsymtContactAddrEmpPK = new BsymtContactAddrEmpPK();
        }
        this.bsymtContactAddrEmpPK.setEmployeeId(employeeId);
    }

    @Override
    public Boolean getIsMailAddressDisplay() {
        return this.isMailAddressDisplay == 1;
    }

    @Override
    public void setIsMailAddressDisplay(Boolean isMailAddressDisplay) {
        this.isMailAddressDisplay = isMailAddressDisplay ? 1 : 0;

    }

    @Override
    public Boolean getIsSeatDialInDisplay() {
        return this.isSeatDialInDisplay == 1;
    }

    @Override
    public void setIsSeatDialInDisplay(Boolean isSeatDialInDisplay) {
        this.isSeatDialInDisplay = isSeatDialInDisplay ? 1 : 0;

    }

    @Override
    public Boolean getIsSeatExtensionNumberDisplay() {
        // TODO Auto-generated method stub
        return this.isSeatExtensionNumberDisplay == 1;
    }

    @Override
    public void setIsSeatExtensionNumberDisplay(Boolean isSeatExtensionNumberDisplay) {
        this.isSeatExtensionNumberDisplay = isSeatExtensionNumberDisplay ? 1 : 0;
    }

    @Override
    public Boolean getIsMobileMailAddressDisplay() {
        // TODO Auto-generated method stub
        return this.isMobileMailAddressDisplay == 1;
    }

    @Override
    public void setIsMobileMailAddressDisplay(Boolean isMobileMailAddressDisplay) {
        this.isMobileMailAddressDisplay = isMobileMailAddressDisplay ? 1 : 0;
    }

    @Override
    public Boolean getIsCellPhoneNumberDisplay() {
        // TODO Auto-generated method stub
        return this.isCellPhoneNumberDisplay == 1;
    }

    @Override
    public void setIsCellPhoneNumberDisplay(Boolean isCellPhoneNumberDisplay) {
        this.isCellPhoneNumberDisplay = isCellPhoneNumberDisplay ? 1 : 0;
    }


}
