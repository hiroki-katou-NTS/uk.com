module nts.uk.at.view.kal002.b.viewmodel {
    export class ScreenModel {

        MailAutoAndNormalDto: MailAutoAndNormalDto;
        
        constructor() {
            var self = this;
            
        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            new service.Service().getAllMailSet().done(function(data: MailAutoAndNormalDto) {
                //console.log(data);
                if(data){
                    self.MailAutoAndNormalDto = data;
                }else{
                     nts.uk.request.jump("/view/ccg/007/b/index.xhtml");
                }
                dfd.resolve(); 
            });
            return dfd.promise();
        }

        setMailNormal(){
            var self = this;
            nts.uk.ui.windows.setShared("sendingAddressCheck", false);
            nts.uk.ui.windows.setShared("MailSettings", self.MailAutoAndNormalDto.mailSettingNormalDto.mailSettings);
            nts.uk.ui.windows.sub.modal("com","view/ccg/027/a/index.xhtml").onClosed(() => {
                let data = nts.uk.ui.windows.getShared("MailSettings");
                if (data != null) {
                    //console.log(data);
                    self.MailAutoAndNormalDto.mailSettingNormalDto.mailSettings = data;
                }
            });
        }
        
        setMailNormalAd(){
            var self = this;
            nts.uk.ui.windows.setShared("sendingAddressCheck", false);
            nts.uk.ui.windows.setShared("MailSettings", self.MailAutoAndNormalDto.mailSettingNormalDto.mailSettingAdmins);
            nts.uk.ui.windows.sub.modal("com","view/ccg/027/a/index.xhtml").onClosed(() => {
                let data = nts.uk.ui.windows.getShared("MailSettings");
                if (data != null) {
                    self.MailAutoAndNormalDto.mailSettingNormalDto.mailSettingAdmins = data;
                }
            });
        }
        
        setMailAuto(){
            var self = this;
            nts.uk.ui.windows.setShared("sendingAddressCheck", true);
            nts.uk.ui.windows.setShared("senderAddress", self.MailAutoAndNormalDto.mailSettingAutomaticDto.senderAddress)
            nts.uk.ui.windows.setShared("MailSettings", self.MailAutoAndNormalDto.mailSettingAutomaticDto.mailSettings);
            nts.uk.ui.windows.sub.modal("com","view/ccg/027/a/index.xhtml").onClosed(() => {
                let data = nts.uk.ui.windows.getShared("MailSettings");
                if (data != null) {
                    self.MailAutoAndNormalDto.mailSettingAutomaticDto.mailSettings = data;
                    self.MailAutoAndNormalDto.mailSettingAutomaticDto.senderAddress = nts.uk.ui.windows.getShared("senderAddress");
                }
            });
        }
        
        setMailAutoAd(){
            var self = this;
            nts.uk.ui.windows.setShared("sendingAddressCheck", true);
            nts.uk.ui.windows.setShared("senderAddress", self.MailAutoAndNormalDto.mailSettingAutomaticDto.senderAddress)
            nts.uk.ui.windows.setShared("MailSettings", self.MailAutoAndNormalDto.mailSettingAutomaticDto.mailSettingAdmins);
            nts.uk.ui.windows.sub.modal("com","view/ccg/027/a/index.xhtml").onClosed(() => {
                let data = nts.uk.ui.windows.getShared("MailSettings");
                if (data != null) {
                    self.MailAutoAndNormalDto.mailSettingAutomaticDto.mailSettingAdmins = data;
                    self.MailAutoAndNormalDto.mailSettingAutomaticDto.senderAddress = nts.uk.ui.windows.getShared("senderAddress");
                    //console.log(self.MailAutoAndNormalDto);
                }
            });
        }
        Registration(){
            var self = this;
            var dfd = $.Deferred();
            var dto = self.MailAutoAndNormalDto;
            
            var mailSettingNormal = new MailSettingNormalCommand(new MailSettingsCommand(dto.mailSettingNormalDto.mailSettings), 
                new MailSettingsCommand(dto.mailSettingNormalDto.mailSettingAdmins));
            
            var mailSettingAutomatic = new MailSettingAutomaticCommand(new MailSettingsCommand(dto.mailSettingAutomaticDto.mailSettings), 
                new MailSettingsCommand(dto.mailSettingAutomaticDto.mailSettingAdmins),dto.mailSettingAutomaticDto.senderAddress);
            
            var command = new MailAutoAndNormalCommand(mailSettingAutomatic, mailSettingNormal);
            console.log(command);
            new service.Service().addMailSet(command).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
            });
            dfd.resolve(); 
            return dfd.promise();
        }
        
    }
    
    export interface MailSettingsDto {
        subject?: string;
        text?: string;
        mailAddressCC: Array<string>;
        mailAddressBCC: Array<string>;
        mailRely?: string;
    }
    export interface MailSettingNormalDto {
        mailSettings?: MailSettingsDto;
        mailSettingAdmins?: MailSettingsDto;
    }
    export interface MailSettingAutomaticDto {
        mailSettings?: MailSettingsDto;
        mailSettingAdmins?: MailSettingsDto;
        senderAddress?: string;
    }
    export interface MailAutoAndNormalDto {
        mailSettingAutomaticDto: MailSettingAutomaticDto;
        mailSettingNormalDto: MailSettingNormalDto;
    }
    
    class MailSettingsCommand {
        subject: string;
        text: string;
        mailAddressCC: Array<string>;
        mailAddressBCC: Array<string>;
        mailRely: string;
        constructor(mailSettingsDto : MailSettingsDto) {
            this.subject = mailSettingsDto.subject;
            this.text = mailSettingsDto.text;
            this.mailAddressCC = mailSettingsDto.mailAddressCC;
            this.mailAddressBCC = mailSettingsDto.mailAddressBCC;
            this.mailRely = mailSettingsDto.mailRely
        }
    }
    
    class MailSettingNormalCommand {
        mailSettings: MailSettingsCommand;
        mailSettingAdmins: MailSettingsCommand;
       
        constructor(mailSettings : MailSettingsCommand, mailSettingAdmins: MailSettingsCommand) {
            this.mailSettings = mailSettings;
            this.mailSettingAdmins = mailSettingAdmins;
        }
    }
    
    class MailSettingAutomaticCommand {
        mailSettings: MailSettingsCommand;
        mailSettingAdmins: MailSettingsCommand;
        senderAddress: string;
       
        constructor(mailSettings : MailSettingsCommand, mailSettingAdmins: MailSettingsCommand, senderAddress: string) {
            this.mailSettings = mailSettings;
            this.mailSettingAdmins = mailSettingAdmins;
            this.senderAddress =  senderAddress;
        }
    }
    
    class MailAutoAndNormalCommand {
        mailSettingAutomatic: MailSettingAutomaticCommand;
        mailSettingNormal: MailSettingNormalCommand;
       
        constructor(mailSettingAutomatic: MailSettingAutomaticCommand, mailSettingNormal: MailSettingNormalCommand) {
            this.mailSettingAutomatic = mailSettingAutomatic;
            this.mailSettingNormal = mailSettingNormal;
        }
    }
    

}

