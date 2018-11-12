package nts.uk.ctx.pr.transfer.dom.rsdttaxpayee;

import lombok.Getter;
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
     * とりまとめ局
     */
    private CompileStation compileStation;

    /**
     * メモ
     */
    private Optional<Memo> note;

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
    private Optional<ResidentTaxPayeeCode> reportCd;

    /**
     * 指定番号
     */
    private Optional<ResidentTaxPayeeDesignationNum> designationNum;

    /**
     * 都道府県
     */
    private int prefectures;

    public ResidentTaxPayee(String cid, String code, String name, String kanaName, String compileStationName,
                            String compileStationZipCd, String note, String subcriberName, String accountNum,
                            String reportCd, String designationNum, int prefectures) {
        this.cid = cid;
        this.code = new ResidentTaxPayeeCode(code);
        this.name = new ResidentTaxPayeeName(name);
        this.kanaName = kanaName == null ? Optional.empty() : Optional.of(new ResidentTaxPayeeKanaName(kanaName));
        this.compileStation = new CompileStation(compileStationName, compileStationZipCd);
        this.note = note == null ? Optional.empty() : Optional.of(new Memo(note));
        this.subscriberName = subcriberName == null ? Optional.empty() : Optional.of(new ResidentTaxPayeeSubscriberName(subcriberName));
        this.accountNumber = accountNum == null ? Optional.empty() : Optional.of(new AccountNumber(accountNum));
        this.reportCd = reportCd == null ? Optional.empty() : Optional.of(new ResidentTaxPayeeCode(reportCd));
        this.designationNum = designationNum == null ? Optional.empty() : Optional.of(new ResidentTaxPayeeDesignationNum(designationNum));
        this.prefectures = prefectures;
    }
}
