package nts.uk.ctx.pr.transfer.dom.rsdttaxpayee;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.transfer.dom.bank.AccountNumber;
import nts.uk.shr.com.primitive.Memo;

import java.util.Optional;

/**
 * 住民税納付先
 */
@Getter
public class ResidentTaxPayee extends AggregateRoot {

    /**
     * 会社ID
     */
    private String cid;

    /**
     * コード
     */
    private ResidentTaxPayeeCode code;

    /**
     * 名称
     */
    private ResidentTaxPayeeName name;

    /**
     * カナ名
     */
    private Optional<ResidentTaxPayeeKanaName> kanaName;

    /**
     * 都道府県
     */
    private int prefectures;

    /**
     * とりまとめ局
     */
    private CompileStation compileStation;

    /**
     * 加入者名
     */
    private Optional<ResidentTaxPayeeSubscriberName> subscriberName;

    /**
     * 口座番号
     */
    private Optional<AccountNumber> accountNumber;

    /**
     * 報告先コード
     */
    @Setter
    private Optional<ResidentTaxPayeeCode> reportCd;

    /**
     * 指定番号
     */
    private Optional<ResidentTaxPayeeDesignationNum> designationNum;

    /**
     * 報告先指定番号
     */
    private Optional<ResidentTaxPayeeDesignationNum> reportNum;

    /**
     * メモ
     */
    private Optional<Memo> note;

    public ResidentTaxPayee(String cid, String code, String name, String kanaName, int prefectures, String compileStationName,
                            String compileStationZipCd, String subcriberName, String accountNum,
                            String reportCd, String designationNum, String reportNum, String note) {
        this.cid = cid;
        this.code = new ResidentTaxPayeeCode(code);
        this.name = new ResidentTaxPayeeName(name);
        this.kanaName = kanaName == null || kanaName.isEmpty() ? Optional.empty() : Optional.of(new ResidentTaxPayeeKanaName(kanaName));
        this.prefectures = prefectures;
        this.compileStation = new CompileStation(compileStationName, compileStationZipCd);
        this.subscriberName = subcriberName == null || subcriberName.isEmpty() ? Optional.empty() : Optional.of(new ResidentTaxPayeeSubscriberName(subcriberName));
        this.accountNumber = accountNum == null || accountNum.isEmpty() ? Optional.empty() : Optional.of(new AccountNumber(accountNum));
        this.reportCd = reportCd == null || reportCd.isEmpty() ? Optional.empty() : Optional.of(new ResidentTaxPayeeCode(reportCd));
        this.designationNum = designationNum == null || designationNum.isEmpty() ? Optional.empty() : Optional.of(new ResidentTaxPayeeDesignationNum(designationNum));
        this.reportNum = reportNum == null || reportNum.isEmpty() ? Optional.empty() : Optional.of(new ResidentTaxPayeeDesignationNum(reportNum));
        this.note = note == null || note.isEmpty() ? Optional.empty() : Optional.of(new Memo(note));
    }
}
