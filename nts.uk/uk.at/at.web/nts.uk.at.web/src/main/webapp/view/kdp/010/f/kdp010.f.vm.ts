module nts.uk.at.view.kdp010.f {
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import info = nts.uk.ui.dialog.info;
    export module viewmodel {
        export class ScreenModel {
            settingsUsingEmbossing = new SettingsUsingEmbossing();
            callBack: any;
            constructor(callBackFuntion?: any){
                let self = this;
                if(callBackFuntion){
                    self.callBack = callBackFuntion;
                }
            }
            start(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                block.grayout();
                service.getData().done(function(data) {
                    if (data) {
                        self.settingsUsingEmbossing.update(data);
                        self.callBack(data);
                    }
                    dfd.resolve();
                }).fail(function (res) {
                    info({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
                return dfd.promise();
            }
            save(){
                let self = this;
                block.grayout();
                service.save(ko.toJS(self.settingsUsingEmbossing)).done(function(data) {
                    self.callBack(ko.toJS(self.settingsUsingEmbossing));
                    $("#removeActive").removeClass("active");
                    $("#tabpanel-a").addClass("disappear");
                    info({ messageId: "Msg_15"});
                }).fail(function (res) {
                    info({ messageId: res.messageId });
                }).always(function () {
                    block.clear();
                });
            }
            
        }
        
        export class SettingsUsingEmbossing {
            name_selection: KnockoutObservable<boolean> = ko.observable(false);
            finger_authc: KnockoutObservable<boolean> = ko.observable(false);
            ic_card: KnockoutObservable<boolean> = ko.observable(false);
            indivition: KnockoutObservable<boolean> = ko.observable(false);
            portal: KnockoutObservable<boolean> = ko.observable(false);
            smart_phone: KnockoutObservable<boolean> = ko.observable(false);
            constructor(){
                let self = this;
            }
            update(data?:any){
                let self = this;
                if(data){
                    self.name_selection(data.name_selection);
                    self.finger_authc(data.finger_authc);
                    self.ic_card(data.ic_card);
                    self.indivition(data.indivition);
                    self.portal(data.portal);
                    self.smart_phone(data.smart_phone);
                }
            }
        }
    }
    
}