package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import lombok.Builder;
import lombok.Data;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionId;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dto ユーザー情報の使用方法
 */
@Data
@Builder
public class UserInfoUseMethod_Dto implements UserInfoUseMethod_.MementoSetter, UserInfoUseMethod_.MementoGetter {
    /**
     * お知らせの利用
     */
    private Integer useOfNotice;

    /**
     * パスワードの利用
     */
    private Integer useOfPassword;

    /**
     * プロフィールの利用
     */
    private Integer useOfProfile;

    /**
     * 言語の利用
     */
    private Integer useOfLanguage;

    /**
     * 会社ID
     */
    private String companyId;

    /**
     * メール送信先機能
     */
    private List<EmailDestinationFunctionDto> emailDestinationFunctions;

    /**
     * 連絡先情報の設定
     */
    private SettingContactInformationDto settingContactInformation;

    @Override
    public void setEmailDestinationFunctions(List<EmailDestinationFunction> emailDestinationFunctions) {
        this.emailDestinationFunctions = emailDestinationFunctions.stream()
                .map(item -> EmailDestinationFunctionDto.builder()
                        .emailClassification(item.getEmailClassification().value)
                        .functionIds(
                                item.getFunctionIds().stream().map(PrimitiveValueBase::v).collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void setSettingContactInformation(SettingContactInformation settingContactInformation) {
        SettingContactInformationDto build = SettingContactInformationDto.builder().build();
        settingContactInformation.setMemento(build);
        this.settingContactInformation = build;
    }

    @Override
    public List<EmailDestinationFunction> getEmailDestinationFunctions() {
        return this.emailDestinationFunctions.stream()
                .map(item -> EmailDestinationFunction.builder()
                        .emailClassification(EmailClassification.valueOf(item.getEmailClassification()))
                        .functionIds(
                                item.getFunctionIds().stream().map(FunctionId::new).collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public SettingContactInformation getSettingContactInformation() {
        return SettingContactInformation.createFromMemento(this.settingContactInformation);
    }
}