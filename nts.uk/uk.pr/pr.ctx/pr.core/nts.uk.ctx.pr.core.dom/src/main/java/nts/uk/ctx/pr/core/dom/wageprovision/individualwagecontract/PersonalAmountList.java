package nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 項目別金額一覧
 */
@Data
@AllArgsConstructor
public class PersonalAmountList {
    //個人金額コード
    private String personalValueCode;

    //個人別金額
    private List<PersonalAmount> individualValue;
}
